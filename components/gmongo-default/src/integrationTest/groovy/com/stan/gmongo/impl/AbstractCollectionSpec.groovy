package com.stan.gmongo.impl

import static com.stan.gmongo.api.client.GMongoClientProvider.create
import spock.lang.Shared
import spock.lang.Specification

import com.stan.gmongo.api.client.GMongoClientType

abstract class AbstractCollectionSpec extends Specification{

	@Shared def client
	@Shared def db
	def collection
	
	def setupSpec(){
		client = create{
			type  GMongoClientType.DEFAULT }
		db = client.use("testDB")
	}
	
	def cleanupSpec(){
		db.dropDatabase()
	}
	
	def setup(){
		collection = db.createCollection('users')
	}
	
	def cleanup(){
		collection.drop()
	}
	
	def persons(){
		return [{person {
					id 1
					name 'Stan'
					age 29
					departments {name 'dep1'} {name 'dep2'}
				}},
				{ person {
					id 2
					name 'Ivan'
					age 31
					departments {name 'dep1'} {name 'dep3'}
				}},
				{ person {
					id 3
					name 'Kate'
					age 32
					departments {name 'dep1'} {name 'dep3'}
					education {name 'Harvard'; year 2005} {name 'BSU'; year 2015}
				}},
				{ person {
					id 4
					name 'Michael'
					age 31
					departments {name 'dep4'}
					education {name 'Harvard'; year 1999} {name 'Yel'; year 2005}
				}}]
	}
}
