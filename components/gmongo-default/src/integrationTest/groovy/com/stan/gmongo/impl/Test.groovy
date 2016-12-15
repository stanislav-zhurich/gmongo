package com.stan.gmongo.impl



class A {
	
	def prop = 5;
	
	def getProperty(String name){
		println "get $name"
		'test'
	}
	
	def invokeMethod(String name, Object args){
		println 'invoke'
	}
	
	def methodMissing(String name, Object args){
		println 'method missing'
	}
	
	def test(){
		println "in real test"
	}
	

}


def a = new A()
a.test()
A.metaClass.test1 << {println delegate.getClass()}
def a1 = new A()
a1.test1()