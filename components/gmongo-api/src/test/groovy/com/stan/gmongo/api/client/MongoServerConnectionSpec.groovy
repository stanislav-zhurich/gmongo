package com.stan.gmongo.api.client

import spock.lang.Specification;

class MongoServerConnectionSpec extends Specification{

	def "server conneciton should have default settings if not specified"(){
		given:
			def connection = new MongoServerConnection()
			
		expect:
			connection.with{

				connection.with {
					serverAddresses[0].host == "localhost"
					serverAddresses[0].port == 27017
				}
			}
	}
	
	def "server conneciton should have specified settings"(){
		given:
			def host = "127.0.0.1"
			def port = 9000
			def connection = new MongoServerConnection(
				[new MongoServerAddress(host, port)],
				["ssl":true,
				 "connectionTimeout":1000
				],
				"catalog",
				new MongoServerAuthParams("Stan", "qwerty")	
			)
			
		expect:
			connection.with{
				serverAddresses[0].host == host
				serverAddresses[0].port == port
				ssl == true
				database == "catalog"
				connectionTimeout == 1000
				authentication.username == "Stan"
				authentication.password == "qwerty"
			}
	}
	
	def "can retrieve options"(){
		given:
			def connection = new MongoServerConnection(
				[new MongoServerAddress()],
				["ssl":true,
				 "username":"Stan",
				 "password":"Pass",
				 "database":"catalog",
				 "connectionTimeout":1000
				]
			)
		
		expect:
			connection.with{
				ssl == true
				username == "Stan"
				password == "Pass"
				database == "catalog"
				connectionTimeout == 1000
			}
		}
}
