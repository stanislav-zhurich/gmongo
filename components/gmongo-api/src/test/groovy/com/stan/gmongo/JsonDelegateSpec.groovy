package com.stan.gmongo

import spock.lang.Specification

class JsonDelegateSpec extends Specification{
	
	def delegate
	
	def setup(){
		delegate = new JsonDelegate()
	}

	def "should throw exception when invalid format"(){
		when:
			delegate.methodWithoutParameters()
		then:
			thrown(IllegalArgumentException)
	}

	def "should convert string value"(){
		when:
			delegate.stringMethod("String value")
		then:
			delegate.getDocument().isString('stringMethod') == true
	}
	
	def "should convert integer value"(){
		when:
			delegate.intMethod(5)
		then:
			delegate.getDocument().isInt32('intMethod') == true
	}
	
	def "should convert long value"(){
		when:
			delegate.longMethod(5L)
		then:
			delegate.getDocument().isInt64('longMethod') == true
	}
	
	def "should convert double value"(){
		when:
			delegate.doubleMethod(6.3)
		then:
			delegate.getDocument().isDouble('doubleMethod') == true
	}
	
	def "should convert boolean value"(){
		when:
			delegate.booleanMethod(true)
		then:
			delegate.getDocument().isBoolean('booleanMethod') == true
	}
	
	def "should convert nested object"(){
		when:
			delegate.person {
				firstName 'Stan'
				age 29
			}
		then:
			delegate.getDocument().isDocument("person") == true
	}
	
	def "should convert array"(){
		when:
			delegate.array 1, 2, 3
		then:
			delegate.getDocument().isArray('array') == true
	}
	
	def "should convert array with nested object"(){
		when:
			delegate.array({id 1},{id 2})
		then:
			delegate.getDocument().isArray('array') == true
	}
	
	def "should convert null value"(){
		when:
			delegate.nullMethod(null)
		then:
			delegate.getDocument().isNull('nullMethod') == true
	}
}
