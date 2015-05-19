package com.lma.pt.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONException;
import org.json.JSONObject;

import com.asprise.ocr.Ocr;
public class ImageValidater {

	private static ImageValidater validater = new ImageValidater();
	
	public static ImageValidater getInstance(){
		return validater;
	}
	
	private ImageValidater(){
		Ocr.setUp();
		
	}
	
	public String validateImage(String json) throws IOException, JSONException{
		JSONObject object = new JSONObject(json);
		String base64 = object.getString("image");
		String suffix = object.getString("suffix");
		File f = File.createTempFile("tmp", suffix);
		FileOutputStream fos = new FileOutputStream(f.getAbsolutePath());
		byte[] data = Base64.decodeBase64(base64);
		fos.write(data);
		fos.close();
		Ocr ocr = new Ocr();
		ocr.startEngine("eng", Ocr.SPEED_FASTEST);
		String sFirst = ocr.recognize(new File[] {f},Ocr.RECOGNIZE_TYPE_ALL, Ocr.OUTPUT_FORMAT_PLAINTEXT);
		Pattern pattern = Pattern.compile(": (.*?) |:(.*?)");
		Matcher m = pattern.matcher(sFirst);
		String format = unknownValue();
		
		if(m.find()){
			String matchet = m.group(1);
			if(matchet.matches("^-?\\d+$")){
				JSONObject returnObject = new JSONObject();
				returnObject.put("size", Integer.valueOf(matchet));
				returnObject.put("accepted", true);
				format = returnObject.toString();
			}
		}

		ocr.stopEngine();
		
		return format;


	}
	
	private String unknownValue(){
		return "";
	}
	
}
