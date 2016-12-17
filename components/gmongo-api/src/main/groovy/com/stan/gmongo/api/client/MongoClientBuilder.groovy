package com.stan.gmongo.api.client

import groovy.transform.TypeChecked

class MongoClientBuilder {
	Map options = [:]
	List addresses = []
	GMongoClientType type = GMongoClientType.DEFAULT
	String database
	MongoServerAuthParams authentication
	
	MongoClientBuilder(){
		def mc = new ExpandoMetaClass(MongoClientBuilder, false, true)
		mc.initialize()
		this.metaClass = mc
	}

	def connection(Map addressMap){
		def addr = new MongoServerAddress(addressMap.host, addressMap.port)
		addresses << addr
	}

	def type(String type){
		this.type = GMongoClientType.CUSTOM
		this.type.setClassName(type)
	}

	def type(GMongoClientType type){
		this.type = type
	}

	def database(String database){
		this.database = database
	}

	def addresses(Closure closure){
		closure()
	}

	@TypeChecked
	def options(Closure closure){
		def optionHolder = new Expando(){
					public Object invokeMethod(String name, Object arg){
						options[name] = arg[0]
					}
				}
		def clone = closure.rehydrate(optionHolder, this, this)
		clone.delegate = optionHolder
		clone.resolveStrategy = Closure.DELEGATE_ONLY
		clone()
	}

	def methodMissing(String name, args){
		this.metaClass."$name" = args[0]
	}

	def authentication(@DelegatesTo(MongoServerAuthParams) Closure closure){
		MongoServerAuthParams auth = new MongoServerAuthParams()
		def clone = closure.rehydrate(auth, this, this)
		clone.delegate = auth
		clone.resolveStrategy = Closure.DELEGATE_ONLY
		clone()
		this.authentication = auth
	}
}
