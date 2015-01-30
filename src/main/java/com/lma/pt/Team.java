package com.lma.pt;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;


@Path("team")
@Produces("application/json")
public class Team {

	
	@GET
	@Path("/test")
	public Response myTest(){
		return Response.status(200).entity("WORKSWORKS").build();
	}
}
