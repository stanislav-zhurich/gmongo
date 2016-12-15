package com.stan.gmongo.impl.collection

import groovy.transform.TupleConstructor
import groovy.util.logging.Slf4j

import com.mongodb.client.MongoCollection
import com.stan.gmongo.api.collection.GMongoIndexIterable
import com.stan.gmongo.api.collection.IndexExecutor

@TupleConstructor
@Slf4j
class IndexExecutorDefault implements IndexExecutor {

	final MongoCollection collection
	
	@Override
	String executeCreate(Closure keys, Closure options) {
		def bsonKeys = bson(keys)
		def indexOptions = new GMongoIndexOptionsDefault()
		if(options != null){
			options.resolveStrategy = Closure.DELEGATE_ONLY
			options.delegate = indexOptions
			options.call()
		}
		collection.createIndex(bsonKeys, indexOptions.options)
	}

	@Override
	void executeDrop(String index) {
		collection.dropIndex(index)
	}
	
	@Override
	GMongoIndexIterable executeFind(){
		new GMongoIdexIterableDefault(collection.listIndexes())
	}

}
