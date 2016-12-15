package com.stan.gmongo.orm.session

import spock.lang.Specification

import com.stan.gmongo.api.client.GMongoClient
import com.stan.gmongo.api.client.GMongoClientProvider
import com.stan.gmongo.orm.config.ConfigurationSource
import com.stan.gmongo.orm.config.GMongoConfiguration

class SessionFactorySpec extends Specification{

	def "Should initialize session"(){
		given:
			def spy = GroovySpy(GMongoClientProvider, global: true)
			GMongoClient client = Mock()
			GMongoClientProvider.create(_,_,_,_,_) >> client
			def source = Stub(ConfigurationSource){
				initialize(_) >> new GMongoConfiguration()
			}
			
		when:
			def sessionFactory = SessionFactory.instance
			sessionFactory.initialize(source)
		then:
			sessionFactory.isInitialized() == true
			sessionFactory.sessionContext().client == client
	}
}
