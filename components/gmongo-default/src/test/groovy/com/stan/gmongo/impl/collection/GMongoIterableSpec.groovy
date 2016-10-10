package com.stan.gmongo.impl.collection

import spock.lang.Specification

import com.mongodb.Block
import com.mongodb.client.FindIterable
import com.stan.gmongo.api.collection.GMongoIterable

class GMongoIterableSpec extends Specification{
	
	GMongoIterable gMongoIterable 
	FindIterable iterable
	
	def setup(){
		iterable = Mock(FindIterable)
		gMongoIterable = new GMongoIterableDefault(iterable, {})
	}

	def "should call forEach for iterable object"(){
		
		when:
			gMongoIterable.forEach {}
		then:
			1 * iterable.forEach(_)
	}
	
	def "should call map for iterable object"(){
		when:
			gMongoIterable.map {}
		then:
			1 * iterable.map(_)
	}
	
	def "should call first for iterable object"(){
		when:
			gMongoIterable.first()
		then:
			1 * iterable.first()
	}
	
	def "should call into for iterable object"(){
		given:
			def list = []
		when:
			gMongoIterable.into(list)
		then:
			 1 * iterable.into(list)
	}
}
