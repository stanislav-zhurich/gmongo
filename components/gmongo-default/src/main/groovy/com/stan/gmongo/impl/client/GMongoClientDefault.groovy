package com.stan.gmongo.impl.client

import groovy.transform.TypeChecked

import com.mongodb.MongoClient
import com.mongodb.MongoClientURI
import com.stan.gmongo.api.client.GMongoClient
import com.stan.gmongo.api.client.GMongoClientType
import com.stan.gmongo.api.client.MongoServerConnection
import com.stan.gmongo.api.database.GMongoDatabase
import com.stan.gmongo.impl.database.GMongoDatabaseDefault

 
class GMongoClientDefault extends GMongoClient{
	
	final MongoClient client
	
	GMongoClientDefault(GMongoClientType type, MongoServerConnection connection){
		super(type, connection)
		def uri = URIBuilder.build(connection)
		def connectionString = new MongoClientURI(uri)
		this.client = new MongoClient(connectionString)
	}
	
	
	GMongoDatabase use(String database){
		new GMongoDatabaseDefault(client, database)
	}
	
	static class URIBuilder{
		
		
		static String build(MongoServerConnection connection){
			StringBuilder uri = new StringBuilder("mongodb://");
			if(connection.authentication){
				uri.append(connection.authentication.username)
					.append(":").append(connection.authentication.password).append("@")
			}
			uri.append(hosts(connection.serverAddresses))
			uri.append("/")
			if(connection.database){
				uri.append(connection.database)
			}
			
			String options = options(connection.options)
			if(options){
				uri.append("?")
				uri.append(options)
			}
			uri.toString()
		}
		
		
		private static String hosts(List addresses){
			addresses.collect{it.host + ":" + it.port}.join(",")
		}
		
		@TypeChecked
		private static String options(Map options){
			List opt = []
			options.each{
				opt.add(it.key.toString() + "=" + it.value)
			}
			return opt.join("&")
		}
		
	}
}
