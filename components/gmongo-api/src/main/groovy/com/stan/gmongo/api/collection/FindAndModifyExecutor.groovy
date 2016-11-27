package com.stan.gmongo.api.collection

trait FindAndModifyExecutor {

	abstract def execute(@DelegatesTo(GMongoFindModifyOptions) Closure options)
}
