package com.lma.pt;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONException;


@Path("devicelocation")
@Produces("application/json")
public class DeviceLocation extends ModelClassesParent{

	@POST
	@Path("")
	public Response insertDeviceLocation(String json){
		try {
			return succesfullInsertOperation(service.insertDeviceLocation(json));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.status(400).build();
	}
	
}
