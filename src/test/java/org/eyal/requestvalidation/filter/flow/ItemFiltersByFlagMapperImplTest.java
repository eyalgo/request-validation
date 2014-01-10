package org.eyal.requestvalidation.filter.flow;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import org.eyal.requestvalidation.filter.filters.Filter;
import org.eyal.requestvalidation.model.Request;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

@RunWith(MockitoJUnitRunner.class)
public class ItemFiltersByFlagMapperImplTest {
	private final static String FLAG_1 = "flag 1";
	private final static String FLAG_2 = "flag 2";

	@Mock
	private Request request;
	@Mock(name = "default filter 1")
	private Filter defaultFilter1;
	@Mock(name = "default filter 2")
	private Filter defaultFilter2;

	@Mock(name = "filter1 for flag 1")
	private Filter filterForFlag11;
	@Mock(name = "filter2 for flag 1")
	private Filter filterForFlag12;
	@Mock(name = "filter3 for flag 2")
	private Filter filterForFlag32;

	private ItemFiltersByFlagMapperImpl mapper;

	@Before
	public void setup() {
		List<Filter> defaultFilters = Lists.newArrayList(defaultFilter1, defaultFilter2);
		Map<String, List<Filter>> mapOfFilters = ImmutableMap.<String, List<Filter>> builder()
		        .put(FLAG_1, Lists.newArrayList(filterForFlag11, filterForFlag12))
		        .put(FLAG_2, Lists.newArrayList(filterForFlag32, filterForFlag11)).build();
		mapper = new ItemFiltersByFlagMapperImpl(defaultFilters, mapOfFilters);
	}

	@Test
	public void whenRequestDoesNotHaveFlagsShouldReturnDefaultFiltersOnly() {
		when(request.getFlags()).thenReturn(Sets.<String> newHashSet());

		List<Filter> filters = mapper.getFilters(request);
		assertThat(filters, containsInAnyOrder(defaultFilter1, defaultFilter2));
	}

	@Test
	public void whenRequestHasFlagsNotInMappingShouldReturnDefaultFiltersOnly() {
		when(request.getFlags()).thenReturn(Sets.<String> newHashSet("un-mapped-flag"));
		List<Filter> filters = mapper.getFilters(request);
		assertThat(filters, containsInAnyOrder(defaultFilter1, defaultFilter2));
	}
	
	@Test
	public void whenRequestHasOneFlagShouldReturnWithDefaultAndMappedFilters() {
		when(request.getFlags()).thenReturn(Sets.<String> newHashSet(FLAG_1));
		List<Filter> filters = mapper.getFilters(request);
		assertThat(filters, containsInAnyOrder(filterForFlag12, defaultFilter1, filterForFlag11, defaultFilter2));
	}
	
	@Test
	public void whenRequestHasTwoFlagsShouldReturnWithDefaultAndMappedFiltersWithoutDuplications() {
		when(request.getFlags()).thenReturn(Sets.<String> newHashSet(FLAG_1, FLAG_2));
		List<Filter> filters = mapper.getFilters(request);
		assertThat(filters, containsInAnyOrder(filterForFlag12, defaultFilter1, filterForFlag11, defaultFilter2, filterForFlag32));
	}

}
