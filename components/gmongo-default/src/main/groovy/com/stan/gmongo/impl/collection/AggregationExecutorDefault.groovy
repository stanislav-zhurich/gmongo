package com.stan.gmongo.impl.collection

import java.util.List

import com.mongodb.client.MongoCollection;
import com.stan.gmongo.api.collection.AggregationExecutor
import com.stan.gmongo.api.collection.GAggregateMongoIterable;

import groovy.lang.Closure
import groovy.transform.TupleConstructor;;;


class AggregationExecutorDefault implements AggregationExecutor{
	
	private MongoCollection collection
	@Delegate private GAggregateMongoIterable iterable
	
	AggregationExecutorDefault(collection){
		this.collection = collection
	}
	
	@Override
	def aggregate(List<Closure> pipeline) {
		def bsonPipelines = pipeline.collect{
			bson(it)
		}
		this.iterable = new GAggregateMongoIterableDefault(collection.aggregate(bsonPipelines))
		this.iterable
	}

}
