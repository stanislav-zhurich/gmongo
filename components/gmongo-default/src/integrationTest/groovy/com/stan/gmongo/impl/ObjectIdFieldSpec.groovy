package com.stan.gmongo.impl

import static com.stan.gmongo.api.client.GMongoClientProvider.create

import org.bson.BsonObjectId

import spock.lang.Shared
import spock.lang.Specification

import com.stan.gmongo.api.client.GMongoClientType

class ObjectIdFieldSpec extends Specification{
	
	@Shared def client
	@Shared def db
	def collection

	def setupSpec(){
		client = create{
			type  GMongoClientType.DEFAULT
		 }
		db = client.use("testDB")
	}
	
	def cleanupSpec(){
		db.dropDatabase()
	}
	
	def setup(){
		collection = db.createCollection('testObjectId')
	}
	
	def cleanup(){
		collection.drop()
	}
	
	def "Generate ObjectId"(){
		given:
			def id = objectId()
			def json = {
				_id id
				name "Stan"
			}
		when:
			db.testObjectId.insert(json)
		then:
			def result = db.testObjectId.findOne({})
			def a = result.get("_id")
			a == new BsonObjectId(id)
	}
}

