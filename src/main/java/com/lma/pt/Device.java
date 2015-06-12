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
import com.mongodb.BasicDBObject;

@Path("device")
@Produces("application/json")
public class Device extends ModelClassesParent{
	
	@GET
	@Path("")
	public Response getDevice(@QueryParam("id") String json){
		return succesfullGetOperation(service.getPtObject(PTObject.DEVICE, json));
	}
	
	@GET
	@Path("devicesFromRunner")
	public Response devicesFromRunner(@QueryParam("id") String json){
		return succesfullGetOperation(service.getPtObjectFromRelation(PTObject.DEVICE, new BasicDBObject("runner", json)));
	}
	
	@POST
	@Path("")
	public Response insertDevice(String json){
		return succesfullInsertNewRowOperation(service.insertPtObjectWithValidate(PTObject.DEVICE, json));
	}
	
	@PUT
	@Path("")
	public Response updateDevice(@QueryParam("id") String id, String json){
			return succesfullInsertOperation(service.updatePtObject(PTObject.DEVICE, id, json));
	}
	
	@DELETE
	@Path("")
	public Response deleteDevice(@QueryParam("id") String id){
		return succesfullInsertOperation(service.deletePtObject(PTObject.DEVICE, id));
}
}
