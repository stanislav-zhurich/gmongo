package com.stan.gmongo.api.collection

trait GMongoCollectionListener {

	abstract void onCreate()
	
	abstract void onDestroy(String collectionName)
}
