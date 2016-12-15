package com.stan.gmongo.api.collection

import java.util.Collection
import java.util.concurrent.TimeUnit

import org.bson.conversions.Bson;

import com.mongodb.client.FindIterable;;

trait GMongoIterable implements GMongoBaseIterable{
	
	abstract GMongoIterable limit(Integer limit)
	
	abstract GMongoIterable skip(Integer skip)
	
	abstract GMongoIterable modifiers(Closure closure)
		
	abstract GMongoIterable projection(Closure closure)
	
	abstract GMongoIterable sort(Closure closure)
}
