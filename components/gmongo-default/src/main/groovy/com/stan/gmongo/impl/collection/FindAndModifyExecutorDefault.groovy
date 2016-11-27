package com.stan.gmongo.impl.collection

import com.mongodb.client.MongoCollection
import com.mongodb.client.model.FindOneAndDeleteOptions;
import com.mongodb.client.model.FindOneAndUpdateOptions
import com.mongodb.client.model.ReturnDocument;
import com.stan.gmongo.api.collection.FindAndModifyExecutor

import groovy.lang.Closure
import groovy.transform.TupleConstructor
import groovy.util.logging.Slf4j

@TupleConstructor
@Slf4j
class FindAndModifyExecutorDefault implements FindAndModifyExecutor {

	final MongoCollection collection
	
	@Override
	public def execute(Closure closure) {
		def options = new GMongoFindModifyOptionsDefault()
		closure.resolveStrategy = Closure.DELEGATE_ONLY
		closure.delegate = options
		closure.call()
		def query = bson(options.query)
		def update = bson(options.update)
		log.debug("find documents: ${query.toJson()}")
		log.debug("update: ${update.toJson()}")
		def driverOptions = new FindOneAndUpdateOptions()
		if(options.fields){
			driverOptions.projection(bson(options.fields))
		}
		if(options.sort){
			driverOptions.projection(bson(options.sort))
		}
		if(options.isNew){
			driverOptions.returnDocument(ReturnDocument.AFTER)
		}
		
		driverOptions.upsert(options.upsert)

		collection.findOneAndUpdate(query, update, driverOptions)
		
	}

}
