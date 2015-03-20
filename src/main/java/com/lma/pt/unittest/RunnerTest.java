package com.lma.pt.unittest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

public class RunnerTest extends TestParent{
	
	private String id1, id2, id3;
	private String runnerId1, runnerId2;
	
	public RunnerTest(){
		super();
	}
	
	@Test
	public void deviceTest(){
		
		
		try {
			JSONObject team = new JSONObject();
			team.put("name", "first");
			JSONObject team2 = new JSONObject();
			team2.put("name", "second");
			JSONObject team3 = new JSONObject();
			team3.put("name", "third");
			Method method = getClass().getMethod("saveId1", new Class[]{String.class});
			TestHelpClass.sendRequest(TestHelpClass.makeJSONPost(team.toString(), TestHelpClass.getPathByAddingContent("team")), TestHelpClass.createMethod(method, this));
			method = getClass().getMethod("saveId2", new Class[]{String.class});
			TestHelpClass.sendRequest(TestHelpClass.makeJSONPost(team2.toString(), TestHelpClass.getPathByAddingContent("team")), TestHelpClass.createMethod(method, this));
			method = getClass().getMethod("saveId3", new Class[]{String.class});
			TestHelpClass.sendRequest(TestHelpClass.makeJSONPost(team3.toString(), TestHelpClass.getPathByAddingContent("team")), TestHelpClass.createMethod(method, this));
			method = getClass().getMethod("teamsTest", new Class[]{String.class});
			TestHelpClass.sendRequest(TestHelpClass.makeJSONGet(TestHelpClass.getPathByAddingContent("team/teams")), TestHelpClass.createMethod(method, this));
			JSONObject updateTeam = new JSONObject();
			updateTeam.put("name", "fourth");
			TestHelpClass.sendRequest(TestHelpClass.makeJSONPut(updateTeam.toString(), TestHelpClass.getPathByAddingContent("team?id="+id1)), null);
			method = getClass().getMethod("updateTest", new Class[]{String.class});
			TestHelpClass.sendRequest(TestHelpClass.makeJSONGet(TestHelpClass.getPathByAddingContent("team?id="+id1)), TestHelpClass.createMethod(method, this));
			TestHelpClass.sendRequest(TestHelpClass.makeJSONDelete(TestHelpClass.getPathByAddingContent("team?id="+id1)), null);
			method = getClass().getMethod("deleteTest", new Class[]{String.class});
			TestHelpClass.sendRequest(TestHelpClass.makeJSONGet(TestHelpClass.getPathByAddingContent("team/teams")), TestHelpClass.createMethod(method, this));
			
			JSONObject runner1 = new JSONObject();
			runner1.put("name", "runner1");
			runner1.put("team", id2);
			JSONObject runner2 = new JSONObject();
			runner2.put("name", "runner2");
			runner2.put("team", id2);
			method = getClass().getMethod("saveRunnerId1", new Class[]{String.class});
			TestHelpClass.sendRequest(TestHelpClass.makeJSONPost(runner1.toString(), TestHelpClass.getPathByAddingContent("runner")),  TestHelpClass.createMethod(method, this));
			method = getClass().getMethod("saveRunnerId2", new Class[]{String.class});
			TestHelpClass.sendRequest(TestHelpClass.makeJSONPost(runner2.toString(), TestHelpClass.getPathByAddingContent("runner")),  TestHelpClass.createMethod(method, this));
			method = getClass().getMethod("runnerFromTeamTest", new Class[]{String.class});
			TestHelpClass.sendRequest(TestHelpClass.makeJSONGet(TestHelpClass.getPathByAddingContent("runner/runnersFromTeam?id="+id2)),  TestHelpClass.createMethod(method, this));
			JSONObject runnerUpdate = new JSONObject();
			runnerUpdate.put("name", "updateRunner");
			runnerUpdate.put("team", id3);
			TestHelpClass.sendRequest(TestHelpClass.makeJSONPut(runnerUpdate.toString(), TestHelpClass.getPathByAddingContent("runner?id="+runnerId1)), null);
			method = getClass().getMethod("runnerUpdate", new Class[]{String.class});
			TestHelpClass.sendRequest(TestHelpClass.makeJSONGet(TestHelpClass.getPathByAddingContent("runner?id="+runnerId1)),  TestHelpClass.createMethod(method, this));
			TestHelpClass.sendRequest(TestHelpClass.makeJSONDelete(TestHelpClass.getPathByAddingContent("runner?id="+runnerId1)), null);
			method = getClass().getMethod("runnerFromTeamTest2", new Class[]{String.class});
			TestHelpClass.sendRequest(TestHelpClass.makeJSONGet(TestHelpClass.getPathByAddingContent("runner/runnersFromTeam?id="+id2)),  TestHelpClass.createMethod(method, this));
			
		} catch (JSONException | NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void saveId1(String id){
		id1 = id;
	}
	public void saveId2(String id){
		id2 = id;
	}
	public void saveId3(String id){
		id3 = id;
	}
	public void saveRunnerId1(String id){
		runnerId1 = id;
	}
	public void saveRunnerId2(String id){
		runnerId2 = id;
	}
	
	public void runnerFromTeamTest(String json){
		try {
			JSONArray array = new JSONArray(json);
			testAttributeArray(0, createRunnerMap("runner1", id2), array);
			testAttributeArray(1, createRunnerMap("runner2", id2), array);
			testSize(2, array);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void runnerFromTeamTest2(String json){
		try {
			JSONArray array = new JSONArray(json);
			testAttributeArray(0, createRunnerMap("runner2", id2), array);
			testSize(1, array);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void runnerUpdate(String json){
		try {
			JSONArray array = new JSONArray();
			array.put(new JSONObject(json));
			testAttributeArray(0, createRunnerMap("updateRunner", id3), array);
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
	
	private Map<String, Object> createRunnerMap(String name, String team){
		Map<String, Object> myMap = new HashMap<String, Object>();
		myMap.put("name", name);
		myMap.put("team", team);
		return myMap;
	}
	private Map<String, Object> createTeamMap(String name){
		Map<String, Object> myMap = new HashMap<String, Object>();
		myMap.put("name", name);
		return myMap;
	}
}
