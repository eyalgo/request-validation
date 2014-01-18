package org.eyal.requestvalidation.flow.example.flow.itemsfilter.filters;

import org.eyal.requestvalidation.flow.example.model.ItemImpl;
import org.eyal.requestvalidation.flow.itemsfilter.filters.Filter;
import org.eyal.requestvalidation.model.Item;

public class NameTooLongFilter implements Filter {
	private final static String ERROR_MESSAGE = "Item has long name. Max = %s";

	private int maxLength;

	public NameTooLongFilter(int maxLength) {
		this.maxLength = maxLength;
	}

	@Override
	public boolean apply(Item input) {
		return input.getName().length() <= maxLength;
	}

	@Override
	public String errorMessage() {
		return String.format(ERROR_MESSAGE, maxLength);
	}
	
	public static void main(String[] args) {
		Filter filter = new NameTooLongFilter(4);
		System.out.println(filter.apply(new ItemImpl("23")));
		System.out.println(filter.apply(new ItemImpl("234343434")));
		System.out.println(filter.errorMessage());
	}
}

