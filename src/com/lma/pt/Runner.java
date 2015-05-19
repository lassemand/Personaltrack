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
import org.json.JSONObject;

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
	public Response getRunnersFromTeam(@QueryParam("id") String id){
		return succesfullGetOperation(service.getPtObjectFromRelation(PTObject.RUNNER, new BasicDBObject("team", id)));
	}
	
	@GET
	@Path("runners")
	public Response getRunners(){
			return succesfullGetOperation(service.getAllPtObject(PTObject.RUNNER));
	}
	
	@POST
	@Path("createRunner")
	public Response insertRunner(String json){
		if(service.runnerNotExistsWithUsername(json))
		return defaultSuccesInsertNewOperation(service.insertPtObjectWithValidate(PTObject.RUNNER, json));
		else
		return Response.status(409).build();	
	}
	
	@PUT
	@Path("")
	public Response updateRunner(@QueryParam("id") String id, String json){
		return succesfullInsertOperation(service.updatePtObject(PTObject.RUNNER, id, json));
	}
	
	
	@PUT
	@Path("addMaps")
	public Response addRunnerMaps(@QueryParam("id") String id, String json){
		return succesfullInsertOperation(service.addValuesToArray(PTObject.RUNNER, json, id, "maps"));
	}
	
	@PUT
	@Path("removeMaps")
	public Response removeRunnerMaps(@QueryParam("id") String id, String json){
		return succesfullInsertOperation(service.removeValuesToArray(PTObject.RUNNER, json, id, "maps"));
	}
	
	@PUT
	@Path("addTeam")
	public Response addRunnerTeam(@QueryParam("id") String id, String json){
		return succesfullInsertOperation(service.addValuesToArray(PTObject.RUNNER, json, id, "team"));
	}
	
	@PUT
	@Path("removeTeam")
	public Response removeRunnerTeam(@QueryParam("id") String id, String json){
		return succesfullInsertOperation(service.removeValuesToArray(PTObject.RUNNER, json, id, "team"));
	}
	
	
	@DELETE
	@Path("")
	public Response deleteDevice(@QueryParam("id") String id){
		return succesfullInsertOperation(service.deletePtObject(PTObject.RUNNER, id));
	}
	
}
