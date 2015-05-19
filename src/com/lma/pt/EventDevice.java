package com.lma.pt;

import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.json.JSONException;

@Path("eventdevice")
@Produces("application/json")
public class EventDevice  extends ModelClassesParent {

	@POST
	@Path("")
	public Response insertEventDevice(String json){
		try {
			return succesfullInsertOperation(service.insertEventDevice(json));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.status(400).build();
	}
	
	@DELETE
	@Path("")
	public Response removeEventDevice(@QueryParam("event") String event, @QueryParam("device") String device){
		try {
			return succesfullInsertOperation(service.removeEventDevice(event, device));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.status(400).build();
	}
	
}
