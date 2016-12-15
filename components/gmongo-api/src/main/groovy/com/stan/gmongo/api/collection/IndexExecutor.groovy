package com.stan.gmongo.api.collection

trait IndexExecutor {

	abstract String executeCreate(Closure keys, Closure options)
	
	abstract void executeDrop(String index)
	
	abstract GMongoIndexIterable executeFind()
}
