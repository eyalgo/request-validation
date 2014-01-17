package org.eyal.requestvalidation.flow.requestvalidation;

import org.eyal.requestvalidation.flow.FlowEngine;
import org.eyal.requestvalidation.flow.requestvalidation.validations.RequestValidation;
import org.eyal.requestvalidation.model.ValidationResponse;

public interface RequestValidationsEngine extends FlowEngine<ValidationResponse, RequestValidation> {
}
