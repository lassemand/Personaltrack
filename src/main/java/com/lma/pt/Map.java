package com.lma.pt;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONException;

import com.lma.pt.service.PTObject;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;


@Path("map")
@Produces("application/json")
public class Map extends ModelClassesParent{

	@GET
	@Path("")
	public Response getMap(@QueryParam("id") String json){
		return succesfullGetOperation(service.getMap(PTObject.MAP, json));
	}
	
	@GET
	@Path("maps")
	public Response getMaps(@QueryParam("id") String json){
		BasicDBObject dbObject = null;
		try {
			JSONArray tempArray = new JSONArray(json);
			ObjectId[] inClauseArray = new ObjectId[tempArray.length()];

			for (int a = 0; a < tempArray.length(); a++) {
				inClauseArray[a] = new ObjectId(tempArray.getString(a));
			}
			DBObject inClause = new BasicDBObject("$nin", inClauseArray);
			dbObject = new BasicDBObject("_id", inClause);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return succesfullGetOperation(service.multipleMaps(dbObject));
	}
	
	@GET
	@Path("allmaps")
	public Response getAllMaps(){
		return succesfullGetOperation(service.multipleMaps(new BasicDBObject()));
	}
	
	@GET
	@Path("allmapsnoimage")
	public Response getAllMapsNoImage(){
		return succesfullGetOperation(service.multipleMapsNoImage(new BasicDBObject()));
	}
	
	@POST
	@Path("")
	public Response insertMap(String json){
		return service.mapExistsWithName(json) ? Response.status(406).build() : succesfullInsertNewRowOperation(service.insertMapObject(PTObject.MAP, json));
	}
	
	@PUT
	@Path("")
	public Response updateMap(@QueryParam("id") String id, String json){
		return succesfullInsertOperation(service.updateMapObject(id, json));
	}
	
	@DELETE
	@Path("")
	public Response deleteDevice(@QueryParam("id") String id){
		return succesfullInsertOperation(service.deletePtObject(PTObject.MAP, id));
}
	
}
