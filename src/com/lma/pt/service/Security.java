package com.lma.pt.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;

public class Security {

	private String s;
	private Map<String, String> permissions = new HashMap<String, String>();
	
	private static Security security = new Security();
	
	private Security(){

	}
	
	public static Security getInstance(){
		return security;
	}
	
	public String generateAndSaveKey(String user){
		Key key = MacProvider.generateKey();
		s = Jwts.builder().setSubject(user).signWith(SignatureAlgorithm.HS512, key).compact();
		permissions.put(user, s);
		return s;
	}
	
	public boolean validateKey(String key, String user){
		for(String currentUser: permissions.keySet()){
			System.out.println("CurrentKey: " + currentUser);
			System.out.println("CurrentValue: " + permissions.get(currentUser));
		}
		System.out.println("Key: " + key + " User: " + user);
		
		if(permissions.containsKey(user))
			if(permissions.get(user).equals(key))
				return true;
		return false;
	}
	
	@Override
	public String toString(){
		return s; 
	}
	
}
