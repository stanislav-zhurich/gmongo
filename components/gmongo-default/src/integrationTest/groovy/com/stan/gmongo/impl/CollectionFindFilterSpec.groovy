package com.stan.gmongo.impl

import static com.stan.gmongo.api.client.GMongoClientProvider.create
import spock.lang.Shared
import spock.lang.Specification

import com.stan.gmongo.api.client.GMongoClientType

class CollectionFindFilterSpec extends AbstractCollectionSpec{

	def "Find All Documents in a Collection"(){
		given:
			db.users.insert(
				persons()
			)
			
		when:
			def result = db.users.find()
			def list = []
			result.into(list)
		then: 
			list.size() == 4
	}
	
	def "Find Documents that Match Query Criteria"(){
		given:
			db.users.insert(persons())			
		when:
			def result = db.users.find({'person.age' {'$lt' 30}})
			def list = []
			result.into(list)
		then:
			list.size() == 1
			list[0].get('person').get('name').getValue() == 'Stan'
		
	}
	
	def "Query for Equality"(){
		given:
			db.users.insert(persons())
		when:
			def result = db.users.find({'person.name' 'Ivan'})
			def list = []
			result.into(list)
		then:
			list.size() == 1
			list[0].get('person').get('name').getValue() == 'Ivan'
		
	}
	
	def "Query Using Operators"(){
		given:
			db.users.insert(persons())
			
		when:
			def result = db.users.find({'person.id' {'$in' 3,4,50}})
			def list = []
			result.into(list)
		then:
			list.size() == 2	
	}
	
	def "Query Exact Matches on Embedded Documents"(){
		given:
			db.users.insert(persons())
			
		when:
			def result = db.users.find({person {
					id 1
					name 'Stan'
					age 29
					departments [{name 'dep1'}, {name 'dep2'}]
					createdAt dateTime("2011-12-03T10:15:30")
				}})
			def list = []
			result.into(list)
		then:
			list.size() == 1
			list[0].get('person').get('name').getValue() == 'Stan'
	}
	
	def "Query a Field that Contains an Array"(){
		given:
			db.users.insert(persons())
			
		when:
			def result = db.users.find({
					'person.departments' {name 'dep1'} {name 'dep3'}
				})
			def list = []
			result.into(list)
		then:
			list.size() == 2
			list[0].get('person').get('name').getValue() == 'Ivan'
	}
	
	def "Query for an Array Element"(){
		given:
			db.users.insert(persons())
			
		when:
			def result = db.users.find({
					'person.departments.name' 'dep4'
				})
			def list = []
			result.into(list)
		then:
			list.size() == 1
			list[0].get('person').get('name').getValue() == 'Michael'
	}
	
	def "Query an Array of Documents"(){
		given:
			db.users.insert(persons())
			
		when:
			def result = db.users.find({
					'person.education'{
						'$elemMatch'{
							name 'Harvard'
							year {
								'$gt' 2000
							}
						}
					}
				})
			def list = []
			result.into(list)
		then:
			list.size() == 1
			list[0].get('person').get('name').getValue() == 'Kate'
	}
	
	def "Query One Document"(){
		given:
			db.users.insert(persons())
			
		when:
			def result = db.users.findOne({'person.name' 'Stan'})
		then:
			result.get('person').get('name').getValue() == 'Stan'
	}
	
}
