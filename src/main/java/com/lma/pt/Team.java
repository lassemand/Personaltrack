package com.lma.pt;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;


@Path("team")
@Produces("application/json")
public class Team extends ModelClassesParent {
	
	@GET
	@Path("")
	public Response getTeam(@QueryParam("id") String json){
		return succesfullGetOperation(service.getTeam(json));
	}
	
	@GET
	@Path("teams")
	public Response getTeams(){
		try {
			return succesfullGetOperation(service.getTeams());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.status(400).build();
	}
	
	@POST
	@Path("")
	public Response insertTeam(String json){
		try {
			return succesfullInsertNewRowOperation(service.insertTeam(json));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.status(400).build();
	}
	@PUT
	@Path("")
	public Response updateTeam(@QueryParam("id") String id, String json){
		try {
			return succesfullInsertOperation(service.updateTeam(id, json));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.status(400).build();
	}
	
//	@POST
//	@Path("SchoolTest")
//	public Response insertMessage(String json){
//		System.out.println(json);
//		try {
//			JSONObject object = new JSONObject(json);
//			String printOut = "Name: " + object.getString("Name") + " Index: " + object.getInt("Index");
//			System.out.println(printOut);
//			return Response.status(200).entity(printOut).build();
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return Response.status(400).build();
//	}
	

}
