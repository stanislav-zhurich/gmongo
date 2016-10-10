package com.stan.gmongo.api.collection

import spock.lang.Specification;

class MongoCollectionOptionsSpec extends Specification{

	
	def "should create MongoCollectionOptions from closure"(){
		given:
			def options = new MongoCollectionOptions({
				 autoIndex  false
				 maxDocuments 500
				 capped true
				 sizeInBytes 1000
			})
			
		expect:
			options.collOptions.with {
				capped == true
				autoIndex == false
				maxDocuments == 500
				sizeInBytes == 1000
			}
	}
}
