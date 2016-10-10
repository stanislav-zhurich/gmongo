package com.stan.gmongo.impl

import com.stan.gmongo.api.collection.DeletedResult

class CollectionDocumentRemoveSpec extends AbstractCollectionSpec{

	def 'Remove All Documents from a Collection'(){
		given:
			db.users.insert(
				persons()
			)
			
		when:
			DeletedResult deleted = db.users.remove()
			def result = db.users.find()
			def list = []
			result.into(list)
		then:
			deleted.count == 4
			list.size() == 0
		}
	
	def 'Remove All Documents that Match a Condition'(){
		given:
			db.users.insert(
				persons()
			)
			
		when:
			DeletedResult deleted = db.users.remove({'person.age' {'$lt' 30}})
			def result = db.users.find()
			def list = []
			result.into(list)
		then:
			list.size() == 3
			deleted.count == 1
		}
	
	def 'Remove a Single Document that Matches a Condition'(){
		given:
			db.users.insert(
				persons()
			)
			
		when:
			DeletedResult deleted = db.users.remove({'person.age' {'$gt' 30}}, true)
			def result = db.users.find()
			def list = []
			result.into(list)
		then:
			list.size() == 3
			deleted.count == 1
		}
}
