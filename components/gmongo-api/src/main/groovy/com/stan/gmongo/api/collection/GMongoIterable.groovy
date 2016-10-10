package com.stan.gmongo.api.collection

import java.util.Collection
import java.util.concurrent.TimeUnit

import org.bson.conversions.Bson;

import com.mongodb.client.FindIterable;;

trait GMongoIterable {

	abstract <T> void forEach(@DelegatesTo(type="T") Closure<T> closure)
	
	abstract <T> GMongoIterable map(@DelegatesTo(type="T") Closure<T> closure)
	
	abstract <S> S first()
	
	abstract <T extends Collection> T into(T target);
	
	abstract GMongoIterable limit(Integer limit)
	
	abstract GMongoIterable skip(Integer skip)
	
	abstract GMongoIterable maxTime(long maxTime, TimeUnit timeUnit)
	
	abstract GMongoIterable modifiers(Closure closure)
		
	abstract GMongoIterable projection(Closure closure)
	
	abstract GMongoIterable sort(Closure closure)
}
