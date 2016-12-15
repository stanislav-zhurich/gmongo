package com.stan.gmongo.extension

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter;

import org.bson.BsonDateTime

class DateExtension {
	
	static LocalDateTime dateTime(final GroovyObject self) {
	    LocalDateTime.now()
	} 

	static LocalDateTime dateTime(final GroovyObject self, final String strDate) {		
		def formatter = DateTimeFormatter.ISO_DATE_TIME
		LocalDateTime.parse(strDate,formatter)
	}
}
