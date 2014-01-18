package org.eyal.requestvalidation.flow.example.injectiontests;

import static org.eyal.requestvalidation.flow.ReflectionUtils.realObjectFromField;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.eyal.requestvalidation.flow.AbstractFlowExecutor;
import org.eyal.requestvalidation.flow.AbstractMapperByFlag;
import org.eyal.requestvalidation.flow.example.flow.requestvalidation.validations.AcceptedIdsValidation;
import org.eyal.requestvalidation.flow.example.flow.requestvalidation.validations.EmptyIdValidation;
import org.eyal.requestvalidation.flow.example.flow.requestvalidation.validations.EmptyItemsValidation;
import org.eyal.requestvalidation.flow.requestvalidation.RequestValidationExecutor;
import org.eyal.requestvalidation.flow.requestvalidation.RequestValidationMapperByFlag;
import org.eyal.requestvalidation.flow.requestvalidation.validations.RequestValidation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = { "classpath:spring/flow-validation-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class WiringValidationTest {
	@Inject
	private RequestValidationExecutor validationExecutor;

	@Test
	public void verifyParametersNotNull() throws NoSuchFieldException, SecurityException, IllegalArgumentException,
			IllegalAccessException {
		Field engineField = AbstractFlowExecutor.class.getDeclaredField("engine");
		assertNotNull(engineField);

		Field mapperField = AbstractFlowExecutor.class.getDeclaredField("mapper");
		assertNotNull(mapperField);
	}

	@Test
	public void verifyMappingOfValidationsBasedOnContext() throws NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException {

		RequestValidationMapperByFlag mapper = realObjectFromField(AbstractFlowExecutor.class, "mapper",
				validationExecutor);

		List<RequestValidation> defaultOperations = realObjectFromField(AbstractMapperByFlag.class,
				"defaultOperations", mapper);

		assertThat(defaultOperations.size(), equalTo(3));
		assertThat(defaultOperations.get(0), instanceOf(EmptyIdValidation.class));
		assertThat(defaultOperations.get(1), instanceOf(AcceptedIdsValidation.class));
		assertThat(defaultOperations.get(2), instanceOf(EmptyItemsValidation.class));

		Map<String, List<RequestValidation>> mapOfOperations = realObjectFromField(AbstractMapperByFlag.class,
				"mapOfOperations", mapper);
		assertThat(mapOfOperations.size(), equalTo(0));
	}

	@Test
	public void verifyInjectionOfParametersToFilters() throws NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException {
		RequestValidationMapperByFlag mapper = realObjectFromField(AbstractFlowExecutor.class, "mapper",
				validationExecutor);

		List<RequestValidation> defaultOperations = realObjectFromField(AbstractMapperByFlag.class,
				"defaultOperations", mapper);
		RequestValidation acceptedIdsValidation = defaultOperations.get(1);
		Set<String> acceptedIds = realObjectFromField(AcceptedIdsValidation.class, "acceptedIds", acceptedIdsValidation);
		assertThat(acceptedIds, containsInAnyOrder("flow1", "flow2", "flow3", "flow4", "flow5"));
	}
}
