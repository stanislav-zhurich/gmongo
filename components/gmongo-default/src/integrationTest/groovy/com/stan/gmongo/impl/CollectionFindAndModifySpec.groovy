package com.stan.gmongo.impl

class CollectionFindAndModifySpec extends AbstractCollectionSpec {

	def "Update and Return"(){
		given:
			db.users.insert(
				persons()
			)
		when:
			def result = db.users.findAndModify({
				query {'person.id' 1}
				update {'$set' {'person.name' 'Yan'}}
			})
			
		then:
			result.get('person').get('name').getValue() == 'Stan'
			def newDoc = db.users.find({'person.id' 1}).first()
			newDoc.get('person').get('name').getValue() == 'Yan'
	}
	
	def "Return New Document"(){
		given:
			db.users.insert(
				persons()
			)
		when:
			def result = db.users.findAndModify({
				query {'person.id' 1}
				update {'$set' {'person.name' 'Yan'}}
				'new' true
			})
			
		then:
			result.get('person').get('name').getValue() == 'Yan'
	}
	
	def "Upsert"(){
		given:
			db.users.insert(
				persons()
			)
		when:
			def result = db.users.findAndModify({
				query {'person.id' 7}
				update {'$set' {person {
					id 7
					name 'Yan'
				}}}
				upsert true
			})
		then:
			def newDoc = db.users.find({'person.id' 7}).first()
			newDoc.get('person').get('name').getValue() == 'Yan'
			newDoc.get('person').get('id').getValue() == 7
	}
}
