package org.eyal.requestvalidation.flow.requestvalidation;

import java.util.List;

import org.eyal.requestvalidation.flow.requestvalidation.validations.RequestValidation;
import org.eyal.requestvalidation.model.Request;
import org.eyal.requestvalidation.model.ValidationResponse;

public interface RequestValidationsEngine {
	ValidationResponse applyValidations(List<RequestValidation> validations, Request request);
}
