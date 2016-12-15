package com.stan.gmongo.orm.config

import java.util.Map;

trait ConfigurationSource {
	
	GMongoConfiguration configuration

	abstract GMongoConfiguration initialize(Map settings)
}
