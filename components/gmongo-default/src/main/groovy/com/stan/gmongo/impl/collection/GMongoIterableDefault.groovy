package com.stan.gmongo.impl.collection

import java.util.concurrent.TimeUnit

import com.mongodb.Block
import com.mongodb.client.MongoIterable
import com.stan.gmongo.api.collection.GMongoIterable


class GMongoIterableDefault extends GMongoBaseIterableDefault implements GMongoIterable{
	
	GMongoIterableDefault(iterable){
		super(iterable)
	}
	
	GMongoIterableDefault(iterable, projectionFilter){
		this(iterable)
		projection(projectionFilter)
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
