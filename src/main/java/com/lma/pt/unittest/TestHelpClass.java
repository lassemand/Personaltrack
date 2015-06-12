package com.lma.pt.unittest;

import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.glassfish.jersey.internal.util.Base64;

public class TestHelpClass {
	private static final String LOCALHOST = "http://localhost:8080/PersonalTrack/rest/";
	
	public static String getPathByAddingContent(String content){
		return LOCALHOST + content;
	}

	public static void sendRequest(final HttpRequestBase request, final List<Object> objects) {
				String responseString = null;
				try {
					HttpClient httpclient = new DefaultHttpClient();

					HttpRequestBase base = request;
//					String authString = "allowedentry:triforkpass";
//					byte[] authEncBytes = Base64.encode(authString.getBytes());
//					String authStringEnc = new String(authEncBytes);
//					base.addHeader("Authorization", "Basic " + authStringEnc);
					HttpResponse response = httpclient.execute(base);
					StatusLine statusLine = response.getStatusLine();
					
					if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
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
						method.invoke(object, responseString);
					} catch (IllegalAccessException | IllegalArgumentException
							| InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
	}
	public static HttpPost makeJSONPost(String object, String path){
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
	public static HttpDelete makeJSONDelete(String path){
		HttpDelete post = new HttpDelete(path.toString());
		return post;
	}

	public static HttpPut makeJSONPut(String object, String path){
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
	
	public static HttpGet makeJSONGet(String path){
		HttpGet post = new HttpGet(path.toString());
		return post;
	}
	public static List<Object> createMethod(Method method, Object object){
		List<Object> objects = new ArrayList<Object>();
		objects.add(method);
		objects.add(object);
		return objects;
	}
}
