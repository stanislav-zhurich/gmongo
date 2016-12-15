package com.stan.gmongo.api.collection

import java.util.Collection;
import java.util.concurrent.TimeUnit;

import groovy.lang.Closure;

trait GMongoBaseIterable {

	abstract <T> void forEach(@DelegatesTo(type="T") Closure<T> closure)
	
	abstract <T> GMongoBaseIterable map(@DelegatesTo(type="T") Closure<T> closure)
	
	abstract <S> S first()
	
	abstract <T extends Collection> T into(T target);
	
	abstract GMongoBaseIterable maxTime(long maxTime, TimeUnit timeUnit)
}
