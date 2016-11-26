package com.stan.gmongo.impl

class CollectionUpdateSpec extends AbstractCollectionSpec {

	def "Update Single Document"(){
		given:
			db.users.insert(
					persons()
					)
		when:
			def newDocument = {
				person {
					id 1
					name 'Stan The Best'
					age 30
					departments {name 'dep1'} {name 'dep2'}
				}
			}
			db.users.update({'person.id' 1}, {'$set' newDocument})

		then:
			def result = db.users.find({'person.id' 1})
			result.first().get('person').get('name').getValue() == 'Stan The Best'
	}
	
	def "Update Specific Fields"(){
		given:
			db.users.insert(persons())
		when:
			db.users.update({'person.id' 1},
							 {'$set' {'person.age' 17}}
				)
		then:
			def result = db.users.find({'person.id' 1})
			result.first().get('person').get('name').getValue() == 'Stan'
			result.first().get('person').get('age').getValue() == 17
	}
	
	def "Remove Fields"(){
		given:
			db.users.insert(persons())
		when:
			db.users.update({'person.id' 1},
							 {'$unset' {'person.age' 1}}
				)
		then:
			def result = db.users.find({'person.id' 1})
			result.first().get('person').get('name').getValue() == 'Stan'
			result.first().get('person').get('age') == null
	}
	
	def "Insert a New Document if No Match Exists"(){
		given:
			db.users.insert(persons())
		when:
			db.users.update({'person.id' 100},
							 {'$set' {
								 'person.id' 100
								 'person.name' 'Peter'
							}}, {'upsert' true}
				)
		then:
			def result = db.users.find({'person.id' 100})
			result.first().get('person').get('name').getValue() == 'Peter'
			result.first().get('person').get('id').getValue() == 100
	}
	
	def "Update Multiple Documents"(){
		given:
			db.users.insert(persons())
		when:
			db.users.update({'person.id' {'$in' 1,2}}, {'$set' {'person.name' 'Test'}}, {'multi' true})
		then:
			def result = db.users.find({'person.id' {'$in' 1,2}})
			result.forEach{
				it.get('person').get('name').getValue() == 'Test'
			}
			
		}
	
	def "Chech Write Result"(){
		given:
			db.users.insert(persons())
		when:
			def writeResult = db.users.update({'person.id' {'$in' 1,2, 3}}, {'$set' {'person.name' 'Test'}}, {'multi' true})
		then:
			writeResult.matched == 3
			writeResult.modified == 3 
		
	}
	
	def "Update Array Element"(){
		given:
			db.users.insert(persons())
		when:
		
			db.users.update({
					'person.id' 4
					'person.departments.name' 'dep4'
				}, 
				{'$set' {'person.departments.$.name' 'test_department'}})
		then:
			def result = db.users.find({
					'person.id' 4
				}).first()
			println result
	}
}
