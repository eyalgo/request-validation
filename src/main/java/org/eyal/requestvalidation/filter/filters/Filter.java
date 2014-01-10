package org.eyal.requestvalidation.filter.filters;

import org.eyal.requestvalidation.model.Item;

import com.google.common.base.Predicate;

public interface Filter extends Predicate<Item> {
	String errorMessage();
}
