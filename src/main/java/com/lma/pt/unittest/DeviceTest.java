package com.lma.pt.unittest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

public class DeviceTest extends TestParent{

	private String teamId, runnerId1, runnerId2, deviceId1, deviceId2, deviceId3;
	
	public DeviceTest(){
		super();
	}
	
	@Test
	public void testDevices(){
		try {
			initialize();
			JSONObject device1 = new JSONObject();
			device1.put("identifier", "test1");
			device1.put("name", "device1");
			device1.put("runner", runnerId1);
			JSONObject device2 = new JSONObject();
			device2.put("identifier", "test2");
			device2.put("name", "device2");
			device2.put("runner", runnerId1);
			JSONObject device3 = new JSONObject();
			device3.put("identifier", "test3");
			device3.put("name", "device3");
			device3.put("runner", runnerId2);
			Method method = getClass().getMethod("saveDeviceId1", new Class[]{String.class});
			TestHelpClass.sendRequest(TestHelpClass.makeJSONPost(device1.toString(), TestHelpClass.getPathByAddingContent("device")), TestHelpClass.createMethod(method, this));
			method = getClass().getMethod("saveDeviceId2", new Class[]{String.class});
			TestHelpClass.sendRequest(TestHelpClass.makeJSONPost(device2.toString(), TestHelpClass.getPathByAddingContent("device")), TestHelpClass.createMethod(method, this));
			method = getClass().getMethod("saveDeviceId3", new Class[]{String.class});
			TestHelpClass.sendRequest(TestHelpClass.makeJSONPost(device3.toString(), TestHelpClass.getPathByAddingContent("device")), TestHelpClass.createMethod(method, this));
			method = getClass().getMethod("devicesFromRunner", new Class[]{String.class});
			TestHelpClass.sendRequest(TestHelpClass.makeJSONGet(TestHelpClass.getPathByAddingContent("device/devicesFromRunner?id="+runnerId1)), TestHelpClass.createMethod(method, this));
			JSONObject deviceUpdate = new JSONObject();
			deviceUpdate.put("name", "deviceUpdate");
			TestHelpClass.sendRequest(TestHelpClass.makeJSONPut(deviceUpdate.toString(), TestHelpClass.getPathByAddingContent("device?id="+deviceId1)), null);
			method = getClass().getMethod("device", new Class[]{String.class});
			TestHelpClass.sendRequest(TestHelpClass.makeJSONGet(TestHelpClass.getPathByAddingContent("device?id="+deviceId1)), TestHelpClass.createMethod(method, this));
			TestHelpClass.sendRequest(TestHelpClass.makeJSONDelete(TestHelpClass.getPathByAddingContent("device?id="+deviceId1)), null);
			method = getClass().getMethod("devicesFromRunner2", new Class[]{String.class});
			TestHelpClass.sendRequest(TestHelpClass.makeJSONGet(TestHelpClass.getPathByAddingContent("device/devicesFromRunner?id="+runnerId1)), TestHelpClass.createMethod(method, this));
			
			
			
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void initialize() throws JSONException, NoSuchMethodException, SecurityException{
		JSONObject team = new JSONObject();
		team.put("name", "first");
		Method method = getClass().getMethod("saveId1", new Class[]{String.class});
		TestHelpClass.sendRequest(TestHelpClass.makeJSONPost(team.toString(), TestHelpClass.getPathByAddingContent("team")), TestHelpClass.createMethod(method, this));
		JSONObject runner1 = new JSONObject();
		runner1.put("name", "runner1");
		runner1.put("team", teamId);
		JSONObject runner2 = new JSONObject();
		runner2.put("name", "runner2");
		runner2.put("team", teamId);
		method = getClass().getMethod("saveRunnerId1", new Class[]{String.class});
		TestHelpClass.sendRequest(TestHelpClass.makeJSONPost(runner1.toString(), TestHelpClass.getPathByAddingContent("runner")),  TestHelpClass.createMethod(method, this));
		method = getClass().getMethod("saveRunnerId2", new Class[]{String.class});
		TestHelpClass.sendRequest(TestHelpClass.makeJSONPost(runner2.toString(), TestHelpClass.getPathByAddingContent("runner")),  TestHelpClass.createMethod(method, this));
		
		
	}
	
	public void devicesFromRunner(String id){
		try {
			JSONArray array = new JSONArray(id);
			testSize(2, array);
			testAttributeArray(0, createDeviceMap("device1", "test1"), array);
			testAttributeArray(1, createDeviceMap("device2", "test2"), array);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void devicesFromRunner2(String id){
		try {
			JSONArray array = new JSONArray(id);
			testSize(1, array);
			testAttributeArray(0, createDeviceMap("device2", "test2"), array);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void device(String id){
		JSONArray array = new JSONArray();
		
		try {
			array.put(new JSONObject(id));
			testAttributeArray(0, createDeviceMap("deviceUpdate", "test1"), array);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private Map<String, Object> createDeviceMap(String name, String identifier){
		Map<String, Object> myMap = new HashMap<String, Object>();
		myMap.put("name", name);
		myMap.put("identifier", identifier);
		return myMap;
	}
	
	public void saveId1(String id){
		teamId = id;
	}
	public void saveRunnerId1(String id){
		runnerId1 = id;
	}
	public void saveRunnerId2(String id){
		runnerId2 = id;
	}
	public void saveDeviceId1(String id){
		deviceId1 = id;
	}
	public void saveDeviceId2(String id){
		deviceId2 = id;
	}
	public void saveDeviceId3(String id){
		deviceId3 = id;
	}

	
}
