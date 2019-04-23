package org.epos_ip.basicCerifConverterPlugin.core;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;


public class DatabaseConnection<BsonArray>  {

	private static DatabaseConnection<Object> md;
	private MongoClient mongo;

	public static DatabaseConnection<Object> getInstance()
	{
		if(md==null) md = new DatabaseConnection<Object>();
		return md;
	}

	private DatabaseConnection()
	{
		mongo = MongoClients.create("mongodb://localhost:27017");
	}

	public MongoClient getMongo() {
		return mongo;
	}

	public void setMongo(MongoClient mongo) {
		this.mongo = mongo;
	}
	
	public MongoCollection<Document> getTableDefaults() {
		return mongo.getDatabase("scheduler").getCollection("defaults");
	}

	public MongoCollection<Document> getDDSSTable() {
		return mongo.getDatabase("scheduler").getCollection("ddssTable");
	}
}
