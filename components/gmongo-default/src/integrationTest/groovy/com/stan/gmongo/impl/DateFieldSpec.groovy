package com.stan.gmongo.impl

import static com.stan.gmongo.api.client.GMongoClientProvider.create

import java.time.ZoneId

import spock.lang.Shared
import spock.lang.Specification

import com.stan.gmongo.api.client.GMongoClientType

class DateFieldSpec extends Specification{
	
	@Shared def client
	@Shared def db
	def collection

	def setupSpec(){
		client = create{
			type  GMongoClientType.DEFAULT
		 }
		db = client.use("testDB")
	}
	
	def cleanupSpec(){
		db.dropDatabase()
	}
	
	def setup(){
		collection = db.createCollection('testDate')
	}
	
	def cleanup(){
		collection.drop()
	}
	
	def "Save Entity with Current Date"(){
		given:
			def date = dateTime()
			def employee = {
				name "Stan"
				hireDate date
			}
		when:
			db.testDate.insert(employee)
		then:
			def timestamp = date.atZone(ZoneId.systemDefault())
			.toInstant().toEpochMilli()
			def result = db.testDate.find({name "Stan"}).first()
			result.get('hireDate').getValue() == timestamp
	}
	
	def "Save Entity with Specified Date"(){
		given:
			def date = dateTime("2011-12-03T10:15:30")
			def employee = {
				name "Stan"
				hireDate date
			}
		when:
			db.testDate.insert(employee)
		then:
			def timestamp = date.atZone(ZoneId.systemDefault())
			.toInstant().toEpochMilli()
			def result = db.testDate.find({name "Stan"}).first()
			result.get('hireDate').getValue() == timestamp
	}
}
