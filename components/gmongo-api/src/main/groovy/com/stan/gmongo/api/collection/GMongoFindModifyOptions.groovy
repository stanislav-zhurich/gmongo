package com.stan.gmongo.api.collection

import org.bson.conversions.Bson;

import com.mongodb.client.model.ReturnDocument;

trait GMongoFindModifyOptions {
	
	abstract def query(Closure query)
	abstract def sort(Closure sort)
	abstract def 'new'(Boolean isNew)
	abstract def fields(Closure fields)
	abstract def upsert(Boolean upsert)
	abstract def update(Closure update)
}
