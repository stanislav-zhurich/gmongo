package com.stan.gmongo.api.collection

trait WriteResult {

	Long matched
	Long inserted
	Long modified
}
