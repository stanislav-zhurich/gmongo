package com.stan.gmongo.extension

import org.bson.conversions.Bson

import com.stan.gmongo.BsonConverter

class JsonExtension {
	
	private static BsonConverter converter = new BsonConverter()
	
	static Bson bson(final GroovyObject self, final Closure closure) {
		converter.toBson(closure) 
	}
	
	static List<Bson> bson(final GroovyObject self, final List<Closure> closures) {
		closures.collect{
			converter.toBson(it)
		}
	}
}
