package com.lma.pt.unittest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

public class RunnerTest extends TestParent{
	
	
	public RunnerTest() throws NoSuchMethodException, SecurityException, JSONException{
		super();
	}
	
	@Test
	public void deviceTest(){
		
		
		try {

			Method method = getClass().getMethod("teamsTest", new Class[]{String.class});
			sendRequest(makeJSONGet(getPathByAddingContent("team/teams")), createMethod(method, new Object[]{this}));
			JSONObject updateTeam = new JSONObject();
			updateTeam.put("name", "fourth");
			sendRequest(makeJSONPut(updateTeam.toString(), getPathByAddingContent("team?id="+team1.getString("_id"))), null);
			method = getClass().getMethod("updateTest", new Class[]{String.class});
			sendRequest(makeJSONGet(getPathByAddingContent("team?id="+team1.getString("_id"))), createMethod(method, new Object[]{this}));
			sendRequest(makeJSONDelete(getPathByAddingContent("team?id="+team1.getString("_id"))), null);
			method = getClass().getMethod("deleteTest", new Class[]{String.class});
			sendRequest(makeJSONGet(getPathByAddingContent("team/teams")), createMethod(method, new Object[]{this}));
			JSONObject runnerUpdate = new JSONObject();
			runnerUpdate.put("name", "updateRunner");
			sendRequest(makeJSONPut(runnerUpdate.toString(), getPathByAddingContent("runner?id="+runner1.getString("_id"))), null);
			method = getClass().getMethod("runnerUpdate", new Class[]{String.class});
			sendRequest(makeJSONGet(getPathByAddingContent("runner?id="+runner1.getString("_id"))),  createMethod(method, new Object[]{this}));
			
			JSONArray array = new JSONArray();
			array.put(team2.getString("_id"));
			sendRequest(makeJSONPut(array.toString(), getPathByAddingContent("runner/addTeam?id=" + runner1.getString("_id"))), null);
			sendRequest(makeJSONPut(array.toString(), getPathByAddingContent("runner/addTeam?id=" + runner2.getString("_id"))), null);
			

			method = getClass().getMethod("runnerFromTeamTest", new Class[]{String.class});
			sendRequest(makeJSONGet(getPathByAddingContent("runner/runnersFromTeam?id="+team2.getString("_id"))),  createMethod(method, new Object[]{this}));
			
			sendRequest(makeJSONDelete(getPathByAddingContent("runner?id="+runner1.getString("_id"))), null);
			method = getClass().getMethod("runnerFromTeamTest2", new Class[]{String.class});
			sendRequest(makeJSONGet(getPathByAddingContent("runner/runnersFromTeam?id="+team2.getString("_id"))),  createMethod(method, new Object[]{this}));
		} catch (JSONException | NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void runnerFromTeamTest(String json){
		try {
			JSONArray array = new JSONArray(json);
			testAttributeArray(0, createRunnerMap("updateRunner"), array);
			testAttributeArray(1, createRunnerMap("runner2"), array);
			testSize(2, array);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void runnerFromTeamTest2(String json){
		System.out.println(json);
		try {
			JSONArray array = new JSONArray(json);
			testSize(1, array);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void runnerUpdate(String json){
		System.out.println(json);
		try {
			JSONArray array = new JSONArray();
			array.put(new JSONObject(json));
			testAttributeArray(0, createRunnerMap("updateRunner"), array);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void deleteTest(String json){
		try {
			JSONArray array = new JSONArray(json);
			testSize(2, array);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void teamsTest(String json){
		try {
			JSONArray array = new JSONArray(json);
			testAttributeArray(0, createTeamMap("first"), array);
			testAttributeArray(1, createTeamMap("second"), array);
			testAttributeArray(2, createTeamMap("third"), array);
			testSize(3, array);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateTest(String json){
		try {
			JSONArray array = new JSONArray();
			array.put(new JSONObject(json));
			testAttributeArray(0, createTeamMap("fourth"), array);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Map<String, Object> createRunnerMap(String name){
		Map<String, Object> myMap = new HashMap<String, Object>();
		myMap.put("name", name);
		return myMap;
	}
	private Map<String, Object> createTeamMap(String name){
		Map<String, Object> myMap = new HashMap<String, Object>();
		myMap.put("name", name);
		return myMap;
	}
}
