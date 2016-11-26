package com.stan.gmongo.impl

import groovy.transform.InheritConstructors
import groovy.transform.ToString
import groovy.transform.TupleConstructor


def s = "test" as GroovyInterceptable
//"".getAt()

//s.getMetaClass().invokeMethod = {name, args -> println args.getClass()}
//s.metaClass.getAt = {Range range -> println "hi"}

//println s [1,2]



def a = {
			id 1
			name 'Test'
			departments [{id 4; name 'prod'}]
						
		}

def b = bson(a)

println b.toJson()