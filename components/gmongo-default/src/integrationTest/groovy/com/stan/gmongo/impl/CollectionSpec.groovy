package com.stan.gmongo.impl

import static com.stan.gmongo.api.client.GMongoClientProvider.create

import spock.lang.IgnoreRest;
import spock.lang.Shared;
import spock.lang.Specification

import com.stan.gmongo.api.client.GMongoClientType

class CollectionSpec extends Specification{
	
	@Shared def client
	def db
	
	
	def setupSpec(){
		client = create{ type  GMongoClientType.DEFAULT }
	}
	
	def setup(){
		db = client.use("testDB")
	}
	
	def cleanup(){
		db.dropDatabase()
	}

	def "should create collection"(){
		when: "create two collections"
			def c = db.createCollection("testCollection1")
			def c1 = db.createCollection("testCollection2")
			println c1

		then: "size will be equal 2"
			println db.collections()
			db.collections().size() == 2
	}
	
	
	def "should return all collection names"(){
		given: "two names"
			def collectionNames = ["testCollection1", "testCollection2"]
		when: "create collections with given names"
			collectionNames.forEach{
				db.createCollection(it)
			}		
		then: "these two names will be returned"
			def names = db.collections()	
			names == collectionNames
	}
	

	def "should return collection by name"(){
		when: "create collection name"
			db.createCollection('new_collection')
		then:
			db.new_collection.name == 'new_collection'
	}
	
}
