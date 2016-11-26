package com.stan.gmongo.api.collection

trait GMongoUpdateOptions {

	abstract def multi(boolean multi)
	
	abstract def upsert(boolean upsert)
}
