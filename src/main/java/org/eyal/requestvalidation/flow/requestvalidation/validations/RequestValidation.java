package org.eyal.requestvalidation.flow.requestvalidation.validations;

import org.eyal.requestvalidation.model.Request;

import com.google.common.base.Predicate;

public interface RequestValidation extends Predicate<Request> {
	String errorMessage();
}
