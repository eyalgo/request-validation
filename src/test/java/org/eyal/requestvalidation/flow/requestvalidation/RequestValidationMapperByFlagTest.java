package org.eyal.requestvalidation.flow.requestvalidation;

import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.eyal.requestvalidation.flow.AbstractMapperByFlag;
import org.eyal.requestvalidation.flow.requestvalidation.validations.RequestValidation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RequestValidationMapperByFlagTest {

	@Mock
	private List<RequestValidation> defaultValidations;
    
	@Mock
	private Map<String, List<RequestValidation>> mapOfValidations;

	@InjectMocks
	private RequestValidationMapperByFlag mapper;

	@SuppressWarnings("unchecked")
    @Test
	public void verifyParameters() throws NoSuchFieldException, SecurityException, IllegalArgumentException,
	        IllegalAccessException {
		Field defaultOperationsField = AbstractMapperByFlag.class.getDeclaredField("defaultOperations");
		defaultOperationsField.setAccessible(true);
        List<RequestValidation> actualFilters = (List<RequestValidation>) defaultOperationsField.get(mapper);
		assertThat(actualFilters, sameInstance(defaultValidations));

		Field mapOfFiltersField = AbstractMapperByFlag.class.getDeclaredField("mapOfOperations");
		mapOfFiltersField.setAccessible(true);
		Map<String, List<RequestValidation>> actualMapOfFilters = (Map<String, List<RequestValidation>>) mapOfFiltersField.get(mapper);
		assertThat(actualMapOfFilters, sameInstance(mapOfValidations));
	}
}
