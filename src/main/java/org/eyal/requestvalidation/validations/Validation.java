package org.eyal.requestvalidation.validations;

import org.eyal.requestvalidation.model.Item;

import com.google.common.base.Predicate;

public interface Validation extends Predicate<Item> {
	String errorMessage();
}
