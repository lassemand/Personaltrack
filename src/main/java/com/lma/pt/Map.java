package com.lma.pt;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;


import org.json.JSONException;


@Path("map")
@Produces("application/json")
public class Map extends ModelClassesParent{

	@GET
	@Path("")
	public Response getMap(@QueryParam("id") String json){
		return succesfullGetOperation(service.getMap(json));
	}
	@POST
	@Path("")
	public Response insertMap(String json){
		try {
			return succesfullInsertNewRowOperation(service.insertMap(json));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.status(400).build();
	}
	@PUT
	@Path("")
	public Response updateMap(@QueryParam("id") String id, String json){
		try {
			return succesfullInsertOperation(service.updateMap(id, json));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.status(400).build();
	}
	
}
