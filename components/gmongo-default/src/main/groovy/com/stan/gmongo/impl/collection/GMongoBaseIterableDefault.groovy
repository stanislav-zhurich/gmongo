package com.stan.gmongo.impl.collection

import java.util.concurrent.TimeUnit

import com.mongodb.Block
import com.mongodb.client.MongoIterable
import com.stan.gmongo.api.collection.GMongoBaseIterable
import com.stan.gmongo.api.collection.GMongoIterable

class GMongoBaseIterableDefault implements GMongoBaseIterable {

	final MongoIterable iterable
	final Map options = [:]
	
	GMongoBaseIterableDefault(iterable){
		this.iterable = iterable
	}
	

	@Override
	<T> void forEach(Closure<T> closure) {
		iterable.forEach((Block<T>)closure)
	}

	@Override
	<T> GMongoBaseIterable map(Closure<T> closure) {
		new GMongoBaseIterableDefault( iterable.map{closure})
	}

	@Override
	<S> S first() {
		return iterable.first();
	}

	@Override
	public <T extends Collection> T into(T target) {
		 iterable.into(target);
		 target
	}

	@Override
	public GMongoBaseIterable maxTime(long maxTime, TimeUnit timeUnit) {
		iterable.maxTime(maxTime, timeUnit)
		options << ['maxTime':['maxTime':maxTime, 'timeUnit':timeUnit]]
		this
	}
	
	def propertyMissing(String name){
		return options."$name"?:super.propertyMissing(name)
	}
	
	
}
