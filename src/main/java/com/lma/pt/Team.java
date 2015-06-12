package com.lma.pt;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.json.JSONException;

import com.lma.pt.service.PTObject;


@Path("team")
@Produces("application/json")
public class Team extends ModelClassesParent {
	
	@GET
	@Path("")
	public Response getTeam(@QueryParam("id") String json){
		return succesfullGetOperation(service.getPtObject(PTObject.TEAM, json));
	}
	
	@GET
	@Path("teams")
	public Response getTeams(){
			return succesfullGetOperation(service.getAllPtObject(PTObject.TEAM));
	}
	
	@POST
	@Path("")
	public Response insertTeam(String json){
		return succesfullInsertNewRowOperation(service.insertPtObjectWithValidate(PTObject.TEAM, json));
	}
	@PUT
	@Path("")
	public Response updateTeam(@QueryParam("id") String id, String json){
		return succesfullInsertOperation(service.updatePtObject(PTObject.TEAM, id, json));
	}
	
	@DELETE
	@Path("")
	public Response deleteDevice(@QueryParam("id") String id){
		return succesfullInsertOperation(service.deletePtObject(PTObject.TEAM, id));
}
	

}
