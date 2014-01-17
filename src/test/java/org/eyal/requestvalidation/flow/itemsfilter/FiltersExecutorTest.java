package org.eyal.requestvalidation.flow.itemsfilter;

import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Field;

import org.eyal.requestvalidation.flow.AbstractFlowExecutor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FiltersExecutorTest {
	@Mock
	private ItemFiltersMapperByFlag filtersMapper;
	@Mock
	private FiltersEngine filtersEngine;

	private FiltersExecutor filtersExecutor;

	@Before
	public void setup() {
		filtersExecutor = new FiltersExecutor(filtersMapper, filtersEngine);
	}

	@Test
	public void verifyParameters() throws NoSuchFieldException, SecurityException, IllegalArgumentException,
			IllegalAccessException {
		Field mapperField = AbstractFlowExecutor.class.getDeclaredField("mapper");
		mapperField.setAccessible(true);
		ItemFiltersMapperByFlag mapper  = (ItemFiltersMapperByFlag) mapperField.get(filtersExecutor);
		assertThat(mapper, sameInstance(filtersMapper));
		
		Field engineField = AbstractFlowExecutor.class.getDeclaredField("engine");
		engineField.setAccessible(true);
		FiltersEngine engine  = (FiltersEngine) engineField.get(filtersExecutor);
		assertThat(engine, sameInstance(filtersEngine));
	}
}
