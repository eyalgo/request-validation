package org.eyal.requestvalidation.model;

public class InvalidItemInformation {
	private final String name;
	private final String errorMessage;

	public InvalidItemInformation(Item item, String errorMessage) {
		this.name = item.getName();
		this.errorMessage = errorMessage;
	}
	
	public String getName() {
		return name;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
}
