package com.stan.gmongo

import groovy.transform.TupleConstructor;

class JsonArrayDelegate {
	
	JsonDelegate jsonDelegate
	String arrayName
	
	JsonArrayDelegate(jsonDelegate, arrayName){
		this.arrayName = arrayName
		this.jsonDelegate = jsonDelegate
	}

	def getAt(value){
		if(!isCollectionOrArray(value)){
			value = [value]
		}
		def bsonValue = jsonDelegate.convertToBsonValue(value)
		jsonDelegate.getBson().put(arrayName, bsonValue)
	}
	
	boolean isCollectionOrArray(object) {
		[Collection, Object[]].any { it.isAssignableFrom(object.getClass()) }
	}
}
