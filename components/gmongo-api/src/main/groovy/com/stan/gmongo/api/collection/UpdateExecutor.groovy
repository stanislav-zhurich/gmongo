package com.stan.gmongo.api.collection

trait UpdateExecutor {

	abstract WriteResult execute(Closure query, Closure document, @DelegatesTo(GMongoUpdateOptions) Closure options)
}
