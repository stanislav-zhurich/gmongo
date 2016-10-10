package com.stan.gmongo

import org.bson.BsonDocument
import org.bson.BsonInt32
import org.bson.BsonValue

def builder = new groovy.json.JsonBuilder()
def root = builder.people {
	person {
		firstName 'Guillame'
		// Named arguments are valid values for objects too
		address{
				city 'Paris'
				country 'France'
				zip 12345
		}
		departments({id 1},{id 2})
		// a list of values
		conferences 'JavaOne', 'Gr8conf'
	}
}

println builder.toString()


def t = "Hi"
println bson{
	id 5
}.toJson()