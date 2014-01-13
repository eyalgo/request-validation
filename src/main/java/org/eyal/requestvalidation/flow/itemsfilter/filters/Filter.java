package org.eyal.requestvalidation.flow.itemsfilter.filters;

import org.eyal.requestvalidation.model.Item;

import com.google.common.base.Predicate;

public interface Filter extends Predicate<Item> {
	String errorMessage();
}
