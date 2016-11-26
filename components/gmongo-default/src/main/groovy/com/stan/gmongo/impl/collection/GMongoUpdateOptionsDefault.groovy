package com.stan.gmongo.impl.collection

import com.stan.gmongo.api.collection.GMongoUpdateOptions

class GMongoUpdateOptionsDefault implements GMongoUpdateOptions{

	boolean upsert;
	boolean multi;
	
	def upsert(boolean upsert){
		this.upsert = upsert
	}
	
	def multi(boolean multi){
		this.multi = multi
	}
}
