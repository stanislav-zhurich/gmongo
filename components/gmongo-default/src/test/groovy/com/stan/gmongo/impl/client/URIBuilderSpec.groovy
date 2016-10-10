package com.stan.gmongo.impl.client

import static com.stan.gmongo.api.client.GMongoClientProvider.create
import static com.stan.gmongo.impl.client.GMongoClientDefault.URIBuilder
import spock.lang.Specification
import spock.lang.Unroll

import com.stan.gmongo.api.client.GMongoClient

class URIBuilderSpec extends Specification{


	@Unroll
	def "should convert to URI"(GMongoClient client, String uri){
		expect:
		URIBuilder.build(client.connection) == uri

		where:
		
		client												|uri	
		
		
		create{
			database "catalog"
			authentication{
				username "Stan"
				password "qwerty"
			}
			options {
				option1 "value1"
				option2 1
				option3 true
			}
			addresses {
				connection(
						"host":"127.0.0.1", "port": 27017)}
		}													|"mongodb://Stan:qwerty@127.0.0.1:27017/catalog?option1=value1&option2=1&option3=true"
		
		
	
		create{
			database "catalog"
			authentication{
				username "Stan"
				password "qwerty"
			}
			options {
				option1 "value1"
				option2 1
				option3 true
			}
			addresses {
				 connection("host":"127.0.0.1", "port": 27017)
				 connection("host":"127.0.0.2", "port": 27018)}
		}														|"mongodb://Stan:qwerty@127.0.0.1:27017,127.0.0.2:27018/catalog?option1=value1&option2=1&option3=true"
	
	
		create{
			authentication{
				username "Stan"
				password "qwerty"
			}
			options {
				option1 "value1"
				option2 1
				option3 true
			}
			addresses {
				connection("host":"127.0.0.1", "port": 27017)
				connection("host":"127.0.0.2", "port": 27018)}
		}														|"mongodb://Stan:qwerty@127.0.0.1:27017,127.0.0.2:27018/?option1=value1&option2=1&option3=true"
	
	
		create{
			options {
				option1 "value1"
				option2 1
				option3 true
			}
			addresses {
				connection("host":"127.0.0.1", "port": 27017)
				connection("host":"127.0.0.2", "port": 27018)}
		}														| "mongodb://127.0.0.1:27017,127.0.0.2:27018/?option1=value1&option2=1&option3=true"				
	}

}
