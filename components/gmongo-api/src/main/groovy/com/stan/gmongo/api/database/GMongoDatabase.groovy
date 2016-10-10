package com.stan.gmongo.api.database

import groovy.transform.Canonical

import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.CreateCollectionOptions
import com.stan.gmongo.api.collection.GMongoCollection

trait GMongoDatabase {
	
	/**
	 * 
	 * @return list of collection names
	 */
	abstract List<String> collections()
	
	/**
	 * 
	 * @param name of new collection
	 * @param options for new collections
	 * @return newly created collection
	 */
	abstract GMongoCollection createCollection(String name, @DelegatesTo(CreateCollectionOptions) Closure options)
	
	/**
	 * 
	 * @param name of new collection
	 * @return newly created collection
	 */
	abstract GMongoCollection createCollection(String name)
	
	/**
	 * drop current database
	 */
	abstract void dropDatabase()
	
	/**
	 * 
	 * @param name of the collection
	 * @return collection
	 */
	abstract GMongoCollection getCollection(String name)
	
	/**
	 * delegates call to getCollection method
	 * @param name
	 * @return
	 */
	def propertyMissing(String name) {
        getCollection(name)
    }
}
