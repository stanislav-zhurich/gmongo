package com.stan.gmongo.api.client

import groovy.transform.ToString
import groovy.transform.TupleConstructor;

@TupleConstructor
@ToString
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
