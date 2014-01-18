package org.eyal.requestvalidation.model;

import java.util.List;

public class FlowResponse {
	private FlowResponseStatus status;
	private List<String> validationErrorMessages;
	private Request originalRequest;
	private List<InvalidItemInformation> invalidItemsInformations;

	public enum FlowResponseStatus {
		OK, NOT_OK
	}

	public FlowResponse(Request originalRequest, FlowResponseStatus status,
			List<InvalidItemInformation> invalidItemsInformations, List<String> validationErrorMessages) {
		this.originalRequest = originalRequest;
		this.status = status;
		this.invalidItemsInformations = invalidItemsInformations;
		this.validationErrorMessages = validationErrorMessages;
	}

	public FlowResponseStatus status() {
		return status;
	}

	public List<String> messages() {
		return validationErrorMessages;
	}

	public Request originalRequest() {
		return originalRequest;
	}

	public List<InvalidItemInformation> invalidsItemsInformation() {
		return invalidItemsInformations;
	}

}
