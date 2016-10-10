package com.stan.gmongo.api.client

import groovy.transform.TypeChecked
import java.lang.reflect.Constructor
import java.util.List;
import java.util.Map;

import com.stan.gmongo.api.exception.GMongoClientException
import groovy.lang.Closure;
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
			def _options = options?:[:]
			def _addresses = addresses?:[new MongoServerAddress()]
			def _database = database
			def _authentication = authentication
			return instantiate(type?:GMongoClientType.DEFAULT, _addresses,
				 _options, _database, _authentication)
		}
	}

	@TypeChecked
	private static GMongoClient instantiate(GMongoClientType type, List addresses,
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


	static class MongoClientBuilder{
		Map options = [:]
		List addresses = []
		GMongoClientType type
		String database
		MongoServerAuthParams authentication
		
		def connection(Map addressMap){
			def addr = new MongoServerAddress(addressMap.host, addressMap.port)
			addresses << addr
		}
		
		def type(String type){
			this.type = GMongoClientType.CUSTOM;
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
		
		def authentication(@DelegatesTo(MongoServerAuthParams) Closure closure){
			MongoServerAuthParams auth = new MongoServerAuthParams()
			def clone = closure.rehydrate(auth, this, this)
			clone.delegate = auth
			clone.resolveStrategy = Closure.DELEGATE_ONLY
			clone()
			this.authentication = auth
		}
	}
}
