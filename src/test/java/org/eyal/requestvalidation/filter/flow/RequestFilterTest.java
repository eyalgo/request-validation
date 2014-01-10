package org.eyal.requestvalidation.filter.flow;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.eyal.requestvalidation.filter.FiltersEngine;
import org.eyal.requestvalidation.filter.filters.Filter;
import org.eyal.requestvalidation.model.Item;
import org.eyal.requestvalidation.model.ItemsFilterResponse;
import org.eyal.requestvalidation.model.Request;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RequestFilterTest {
	@Mock
	private ItemFiltersByFlagMapper filtersMapper;
	@Mock
	private FiltersEngine filtersEngine;
	
	@SuppressWarnings("unchecked")
    @Test
	public void verifyCallToMapperEngineShouldReturnResponse() {
		
		List<Item> items = mock(List.class);
		Request request = mock(Request.class);
		when(request.getItems()).thenReturn(items);
		
		List<Filter> filters = mock(List.class);
		ItemsFilterResponse response = mock(ItemsFilterResponse.class);
		
		when(filtersMapper.getFilters(request)).thenReturn(filters);
		when(filtersEngine.applyFilters(filters, items)).thenReturn(response);
		
		RequestFilter requestFilter = new RequestFilter(filtersMapper, filtersEngine);

		assertThat(requestFilter.filter(request), equalTo(response));
	}
}
