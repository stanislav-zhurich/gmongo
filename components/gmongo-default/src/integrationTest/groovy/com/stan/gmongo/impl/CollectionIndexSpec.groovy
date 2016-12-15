package com.stan.gmongo.impl

class CollectionIndexSpec extends AbstractCollectionSpec {

	def "Create an Ascending Index on a Single Field"(){
		given:
			db.users.insert(
				persons()
			)
			
		when:
			def result = db.users.createIndex({'person.name' 1})
			def indexes = db.users.getIndexes()
		then:
			def indexesNames = []
			indexes.into(indexesNames)
			indexesNames.collect{it -> it.getString('name')} == ['_id_', 'person.name_1']
		}
	
	def "Create an Index on a Multiple Fields"(){
		given:
			db.users.insert(
				persons()
			)
			
		when:
			db.users.createIndex({'person.name' 1; 'person.age' 1})
			def indexes = db.users.getIndexes()
		then:
			def indexesNames = []
			indexes.into(indexesNames)
			indexesNames.collect{it -> it.getString('name')} == ['_id_', 'person.name_1_person.age_1']
	}
	def "Create an Index with options"(){
		given:
			db.users.insert(
				persons()
			)
			
		when:
			db.users.createIndex({'person.name' 1}, {background true})
			def indexes = db.users.getIndexes()
		then:
			def indexesNames = []
			indexes.into(indexesNames)
			def names = indexesNames.collect{it -> it.getBoolean('background')}
			names == [null, true]
		}
}
