package com.stan.gmongo.orm.config

import com.stan.gmongo.api.client.GMongoClientType
import com.stan.gmongo.api.client.MongoServerAddress
import com.stan.gmongo.api.client.MongoServerAuthParams

abstract class AbstractConfigurationSource implements ConfigurationSource{
	
	AbstractConfigurationSource(Map settings){
		this.configuration = initialize settings
	}
}
