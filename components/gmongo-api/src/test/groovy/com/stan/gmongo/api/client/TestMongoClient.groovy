package com.stan.gmongo.api.client

import com.stan.gmongo.api.database.GMongoDatabase

import groovy.transform.InheritConstructors;

@InheritConstructors
class TestMongoClient extends GMongoClient{
	GMongoDatabase use(String name){}
}
