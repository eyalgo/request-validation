package org.eyal.requestvalidation.flow.itemsfilter;

import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.eyal.requestvalidation.flow.AbstractMapperByFlag;
import org.eyal.requestvalidation.flow.itemsfilter.filters.Filter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ItemFiltersByFlagMapperTest {

	@Mock
	private List<Filter> defaultFilters;
	@Mock
	private Map<String, List<Filter>> mapOfFilters;

	@InjectMocks
	private ItemFiltersByFlagMapper mapper;

	@SuppressWarnings("unchecked")
    @Test
	public void verifyParameters() throws NoSuchFieldException, SecurityException, IllegalArgumentException,
	        IllegalAccessException {
		Field defaultOperationsField = AbstractMapperByFlag.class.getDeclaredField("defaultOperations");
		defaultOperationsField.setAccessible(true);
        List<Filter> actualFilters = (List<Filter>) defaultOperationsField.get(mapper);
		assertThat(actualFilters, sameInstance(defaultFilters));

		Field mapOfFiltersField = AbstractMapperByFlag.class.getDeclaredField("mapOfOperations");
		mapOfFiltersField.setAccessible(true);
		Map<String, List<Filter>> actualMapOfFilters = (Map<String, List<Filter>>) mapOfFiltersField.get(mapper);
		assertThat(actualMapOfFilters, sameInstance(mapOfFilters));
	}
}
