package com.lma.pt;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.json.JSONException;

@Path("device")
@Produces("application/json")
public class Device extends ModelClassesParent{
	
	@GET
	@Path("")
	public Response getDevice(@QueryParam("id") String json){
		return succesfullGetOperation(service.getDevice(json));
	}
	
	@POST
	@Path("")
	public Response insertDevice(String json){
		try {
			return succesfullInsertNewRowOperation(service.insertDevice(json));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.status(400).build();
	}
	
	@PUT
	@Path("")
	public Response updateDevice(@QueryParam("id") String id, String json){
		try {
			return succesfullInsertOperation(service.updateDevice(id, json));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.status(400).build();
	}
}
