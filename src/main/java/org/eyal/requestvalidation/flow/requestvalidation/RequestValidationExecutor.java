package org.eyal.requestvalidation.flow.requestvalidation;

import java.util.List;

import org.eyal.requestvalidation.flow.MapperByFlag;
import org.eyal.requestvalidation.flow.requestvalidation.validations.RequestValidation;
import org.eyal.requestvalidation.model.Request;
import org.eyal.requestvalidation.model.ValidationResponse;

public class RequestValidationExecutor {

	private MapperByFlag<RequestValidation> mapper;
	private RequestValidationsEngine engine;

	public RequestValidationExecutor(MapperByFlag<RequestValidation> mapper, RequestValidationsEngine engine) {
		this.mapper = mapper;
		this.engine = engine;
	}
	
	public ValidationResponse validate(Request request) {
		List<RequestValidation> validations = mapper.getOperations(request);
		return engine.applyOperations(validations, request);
	}

}
