package org.eyal.requestvalidation.flow.requestvalidation;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import org.eyal.requestvalidation.flow.requestvalidation.validations.RequestValidation;
import org.eyal.requestvalidation.model.Request;
import org.eyal.requestvalidation.model.ValidationResponse;
import org.eyal.requestvalidation.model.ValidationResponse.ValidationResponseStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;

@RunWith(MockitoJUnitRunner.class)
public class RequestValidationsEngineTest {
	private final static String MESSAGE_FOR_VALIDATION_1="VALIDATION - 1 - ERROR";
	private final static String MESSAGE_FOR_VALIDATION_2="VALIDATION - 2 - ERROR";
	@Mock(name="validation 1")
	private RequestValidation validation1;
	@Mock(name="validation 2")
	private RequestValidation validation2;
	
	@Mock(name="the request")
	private Request request;
	
	private List<RequestValidation> validations;
	
	@InjectMocks
	private RequestValidationsEngine engine;
	
	@Before
	public void setup() {
		when(validation1.errorMessage()).thenReturn(MESSAGE_FOR_VALIDATION_1);
		when(validation2.errorMessage()).thenReturn(MESSAGE_FOR_VALIDATION_2);
		
		validations = Lists.newArrayList(validation1, validation2);
	}

	@Test
	public void whenAllValidationPassThanRequestIsValid() {
		when(validation1.apply(request)).thenReturn(true);
		when(validation2.apply(request)).thenReturn(true);
		
		 ValidationResponse response = engine.applyOperations(validations, request);
		 assertThat(response.getStatus(), equalTo(ValidationResponseStatus.OK));
	}
	
	@Test
	public void validation1PassValidation2DoesntShouldCollectMessages() {
		when(validation1.apply(request)).thenReturn(true);
		when(validation2.apply(request)).thenReturn(false);
		
		List<String> expectedMessgaes = Lists.newArrayList(MESSAGE_FOR_VALIDATION_2);
		ValidationResponse response = engine.applyOperations(validations, request);
		assertThat(response.getStatus(), equalTo(ValidationResponseStatus.NOT_OK));
		assertThat(response.getMessages(), equalTo(expectedMessgaes));
		
		ValidationResponse.ValidationResponseStatus.NOT_OK.name();
	}

	@Test
	public void allValidationFailThenErrorMessagesIsTheSameOrder() {
		when(validation1.apply(request)).thenReturn(false);
		when(validation2.apply(request)).thenReturn(false);
		
		List<String> expectedMessgaes = Lists.newArrayList(MESSAGE_FOR_VALIDATION_1, MESSAGE_FOR_VALIDATION_2);
		ValidationResponse response = engine.applyOperations(validations, request);
		assertThat(response.getStatus(), equalTo(ValidationResponseStatus.NOT_OK));
		assertThat(response.getMessages(), equalTo(expectedMessgaes));
		
	}
}
