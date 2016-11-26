package com.stan.gmongo.impl

import spock.lang.Specification;

class CollectionAggregationSpec extends AbstractCollectionSpec{

	def "Count Documents By Age"(){
		given:
			db.users.insert(
					persons()
					)
		when:
			def result = db.users.aggregate([
				{
					'$group'  {
						_id '$person.age'
						count {'$sum' 1
						} }
				}
			])
			def list = []
			result.forEach{ list << it }

		then:
			def a = list.grep{it.get('_id').getValue() == 31}
			a[0].get("count").getValue() == 2
	}
	
	def "Find Document"(){
		given:
			db.users.insert(persons())
		when:
			def result = db.users.aggregate([
				{'$match'  { 'person.id' 1} },
				{
				'$group'  {
					_id '$person.id'
					count {'$sum' 1} }
				}
			]).first()
		then:
			result.get("count").getValue() == 1
	}
	
	def "Calculate Average"(){
		given:
			db.users.insert(persons())
		when:
			def result = db.users.aggregate([
				{
					'$group'  {
						_id 1
						average {'$avg' '$person.age'}
						count {'$sum' 1}
					 }
				}
			])
			def list = []
			result.forEach{ list << it }
	
		then:
			list[0].get("average").getValue() == 30.75
	}
	
	def "Normalize and Sort Documents"(){
		given:
			db.users.insert(persons())
		when:
			def result = db.users.aggregate(
				[
					{ '$project' { 
						'name' { '$toUpper' '$person.name'} 
						 _id 0 } 
					},
					{ '$sort' { 'name'  1 } }
				]
			)
			def a = result.first()	
		then:
			a.get('name').getValue() == 'IVAN'
	}
	
	def "Most common department count"(){
		given:
			db.users.insert(persons())
		when:
			def result = db.users.aggregate(
				[
					{'$unwind' '$person.departments'},
					{'$group'  {
						_id '$person.departments.name'
						count {'$sum' 1} 
					}},
					{'$sort' {'count' (-1)}}
				
				]
			)
		then:
			result.first().get('_id').getValue() == 'dep1'
	}
	
	def "Save Result Pipeline to New Collection"(){
		given:
			db.users.insert(persons())
		when:
			def result = db.users.aggregate(
				[
					
					{'$unwind' '$person.departments'},
					{'$group'  {
						_id '$person.departments.name'
						count {'$sum' 1} 
					}},
					{'$out' 'test'}
				
				]
			)
		then:
			def grad = db.graduation.find()
			//def list = []
			//result.forEach{ list << it }
			println grad
	}
	
	def "Document Reshaping"(){
		given:
			db.users.insert(persons())
		when:
			def result = db.users.aggregate(
				[
					
					{'$match'  {'person.id' 1}},
					{'$project' {'username' '$person.name'}}
				
				]
			)
		then:
		  result.first().get('username').getValue() == 'Stan'

	}
	
	
}
