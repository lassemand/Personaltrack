package com.lma.pt;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.json.JSONException;

@Path("event")
@Produces("application/json")
public class Event extends ModelClassesParent{
	
	@GET
	@Path("")
	public Response getEvent(@QueryParam("id") String json){
		return succesfullGetOperation(service.getTeam(json));
	}
	
	@POST
	@Path("")
	public Response insertEvent(String json){
		try {
			System.out.println("insertEvent");
			return succesfullInsertNewRowOperation(service.insertEvent(json));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.status(400).build();
	}
	
	@PUT
	@Path("")
	public Response updateEvent(@QueryParam("id") String id, String json){
		try {
			return succesfullInsertOperation(service.updateEvent(id, json));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.status(400).build();
	}
}
