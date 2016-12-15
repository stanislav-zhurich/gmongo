package com.stan.gmongo.impl.collection

import com.mongodb.client.model.IndexOptions;

class GMongoIndexOptionsDefault {

	@Delegate IndexOptions options = new IndexOptions()
}
