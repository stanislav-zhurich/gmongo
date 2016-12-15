package com.stan.gmongo.orm.session

import com.stan.gmongo.api.client.GMongoClient

import groovy.transform.Immutable;

class SessionContext {

	@Delegate private GMongoClient client
	
	SessionContext(GMongoClient client){
		this.client = client
	}
}
