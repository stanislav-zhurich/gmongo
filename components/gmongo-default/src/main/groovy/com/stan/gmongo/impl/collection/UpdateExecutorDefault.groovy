package com.stan.gmongo.impl.collection

import groovy.transform.TupleConstructor
import groovy.util.logging.Slf4j

import com.mongodb.client.MongoCollection
import com.mongodb.client.model.UpdateOptions
import com.mongodb.client.result.UpdateResult
import com.stan.gmongo.api.collection.UpdateExecutor
import com.stan.gmongo.api.collection.WriteResult

@TupleConstructor
@Slf4j
class UpdateExecutorDefault implements UpdateExecutor {
	
	final MongoCollection collection
	
	@Override
	WriteResult execute(Closure query, Closure document, @DelegatesTo(GMongoUpdateOptionsDefault) Closure optionsClosure) {
		
		def bsonQuery = bson(query)
		def bsonDocument = bson(document)
		
		def updateOptions = new UpdateOptions()
		GMongoUpdateOptionsDefault options = new GMongoUpdateOptionsDefault()
		if(options != null){
			optionsClosure.resolveStrategy = Closure.DELEGATE_ONLY
			optionsClosure.delegate = options
			optionsClosure.call()
			updateOptions.upsert(options.upsert)
		}
		log.debug("find documents: ${bsonQuery.toJson()}")
		log.debug("replace with: ${bsonDocument.toJson()}")
		UpdateResult result
		if(options.multi){
			result = collection.updateMany(bsonQuery, bsonDocument, updateOptions)
		}
		else{
			result = collection.updateOne(bsonQuery,bsonDocument, updateOptions)
		}
		
		new WriteResultDefault(matched:result.getMatchedCount(), modified:result.getModifiedCount())
		
	}

}
