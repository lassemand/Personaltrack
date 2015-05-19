package com.lma.pt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

import java.security.Key;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;

import com.lma.pt.service.Security;

@Path("token")
@Produces("application/json")
public class Token extends ModelClassesParent {

	@POST
	@Path("retrieve")
	public Response retrieveToken(String information){
		try {
			JSONObject object = new JSONObject(information);
			String username = object.getString("username");
			String password = object.getString("password");
			boolean valid =  service.validateUser(username, password);
			if(valid){
				String s = Security.getInstance().generateAndSaveKey(username);
				return Response.accepted().entity(s).build();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.status(406).build();
	}
	
}
