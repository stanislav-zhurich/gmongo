package com.stan.gmongo.impl.database

import spock.lang.Ignore
import spock.lang.Specification

import com.mongodb.MongoClient
import com.mongodb.client.MongoDatabase
import com.mongodb.client.MongoIterable
import com.stan.gmongo.api.client.GMongoClientType
import com.stan.gmongo.api.collection.GMongoCollection

class GMongoDatabaseDefaultSpec extends Specification{
	
	private static String databaseName = "testDB"
	
	def database
	def mongoDB
	def client
	
	def setup(){
		mongoDB = Mock(MongoDatabase)
		
		client = Mock(MongoClient)
		client.getDatabase(databaseName) >> mongoDB
		database = new GMongoDatabaseDefault(client, databaseName)
	}

	def "should call listCollectionNames method"(){
			
		when :
			database.collections()
		then :
			1 * mongoDB.listCollectionNames() >> Mock(MongoIterable)
		
	}
	
	@Ignore
	def "should create collection with options"(){
		when:
			GMongoCollection colln = database.createCollection("testColln", {
				autoIndex true
				capped true
				maxDocuments 100
			})
			
		then:
			1 * mongoDB.createCollection("testColln", _)
		
			
		then:
			colln.name == "testColln"
			colln.options.collOptions.with{
				autoIndex == true
				capped == true
				maxDocuments == 100
			}
	}
	
	def "should drop database"(){
		when :
			database.dropDatabase()
		then :
			1 * mongoDB.drop()
	}
}
