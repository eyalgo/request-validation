package org.eyal.requestvalidation.flow.example.flow.requestvalidation.validations;

import java.util.Set;

import org.eyal.requestvalidation.flow.requestvalidation.validations.RequestValidation;
import org.eyal.requestvalidation.model.Request;

public class AcceptedIdsValidation implements RequestValidation {

	private final Set<String> acceptedIds;

	public AcceptedIdsValidation(Set<String> acceptedIds) {
		this.acceptedIds = acceptedIds;
	}

	@Override
	public boolean apply(Request input) {
		return acceptedIds.contains(input.getId());
	}

	@Override
	public String errorMessage() {
		return String.format("Request does not have an ID from: %s", acceptedIds);
	}
}
