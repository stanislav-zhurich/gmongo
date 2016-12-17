package com.stan.gmongo.orm.config

import groovy.transform.Canonical
import groovy.transform.TupleConstructor

import com.stan.gmongo.api.client.GMongoClientType
import com.stan.gmongo.api.client.MongoServerAddress
import com.stan.gmongo.api.client.MongoServerAuthParams
import groovy.transform.builder.*

@Canonical
class GMongoConfiguration {
	
	List<String> entityPackages

	List<MongoServerAddress> addresses

	MongoServerAuthParams authentication

	Map options

	GMongoClientType clientType

	String database
}
