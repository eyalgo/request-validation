package org.eyal.requestvalidation.flow;

import org.eyal.requestvalidation.flow.itemsfilter.filters.Filter;
import org.eyal.requestvalidation.flow.requestvalidation.validations.RequestValidation;
import org.eyal.requestvalidation.model.FlowResponse;
import org.eyal.requestvalidation.model.FlowResponse.FlowResponseStatus;
import org.eyal.requestvalidation.model.InvalidItemInformation;
import org.eyal.requestvalidation.model.ItemsFilterResponse;
import org.eyal.requestvalidation.model.Request;
import org.eyal.requestvalidation.model.ValidationResponse;
import org.eyal.requestvalidation.model.ValidationResponse.ValidationResponseStatus;

import com.google.common.collect.Lists;

public class Flow {

	private FlowExecutor<RequestValidation, ValidationResponse> validationExecutor;
	private FlowExecutor<Filter, ItemsFilterResponse> filtersExecutor;

	public Flow(FlowExecutor<RequestValidation, ValidationResponse> validationExecutor,
			FlowExecutor<Filter, ItemsFilterResponse> filtersExecutor) {
		this.validationExecutor = validationExecutor;
		this.filtersExecutor = filtersExecutor;
	}

	public FlowResponse process(Request inputRequest) {
		ValidationResponse validationResponse = validationExecutor.execute(inputRequest);
		if (validationResponse.getStatus().equals(ValidationResponseStatus.OK)) {
			return analyzeRequest(inputRequest);
		} else {
			return new FlowResponse(inputRequest, FlowResponseStatus.NOT_OK,
					Lists.<InvalidItemInformation> newLinkedList(), validationResponse.getMessages());
		}
	}

	private FlowResponse analyzeRequest(Request inputRequest) {
		ItemsFilterResponse filterResponse = filtersExecutor.execute(inputRequest);

		return new FlowResponse(inputRequest, FlowResponseStatus.OK, filterResponse.getInvalidItemsInformations(),
				Lists.<String> newLinkedList());
	}

}
