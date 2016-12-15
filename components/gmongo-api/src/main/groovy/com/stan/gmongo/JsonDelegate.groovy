package com.stan.gmongo

import java.time.LocalDateTime
import java.time.ZoneId

import org.bson.BsonArray
import org.bson.BsonBoolean
import org.bson.BsonDateTime
import org.bson.BsonDocument
import org.bson.BsonDouble
import org.bson.BsonInt32
import org.bson.BsonInt64
import org.bson.BsonNull
import org.bson.BsonObjectId
import org.bson.BsonString
import org.bson.BsonValue
import org.bson.conversions.Bson
import org.bson.types.ObjectId;

class JsonDelegate {
	
	Bson document = new BsonDocument()
	
	final BsonConverter converter = new BsonConverter()
	
	private def BsonValue convertToBsonValue(value){
		if(value == null){
			return BsonNull.VALUE
		}
		switch (value.getClass()){
			case ArrayList:
				def array = value.collect{
					convertToBsonValue(it)
				}
				return new BsonArray(array)
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
			case LocalDateTime:
				def timestamp = value.atZone(ZoneId.systemDefault())
					.toInstant().toEpochMilli()
				return new BsonDateTime(timestamp)
			case ObjectId:
				return new BsonObjectId(value)
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
	
	
	def propertyMissing(String name){
		new JsonArrayDelegate(this, name)
	}
	
	Bson getBson(){
		return document
	}
	
}
