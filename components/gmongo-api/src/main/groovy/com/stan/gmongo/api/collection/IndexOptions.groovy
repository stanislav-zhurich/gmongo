package com.stan.gmongo.api.collection

import org.bson.conversions.Bson;

trait IndexOptions {

	abstract Boolean background(Boolean background)
	abstract Boolean unique(Boolean unique)
	abstract String name(String name)
	abstract Boolean sparse(Boolean sparse)
	abstract Long expireAfterSeconds(Long expireAfterSeconds);
	abstract Integer version;
	private Bson weights;
	private String defaultLanguage;
	private String languageOverride;
	private Integer textVersion;
	private Integer sphereVersion;
	private Integer bits;
	private Double min;
	private Double max;
	private Double bucketSize;
	private Bson storageEngine;
	private Bson partialFilterExpression;
}
