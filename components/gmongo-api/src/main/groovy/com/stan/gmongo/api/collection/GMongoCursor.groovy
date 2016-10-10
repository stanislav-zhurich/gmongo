package com.stan.gmongo.api.collection

trait GMongoCursor {

	abstract void close();
	
	abstract boolean hasNext();
	
	abstract def next();
}
