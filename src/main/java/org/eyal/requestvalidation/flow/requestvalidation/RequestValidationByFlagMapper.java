package org.eyal.requestvalidation.flow.requestvalidation;

import java.util.List;
import java.util.Map;

import org.eyal.requestvalidation.flow.AbstractMapperByFlag;
import org.eyal.requestvalidation.flow.requestvalidation.validations.RequestValidation;

public class RequestValidationByFlagMapper extends AbstractMapperByFlag<RequestValidation> {

	public RequestValidationByFlagMapper(List<RequestValidation> defaultValidations,
	        Map<String, List<RequestValidation>> mapOfValidations) {
		super(defaultValidations, mapOfValidations);
	}
}
