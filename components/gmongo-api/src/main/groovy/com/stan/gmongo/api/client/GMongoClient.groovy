package com.stan.gmongo.api.client

import com.stan.gmongo.api.database.GMongoDatabase

import groovy.transform.TupleConstructor
import groovy.util.logging.Slf4j;

/**
 * 
 * @author Stanislav Zhurich
 *
 */
@Slf4j
@TupleConstructor
abstract class GMongoClient {

	final GMongoClientType type
	final MongoServerConnection connection 
	
	abstract GMongoDatabase use(String database);
	
}
