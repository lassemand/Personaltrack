package com.lma.pt.unittest;

import static org.junit.Assert.fail;

import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TestParent {

	public TestParent(){
		String myPath = TestHelpClass.getPathByAddingContent("test/removeObjects");
		TestHelpClass.sendRequest(TestHelpClass.makeJSONDelete(myPath), null);
	}
	
	void testSize(int size, JSONArray array){
		if(array.length() != size)
			fail("Wrong size");
	}
	
	void testAttributeArray(int column, Map<String, Object> values, JSONArray array){
		try {
			JSONObject object = array.getJSONObject(column);
			for(String key: values.keySet()){
				Object value = values.get(key);
				if(!object.get(key).equals(value)){
					System.out.println("Value: " + value + " Key: " + object.get(key));
					fail("Wrong value");
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
