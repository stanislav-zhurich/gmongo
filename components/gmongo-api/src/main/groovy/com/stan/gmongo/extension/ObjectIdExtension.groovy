package com.stan.gmongo.extension

import java.time.LocalDateTime

import org.bson.types.ObjectId;

import groovy.lang.GroovyObject;

class ObjectIdExtension {

	static ObjectId objectId(final GroovyObject self) {
		new ObjectId()
	}
}
