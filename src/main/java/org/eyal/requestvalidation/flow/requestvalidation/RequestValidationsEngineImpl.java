package org.eyal.requestvalidation.flow.requestvalidation;

import java.util.List;

import org.eyal.requestvalidation.flow.requestvalidation.validations.RequestValidation;
import org.eyal.requestvalidation.model.Request;
import org.eyal.requestvalidation.model.ValidationResponse;

import com.google.common.collect.Lists;

public class RequestValidationsEngineImpl implements RequestValidationsEngine {

	public RequestValidationsEngineImpl() {
	}

	@Override
	public ValidationResponse applyOperations(List<RequestValidation> validations, Request request) {
		List<String> errorMessages = Lists.newLinkedList();
		for (RequestValidation validation : validations) {
	        if (!validation.apply(request)) {
	        	errorMessages.add(validation.errorMessage());
	        }
        }
		if (errorMessages.size() == 0) {
			return ValidationResponse.pass();
		}
		return ValidationResponse.fail(errorMessages);
	}

}
