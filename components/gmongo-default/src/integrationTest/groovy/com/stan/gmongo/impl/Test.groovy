package com.stan.gmongo.impl

def p = {person {
					id 1
					name 'Stan'
					age 29
					departments {name 'dep1'} {name 'dep2'}
				}}

	println bson(p).toJson()
