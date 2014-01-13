package org.eyal.requestvalidation.model;

import java.util.List;

import com.google.common.collect.Lists;

public class ValidationResponse {
	public enum ValidationResponseStatus {
		OK, NOT_OK;
	}
	
	private ValidationResponseStatus status;
	private List<String> errorMessages;

	private ValidationResponse(ValidationResponseStatus status, List<String> errorMessages) {
		this.status = status;
		this.errorMessages = errorMessages;
		
	}

	public ValidationResponseStatus getStatus() {
	    return status;
    }
	
	public List<String> getMessages() {
	    return errorMessages;
    }

	public static ValidationResponse pass() {
		return new ValidationResponse(ValidationResponseStatus.OK, Lists.<String>newLinkedList());
	}
	
	public static ValidationResponse fail(List<String> errorMessages) {
		return new ValidationResponse(ValidationResponseStatus.NOT_OK, errorMessages);
	}
}