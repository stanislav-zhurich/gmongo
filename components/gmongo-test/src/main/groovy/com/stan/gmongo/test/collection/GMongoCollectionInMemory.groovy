package com.stan.gmongo.test.collection

import java.util.List;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase
import com.stan.gmongo.api.collection.DeletedResult;
import com.stan.gmongo.api.collection.GMongoCollection
import com.stan.gmongo.api.collection.GMongoIterable;
import com.stan.gmongo.api.collection.MongoCollectionOptions
import com.stan.gmongo.api.collection.WriteResult;

import groovy.lang.Closure;;

class GMongoCollectionInMemory implements GMongoCollection {
	final String name
	final MongoCollectionOptions options
	final MongoCollection collection
	
	GMongoCollectionInMemory(MongoDatabase database, String name){
		this.name = name
	}

	@Override
	public void insert(Closure document) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insert(List documents) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public GMongoIterable find(Closure filter, Closure projection) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GMongoIterable find(Closure filter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GMongoIterable find() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long count(Closure filter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long count() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void drop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public DeletedResult remove() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DeletedResult remove(Closure closure) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DeletedResult remove(Closure closure, boolean justOne) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object findOne(Closure filter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object findOne(Closure filter, Closure projection) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object aggregate(List<Closure> pipeline) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WriteResult update(Closure query, Closure document, Closure options) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WriteResult update(Closure query, Closure document) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object findAndModify(Closure closure) {
		// TODO Auto-generated method stub
		return null;
	}
}
