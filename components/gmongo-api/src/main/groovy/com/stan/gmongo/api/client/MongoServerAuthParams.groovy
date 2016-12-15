package com.stan.gmongo.api.client

import groovy.transform.Canonical;
import groovy.transform.ToString
import groovy.transform.TupleConstructor;

@TupleConstructor
@Canonical
class MongoServerAuthParams {

	String username
	String password
	
	def username(String username){
		this.username = username
	}
	
	def password(String password){
		this.password = password
	}
}
