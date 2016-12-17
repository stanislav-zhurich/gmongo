package com.stan.gmongo.api.client

import groovy.transform.TypeChecked
import java.lang.reflect.Constructor
import java.util.List;
import java.util.Map;

import com.stan.gmongo.api.exception.GMongoClientException
import groovy.lang.Closure
import groovy.transform.ToString;
import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j


@Slf4j
class GMongoClientProvider {
	
	@TypeChecked
	static GMongoClient create(@DelegatesTo(MongoClientBuilder) Closure settings){
		MongoClientBuilder builder = new MongoClientBuilder()
		def clone = settings.rehydrate(builder, this, this)
		clone.delegate = builder
		clone.resolveStrategy = Closure.DELEGATE_ONLY
		clone.call()
		
		builder.with{
			def _addresses = addresses?:[new MongoServerAddress()]
			def _database = database
			def _authentication = authentication
			return create(type, _addresses,
				 options, _database, _authentication)
		}
	}

	@TypeChecked
	static GMongoClient create(GMongoClientType type, List addresses,
		Map options, String database, MongoServerAuthParams auth){
		
		log.info("Create connection: addresses ${addresses} options:${options}")
		
		try{
			Class clazz = Class.forName(type.className as String)
			if(!GMongoClient.isAssignableFrom(clazz)){
				throw new GMongoClientException("Custom client has to extend ${GMongoClient}")
			}
			Constructor con = clazz.getConstructor(GMongoClientType, MongoServerConnection)
			return con.newInstance(type, new MongoServerConnection(addresses, options, database, auth))
		}
		catch(GMongoClientException e){
			throw e
		}
		catch(e){
			log.error("Error instantiating Mongo client", e)
			throw new RuntimeException(e)
		}
	}

}
