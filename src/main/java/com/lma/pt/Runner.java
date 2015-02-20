package com.lma.pt;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.json.JSONException;

@Path("runner")
@Produces("application/json")
public class Runner extends ModelClassesParent{
	
	@GET
	@Path("")
	public Response getRunner(@QueryParam("id") String json){
		return succesfullGetOperation(service.getRunner(json));
	}
	
	@POST
	@Path("")
	public Response insertRunner(String json){
		try {
			return succesfullInsertNewRowOperation(service.insertRunner(json));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.status(400).build();
	}
	
	@PUT
	@Path("")
	public Response updateRunner(@QueryParam("id") String id, String json){
		try {
			return succesfullInsertOperation(service.updateRunner(id, json));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.status(400).build();
	}
}
