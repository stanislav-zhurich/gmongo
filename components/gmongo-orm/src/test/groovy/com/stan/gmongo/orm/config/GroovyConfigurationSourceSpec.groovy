package com.stan.gmongo.orm.config

import spock.lang.Specification

import com.stan.gmongo.api.client.GMongoClientType
import com.stan.gmongo.api.client.MongoServerAddress
import com.stan.gmongo.api.client.MongoServerAuthParams
import com.stan.gmongo.orm.exception.ConfigurationException;
import com.stan.gmongo.orm.exception.NotFoundConfigurationFileException;

class GroovyConfigurationSourceSpec extends Specification{
	def key = GroovyConfigurationSource.CONFIG_PATH_PARAM_NAME

	def "Should find configuration by name"(){
		given:
			def filePath = "META-INF/config/gmongo.config"
		when:
			def config = new GroovyConfigurationSource([(key):filePath]).configuration
		then:
			config.clientType == GMongoClientType.DEFAULT
			config.addresses == [new MongoServerAddress('127.0.0.1', 27017)]
			config.authentication == new MongoServerAuthParams('Stan', 'qwerty')
			config.database == 'catalog'
			config.options == ['poolSize':10, 'ssl':true]
	}
	
	def "Should use default parameters"(){
		given:
			def filePath = "META-INF/config/gmongo_default.config"
		when:
			def config = new GroovyConfigurationSource([(key) : filePath]).configuration
		then:
			config.clientType == GMongoClientType.DEFAULT
			config.addresses == [new MongoServerAddress('127.0.0.1', 27017)]
			config.authentication == null
			config.database == null
			config.options == [:]
	}
	
	def "Should throw exception if configuration file doesn't found"(){
		given:
			def filePath = "META-INF/config/not_found.config"
		when:
			def config = new GroovyConfigurationSource([(key) : filePath])
		then:
			thrown(NotFoundConfigurationFileException)
	}
	
	def "Should throw exception if cannot compile configuration file"(){
		given:
			def filePath = "META-INF/config/gmongo_incorrect.config"
		when:
			def config = new GroovyConfigurationSource([(key) : filePath])
		then:
			thrown(ConfigurationException)
	}
}
