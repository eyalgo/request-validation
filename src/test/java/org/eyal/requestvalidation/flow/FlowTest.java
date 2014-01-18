package org.eyal.requestvalidation.flow;

import static org.hamcrest.Matchers.emptyCollectionOf;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.eyal.requestvalidation.flow.itemsfilter.filters.Filter;
import org.eyal.requestvalidation.flow.requestvalidation.validations.RequestValidation;
import org.eyal.requestvalidation.model.FlowResponse;
import org.eyal.requestvalidation.model.InvalidItemInformation;
import org.eyal.requestvalidation.model.Item;
import org.eyal.requestvalidation.model.ItemsFilterResponse;
import org.eyal.requestvalidation.model.Request;
import org.eyal.requestvalidation.model.ValidationResponse;
import org.eyal.requestvalidation.model.ValidationResponse.ValidationResponseStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FlowTest {
	@Mock
	private Request inputRequest;

	@Mock
	private ValidationResponse validationResponse;

	@Mock
	private ItemsFilterResponse itemsFilterResponse;
	@Mock
	private List<InvalidItemInformation> invalidsItemsInformation;
	@Mock
	private List<Item> validItemsFromFilter;

	@Mock(name="validation executor")
	private FlowExecutor<RequestValidation, ValidationResponse> validationExecutor;
	
	@Mock(name="filters executor")
	private FlowExecutor<Filter, ItemsFilterResponse> filtersExecutor;

	private Flow flow;

	@Before
	public void setup() {
		when(validationExecutor.execute(inputRequest)).thenReturn(validationResponse);
		when(filtersExecutor.execute(inputRequest)).thenReturn(itemsFilterResponse);
		
		flow = new Flow(validationExecutor, filtersExecutor);
	}

	@Test
	public void whenValidationDoesNotPassShouldReturnMessageWithInvalid() {
		@SuppressWarnings("unchecked")
		List<String> validationErrorMessage = mock(List.class);
		when(validationResponse.getStatus()).thenReturn(ValidationResponseStatus.NOT_OK);
		when(validationResponse.getMessages()).thenReturn(validationErrorMessage);

		FlowResponse flowResponse = flow.process(inputRequest);

		assertThat(flowResponse.status(), equalTo(FlowResponse.FlowResponseStatus.NOT_OK));
		assertThat(flowResponse.messages(), equalTo(validationErrorMessage));
		assertThat(flowResponse.originalRequest(), equalTo(inputRequest));
	}

	@Test
	public void whenValidationOkShouldPassToActionsAndReturnRequestWithItemsFromActions() {
		when(validationResponse.getStatus()).thenReturn(ValidationResponseStatus.OK);

		when(itemsFilterResponse.getInvalidItemsInformations()).thenReturn(invalidsItemsInformation);
		when(itemsFilterResponse.getValidItems()).thenReturn(validItemsFromFilter);

		FlowResponse flowResponse = flow.process(inputRequest);

		assertThat(flowResponse.status(), equalTo(FlowResponse.FlowResponseStatus.OK));
		assertThat(flowResponse.messages(), emptyCollectionOf(String.class));
		assertThat(flowResponse.originalRequest(), equalTo(inputRequest));
		assertThat(flowResponse.invalidsItemsInformation(), equalTo(invalidsItemsInformation));
	}

}
