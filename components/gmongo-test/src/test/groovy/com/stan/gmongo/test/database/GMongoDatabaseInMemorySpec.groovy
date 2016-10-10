package com.stan.gmongo.test.database


import static com.stan.gmongo.api.client.GMongoClientProvider.create

import spock.lang.Ignore;
import spock.lang.Specification

import com.stan.gmongo.api.client.GMongoClientType

@Ignore
class GMongoDatabaseInMemorySpec extends Specification{
	
	private static String databaseName = "testDB"

	def "should call listCollectionNames method"(){
		
		setup:
			def client = create{
				type  GMongoClientType.IN_MEMORY
			}
			def db = client.use("testDB")
			
		when:
			db.createCollection("testCollection")
			
		then:
			db.collections().size == 1
			
	}
}
