package com.stan.gmongo.orm.session

import com.stan.gmongo.api.client.GMongoClient;

import spock.lang.Specification

class SessionContextSpec extends Specification{

	def "Should delegate calls to client"(){
		given:
			GMongoClient client = Mock()
			def context = new SessionContext(client)
		when:
			context.use("testDB")
		then:
			1*client.use("testDB")
		
		
	}
}
