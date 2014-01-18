package org.eyal.requestvalidation.flow.example.flow.requestvalidation.validations;

import org.eyal.requestvalidation.flow.requestvalidation.validations.RequestValidation;
import org.eyal.requestvalidation.model.Request;

public class EmptyItemsValidation implements RequestValidation {

	public EmptyItemsValidation() {
	}

	@Override
	public boolean apply(Request input) {
		return input.getItems().size() > 0;
	}

	@Override
	public String errorMessage() {
		return "Request does not have any items";
	}

}
