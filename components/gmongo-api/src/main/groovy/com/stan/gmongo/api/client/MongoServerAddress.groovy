package com.stan.gmongo.api.client

import groovy.transform.Canonical


@Canonical
class MongoServerAddress {
	
	static final String DEFAULT_HOST = "localhost"
	static final Integer DEFAULT_PORT = 27017

	final String host
	final Integer port
	
	MongoServerAddress(){
		this(DEFAULT_HOST, DEFAULT_PORT)
	}
	
	MongoServerAddress(String host, Integer port){
		this.host = host?:DEFAULT_HOST
		this.port = port?:DEFAULT_PORT
	}
}
