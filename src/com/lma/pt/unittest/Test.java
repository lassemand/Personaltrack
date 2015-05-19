package com.lma.pt.unittest;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.security.Key;

import org.json.JSONException;
import org.json.JSONObject;

import com.lma.pt.service.ImageValidater;
import com.sun.org.apache.xml.internal.security.utils.Base64;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("WTF?");
		Key key = MacProvider.generateKey();
		String s = Jwts.builder().setSubject("Joe2").signWith(SignatureAlgorithm.HS512, key).compact();
		System.out.println("Key: " + s);
	}

}
