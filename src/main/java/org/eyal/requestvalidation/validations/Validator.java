package org.eyal.requestvalidation.validations;

import java.util.List;

import org.eyal.requestvalidation.model.InvalidItemInformation;
import org.eyal.requestvalidation.model.Item;
import org.eyal.requestvalidation.model.ItemsValidationResponse;

import com.google.common.collect.Lists;

public class Validator {

	public Validator() {
	}

	public ItemsValidationResponse validate(List<Validation> validations, List<Item> items) {
		List<Item> validItems = Lists.newLinkedList(items);
		List<InvalidItemInformation> invalidItemInformations = Lists.newLinkedList();
		for (Validation validator : validations) {
			ItemsValidationResponse responseFromValidator = responseFromValidator(validItems, validator);
			validItems = responseFromValidator.getValidItems();
			invalidItemInformations.addAll(responseFromValidator.getInvalidItemsInformations());
		}

		return new ItemsValidationResponse(validItems, invalidItemInformations);
	}

	private ItemsValidationResponse responseFromValidator(List<Item> items, Validation validator) {
		List<Item> validItems = Lists.newLinkedList();
		List<InvalidItemInformation> invalidItemInformations = Lists.newLinkedList();
		for (Item item : items) {
			if (validator.apply(item)) {
				validItems.add(item);
			} else {
				invalidItemInformations.add(new InvalidItemInformation(item, validator.errorMessage()));
			}
		}
		return new ItemsValidationResponse(validItems, invalidItemInformations);
	}
}
