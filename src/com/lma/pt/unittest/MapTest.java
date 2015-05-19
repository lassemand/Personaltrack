package com.lma.pt.unittest;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import com.sun.org.apache.xml.internal.security.utils.Base64;

public class MapTest extends TestParent{

	private String id1, id2;
	
	public MapTest() throws NoSuchMethodException, SecurityException, JSONException{
		super();
	}
	
	
	@Test
	public void mapTest(){


		try {
			URL location = MapTest.class.getProtectionDomain().getCodeSource().getLocation();
			File fi = new File(location.getFile() + "com/lma/pt/unittest/testmap.jpg");
			byte[] fileContent = null;
			fileContent = Files.readAllBytes(fi.toPath());  
			JSONObject map = new JSONObject();
			map.put("name", "first");
			map.put("image", Base64.encode(fileContent));
			map.put("size", 10000);
			Method method = getClass().getMethod("createTest", new Class[]{String.class});
			System.out.println(map.toString());
			sendRequest(makeJSONPost(map.toString(), getPathByAddingContent("map")), createMethod(method, new Object[]{this}));
			map.put("name", "second");
			method = getClass().getMethod("createTest2", new Class[]{String.class});
			sendRequest(makeJSONPost(map.toString(), getPathByAddingContent("map")), createMethod(method, new Object[]{this}));
			method = getClass().getMethod("getAllMapsTest", new Class[]{String.class});
			sendRequest(makeJSONGet(getPathByAddingContent("map/allmaps")), createMethod(method, new Object[]{this}));
			sendRequest(makeJSONDelete(getPathByAddingContent("map?id="+id2)), null);
			method = getClass().getMethod("getAllMapsTest2", new Class[]{String.class});
			sendRequest(makeJSONGet(getPathByAddingContent("map/allmaps")), createMethod(method, new Object[]{this}));
			map.put("name", "fourth");
			map.put("size", 1000);
			method = getClass().getMethod("mapsTest", new Class[]{String.class});
			sendRequest(makeJSONPost(map.toString(), getPathByAddingContent("map")), createMethod(method, new Object[]{this}));
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	public void mapsTest(String id){
		Method method = null;
		try {
			method = getClass().getMethod("mapsTest2", new Class[]{String.class});
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sendRequest(makeJSONGet(getPathByAddingContent("map/maps?id=["+id+"]")), createMethod(method, new Object[]{this}));
	}
	
	public void mapsTest2(String array){
		try {
			JSONArray jsonArray = new JSONArray(array);
			testSize(1, jsonArray);
			testAttributeArray(0, createMapMap("third", 10000), jsonArray);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void createTest(String id){
		id1 = id;
		JSONObject mapUpdate = new JSONObject();
		try {
			mapUpdate.put("name", "third");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(id1);
		System.out.println(mapUpdate.toString());
		sendRequest(makeJSONPut(mapUpdate.toString(), getPathByAddingContent("map?id="+id)), null);
	}
	public void createTest2(String id){
		this.id2 = id;
	}
	
	public void getAllMapsTest(String info ){
		try {
			JSONArray objects = new JSONArray(info);
			testSize(2, objects);
			testAttributeArray(0, createMapMap("third", 10000), objects);
			testAttributeArray(1, createMapMap("second", 10000), objects);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void getAllMapsTest2(String info ){
		try {
			JSONArray objects = new JSONArray(info);
			testSize(1, objects);
			testAttributeArray(0, createMapMap("third", 10000), objects);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void testData(String data){
		
		try {
			JSONArray maps = new JSONArray(data);
			testSize(2, maps);
			testAttributeArray(0, createMapMap("third", 10000), maps);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Map<String, Object> createMapMap(String name, int size){
		Map<String, Object> myMap = new HashMap<String, Object>();
		myMap.put("name", name);
		myMap.put("size", size);
		return myMap;
	}
	
}
