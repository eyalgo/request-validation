package org.eyal.requestvalidation.flow;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

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
public class AbstractMapperByFlagTest {
	private final static String FLAG_1 = "flag 1";
	private final static String FLAG_2 = "flag 2";

	@Mock
	private Request request;

	private String defaultOperation1 = "defaultOperation1";
	private String defaultOperation2 = "defaultOperation2";
	private String mapOperation11 = "mapOperation11";
	private String mapOperation12 = "mapOperation12";
	private String mapOperation23 = "mapOperation23";

	private MapperByFlag<String> mapper;

	@Before
	public void setup() {
		List<String> defaults = Lists.newArrayList(defaultOperation1, defaultOperation2);
		Map<String, List<String>> mapped = ImmutableMap.<String, List<String>> builder()
		        .put(FLAG_1, Lists.newArrayList(mapOperation11, mapOperation12))
		        .put(FLAG_2, Lists.newArrayList(mapOperation23, mapOperation11)).build();
		mapper = new AbstractMapperByFlag<String>(defaults, mapped) {
		};
	}

	@Test
	public void whenRequestDoesNotHaveFlagsShouldReturnDefaultFiltersOnly() {
		when(request.getFlags()).thenReturn(Sets.<String> newHashSet());

		List<String> filters = mapper.getOperations(request);
		assertThat(filters, containsInAnyOrder(defaultOperation1, defaultOperation2));
	}

	@Test
	public void whenRequestHasFlagsNotInMappingShouldReturnDefaultFiltersOnly() {
		when(request.getFlags()).thenReturn(Sets.<String> newHashSet("un-mapped-flag"));
		List<String> filters = mapper.getOperations(request);
		assertThat(filters, containsInAnyOrder(defaultOperation1, defaultOperation2));
	}
	
	@Test
	public void whenRequestHasOneFlagShouldReturnWithDefaultAndMappedFilters() {
		when(request.getFlags()).thenReturn(Sets.<String> newHashSet(FLAG_1));
		List<String> filters = mapper.getOperations(request);
		assertThat(filters, containsInAnyOrder(mapOperation12, defaultOperation1, mapOperation11, defaultOperation2));
	}
	
	@Test
	public void whenRequestHasTwoFlagsShouldReturnWithDefaultAndMappedFiltersWithoutDuplications() {
		when(request.getFlags()).thenReturn(Sets.<String> newHashSet(FLAG_1, FLAG_2));
		List<String> filters = mapper.getOperations(request);
		assertThat(filters, containsInAnyOrder(mapOperation12, defaultOperation1, mapOperation11, defaultOperation2, mapOperation23));
	}
}
