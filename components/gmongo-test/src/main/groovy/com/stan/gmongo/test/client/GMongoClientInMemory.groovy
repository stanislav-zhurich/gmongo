package com.stan.gmongo.test.client

import com.github.fakemongo.Fongo
import com.stan.gmongo.api.client.GMongoClient
import com.stan.gmongo.api.client.GMongoClientType
import com.stan.gmongo.api.client.MongoServerConnection
import com.stan.gmongo.api.database.GMongoDatabase
import com.stan.gmongo.test.database.GMongoDatabaseInMemory

class GMongoClientInMemory extends GMongoClient{
	
	final Fongo client
	
	GMongoClientInMemory(GMongoClientType type, MongoServerConnection connection){
		super(type, connection)
		this.client = new Fongo("mongo server 1");
	}

	GMongoDatabase use(String database){
		new GMongoDatabaseInMemory(client, database);
	}
}
