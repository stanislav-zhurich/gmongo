package com.stan.gmongo.api.collection

trait AggregationExecutor {

	abstract def aggregate(List<Closure> pipeline)
}
