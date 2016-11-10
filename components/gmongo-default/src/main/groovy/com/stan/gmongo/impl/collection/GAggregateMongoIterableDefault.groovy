package com.stan.gmongo.impl.collection

import java.util.Collection
import java.util.concurrent.TimeUnit

import com.mongodb.Block
import com.mongodb.client.AggregateIterable;
import com.stan.gmongo.api.collection.GAggregateMongoIterable
import com.stan.gmongo.api.collection.GMongoIterable;

import groovy.lang.Closure;;

class GAggregateMongoIterableDefault extends GMongoIterableDefault implements GAggregateMongoIterable{
	
	GAggregateMongoIterableDefault(iterable){
		super(iterable)
	}
}
