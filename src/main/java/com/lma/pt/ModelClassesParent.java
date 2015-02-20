package com.lma.pt;

import javax.ws.rs.core.Response;
import org.bson.types.ObjectId;

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
	
	protected Response succesfullInsertNewRowOperation(ObjectId value){
		boolean succesfull = value != null;
		return succesfull ? Response.status(200).entity(value.toString()).build(): Response.status(400).build();
	}
	
	protected Response succesfullGetOperation(String value){
		return !value.isEmpty() ? Response.status(200).entity(value).build(): Response.status(400).build();
			
	}
	protected boolean insertSuccesFully(int value){
		return value > 0;
	}
}
