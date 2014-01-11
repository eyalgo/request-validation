package org.eyal.requestvalidation.filter;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.emptyCollectionOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.eyal.requestvalidation.filter.filters.Filter;
import org.eyal.requestvalidation.model.InvalidItemInformation;
import org.eyal.requestvalidation.model.Item;
import org.eyal.requestvalidation.model.ItemsFilterResponse;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;

@RunWith(MockitoJUnitRunner.class)
public class FiltersEngineTest {
	private final static String MESSAGE_FOR_FILTER_1 = "FILTER - 1 - ERROR";
	private final static String MESSAGE_FOR_Filter_2 = "FILTER - 2 - ERROR";
	@Mock(name = "filter 1")
	private Filter singleFilter1;
	@Mock(name = "filter 2")
	private Filter singleFilter2;
	@Mock(name = "item 1")
	private Item item1;
	@Mock(name = "item 2")
	private Item item2;

	@InjectMocks
	private FiltersEngine filtersEngine;

	@Before
	public void setup() {
		when(singleFilter1.errorMessage()).thenReturn(MESSAGE_FOR_FILTER_1);
		when(singleFilter2.errorMessage()).thenReturn(MESSAGE_FOR_Filter_2);

		when(item1.getName()).thenReturn("name1");

		when(item2.getName()).thenReturn("name2");
	}

	@Test
	public void verifyThatAllSingleFiltersAreCalledForValidItems() {
		when(singleFilter1.apply(item1)).thenReturn(true);
		when(singleFilter1.apply(item2)).thenReturn(true);
		when(singleFilter2.apply(item1)).thenReturn(true);
		when(singleFilter2.apply(item2)).thenReturn(true);

		ItemsFilterResponse response = filtersEngine.applyFilters(Lists.newArrayList(singleFilter1, singleFilter2),
				Lists.newArrayList(item1, item2));
		assertThat("expected no invalid", response.getInvalidItemsInformations(),
				emptyCollectionOf(InvalidItemInformation.class));
		assertThat(response.getValidItems(), containsInAnyOrder(item1, item2));

		verify(singleFilter1).apply(item1);
		verify(singleFilter1).apply(item2);
		verify(singleFilter2).apply(item1);
		verify(singleFilter2).apply(item2);
		verifyNoMoreInteractions(singleFilter1, singleFilter2);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void itemsFailIndifferentFiltersShouldGetOnlyFailures() {
		when(singleFilter1.apply(item1)).thenReturn(false);
		when(singleFilter1.apply(item2)).thenReturn(true);
		when(singleFilter2.apply(item2)).thenReturn(false);

		ItemsFilterResponse response = filtersEngine.applyFilters(Lists.newArrayList(singleFilter1, singleFilter2),
				Lists.newArrayList(item1, item2));
		assertThat(
				response.getInvalidItemsInformations(),
				containsInAnyOrder(matchInvalidInformation(new InvalidItemInformation(item1, MESSAGE_FOR_FILTER_1)),
						matchInvalidInformation(new InvalidItemInformation(item2, MESSAGE_FOR_Filter_2))));
		assertThat(response.getValidItems(), emptyCollectionOf(Item.class));

		verify(singleFilter1).apply(item1);
		verify(singleFilter1).apply(item2);
		verify(singleFilter1).errorMessage();
		verify(singleFilter2).apply(item2);
		verify(singleFilter2).errorMessage();
		verifyNoMoreInteractions(singleFilter1, singleFilter2);
	}

	@Test
	public void firstItemFailSecondItemSuccessShouldGetOneItemInEachList() {
		when(singleFilter1.apply(item1)).thenReturn(true);
		when(singleFilter1.apply(item2)).thenReturn(true);
		when(singleFilter2.apply(item1)).thenReturn(false);
		when(singleFilter2.apply(item2)).thenReturn(true);

		ItemsFilterResponse response = filtersEngine.applyFilters(Lists.newArrayList(singleFilter1, singleFilter2),
				Lists.newArrayList(item1, item2));
		assertThat(response.getInvalidItemsInformations(), contains(matchInvalidInformation(new InvalidItemInformation(item1,
				MESSAGE_FOR_Filter_2))));
		assertThat(response.getValidItems(), containsInAnyOrder(item2));

		verify(singleFilter1).apply(item1);
		verify(singleFilter1).apply(item2);
		verify(singleFilter2).apply(item1);
		verify(singleFilter2).apply(item2);
		verify(singleFilter2).errorMessage();
		verifyNoMoreInteractions(singleFilter1, singleFilter2);
	}

	private static BaseMatcher<InvalidItemInformation> matchInvalidInformation(InvalidItemInformation expected) {
		return new InvalidItemInformationMatcher(expected);
	}

	private final static class InvalidItemInformationMatcher extends BaseMatcher<InvalidItemInformation> {
		private InvalidItemInformation expected;

		private InvalidItemInformationMatcher(InvalidItemInformation expected) {
			this.expected = expected;
		}

		public boolean matches(Object itemInformation) {
			InvalidItemInformation actual = (InvalidItemInformation) itemInformation;
			return actual.getName().equals(expected.getName())
					&& actual.getErrorMessage().equals(expected.getErrorMessage());
		}

		public void describeTo(Description description) {
		}
	}
}
