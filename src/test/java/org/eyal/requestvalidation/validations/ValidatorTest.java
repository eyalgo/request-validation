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
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;

@RunWith(MockitoJUnitRunner.class)
public class ValidatorTest {
	private final static String MESSAGE_FOR_VALIDATION_1 = "VALIDATION - 1 - ERROR";
	private final static String MESSAGE_FOR_VALIDATION_2 = "VALIDATION - 2 - ERROR";
	@Mock(name = "validation 1")
	private Validation singleValidation1;
	@Mock(name = "validation 2")
	private Validation singleValidation2;
	@Mock(name = "item 1")
	private Item item1;
	@Mock(name = "item 2")
	private Item item2;

	@InjectMocks
	private Validator validator;

	@Before
	public void setup() {
		when(singleValidation1.errorMessage()).thenReturn(MESSAGE_FOR_VALIDATION_1);
		when(singleValidation2.errorMessage()).thenReturn(MESSAGE_FOR_VALIDATION_2);

		when(item1.getName()).thenReturn("name1");

		when(item2.getName()).thenReturn("name2");
	}

	@Test
	public void verifyThatAllSingleValidatorsAreCalledForValidItems() {
		when(singleValidation1.apply(item1)).thenReturn(true);
		when(singleValidation1.apply(item2)).thenReturn(true);
		when(singleValidation2.apply(item1)).thenReturn(true);
		when(singleValidation2.apply(item2)).thenReturn(true);

		ItemsValidationResponse response = validator.validate(Lists.newArrayList(singleValidation1, singleValidation2),
				Lists.newArrayList(item1, item2));
		assertThat("expected no invalid", response.getInvalidItemsInformations(),
				emptyCollectionOf(InvalidItemInformation.class));
		assertThat(response.getValidItems(), containsInAnyOrder(item1, item2));

		verify(singleValidation1).apply(item1);
		verify(singleValidation1).apply(item2);
		verify(singleValidation2).apply(item1);
		verify(singleValidation2).apply(item2);
		verifyNoMoreInteractions(singleValidation1, singleValidation2);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void itemsFailIndifferentValidatorsShouldGetOnlyFailures() {
		when(singleValidation1.apply(item1)).thenReturn(false);
		when(singleValidation1.apply(item2)).thenReturn(true);
		when(singleValidation2.apply(item2)).thenReturn(false);

		ItemsValidationResponse response = validator.validate(Lists.newArrayList(singleValidation1, singleValidation2),
				Lists.newArrayList(item1, item2));
		assertThat(
				response.getInvalidItemsInformations(),
				containsInAnyOrder(matchInvalidInformation(new InvalidItemInformation(item1, MESSAGE_FOR_VALIDATION_1)),
						matchInvalidInformation(new InvalidItemInformation(item2, MESSAGE_FOR_VALIDATION_2))));
		assertThat(response.getValidItems(), emptyCollectionOf(Item.class));

		verify(singleValidation1).apply(item1);
		verify(singleValidation1).apply(item2);
		verify(singleValidation1).errorMessage();
		verify(singleValidation2).apply(item2);
		verify(singleValidation2).errorMessage();
		verifyNoMoreInteractions(singleValidation1, singleValidation2);
	}

	@Test
	public void firstItemFailSecondItemSuccessShouldGetOneItemInEachList() {
		when(singleValidation1.apply(item1)).thenReturn(true);
		when(singleValidation1.apply(item2)).thenReturn(true);
		when(singleValidation2.apply(item1)).thenReturn(false);
		when(singleValidation2.apply(item2)).thenReturn(true);

		ItemsValidationResponse response = validator.validate(Lists.newArrayList(singleValidation1, singleValidation2),
				Lists.newArrayList(item1, item2));
		assertThat(response.getInvalidItemsInformations(), contains(matchInvalidInformation(new InvalidItemInformation(item1,
				MESSAGE_FOR_VALIDATION_2))));
		assertThat(response.getValidItems(), containsInAnyOrder(item2));

		verify(singleValidation1).apply(item1);
		verify(singleValidation1).apply(item2);
		verify(singleValidation2).apply(item1);
		verify(singleValidation2).apply(item2);
		verify(singleValidation2).errorMessage();
		verifyNoMoreInteractions(singleValidation1, singleValidation2);
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
