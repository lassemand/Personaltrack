package com.lma.pt.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;


public class Service {

	private static Service service = new Service();
	private final String teamValidateString = "name:";
	private final String mapValidateString = "name:image:size:";
	private final String eventValidateString = "name:map:start:end:!eventdevices:";
	private final String eventDeviceValidateString = "device:event:!locations";
	private final String deviceLocationValidateString = "latitude:longtitude:device:event:";
	private final String deviceValidateString = "identifier:name:runner";
	private final String runnerValidateString = "name:!team:username:password:!maps:";
	private DBCollection dbTeam, dbDevice, dbEvent, dbMap, dbRunner;
	private GridFS fs;

	private Service() {
		// To connect to mongodb server
		MongoClient mongoClient;
		try {
			mongoClient = new MongoClient("localhost", 27017);
			// Now connect to your databases
			DB db = mongoClient.getDB("pt");
			fs = new GridFS(db);
			dbTeam = db.getCollection("team");
			dbDevice = db.getCollection("device");
			dbEvent = db.getCollection("event");
			dbMap = db.getCollection("map");
			dbRunner = db.getCollection("runner");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private GridFSInputFile createFile(byte[] data) {
		return createFile(new ByteArrayInputStream(data), true);
	}


	private GridFSInputFile createFile(InputStream in,
			boolean closeStreamOnPersist) {
		return createFile(in, null, closeStreamOnPersist);
	}

	private GridFSInputFile createFile(InputStream in, String filename,
			boolean closeStreamOnPersist) {
		return fs.createFile(in, filename, closeStreamOnPersist);
	}

	public static Service getInstance() {
		return service;
	}

	public void removeObjects() {
		dbMap.remove(new BasicDBObject());
		dbTeam.remove(new BasicDBObject());
		dbDevice.remove(new BasicDBObject());
		dbEvent.remove(new BasicDBObject());
		dbRunner.remove(new BasicDBObject());
		fs.remove(new BasicDBObject());
	}

	private boolean validateStringFromPT(PTObject ptobject, JSONObject object) {
		String[] validateString = validateStringFromPT(ptobject);
		return validate(object, validateString);
	}

	public ObjectId insertPtObjectWithValidate(PTObject object, String s) {
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(s);
			return validateStringFromPT(object, jsonObject) ? insertPTObject(
					jsonObject, object) : null;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public ObjectId insertPTObject(JSONObject jsonObject, PTObject object)
			throws JSONException {
		DBCollection dbCollection = collectionFromPT(object);
		insertDeleteField(jsonObject);

		BasicDBObject currentObject = convertFromJSONToDBObject(jsonObject);
		dbCollection.insert(currentObject);
		return (ObjectId) currentObject.get("_id");
	}

	public String getPtObject(PTObject ptObject, String s) {
		DBCursor cursor = collectionFromPT(ptObject).find(
				new BasicDBObject().append("_id", new ObjectId(s)).append(
						"deleted", 0));
		return cursor.hasNext() ? cursor.next().toString() : "";
	}

	public boolean validateUser(String username, String password){
		BasicDBObject object = new BasicDBObject("username", username).append("password", password);
		DBCursor cursor = collectionFromPT(PTObject.RUNNER).find(object);
		return cursor.hasNext();
		
		
	}

	public String getAllPtObject(PTObject ptObject) {
		DBCursor cursor = collectionFromPT(ptObject).find(
				new BasicDBObject("deleted", 0));
		return cursorToArray(cursor);
	}
	
	public String getMap(PTObject ptObject, String s) {
		String map = getPtObject(ptObject, s);
		if (map.equals(""))
			return "";

		try {
			JSONObject object = new JSONObject(map);
			addImageToMapObject(object);
			if(object.has("image"))
			return object.toString();
		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	private void addImageToMapObject(JSONObject object) throws IOException, JSONException{
		
		GridFSDBFile file = fs.findOne(object.getString("name"));
		if(file != null){
		InputStream is = file.getInputStream();
		byte[] values = IOUtils.toByteArray(is);
		object.put("image", Base64.encode(values));
		}
	}
	
	public String multipleMaps(BasicDBObject object){
		DBObject runnersDBObject = object.append("deleted", 0);
		DBCursor cursor = collectionFromPT(PTObject.MAP).find(runnersDBObject);
		JSONArray array = new JSONArray();
		while(cursor.hasNext()){
			String cursorText = cursor.next().toString();
			try {
				JSONObject jsonObject = new JSONObject(cursorText);
				addImageToMapObject(jsonObject);
				array.put(jsonObject);
			} catch (JSONException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "";
			}
			
		}
		return array.toString();
	}
	
	public String multipleMapsNoImage(BasicDBObject object){
		DBObject runnersDBObject = object.append("deleted", 0);
		DBCursor cursor = collectionFromPT(PTObject.MAP).find(runnersDBObject);
		JSONArray array = new JSONArray();
		while(cursor.hasNext()){
			String cursorText = cursor.next().toString();
			try {
				array.put(new JSONObject(cursorText));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "";
			}
			
		}
		return array.toString();
	}
	
	public boolean mapExistsWithName(String s){
		try {
			JSONObject object = new JSONObject(s);
			DBCursor cursor = dbMap.find(new BasicDBObject("name", object.getString("name")));
			return cursor.length() > 0;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public ObjectId insertMapObject(PTObject object, String s) {

		try {

			JSONObject map = new JSONObject(s);
			if (validateStringFromPT(object, map)) {
				GridFSInputFile inputFile = createFile(Base64.decode(map
						.getString("image")));
				inputFile.setFilename(map.getString("name"));
				inputFile.save();
				map.remove("image");
				
				return insertPTObject(map, object);
			}
		} catch (JSONException | Base64DecodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public int updateMapObject(String id, String json){
		try {
			JSONObject object = new JSONObject(json);
			if(object.has("name")){
				String map = getPtObject(PTObject.MAP, id);
				JSONObject oldObject = new JSONObject(map);
				String name = oldObject.getString("name");
				GridFSDBFile file = fs.findOne(name);
				byte[] values = IOUtils.toByteArray(file.getInputStream());
				fs.remove(name);
				GridFSInputFile inputFile = createFile(values);
				inputFile.setFilename(object.getString("name"));
				inputFile.save();
			}
			return updatePtObject(PTObject.MAP, id, json);
		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}

	}

	private String cursorToArray(DBCursor cursor) {
		JSONArray array = new JSONArray();

		while (cursor.hasNext()) {
			try {
				array.put(new JSONObject(cursor.next().toString()));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return array.toString();
	}
	
	private Set<String> prepareForUpdateCheck(String[] correctValues){
		Set<String> values = new HashSet<String>();
		for(String s: correctValues){
			s.replace("!", ""); 
			values.add(s);
		}
		return values;
	}
	

	public int updatePtObject(PTObject ptObject, String id, String s) {
		JSONObject jsonObject;
		DBCollection dbCollection = collectionFromPT(ptObject);
		try {
			String[] correctValues = validateStringFromPT(ptObject);
			Set<String> value = prepareForUpdateCheck(correctValues);
			jsonObject = new JSONObject(s);
			Iterator<String> keys = jsonObject.keys();
			while (keys.hasNext()) {
				String key = keys.next();
				if(!value.contains(key)){
					jsonObject.remove(key);
				}
			}
			if(jsonObject.keys().hasNext()){
			DBObject inClause = new BasicDBObject("$set",
					convertFromJSONToDBObject(jsonObject));
			return dbCollection.update(
					new BasicDBObject("_id", new ObjectId(id)), inClause)
					.getN();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return -1;
	}
	
	public int addValuesToArray(PTObject ptObject, String json, String id, String typename){
		DBCollection dbCollection = collectionFromPT(ptObject);
		try {
			int rowCount = 0;
			JSONArray array = new JSONArray(json);
			for(int a = 0; a<array.length(); a++){
				String currentObject = (String) array.get(a);
				String addValue = "{$push:{\""+typename+"\":" + currentObject.toString() + "}}";
				BasicDBObject defaultObject = convertFromJSONToDBObject(new JSONObject(
						addValue));
				rowCount = dbCollection.update(new BasicDBObject("_id", new ObjectId(id)),  defaultObject).getN();
			}
			return rowCount;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}
	
	public int removeValuesToArray(PTObject ptObject, String json, String id, String typename){
		DBCollection dbCollection = collectionFromPT(ptObject);
		try {
			int rowCount = 0;
			JSONArray array = new JSONArray(json);
			for(int a = 0; a<array.length(); a++){
				String currentObject = (String) array.get(a);
				String addValue = "{$pull:{\""+typename+"\":" + currentObject.toString() + "}}";
				BasicDBObject defaultObject = convertFromJSONToDBObject(new JSONObject(
						addValue));
				rowCount = dbCollection.update(new BasicDBObject("_id", new ObjectId(id)),  defaultObject).getN();
			}
			return rowCount;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}

	public int deletePtObject(PTObject ptObject, String id) {
		DBCollection dbCollection = collectionFromPT(ptObject);
		JSONObject object = new JSONObject();
		deleteField(object);
		try {
			DBObject inClause = new BasicDBObject("$set",
					convertFromJSONToDBObject(object));
			return dbCollection.update(
					new BasicDBObject("_id", new ObjectId(id)), inClause)
					.getN();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}
	


	public String getPtObjectFromRelation(PTObject type, BasicDBObject object) {
		DBObject runnersDBObject = object.append("deleted", 0);
		DBCursor cursor = collectionFromPT(type).find(runnersDBObject);
		return cursorToArray(cursor);
	}
	
	
	
	public boolean runnerNotExistsWithUsername(String json){
		try {
			JSONObject object = new JSONObject(json);
			if(object.has("username")){
				return dbRunner.find(new BasicDBObject("username", object.get("username"))).count() == 0;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public int insertDeviceLocation(String object) throws JSONException {
		JSONObject myObject = new JSONObject(object);
		if (validate(myObject, deviceLocationValidateString.split(":"))) {
			String eventId = (String) myObject.remove("event");
			String deviceId = (String) myObject.remove("device");
			String json = "{$push:{\"eventdevices.$.locations\":"
					+ myObject.toString() + "}}";
			BasicDBObject defaultObject = convertFromJSONToDBObject(new JSONObject(
					json));
			HashMap<String, Object> myMap = new HashMap<String, Object>();
			myMap.put("_id", new ObjectId(eventId));
			myMap.put("eventdevices.device", deviceId);
			return dbEvent.update(new BasicDBObject(myMap), defaultObject)
					.getN();
		}
		return -1;
	}

	public int insertEventDevice(String object) throws JSONException {
		JSONObject tempJSON = new JSONObject(object);
		if (validate(tempJSON, eventDeviceValidateString.split(":"))) {
			String eventId = (String) tempJSON.remove("event");
			String json = "{$push:{eventdevices:" + tempJSON.toString() + "}}";
			BasicDBObject defaultObject = convertFromJSONToDBObject(new JSONObject(
					json));
			int number = dbEvent.update(
					new BasicDBObject("_id", new ObjectId(eventId)),
					defaultObject).getN();
			return number;
		}
		return -1;
	}

	public int removeEventDevice(String event, String device)
			throws JSONException {
		BasicDBObject searchCondition = new BasicDBObject("_id", new ObjectId(
				event));
		BasicDBObject innerInnerObject = new BasicDBObject("device", device);
		BasicDBObject innerObject = new BasicDBObject("eventdevices",
				innerInnerObject);
		BasicDBObject outerObject = new BasicDBObject("$pull", innerObject);
		return dbEvent.update(searchCondition, outerObject).getN();

	}

	public String eventsFromDevice(String device) {
		BasicDBObject innerInnerObject = new BasicDBObject("device", device);
		BasicDBObject innerObject = new BasicDBObject("$elemMatch",
				innerInnerObject);
		BasicDBObject outerObject = new BasicDBObject("eventdevices",
				innerObject);
		DBCursor cursor = dbEvent.find(outerObject);
		return cursorToArray(cursor);
	}

	private boolean validate(JSONObject object, String[] fields) {
		List<Boolean> values = new ArrayList<>();
		for (String field : fields) {
			if (field.startsWith("!")) {
				String temp = field.replace("!", "");
				if (!object.has(temp))
					try {
						object.put(temp, new JSONArray());
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
			else
				values.add(object.has(field));

		}
		insertCurrentTime(object);
		return myValidate(values);
	}

	private void insertCurrentTime(JSONObject object) {
		try {
			object.put("modified", new Date().getTime() / 1000);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void insertDeleteField(JSONObject object) {
		try {
			object.put("deleted", 0);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void deleteField(JSONObject object) {
		try {
			object.put("deleted", 1);
			object.put("deletedDay", new Date().getTime() / 1000);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private DBCollection collectionFromPT(PTObject ptObject) {
		DBCollection dbCollection = null;
		switch (ptObject) {
		case EVENT:
			dbCollection = dbEvent;
			break;
		case DEVICE:
			dbCollection = dbDevice;
			break;
		case TEAM:
			dbCollection = dbTeam;
			break;
		case RUNNER:
			dbCollection = dbRunner;
			break;
		case MAP:
			dbCollection = dbMap;
			break;
		default:
			return null;
		}
		return dbCollection;
	}

	private String[] validateStringFromPT(PTObject ptObject) {
		String[] validateString = null;
		switch (ptObject) {
		case EVENT:
			validateString = eventValidateString.split(":");
			break;
		case DEVICE:
			validateString = deviceValidateString.split(":");
			break;
		case TEAM:
			validateString = teamValidateString.split(":");
			break;
		case RUNNER:
			validateString = runnerValidateString.split(":");
			break;
		case MAP:
			validateString = mapValidateString.split(":");
			break;
		default:
			return null;
		}
		return validateString;
	}

	private BasicDBObject convertFromJSONToDBObject(JSONObject object)
			throws JSONException {
		BasicDBObject dbObject = new BasicDBObject();
		Iterator<String> iterator = object.keys();
		while (iterator.hasNext()) {
			String tempObject = iterator.next();
			Object o = object.get(tempObject);
			if (o.getClass().equals(JSONArray.class)) {
				JSONArray array = (JSONArray) o;
				Object[] subArray = new Object[array.length()];
				for (int a = 0; a < array.length(); a++) {
					Object tmpObject = array.get(a);
					if (tmpObject.getClass().equals(JSONObject.class)) {
						subArray[a] = convertFromJSONToDBObject((JSONObject) tmpObject);
					} else
						subArray[a] = tmpObject;
				}
				dbObject.append(tempObject, subArray);
			} else if (o.getClass().equals(JSONObject.class)) {
				BasicDBObject subObject = convertFromJSONToDBObject((JSONObject) o);
				dbObject.append(tempObject, subObject);
			} else
				dbObject.append(tempObject, o);
		}
		return dbObject;
	}

	private boolean myValidate(List<Boolean> values) {
		for (Boolean bool : values) {
			if (!bool)
				return false;
		}
		return true;
	}

}
