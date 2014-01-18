package org.eyal.requestvalidation.flow.example.flow.requestvalidation.validations;

import org.eyal.requestvalidation.flow.requestvalidation.validations.RequestValidation;
import org.eyal.requestvalidation.model.Request;

public class EmptyIdValidation implements RequestValidation {

	public EmptyIdValidation() {
	}

	@Override
	public boolean apply(Request input) {
		if (input.getId() == null || input.getId().isEmpty()) {
			return false;
		}
		return true;
	}

	@Override
	public String errorMessage() {
		return "Request does not have an ID (empty or null)";
	}

}
