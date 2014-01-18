package org.eyal.requestvalidation.flow.example.flow.itemsfilter.filters;

import org.eyal.requestvalidation.flow.itemsfilter.filters.Filter;
import org.eyal.requestvalidation.model.Item;

public class EmptyNameFilter implements Filter {

	@Override
	public String errorMessage() {
		return "Item has empty name";
	}

	@Override
	public boolean apply(Item input) {
		if (input.getName() == null || input.getName().isEmpty()) {
			return false;
		}
		return true;
	}

}
