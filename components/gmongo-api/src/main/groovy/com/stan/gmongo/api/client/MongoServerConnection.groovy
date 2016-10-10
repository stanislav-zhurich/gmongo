package com.stan.gmongo.api.client

import java.util.List;
import java.util.Map;

import groovy.transform.ToString

@ToString
class MongoServerConnection {
	
	List<MongoServerAddress> serverAddresses
	MongoServerAuthParams authentication
	Map options
	String database
	
	
	Object getProperty(String name){
		return this.hasProperty(name)? this.@"$name": options[name]
	}
	
	MongoServerConnection(){
		this([new MongoServerAddress()], [:])
	}
	
	MongoServerConnection(List addresses, Map options){
		this(addresses, options, null, null)
	}
	
	MongoServerConnection(List addresses, Map options, String database){
		this(addresses, options, database, null)
	}
	
	MongoServerConnection(List addresses, Map options, String database, MongoServerAuthParams auth){
		this.serverAddresses = addresses?:[new MongoServerAddress()]
		this.options = options?:[:]
		this.database = database
		this.authentication = auth
	}
}
