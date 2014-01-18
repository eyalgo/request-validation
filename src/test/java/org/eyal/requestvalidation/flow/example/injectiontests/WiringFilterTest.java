package org.eyal.requestvalidation.flow.example.injectiontests;

import static org.eyal.requestvalidation.flow.ReflectionUtils.realObjectFromField;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.eyal.requestvalidation.flow.AbstractFlowExecutor;
import org.eyal.requestvalidation.flow.AbstractMapperByFlag;
import org.eyal.requestvalidation.flow.example.flow.itemsfilter.filters.EmptyNameFilter;
import org.eyal.requestvalidation.flow.example.flow.itemsfilter.filters.EvenIdFilter;
import org.eyal.requestvalidation.flow.example.flow.itemsfilter.filters.NameTooLongFilter;
import org.eyal.requestvalidation.flow.example.flow.itemsfilter.filters.NameTooShortFilter;
import org.eyal.requestvalidation.flow.example.flow.itemsfilter.filters.OddIdFilter;
import org.eyal.requestvalidation.flow.itemsfilter.FiltersExecutor;
import org.eyal.requestvalidation.flow.itemsfilter.ItemFiltersMapperByFlag;
import org.eyal.requestvalidation.flow.itemsfilter.filters.Filter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = { "classpath:spring/flow-filter-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class WiringFilterTest {

	@Inject
	private FiltersExecutor filtersExecutor;

	@Test
	public void verifyParametersNotNull() throws NoSuchFieldException, SecurityException, IllegalArgumentException,
			IllegalAccessException {
		Field engineField = AbstractFlowExecutor.class.getDeclaredField("engine");
		assertNotNull(engineField);

		Field mapperField = AbstractFlowExecutor.class.getDeclaredField("mapper");
		assertNotNull(mapperField);
	}

	@Test
	public void verifyMappingOfFiltersBasedOnContext() throws NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException {

		ItemFiltersMapperByFlag mapper = realObjectFromField(AbstractFlowExecutor.class, "mapper", filtersExecutor);

		List<Filter> defaultOperations = realObjectFromField(AbstractMapperByFlag.class, "defaultOperations", mapper);

		assertThat(defaultOperations.size(), equalTo(1));
		assertThat(defaultOperations.get(0), instanceOf(EmptyNameFilter.class));

		Map<String, List<Filter>> mapOfOperations = realObjectFromField(AbstractMapperByFlag.class, "mapOfOperations",
				mapper);
		List<Filter> listOfExternal = mapOfOperations.get("EXTERNAL");
		assertThat(listOfExternal.size(), equalTo(2));
		assertThat(listOfExternal.get(0), instanceOf(NameTooLongFilter.class));
		assertThat(listOfExternal.get(1), instanceOf(NameTooShortFilter.class));

		List<Filter> listOfInternal = mapOfOperations.get("INTERNAL");
		assertThat(listOfInternal.size(), equalTo(2));
		assertThat(listOfInternal.get(0), instanceOf(NameTooLongFilter.class));
		assertThat(listOfInternal.get(1), instanceOf(NameTooShortFilter.class));

		List<Filter> listOfEven = mapOfOperations.get("EVEN");
		assertThat(listOfEven.size(), equalTo(1));
		assertThat(listOfEven.get(0), instanceOf(EvenIdFilter.class));

		List<Filter> listOfOdd = mapOfOperations.get("ODD");
		assertThat(listOfOdd.size(), equalTo(1));
		assertThat(listOfOdd.get(0), instanceOf(OddIdFilter.class));

		assertThat(listOfExternal.get(0), not(sameInstance(listOfInternal.get(0))));
		assertThat(listOfExternal.get(1), not(sameInstance(listOfInternal.get(1))));
	}

	@Test
	public void verifyInjectionOfParametersToFilters() throws NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException {
		ItemFiltersMapperByFlag mapper = realObjectFromField(AbstractFlowExecutor.class, "mapper", filtersExecutor);
		Map<String, List<Filter>> mapOfOperations = realObjectFromField(AbstractMapperByFlag.class, "mapOfOperations",
				mapper);

		List<Filter> listOfExternal = mapOfOperations.get("EXTERNAL");
		Filter externalNameTooLongFilter = listOfExternal.get(0);
		int maxLengthExternal = realObjectFromField(NameTooLongFilter.class, "maxLength", externalNameTooLongFilter);
		assertThat(maxLengthExternal, equalTo(10));

		Filter externalNameTooShortFilter = listOfExternal.get(1);
		int minLengthExternal = realObjectFromField(NameTooShortFilter.class, "minLength", externalNameTooShortFilter);
		assertThat(minLengthExternal, equalTo(4));

		List<Filter> listOfInternal = mapOfOperations.get("INTERNAL");
		Filter internalNameTooLongFilter = listOfInternal.get(0);
		int maxLengthInternal = realObjectFromField(NameTooLongFilter.class, "maxLength", internalNameTooLongFilter);
		assertThat(maxLengthInternal, equalTo(6));

		Filter internalNameTooShortFilter = listOfInternal.get(1);
		int minLengthInternal = realObjectFromField(NameTooShortFilter.class, "minLength", internalNameTooShortFilter);
		assertThat(minLengthInternal, equalTo(2));
	}
}
