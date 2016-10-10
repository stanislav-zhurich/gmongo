package com.stan.gmongo.impl.client

import static com.stan.gmongo.api.client.GMongoClientProvider.create
import spock.lang.Specification

import com.mongodb.MongoClient
import com.stan.gmongo.api.client.GMongoClientType

class GMongoClientDefaultSpec extends Specification{
	
	def setup(){
		GroovySpy(MongoClient, global: true)
		new MongoClient(_) >> Mock(MongoClient)
	}

	def "should return client with single address"(){
		given:
			def client = create{
				type  GMongoClientType.DEFAULT
				database  "catalog"
				authentication {
					username "Stan"
					password "qwerty"
				}
				options {
					poolSize 10
					ssl true
				}
				addresses {
					connection("host":"127.0.0.1", "port": 27017)
				}
			}

		expect:
			client.type == GMongoClientType.DEFAULT
			client.connection.with {
				ssl == true
				poolSize == 10
				
				serverAddresses[0].with{
					host == "127.0.0.1"
					port == 27017
				}
			}
	}

	def "should have default type when no type specified"(){
		given:
			def client = create{}

		expect:
			client.type == GMongoClientType.DEFAULT
	}

	def "should have default connection when no connection specified"(){
		given:
		def client = create{}

		expect:
		client.connection.with {
				ssl == false
				poolSize == null
				username == null
				password == null
				
				serverAddresses[0].with{
					host == "localhost"
					port == 27017
				}
			}
	}
}
