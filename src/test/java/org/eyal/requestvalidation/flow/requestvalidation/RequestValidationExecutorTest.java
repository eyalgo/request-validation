package org.eyal.requestvalidation.flow.requestvalidation;

import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Field;

import org.eyal.requestvalidation.flow.AbstractFlowExecutor;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;



public class RequestValidationExecutorTest {
	@Mock
	private RequestValidationMapperByFlag validationMapper;
	@Mock
	private RequestValidationsEngine validationEngine;

	private RequestValidationExecutor validationsExecutor;

	@Before
	public void setup() {
		validationsExecutor = new RequestValidationExecutor(validationMapper, validationEngine);
	}
	
	@Test
	public void verifyParameters() throws NoSuchFieldException, SecurityException, IllegalArgumentException,
			IllegalAccessException {
		Field mapperField = AbstractFlowExecutor.class.getDeclaredField("mapper");
		mapperField.setAccessible(true);
		RequestValidationMapperByFlag mapper  = (RequestValidationMapperByFlag) mapperField.get(validationsExecutor);
		assertThat(mapper, sameInstance(validationMapper));
		
		Field engineField = AbstractFlowExecutor.class.getDeclaredField("engine");
		engineField.setAccessible(true);
		RequestValidationsEngine engine  = (RequestValidationsEngine) engineField.get(validationsExecutor);
		assertThat(engine, sameInstance(validationEngine));
	}
}
