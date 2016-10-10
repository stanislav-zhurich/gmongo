package com.stan.gmongo.test.database

import com.mongodb.client.MongoDatabase
import com.stan.gmongo.api.collection.GMongoCollection
import com.stan.gmongo.api.database.GMongoDatabase
import com.stan.gmongo.test.collection.GMongoCollectionInMemory

import groovy.lang.Closure;

class GMongoDatabaseInMemory implements GMongoDatabase {
	
	final MongoDatabase database
	
	GMongoDatabaseInMemory(def client, String name){
		this.database = client.getDatabase(name)
	}
	
	List<String> collections(){
		def collections = database.listCollectionNames()
		def collns = []
		collections.into(collns)
		collns
	}
	
	
	GMongoCollection createCollection(String name){
		new GMongoCollectionInMemory(database, name)
	}

	@Override
	public GMongoCollection createCollection(String name, Closure options) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void dropDatabase() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public GMongoCollection getCollection(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object propertyMissing(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
