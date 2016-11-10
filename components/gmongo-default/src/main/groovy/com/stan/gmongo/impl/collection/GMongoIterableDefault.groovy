package com.stan.gmongo.impl.collection

import java.util.concurrent.TimeUnit

import com.mongodb.Block
import com.mongodb.client.MongoIterable
import com.stan.gmongo.api.collection.GMongoIterable


class GMongoIterableDefault implements GMongoIterable{
	
	private final MongoIterable iterable
	private final Map options = [:]
	
	GMongoIterableDefault(iterable){
		this.iterable = iterable
	}
	
	GMongoIterableDefault(iterable, projectionFilter){
		this(iterable)
		projection(projectionFilter)
	}

	@Override
	<T> void forEach(Closure<T> closure) {
		iterable.forEach((Block<T>)closure)
	}

	@Override
	<T> GMongoIterable map(Closure<T> closure) {
		new GMongoIterableDefault( iterable.map{closure})
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
	public GMongoIterable limit(Integer limit) {
		iterable.limit(limit)
		options << ['limit':limit]
		this
	}

	@Override
	public GMongoIterable skip(Integer skip) {
		iterable.skip(skip)
		options << ['skip':skip]
		this
	}

	@Override
	public GMongoIterable maxTime(long maxTime, TimeUnit timeUnit) {
		iterable.maxTime(maxTime, timeUnit)
		options << ['maxTime':['maxTime':maxTime, 'timeUnit':timeUnit]]
		this
	}

	@Override
	public GMongoIterable modifiers(Closure closure) {
		def modifiers = bson(closure)
		iterable.modifiers(modifiers)
		options << ['modifiers':modifiers]
		return this;
	}

	@Override
	public GMongoIterable projection(Closure closure) {
		def projection = bson(closure)
		iterable.projection(projection)
		options << ['projection':projection]
		return this;
	}
	
	def GMongoIterable sort(Closure closure){
		def sort = bson(closure)
		iterable.sort(sort)
		options << ['sort':sort]
		return this;
	}
	
	def propertyMissing(String name){
		return options."$name"?:super.propertyMissing(name)
	}
	
	

}
