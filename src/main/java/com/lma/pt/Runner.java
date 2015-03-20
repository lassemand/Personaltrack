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

@Path("runner")
@Produces("application/json")
public class Runner extends ModelClassesParent{
	
	@GET
	@Path("")
	public Response getRunner(@QueryParam("id") String json){
		return succesfullGetOperation(service.getPtObject(PTObject.RUNNER, json));
	}
	
	@GET
	@Path("runnersFromTeam")
	public Response getRunnersFromTeam(@QueryParam("id") String json){
		return succesfullGetOperation(service.getPtObjectFromRelation(PTObject.RUNNER, new BasicDBObject("team", json)));
	}
	
	@POST
	@Path("")
	public Response insertRunner(String json){
		return succesfullInsertNewRowOperation(service.insertPtObjectWithValidate(PTObject.RUNNER, json));
	}
	
	@PUT
	@Path("")
	public Response updateRunner(@QueryParam("id") String id, String json){
		return succesfullInsertOperation(service.updatePtObject(PTObject.RUNNER, id, json));
	}
	
	@DELETE
	@Path("")
	public Response deleteDevice(@QueryParam("id") String id){
		return succesfullInsertOperation(service.deletePtObject(PTObject.RUNNER, id));
	}
	
}
