package com.stan.gmongo.api.client

enum GMongoClientType {
	DEFAULT("com.stan.gmongo.impl.client.GMongoClientDefault"),
	IN_MEMORY("com.stan.gmongo.test.client.GMongoClientInMemory"),
	CUSTOM("")

	String className
	
	void setClassName(String className){
		this.@className = className
	}

	GMongoClientType(className){
		this.className = className
	}
}
