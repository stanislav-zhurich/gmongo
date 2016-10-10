package com.stan.gmongo.impl

class CollectionCursorSpec extends AbstractCollectionSpec{

	def 'With forEach() Method'(){
		given:
			db.users.insert(persons())

		when:
			def iterator = db.users.find()
			def list = []
			iterator.forEach{
				list << it.get('person').getString('name').getValue()
			}
		then:
			list == ['Stan', 'Ivan', 'Kate', 'Michael']
	}
	
	def 'Order Documents in the Result Set'(){
		given:
			db.users.insert(persons())
	
		when:
			def iterator = db.users.find().sort({'person.name' 1})
			
			def list = []
			iterator.forEach{
				list << it.get('person').getString('name').getValue()
			}
		then:
			list == ['Ivan', 'Kate', 'Michael', 'Stan']
	}
	
	def 'Limit the Number of Documents to Return'(){
		given:
			db.users.insert(persons())
	
		when:
			def iterator = db.users.find().sort({'person.name' 1}).limit(2)
			
			def list = []
			iterator.forEach{
				list << it.get('person').getString('name').getValue()
			}
		then:
			list == ['Ivan', 'Kate']
	}
	
	def 'Set the Starting Point of the Result Set'(){
		given:
			db.users.insert(persons())
	
		when:
			def iterator = db.users.find().sort({'person.name' 1}).skip(2)
			
			def list = []
			iterator.forEach{
				list << it.get('person').getString('name').getValue()
			}
		then:
			list == ['Michael', 'Stan']

	}
}
