package com.stan.gmongo

import org.bson.BsonArray
import org.bson.BsonBoolean
import org.bson.BsonDocument
import org.bson.BsonDouble
import org.bson.BsonInt32
import org.bson.BsonInt64
import org.bson.BsonNull
import org.bson.BsonString
import org.bson.BsonValue
import org.bson.conversions.Bson

class JsonDelegate {
	
	Bson document = new BsonDocument()
	
	final BsonConverter converter = new BsonConverter()
	
	private def BsonValue convertToBsonValue(value){
		if(value == null){
			return BsonNull.VALUE
		}
		switch (value.getClass()){
			case String:
				return new BsonString(value)
			case Integer:
				return new BsonInt32(value)
			case Long:
				return new BsonInt64(value)
			case BigDecimal:
				return new BsonDouble(value)
			case Boolean:
				return new BsonBoolean(value)
			case Closure:
				return converter.toBson(value)
		}
	}

	def methodMissing(String name, args){
		def bsonValue
		if(args.length < 1){
			throw new IllegalArgumentException("At least one argument is expected")
		}
		else if(args.length > 1){
			def array = args.collect{
				convertToBsonValue(it)
			}
			bsonValue = new BsonArray(array)
		}
		else{
			
			bsonValue = convertToBsonValue( args[0])
		}
		document.put(name, bsonValue)
	}
	
	Bson getBson(){
		return document
	}
	
}
