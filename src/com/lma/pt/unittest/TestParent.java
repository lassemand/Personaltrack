package com.lma.pt.unittest;

import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TestParent {

	private final String LOCALHOST = "http://localhost:8080/PersonalTrack/rest/";
	private String[] token;
	public JSONObject runner1, runner2, team1, team2, team3;
	
	public TestParent(){
		try{
		String myPath = getPathByAddingContent("test/removeObjects");
		HttpRequestBase base = makeJSONDelete(myPath);
		
		base.addHeader("testAllowed", "4562t4178248123y213218361728ygf1hiuhui");
		sendRequest(base, null);
		Method method = getClass().getMethod("saveObjectId", new Class[]{String.class, JSONObject.class});
		runner1 = createDefaultRunner("runner1", "user1", "asdasd");
		runner2 = createDefaultRunner("runner2", "user2", "asdasd");
		sendRequest(makeJSONPost(runner1.toString(), getPathByAddingContent("runner/createRunner")), createMethod(method, new Object[]{this, runner1}));
		sendRequest(makeJSONPost(runner2.toString(), getPathByAddingContent("runner/createRunner")), createMethod(method, new Object[]{this, runner2}));
		
		method = getClass().getMethod("saveToken", new Class[]{String.class});
		sendRequest(makeJSONPost(createTokenRequest(runner2).toString(), getPathByAddingContent("token/retrieve")), createMethod(method, new Object[]{this}));
		
		team1 = createDefaultTeam("first");
		team2 = createDefaultTeam("second");
		team3 = createDefaultTeam("third");
		method = getClass().getMethod("saveObjectId", new Class[]{String.class, JSONObject.class});
		sendRequest(makeJSONPost(team1.toString(), getPathByAddingContent("team")), createMethod(method, new Object[]{this, team1}));
		sendRequest(makeJSONPost(team2.toString(), getPathByAddingContent("team")), createMethod(method, new Object[]{this, team2}));
		sendRequest(makeJSONPost(team3.toString(), getPathByAddingContent("team")), createMethod(method, new Object[]{this, team3}));
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void saveObjectId(String id, JSONObject object){
		try {
			object.put("_id", id);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void saveToken(String object){
		try {
			token = new String[]{object, runner2.getString("username")};
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public JSONObject createDefaultRunner(String name,  String username, String password) throws JSONException{
		JSONObject runner = new JSONObject();
		runner.put("name", name);
		runner.put("username", username);
		runner.put("password", password);
		return runner;
	}
	
	public JSONObject createDefaultTeam(String name){
		JSONObject object = new JSONObject();
		try {
			object.put("name", name);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return object;
	}
	
	public JSONObject createTokenRequest(JSONObject runner){
		JSONObject tokenRequest = new JSONObject();
		try {
			tokenRequest.put("username", runner.getString("username"));
			tokenRequest.put("password", runner.getString("password"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return tokenRequest;
	}
	
	public String getPathByAddingContent(String content){
		return LOCALHOST + content;
	}

	public void sendRequest(final HttpRequestBase request, final List<Object> objects) {
				String responseString = null;
				try {
					
					HttpClient httpclient = new DefaultHttpClient();
					
					HttpRequestBase base = request;
//					String authString = "allowedentry:triforkpass";
//					byte[] authEncBytes = Base64.encode(authString.getBytes());
//					String authStringEnc = new String(authEncBytes);
//					base.addHeader("Authorization", "Basic " + authStringEnc);
					if(token != null){
						base.addHeader("user", token[1]);
						base.addHeader("token", token[0]);
					}
					HttpResponse response = httpclient.execute(base);
					StatusLine statusLine = response.getStatusLine();
					
					if (statusLine.getStatusCode() == HttpStatus.SC_OK || statusLine.getStatusCode() == HttpStatus.SC_ACCEPTED) {
						ByteArrayOutputStream out = new ByteArrayOutputStream();
						response.getEntity().writeTo(out);
						out.close();
						responseString = out.toString();
					} else {
						fail("Wrong statusCode");
						response.getEntity().getContent().close();
						throw new IOException(statusLine.getReasonPhrase());
					}
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (objects != null) {
					Method method = (Method) objects.get(0);
					Object object = objects.get(1);
					
					try {
						if(objects.size() != 3)
						method.invoke(object, responseString);
						else{
							method.invoke(object, responseString, objects.get(2));
						}
					} catch (IllegalAccessException | IllegalArgumentException
							| InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
	}
	public HttpPost makeJSONPost(String object, String path){
		HttpPost post = new HttpPost(path.toString());
		 post.setHeader("Accept", "application/json");
		 post.setHeader("Content-type", "application/json");
		 StringEntity registrationRequest_json_entity;
		try {
			registrationRequest_json_entity = new StringEntity(object);
			registrationRequest_json_entity.setContentType("application/json");
			post.setEntity(registrationRequest_json_entity);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return post;
	}
	public HttpDelete makeJSONDelete(String path){
		HttpDelete post = new HttpDelete(path.toString());
		return post;
	}

	public HttpPut makeJSONPut(String object, String path){
		HttpPut post = new HttpPut(path.toString());
		 post.setHeader("Accept", "application/json");
		 post.setHeader("Content-type", "application/json");
		 StringEntity registrationRequest_json_entity;
		try {
			registrationRequest_json_entity = new StringEntity(object);
			registrationRequest_json_entity.setContentType("application/json");
			post.setEntity(registrationRequest_json_entity);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return post;
	}	
	
	public HttpGet makeJSONGet(String path){
		HttpGet post = new HttpGet(path.toString());
		return post;
	}
	public List<Object> createMethod(Method method, Object[] parameterObjects){
		List<Object> objects = new ArrayList<Object>();
		objects.add(method);
		for(Object object: parameterObjects)
		objects.add(object);
		return objects;
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
					fail("Wrong value");
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
