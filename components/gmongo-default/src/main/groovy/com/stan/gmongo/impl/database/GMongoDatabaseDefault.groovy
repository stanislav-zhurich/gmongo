package com.stan.gmongo.impl.database

import java.util.concurrent.ConcurrentHashMap

import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.CreateCollectionOptions
import com.stan.gmongo.api.collection.GMongoCollection
import com.stan.gmongo.api.database.GMongoDatabase
import com.stan.gmongo.impl.collection.GMongoCollectionDefault

class GMongoDatabaseDefault implements GMongoDatabase{

	final MongoDatabase database
	
	final ConcurrentHashMap collectionCache = new ConcurrentHashMap()
	
	GMongoDatabaseDefault(def client, String name){
		this.database = client.getDatabase(name)
	}
	
	List<String> collections(){
		def collections = database.listCollectionNames()
		collections.into([])
	}
	
	GMongoCollection createCollection(String name, @DelegatesTo(CreateCollectionOptions) Closure options){
		new GMongoCollectionDefault(database, name, options)
	}
	
	GMongoCollection createCollection(String name){
		def onDropCallback = {collectionName -> collectionCache.remove(collectionName)}
		def computeCollection = {collectionName -> new GMongoCollectionDefault(database, collectionName, onDropCallback)}
		def colln = collectionCache.computeIfAbsent(name, computeCollection)
		return colln
	}

	@Override
	void dropDatabase() {
		collectionCache.clear()
		database.drop()
	}

	@Override
	GMongoCollection getCollection(String name) {
		createCollection(name)
	}
}
