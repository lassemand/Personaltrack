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

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class EventTest extends TestParent{

	private String mapId, eventId1, eventId2, teamId, runnerId, deviceId1, deviceId2;
	
	public EventTest(){
		super();
	}
	
	@Test
	public void eventTest(){
		try {
			initialize();
			JSONObject event = new JSONObject();
			event.put("name", "event1");
			event.put("map", mapId);
			event.put("start", 1000);
			event.put("end", 10000);
			JSONObject event2 = new JSONObject();
			event2.put("name", "event2");
			event2.put("map", mapId);
			event2.put("start", 100000);
			event2.put("end", 1000000);
			Method method = getClass().getMethod("createEvent1", new Class[]{String.class});
			TestHelpClass.sendRequest(TestHelpClass.makeJSONPost(event.toString(), TestHelpClass.getPathByAddingContent("event")), TestHelpClass.createMethod(method, this));
			method = getClass().getMethod("createEvent2", new Class[]{String.class});
			TestHelpClass.sendRequest(TestHelpClass.makeJSONPost(event2.toString(), TestHelpClass.getPathByAddingContent("event")), TestHelpClass.createMethod(method, this));
			JSONObject eventUpdate = new JSONObject();
			eventUpdate.put("name", "eventUpdate");
			TestHelpClass.sendRequest(TestHelpClass.makeJSONPut(eventUpdate.toString(), TestHelpClass.getPathByAddingContent("event?id="+eventId1)), null);
			method = getClass().getMethod("updateEvent", new Class[]{String.class});
			TestHelpClass.sendRequest(TestHelpClass.makeJSONGet(TestHelpClass.getPathByAddingContent("event?id="+eventId1)), TestHelpClass.createMethod(method, this));
			
			initialize2();
			
			JSONObject eventDevice1 = new JSONObject();
			eventDevice1.put("device", deviceId1);
			eventDevice1.put("event", eventId1);
			JSONObject eventDevice2 = new JSONObject();
			eventDevice2.put("device", deviceId2);
			eventDevice2.put("event", eventId1);
			TestHelpClass.sendRequest(TestHelpClass.makeJSONPost(eventDevice1.toString(), TestHelpClass.getPathByAddingContent("eventdevice")), null);
			TestHelpClass.sendRequest(TestHelpClass.makeJSONPost(eventDevice2.toString(), TestHelpClass.getPathByAddingContent("eventdevice")), null);
			eventDevice1.put("event", eventId2);
			TestHelpClass.sendRequest(TestHelpClass.makeJSONPost(eventDevice1.toString(), TestHelpClass.getPathByAddingContent("eventdevice")), null);
			method = getClass().getMethod("eventDevices1", new Class[]{String.class});
			TestHelpClass.sendRequest(TestHelpClass.makeJSONGet(TestHelpClass.getPathByAddingContent("event?id="+eventId1)), TestHelpClass.createMethod(method, this));
			method = getClass().getMethod("eventDevices2", new Class[]{String.class});
			TestHelpClass.sendRequest(TestHelpClass.makeJSONGet(TestHelpClass.getPathByAddingContent("event?id="+eventId2)), TestHelpClass.createMethod(method, this));
			TestHelpClass.sendRequest(TestHelpClass.makeJSONDelete(TestHelpClass.getPathByAddingContent("eventdevice?event="+eventId1 + "&device=" + deviceId2)), null);
			method = getClass().getMethod("eventDevicesDelete", new Class[]{String.class});
			TestHelpClass.sendRequest(TestHelpClass.makeJSONGet(TestHelpClass.getPathByAddingContent("event?id="+eventId1)), TestHelpClass.createMethod(method, this));
			method = getClass().getMethod("eventsFromDevice", new Class[]{String.class});
			TestHelpClass.sendRequest(TestHelpClass.makeJSONGet(TestHelpClass.getPathByAddingContent("event/eventsFromDevice?id="+deviceId1)), TestHelpClass.createMethod(method, this));
			JSONObject deviceLocation = new JSONObject();
			deviceLocation.put("latitude", 10.0);
			deviceLocation.put("longtitude", 10.0);
			deviceLocation.put("device", deviceId1);
			deviceLocation.put("event", eventId1);
			TestHelpClass.sendRequest(TestHelpClass.makeJSONPost(deviceLocation.toString(), TestHelpClass.getPathByAddingContent("devicelocation")), null);
			deviceLocation.put("latitude", 9.5);
			deviceLocation.put("longtitude", 10.5);
			TestHelpClass.sendRequest(TestHelpClass.makeJSONPost(deviceLocation.toString(), TestHelpClass.getPathByAddingContent("devicelocation")), null);
			deviceLocation.put("event", eventId2);
			TestHelpClass.sendRequest(TestHelpClass.makeJSONPost(deviceLocation.toString(), TestHelpClass.getPathByAddingContent("devicelocation")), null);
			method = getClass().getMethod("deviceLocation", new Class[]{String.class});
			TestHelpClass.sendRequest(TestHelpClass.makeJSONGet(TestHelpClass.getPathByAddingContent("event?id="+eventId1)), TestHelpClass.createMethod(method, this));
			
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void deviceLocation(String json){
		try {
			JSONObject event = new JSONObject(json);
			JSONArray eventDevices = event.getJSONArray("eventdevices");
			JSONArray deviceLocations = ((JSONObject)eventDevices.get(0)).getJSONArray("locations");
			testSize(2, deviceLocations);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void initialize() throws NoSuchMethodException, SecurityException, IOException, JSONException{
			URL location = MapTest.class.getProtectionDomain().getCodeSource().getLocation();
			File fi = new File(location.getFile() + "com\\lma\\pt\\unittest\\testmap.jpg");
			byte[] fileContent = Files.readAllBytes(fi.toPath());
			String filePath =  Base64.encode(fileContent);
			JSONObject map = new JSONObject();
			map.put("name", "first");
			map.put("image", filePath);
			map.put("size", 10000);
			Method method = getClass().getMethod("createMap", new Class[]{String.class});
			TestHelpClass.sendRequest(TestHelpClass.makeJSONPost(map.toString(), TestHelpClass.getPathByAddingContent("map")), TestHelpClass.createMethod(method, this));
	}
	
	private void initialize2() throws NoSuchMethodException, SecurityException, IOException, JSONException{
		JSONObject team = new JSONObject();
		team.put("name", "first");
		Method method = getClass().getMethod("saveTeamId1", new Class[]{String.class});
		TestHelpClass.sendRequest(TestHelpClass.makeJSONPost(team.toString(), TestHelpClass.getPathByAddingContent("team")), TestHelpClass.createMethod(method, this));
		JSONObject runner1 = new JSONObject();
		runner1.put("name", "runner1");
		runner1.put("team", teamId);
		JSONObject runner2 = new JSONObject();
		runner2.put("name", "runner2");
		runner2.put("team", teamId);
		method = getClass().getMethod("saveRunnerId1", new Class[]{String.class});
		TestHelpClass.sendRequest(TestHelpClass.makeJSONPost(runner1.toString(), TestHelpClass.getPathByAddingContent("runner")),  TestHelpClass.createMethod(method, this));
		JSONObject device1 = new JSONObject();
		device1.put("identifier", "test1");
		device1.put("name", "device1");
		device1.put("runner", runnerId);
		JSONObject device2 = new JSONObject();
		device2.put("identifier", "test2");
		device2.put("name", "device2");
		device2.put("runner", runnerId);
		method = getClass().getMethod("saveDeviceId1", new Class[]{String.class});
		TestHelpClass.sendRequest(TestHelpClass.makeJSONPost(device1.toString(), TestHelpClass.getPathByAddingContent("device")), TestHelpClass.createMethod(method, this));
		method = getClass().getMethod("saveDeviceId2", new Class[]{String.class});
		TestHelpClass.sendRequest(TestHelpClass.makeJSONPost(device2.toString(), TestHelpClass.getPathByAddingContent("device")), TestHelpClass.createMethod(method, this));
}
	
	public void eventDevices1(String json){
		try {
			JSONObject event = new JSONObject(json);
			JSONArray array = event.getJSONArray("eventdevices");
			testSize(2, array);
			testAttributeArray(0, createEventDeviceMap(deviceId1), array);
			testAttributeArray(1, createEventDeviceMap(deviceId2), array);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void eventDevices2(String json){
		try {
			JSONObject event = new JSONObject(json);
			JSONArray array = event.getJSONArray("eventdevices");
			testSize(1, array);
			testAttributeArray(0, createEventDeviceMap(deviceId1), array);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void eventDevicesDelete(String json){
		try {
			JSONObject event = new JSONObject(json);
			JSONArray array = event.getJSONArray("eventdevices");
			testSize(1, array);
			testAttributeArray(0, createEventDeviceMap(deviceId1), array);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void eventsFromDevice(String json){
		try {
			JSONArray array = new JSONArray(json);
			testSize(2, array);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void createMap(String id){
		this.mapId = id;
	}
	public void createEvent1(String id){
		this.eventId1 = id;
	}
	public void createEvent2(String id){
		this.eventId2 = id;
	}
	public void saveTeamId1(String id){
		this.teamId = id;
	}
	public void saveRunnerId1(String id){
		this.runnerId = id;
	}
	public void saveDeviceId1(String id){
		this.deviceId1 = id;
	}
	public void saveDeviceId2(String id){
		this.deviceId2 = id;
	}
	public void updateEvent(String json){
		JSONArray array = new JSONArray();
		try {
			array.put(new JSONObject(json));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		testAttributeArray(0, createEventMap("eventUpdate", mapId, 1000, 10000), array);
	}
	
	private Map<String, Object> createEventMap(String name, String map, int start, int end){
		Map<String, Object> myMap = new HashMap<String, Object>();
		myMap.put("name", name);
		myMap.put("map", map);
		myMap.put("start", start);
		myMap.put("end", end);
		return myMap;
	}
	private Map<String, Object> createEventDeviceMap(String device){
		Map<String, Object> eventDevice = new HashMap<String, Object>();
		eventDevice.put("device", device);
		return eventDevice;
	}
}
