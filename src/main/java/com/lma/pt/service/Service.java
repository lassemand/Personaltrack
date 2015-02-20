package com.lma.pt.service;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

public class Service {

	private static Service service = new Service();
	private final String teamValidateString = "name:";
	private final String mapValidateString = "name:image:";
	private final String eventValidateString = "name:map:start:end:!eventdevices:";
	private final String eventDeviceValidateString = "device:event:!locations";
	private final String deviceLocationValidateString = "latitude:longtitude:device:event:";
	private final String deviceValidateString = "identifier:name:runner";
	private final String runnerValidateString = "name:team:";
	private DBCollection dbTeam, dbDevice, dbEvent, dbDeviceLocation, dbMap, dbRunner, dbTeamRunner;
	
	private Service(){
		// To connect to mongodb server
        MongoClient mongoClient;
		try {
			mongoClient = new MongoClient( "localhost" , 27017 );
	        // Now connect to your databases
	        DB db = mongoClient.getDB("pt");
	        dbTeam = db.getCollection("team");
	        dbDevice = db.getCollection("device");
	        dbEvent = db.getCollection("event");
	        dbDeviceLocation = db.getCollection("devicelocation");
	        dbMap = db.getCollection("map");
	        dbRunner = db.getCollection("runner");
	        dbTeamRunner = db.getCollection("teamRunner");
	        
	        
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static Service getInstance(){
		return service;
	}
	
	public String getTeam(String s){
		DBCursor cursor = dbTeam.find(new BasicDBObject().append("_id", new ObjectId(s)));
		return cursor.hasNext() ? cursor.next().toString() : "";
	}
	
	public String getTeams() throws JSONException{
		DBCursor cursor = dbTeam.find();
		JSONArray array = new JSONArray();
		while(cursor.hasNext())
			array.put(new JSONObject(cursor.next().toString()));
		return array.toString();
	}
	
	public ObjectId insertTeam(String object) throws JSONException{
		
		if(validate(new JSONObject(object), teamValidateString.split(":"))){
			BasicDBObject currentObject = convertFromJSONToDBObject(new JSONObject(object));
			dbTeam.insert(currentObject);
			return (ObjectId) currentObject.get("_id");
		}
		return null;
	}
	
	public int updateTeam(String id, String object) throws JSONException{
		BasicDBObject defaultObject = convertFromJSONToDBObject(new JSONObject(object));
		if(validate(new JSONObject(object), teamValidateString.split(":")))
		return dbTeam.update(new BasicDBObject("_id", new ObjectId(id)), defaultObject).getN();
		return -1;
	}
	
	public String getMap(String s){
		DBCursor cursor = dbMap.find(new BasicDBObject().append("_id", new ObjectId(s)));
		return cursor.hasNext() ? cursor.next().toString() : "";
	}
	
	public ObjectId insertMap(String object) throws JSONException{
		if(validate(new JSONObject(object), mapValidateString.split(":")))	{
			BasicDBObject currentObject = convertFromJSONToDBObject(new JSONObject(object));
			dbMap.insert(currentObject);
			return (ObjectId) currentObject.get("_id");
		}
			
		return null;
	}
	
	public int updateMap(String id, String object) throws JSONException{
		BasicDBObject defaultObject = convertFromJSONToDBObject(new JSONObject(object));
		if(validate(new JSONObject(object), mapValidateString.split(":")))
		return dbMap.update(new BasicDBObject("_id", new ObjectId(id)), defaultObject).getN();
		return -1;
	}
	
	
	public String getEvent(String s){
		DBCursor cursor = dbEvent.find(new BasicDBObject().append("_id", new ObjectId(s)));
		if(!cursor.hasNext()){
			return "";
		}
		try {
			JSONObject object  = new JSONObject(cursor.next().toString());
			DBCursor deviceLocationCursor = dbDeviceLocation.find(new BasicDBObject("event", new ObjectId(s)));
			JSONArray deviceLocations = new JSONArray();
			while(cursor.hasNext()){
				deviceLocations.put(deviceLocationCursor.next().toString());
			}
			object.put("deviceLocations", deviceLocations.toString());
			return object.toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";

	}
	
	public ObjectId insertEvent(String object) throws JSONException{
		JSONObject currentJSON = new JSONObject(object);
		
		if(validate(currentJSON, eventValidateString.split(":")))	{
			BasicDBObject tempObject = convertFromJSONToDBObject(currentJSON);
			dbEvent.insert(tempObject);
			return (ObjectId) tempObject.get("_id");
		}
			
		return null;
	}
	
	public int updateEvent(String id, String object) throws JSONException{
		JSONObject eventJSON = new JSONObject(object);
		if(validate(eventJSON, eventValidateString.split(":"))){
			BasicDBObject defaultObject = convertFromJSONToDBObject(eventJSON);
			return dbEvent.update(new BasicDBObject("_id", new ObjectId(id)), defaultObject).getN();
		}
		
		return -1;
	}
	
	public int insertDeviceLocation(String object) throws JSONException{
		JSONObject myObject = new JSONObject(object);
		if(validate(myObject, deviceLocationValidateString.split(":"))){
		String eventId = (String) myObject.remove("event");
		String deviceId = (String) myObject.remove("device");
		String json = "{$push:{\"eventdevices.$.locations\":"+myObject.toString()+"}})";
		BasicDBObject defaultObject = convertFromJSONToDBObject(new JSONObject(json));
		HashMap<String, Object> myMap = new HashMap<String, Object>();
		myMap.put("_id", new ObjectId(eventId));
		myMap.put("eventdevices.device", deviceId);
		return dbEvent.update(new BasicDBObject(myMap), defaultObject).getN();
		}
		return -1;
	}
	
	public int insertEventDevice(String object) throws JSONException{
		JSONObject tempJSON = new JSONObject(object);
		if(validate(tempJSON, eventDeviceValidateString.split(":"))){
		String eventId = (String) tempJSON.remove("event");
		String json = "{$push:{eventdevices:"+tempJSON.toString()+"}}";
		BasicDBObject defaultObject = convertFromJSONToDBObject(new JSONObject(json));
		int number = dbEvent.update(new BasicDBObject("_id", new ObjectId(eventId)), defaultObject).getN();
		return number;
		}
		return -1;
	}
	
	public String getDevice(String s){
		DBCursor cursor = dbDevice.find(new BasicDBObject().append("_id", new ObjectId(s)));
		return cursor.hasNext() ? cursor.next().toString() : "";
	}
	
	public ObjectId insertDevice(String object) throws JSONException{
		
		if(validate(new JSONObject(object), deviceValidateString.split(":")))	{
			BasicDBObject currentObject = convertFromJSONToDBObject(new JSONObject(object));
			dbDevice.insert(currentObject);
			return (ObjectId) currentObject.get("_id");
		}
		return null;
	}
	
	public int updateDevice(String id, String object) throws JSONException{
		BasicDBObject defaultObject = convertFromJSONToDBObject(new JSONObject(object));
		if(validate(new JSONObject(object), deviceValidateString.split(":")))
		return dbDevice.update(new BasicDBObject("_id", new ObjectId(id)), defaultObject).getN();
		return -1;
	}
	
	public String getRunner(String s){
		DBCursor cursor = dbRunner.find(new BasicDBObject().append("_id", new ObjectId(s)));
		return cursor.hasNext() ? cursor.next().toString() : "";
	}
	
	public ObjectId insertRunner(String object) throws JSONException{
		
		if(validate(new JSONObject(object), runnerValidateString.split(":")))	{
			BasicDBObject currentObject = convertFromJSONToDBObject(new JSONObject(object));
			dbRunner.insert(currentObject);
			return (ObjectId) currentObject.get("_id");
		}
		return null;
	}
	
	public int updateRunner(String id, String object) throws JSONException{
		BasicDBObject defaultObject = convertFromJSONToDBObject(new JSONObject(object));
		if(validate(new JSONObject(object), runnerValidateString.split(":")))
		return dbRunner.update(new BasicDBObject("_id", new ObjectId(id)), defaultObject).getN();
		return -1;
	}
	
	private boolean validate(JSONObject object, String[] fields){
		List<Boolean> values = new ArrayList<>();
		for(String field: fields){
			if(field.startsWith("!")){
				String temp = field.replace("!", "");
				if(!object.has(temp))
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
	
	private void insertCurrentTime(JSONObject object){
		try {
			object.put("modified", new Date().getTime());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private BasicDBObject convertFromJSONToDBObject(JSONObject object) throws JSONException{
		BasicDBObject dbObject = new BasicDBObject();
		Iterator<String> iterator = object.keys();
		while(iterator.hasNext()){
			String tempObject = iterator.next();
			Object o = object.get(tempObject);	
			if(o.getClass().equals(JSONArray.class)){
				JSONArray array = (JSONArray) o;
				Object[] subArray = new Object[array.length()];
				for(int a = 0; a<array.length(); a++){
					Object tmpObject = array.get(a);
					if(tmpObject.getClass().equals(JSONObject.class)){
						subArray[a] = convertFromJSONToDBObject((JSONObject)tmpObject);
					}
					else
						subArray[a] = tmpObject;
				}
				dbObject.append(tempObject, subArray);
			}
			else if(o.getClass().equals(JSONObject.class)){
				BasicDBObject subObject = convertFromJSONToDBObject((JSONObject)o);
				dbObject.append(tempObject, subObject);
			}
			else
			dbObject.append(tempObject, o);
		}
		return dbObject;
	}
	
	private boolean myValidate(List<Boolean> values){
		for(Boolean bool: values){
			if(!bool)
				return false;
		}
		return true;
	}
	
}
