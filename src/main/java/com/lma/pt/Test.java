package com.lma.pt;

import java.net.UnknownHostException;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        MongoClient mongoClient;
		try {
			mongoClient = new MongoClient( "localhost" , 27017 );
	        // Now connect to your databases
	        DB db = mongoClient.getDB("pt");
	        DBCollection collection = db.getCollection("team");
	        BasicDBObject whereQuery = new BasicDBObject().append("_id", new ObjectId("54cb4b8dccc5aadcdf4a6c23"));
	        DBCursor cursor = collection.find(whereQuery);
	    	while(cursor.hasNext()) {
	    	    System.out.println(cursor.next());
	    	}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
