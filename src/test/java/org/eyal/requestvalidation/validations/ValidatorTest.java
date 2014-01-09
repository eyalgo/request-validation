package org.eyal.requestvalidation.validations;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.emptyCollectionOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.eyal.requestvalidation.model.InvalidItemInformation;
import org.eyal.requestvalidation.model.Item;
import org.eyal.requestvalidation.model.ItemsValidationResponse;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.common.collect.Lists;

public class ValidatorTest {
	private final static String MESSAGE_FOR_VALIDATOR_1 = "VALIDATOR - 1 - ERROR";
	private final static String MESSAGE_FOR_VALIDATOR_2 = "VALIDATOR - 2 - ERROR";
	@Mock(name = "validation 1")
	private Validation singleValidator1;
	@Mock(name = "validation 2")
	private Validation singleValidator2;
	@Mock(name = "item 1")
	private Item item1;
	@Mock(name = "item 2")
	private Item item2;

	@InjectMocks
	private Validator validator;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);

		when(singleValidator1.errorMessage()).thenReturn(MESSAGE_FOR_VALIDATOR_1);
		when(singleValidator2.errorMessage()).thenReturn(MESSAGE_FOR_VALIDATOR_2);

		when(item1.getName()).thenReturn("name1");

		when(item2.getName()).thenReturn("name2");
	}

	@Test
	public void verifyThatAllSingleValidatorsAreCalledForValidItems() {
		when(singleValidator1.apply(item1)).thenReturn(true);
		when(singleValidator1.apply(item2)).thenReturn(true);
		when(singleValidator2.apply(item1)).thenReturn(true);
		when(singleValidator2.apply(item2)).thenReturn(true);

		ItemsValidationResponse response = validator.validate(Lists.newArrayList(singleValidator1, singleValidator2),
				Lists.newArrayList(item1, item2));
		assertThat("expected no invalid", response.getInvalidItemsInformations(),
				emptyCollectionOf(InvalidItemInformation.class));
		assertThat(response.getValidItems(), containsInAnyOrder(item1, item2));

		verify(singleValidator1).apply(item1);
		verify(singleValidator1).apply(item2);
		verify(singleValidator2).apply(item1);
		verify(singleValidator2).apply(item2);
		verifyNoMoreInteractions(singleValidator1, singleValidator2);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void itemsFailIndifferentValidatorsShouldGetOnlyFailures() {
		when(singleValidator1.apply(item1)).thenReturn(false);
		when(singleValidator1.apply(item2)).thenReturn(true);
		when(singleValidator2.apply(item2)).thenReturn(false);

		ItemsValidationResponse response = validator.validate(Lists.newArrayList(singleValidator1, singleValidator2),
				Lists.newArrayList(item1, item2));
		assertThat(
				response.getInvalidItemsInformations(),
				containsInAnyOrder(matchInvalidInformation(new InvalidItemInformation(item1, MESSAGE_FOR_VALIDATOR_1)),
						matchInvalidInformation(new InvalidItemInformation(item2, MESSAGE_FOR_VALIDATOR_2))));
		assertThat(response.getValidItems(), emptyCollectionOf(Item.class));

		verify(singleValidator1).apply(item1);
		verify(singleValidator1).apply(item2);
		verify(singleValidator1).errorMessage();
		verify(singleValidator2).apply(item2);
		verify(singleValidator2).errorMessage();
		verifyNoMoreInteractions(singleValidator1, singleValidator2);
	}

	@Test
	public void firstItemFailSecondItemSuccessShouldGetOneItemInEachList() {
		when(singleValidator1.apply(item1)).thenReturn(true);
		when(singleValidator1.apply(item2)).thenReturn(true);
		when(singleValidator2.apply(item1)).thenReturn(false);
		when(singleValidator2.apply(item2)).thenReturn(true);

		ItemsValidationResponse response = validator.validate(Lists.newArrayList(singleValidator1, singleValidator2),
				Lists.newArrayList(item1, item2));
		assertThat(response.getInvalidItemsInformations(), contains(matchInvalidInformation(new InvalidItemInformation(item1,
				MESSAGE_FOR_VALIDATOR_2))));
		assertThat(response.getValidItems(), containsInAnyOrder(item2));

		verify(singleValidator1).apply(item1);
		verify(singleValidator1).apply(item2);
		verify(singleValidator2).apply(item1);
		verify(singleValidator2).apply(item2);
		verify(singleValidator2).errorMessage();
		verifyNoMoreInteractions(singleValidator1, singleValidator2);
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
