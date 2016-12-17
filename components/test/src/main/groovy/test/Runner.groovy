package test

import com.stan.gmongo.orm.config.GroovyConfigurationSource
import com.stan.gmongo.orm.session.SessionFactory

def factory = SessionFactory.getInstance()

factory.initialize(new GroovyConfigurationSource())
def context = factory.sessionContext()

def db = context.use('catalog')

db.products.insert({
	name 'pr1'
	category 'cat1'
	price 123
})

def pr = db.products.find({name 'pr1'}).first()

println pr

db.dropDatabase()