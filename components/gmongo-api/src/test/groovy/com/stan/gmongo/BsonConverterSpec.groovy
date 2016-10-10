package com.stan.gmongo

import org.bson.conversions.Bson

import spock.lang.Specification
import spock.lang.Unroll;

class BsonConverterSpec extends Specification{
	

	def "test"(){
		given:
			def json = bson {
				person {
					id 1
					firstName "Stan"
					single true
				}
			}
			
		expect:
			json.toJson() == '{ "person" : { "id" : 1, "firstName" : "Stan", "single" : true } }'	
	}
	
	@Unroll
	def "should convert closure to bson"(Bson bsonParam, String json){
		expect:
		    println bsonParam.toJson()
			bsonParam.toJson() == json
			
		where:
		  ///////////////////////////////////////////////////////////////////////////////////	 
		  	bsonParam										| 						     json
		  /////////	//////////////////////////////////////////////////////////////////////////
		  bson{id 5}										|				'{ "id" : 5 }'
		  
		  bson{ids 1,2,3}									|				'{ "ids" : [1, 2, 3] }'
		  
		  bson{
			  person {
				  id 7
				  firstName "Stan"
				  lastName	"Zhurich"						
			  }
		  }													|'{ "person" : { "id" : 7, "firstName" : "Stan", "lastName" : "Zhurich" } }'
		  bson{
			  persons(
				  {person {
					  name "Kate"
					  job null
				  }},
				  {person {
					  name "John"
					  job "manager"
				  }},
			  )
		  }													|'{ "persons" : [{ "person" : { "name" : "Kate", "job" : null } }, { "person" : { "name" : "John", "job" : "manager" } }] }'						
	}
}
