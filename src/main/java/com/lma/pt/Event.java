package com.lma.pt;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.lma.pt.service.PTObject;

@Path("event")
@Produces("application/json")
public class Event extends ModelClassesParent{
	
	@GET
	@Path("")
	public Response getEvent(@QueryParam("id") String json){
		return succesfullGetOperation(service.getPtObject(PTObject.EVENT, json));
	}
	
	@POST
	@Path("")
	public Response insertEvent(String json){
		return succesfullInsertNewRowOperation(service.insertPtObjectWithValidate(PTObject.EVENT, json));
	}
	
	@PUT
	@Path("")
	public Response updateEvent(@QueryParam("id") String id, String json){
		return succesfullInsertOperation(service.updatePtObject(PTObject.EVENT, id, json));
	}
	
	@DELETE
	@Path("")
	public Response deleteDevice(@QueryParam("id") String id){
		return succesfullInsertOperation(service.deletePtObject(PTObject.EVENT, id));
	}
	
	@GET 
	@Path("eventsFromDevice")
	public Response getEventFromDevice(@QueryParam("id") String device){
		System.out.println(device);
		return succesfullGetOperation(service.eventsFromDevice(device));
	}
}
