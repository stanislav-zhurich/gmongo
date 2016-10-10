package com.stan.gmongo.impl.collection

import spock.lang.Specification

import com.mongodb.client.MongoDatabase
import com.stan.gmongo.api.collection.GMongoCollection;

class GMongoCollectionSpec extends Specification{
	
	def mongoDatabase
	
	def setup(){
		mongoDatabase = Mock(MongoDatabase)
	}
	
	def "should create collection"(){
		when:
			def collection = new GMongoCollectionDefault(mongoDatabase, "test", {})
			
		then:
			1 * mongoDatabase.createCollection("test")
	}
}
