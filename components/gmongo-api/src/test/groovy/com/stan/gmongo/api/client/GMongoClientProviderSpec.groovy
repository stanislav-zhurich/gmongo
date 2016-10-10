package com.stan.gmongo.api.client

import static com.stan.gmongo.api.client.GMongoClientProvider.create
import spock.lang.Specification

import com.stan.gmongo.api.exception.GMongoClientException

class GMongoClientProviderSpec extends Specification {
	
	
	
	def "shoudl throw exception when custom client not implement GMongoClient"(){
		when:
			def client = create{
				type  String.getName()
			}
		then:
			thrown(GMongoClientException)
			
	}

	def "should return custom client implementation"(){
		
		given:
		
			def client = create{
				type  TestMongoClient.getName()
			}		
		expect:
		
			client.type == GMongoClientType.CUSTOM
	}
}
