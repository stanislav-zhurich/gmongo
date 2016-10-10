package com.stan.gmongo.api.collection

import groovy.transform.ToString

import com.mongodb.client.model.CreateCollectionOptions

@ToString
class MongoCollectionOptions {
	
	final CreateCollectionOptions collOptions
	
	MongoCollectionOptions(Closure options){
		this.collOptions = new CreateCollectionOptions();
		options.delegate = collOptions
		options.resolveStrategy = Closure.DELEGATE_FIRST
		options()
	}

}
