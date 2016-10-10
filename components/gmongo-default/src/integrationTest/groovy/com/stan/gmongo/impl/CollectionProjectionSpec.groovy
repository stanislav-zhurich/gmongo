package com.stan.gmongo.impl

class CollectionProjectionSpec extends AbstractCollectionSpec{

	def "Specify the Fields to Return"(){
		given:
			db.users.insert(
				persons()
			)
			
		when:
			def result = db.users.find({}, {'person.name' 1})
			def list = []
			result.into(list)
		then:
			list.size() == 4
			list[0].get('person').containsKey('name') == true
			list[0].get('person').containsKey('id') == false
	}
	
	def "Explicitly Excluded Fields"(){
		given:
			db.users.insert(
				persons()
			)
			
		when:
			def result = db.users.find({}, {'person.name' 0})
			def list = []
			result.into(list)
		then:
			list.size() == 4
			list[0].get('person').containsKey('name') == false
			list[0].get('person').containsKey('id') == true
	}
	
	def "Explicitly Exclude the _id Field"(){
		given:
			db.users.insert(
				persons()
			)
			
		when:
			def result = db.users.find({}, {'person.name' 0; '_id' 0})
			def list = []
			result.into(list)
		then:
			list.size() == 4
			list[0].get('person').containsKey('name') == false
			list[0].containsKey('_id') == false
	}
	
	def "On Arrays and Embedded Documents"(){
		given:
			db.users.insert(
				persons()
			)
			
		when:
			def result = db.users.find({'person.name' 'Kate'}, {'person.education' {'$slice' 1}})
			def list = []
			result.into(list)
		then:
			list.size() == 1
			list[0].get('person').getArray('education').getValues().size() == 1
			
	}
}
