package com.lma.pt;

import javax.ws.rs.core.Response;

import org.bson.types.ObjectId;
import org.json.JSONException;
import org.json.JSONObject;

import com.lma.pt.service.Service;

public abstract class ModelClassesParent {
	
	
	
	protected Service service;
	
	public ModelClassesParent(){

		service = Service.getInstance();
	}
	
	protected Response succesfullInsertOperation(int value){
		boolean succesfull = insertSuccesFully(value);
		return succesfull ? Response.status(200).build(): Response.status(400).build();
	}
	
	protected Response succesfullInsertNewRowOperation(ObjectId value, JSONObject object){
		boolean succesfull = value != null;
		try {
			object.append("_id", value);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			return Response.status(400).build();
		}
		return succesfull ? Response.status(200).entity(value.toString()).build(): Response.status(400).build();
	}
	protected Response defaultSuccesInsertNewOperation(ObjectId value){
		return succesfullInsertNewRowOperation(value, new JSONObject());
	}
	
	
	protected Response succesfullGetOperation(String value){
		return !value.isEmpty() ? Response.status(200).entity(value).build(): Response.status(400).build();
			
	}
	protected boolean insertSuccesFully(int value){
		return value > 0;
	}
}
