package com.stan.gmongo.impl.collection

import com.stan.gmongo.api.collection.GMongoFindModifyOptions

import groovy.lang.Closure;

class GMongoFindModifyOptionsDefault implements GMongoFindModifyOptions{
	
	Closure query
	Closure update
	Closure sort
	Closure fields
	boolean isNew
	boolean upsert

	@Override
	def query(Closure query){
		this.query = query
	}
	
	@Override
	def sort(Closure sort){
		this.sort = sort
	}
	
	@Override
	def 'new'(Boolean isNew){
		this.isNew = isNew
	}
	
	@Override
	def fields(Closure fields){
		this.fields = fields
	}
	
	@Override
	def upsert(Boolean upsert){
		this.upsert = upsert
	}

	@Override
	def update(Closure update) {
		this.update = update
	}
}
