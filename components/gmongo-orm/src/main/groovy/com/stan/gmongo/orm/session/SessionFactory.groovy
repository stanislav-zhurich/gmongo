package com.stan.gmongo.orm.session

import static com.stan.gmongo.api.client.GMongoClientProvider.create
import groovy.transform.Synchronized
import groovy.util.logging.Slf4j

import com.stan.gmongo.orm.config.ConfigurationSource

@Slf4j
@Singleton
class SessionFactory {
	
	private SessionContext session
	private Boolean isInitialized = false

	@Synchronized
	def initialize(ConfigurationSource source){
		log.info('Start session initialization')
		def configuration = source.configuration
		def client = create(configuration.clientType, configuration.addresses,
			configuration.options, configuration.database, configuration.authentication)
		
		this.session = new SessionContext(client)
		this.isInitialized = true
		this
	}
	
	@Synchronized
	Boolean isInitialized(){
		isInitialized
	}
	
	SessionContext sessionContext(){
		session
	}
}
