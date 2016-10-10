package com.stan.gmongo.api.client

import spock.lang.Specification


class MongoServerAddressSpec extends Specification {
	
	
	def "server address should have default host and port"(){
		
		given:
			def serverAddress = new MongoServerAddress();
		
		expect:
			serverAddress.host == "localhost"
			serverAddress.port == 27017
	}
	
	def "server address should have specified host and port"(){
		given:
			def serverAddress = new MongoServerAddress("127.0.0.1", 8080);
			
		expect:
			serverAddress.host == "127.0.0.1"
			serverAddress.port == 8080
	}
	
	
}
