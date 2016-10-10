package com.stan.gmongo

import org.bson.conversions.Bson

class BsonConverter {

	Bson toBson(Closure closure){
		JsonDelegate delegate = new JsonDelegate()
		closure.delegate = delegate
		closure.resolveStrategy = Closure.DELEGATE_FIRST
		closure.call()
		delegate.getBson()
	}
}
