package org.eyal.requestvalidation.flow.requestvalidation;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;

import org.eyal.requestvalidation.flow.MapperByFlag;
import org.eyal.requestvalidation.flow.requestvalidation.validations.RequestValidation;
import org.eyal.requestvalidation.model.Request;
import org.eyal.requestvalidation.model.ValidationResponse;
import org.junit.Test;
import org.mockito.InOrder;



public class RequestValidationExecutorTest {
	
	@SuppressWarnings("unchecked")
	@Test
	public void mapperAndEngineAreCalled(){
		MapperByFlag<RequestValidation> mapper = mock(MapperByFlag.class);
		RequestValidationsEngine engine = mock(RequestValidationsEngine.class);
		List<RequestValidation> validations = mock(List.class);
		Request request = mock(Request.class);
		ValidationResponse expectedValidationResponse = mock(ValidationResponse.class);
		
		when(mapper.getOperations(request)).thenReturn(validations);
		when(engine.applyOperations(validations , request)).thenReturn(expectedValidationResponse);
		
		RequestValidationExecutor requestFlowValidation = new RequestValidationExecutor(mapper, engine);
		ValidationResponse validationResponse = requestFlowValidation.validate(request);
		assertThat("wrong validation response", validationResponse, equalTo(expectedValidationResponse));
		
		InOrder order = inOrder(mapper, engine);
		order.verify(mapper).getOperations(request);
		order.verify(engine).applyOperations(validations, request);
		verifyNoMoreInteractions(mapper, engine);
	}

}
