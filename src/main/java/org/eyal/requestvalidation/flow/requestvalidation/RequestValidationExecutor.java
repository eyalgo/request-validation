package org.eyal.requestvalidation.flow.requestvalidation;

import org.eyal.requestvalidation.flow.AbstractFlowExecutor;
import org.eyal.requestvalidation.flow.MapperByFlag;
import org.eyal.requestvalidation.flow.requestvalidation.validations.RequestValidation;
import org.eyal.requestvalidation.model.ValidationResponse;

public class RequestValidationExecutor extends AbstractFlowExecutor<RequestValidation, ValidationResponse> {

	public RequestValidationExecutor(MapperByFlag<RequestValidation> mapper, RequestValidationsEngine engine) {
		super(mapper, engine);
	}
}
