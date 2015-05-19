package com.lma.pt;

import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.json.JSONArray;

import com.lma.pt.service.PTObject;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

@Path("test")
@Produces("application/json")
public class Test extends ModelClassesParent{
	
	@DELETE
	@Path("removeObjects")
	public Response deleteDevice(@QueryParam("id") String id){
		service.removeObjects();
		return Response.status(200).build();
	}

}
