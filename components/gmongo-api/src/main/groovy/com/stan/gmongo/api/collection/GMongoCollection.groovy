package com.stan.gmongo.api.collection

import groovy.lang.Closure;
import groovy.transform.Canonical

import java.util.List;

import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.CreateCollectionOptions

trait GMongoCollection {
	
	abstract void insert(Closure document)
	
	abstract void insert(List documents)
	
	abstract GMongoIterable find(Closure filter, Closure projection)
	
	abstract GMongoIterable find(Closure filter)
	
	abstract GMongoIterable find()
	
	abstract Long count(Closure filter)
	
	abstract Long count()
	
	abstract void drop()
	
	abstract DeletedResult remove()
	
	abstract DeletedResult remove(Closure closure)
	
	abstract DeletedResult remove(Closure closure, boolean justOne)
	
	abstract def findOne(Closure filter)
	
	abstract def findOne(Closure filter, Closure projection)
	
	abstract def aggregate(List<Closure> pipeline)
	
	abstract WriteResult update(Closure query, Closure document, Closure options = {})
	
	abstract def findAndModify(@DelegatesTo(GMongoFindModifyOptions) Closure closure)
}
