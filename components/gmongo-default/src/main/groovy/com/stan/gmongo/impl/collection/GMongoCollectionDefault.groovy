package com.stan.gmongo.impl.collection

import groovy.util.logging.Slf4j

import org.bson.BsonDocument
import org.bson.conversions.Bson

import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.result.DeleteResult
import com.stan.gmongo.api.collection.AggregationExecutor
import com.stan.gmongo.api.collection.DeletedResult
import com.stan.gmongo.api.collection.FindAndModifyExecutor
import com.stan.gmongo.api.collection.GMongoCollection
import com.stan.gmongo.api.collection.GMongoIterable
import com.stan.gmongo.api.collection.IndexExecutor
import com.stan.gmongo.api.collection.MongoCollectionOptions
import com.stan.gmongo.api.collection.UpdateExecutor
import com.stan.gmongo.api.collection.WriteResult

@Slf4j
class GMongoCollectionDefault implements GMongoCollection{

	final String name
	final MongoCollectionOptions options
	final MongoCollection collection
	final Closure onDropCallback
	final AggregationExecutor aggegationExecutor
	final UpdateExecutor updateExecutor
	final FindAndModifyExecutor findAndModifyExecutor
	final IndexExecutor indexExecutor
	
	GMongoCollectionDefault(MongoDatabase database, String name, Closure onDropCallback){
		log.debug('Creating collection: $name')
		this.name = name
		this.onDropCallback = onDropCallback
		boolean alreadyExists = false
		def list = database.listCollectionNames().grep{
			it == name
		}
		
		if(list.isEmpty()){
			database.createCollection(name)			
		}
		this.collection = database.getCollection(name, BsonDocument)
		this.aggegationExecutor = new AggregationExecutorDefault(this.collection)
		this.updateExecutor = new UpdateExecutorDefault(this.collection)
		this.findAndModifyExecutor = new FindAndModifyExecutorDefault(this.collection)
		this.indexExecutor = new IndexExecutorDefault(this.collection)
	}

	@Override
	GMongoIterable find(Closure filter = {}, Closure projection = {}) {
		log.debug('Looking for elements')
		Bson bsonFilter = bson(filter)
		log.debug("Applying filter: ${bsonFilter.toJson()}")
		def result = collection.find(bsonFilter)
		new GMongoIterableDefault(result, projection)
	}
	
	@Override
	def findOne(Closure filter, Closure projection = {}) {
		log.debug('Looking for single element')
		def result = find(filter, projection)
		result.first()
	}

	@Override
	void insert(Closure document) {
		def bsonDoc = bson(document)
		log.debug("Insert document:" + bsonDoc.toJson())
		collection.insertOne(bsonDoc)
	}

	@Override
	void insert(List documents) {
		log.debug("Insert multiple documents")
		def bsonDocuments = documents.collect{
			bson(it)
		}
		collection.insertMany(bsonDocuments)
	}

	@Override
	void drop() {
		log.debug('Drop collection:' + this.name)
		collection.drop()
		if(this.onDropCallback){
			this.onDropCallback(this.name)
		}
	}

	@Override
	Long count(Closure filter = {}) {
		def bsonFilter = bson(filter)
		collection.count(bsonFilter)
	}

	@Override
	DeletedResult remove(Closure closure = {}, boolean justOne = false) {
		def bsonFilter = bson(closure)
		log.debug("Remove documents by filter:" + bsonFilter.toJson())
		DeleteResult result
		if(justOne){
			result = collection.deleteOne(bsonFilter)
		}
		else{
			result = collection.deleteMany(bsonFilter)
		}
		new DeletedResult(result.getDeletedCount())
	}

	@Override
	def aggregate(List<Closure> pipeline) {
		aggegationExecutor.aggregate(pipeline)
	}

	@Override
	WriteResult update(Closure query, Closure document, @DelegatesTo(GMongoUpdateOptionsDefault) Closure options) {
		updateExecutor.execute(query, document, options)
	}

	@Override
	WriteResult update(Closure query, Closure document) {
		updateExecutor.execute(query, document, {})
	}

	@Override
	def findAndModify(Closure closure) {
		findAndModifyExecutor.execute(closure)
	}

	@Override
	def createIndex(Closure keys, Closure options) {
		indexExecutor.executeCreate(keys, options)
	}

	@Override
	def createIndex(Closure keys) {
		indexExecutor.executeCreate(keys, {})
	}

	@Override
	def dropIndex(String index) {
		indexExecutor.executeDrop(index)
	}

	@Override
	def getIndexes() {
		indexExecutor.executeFind()
	}
	
}
