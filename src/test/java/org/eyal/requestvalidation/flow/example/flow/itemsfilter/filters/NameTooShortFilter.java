package org.eyal.requestvalidation.flow.example.flow.itemsfilter.filters;

import org.eyal.requestvalidation.flow.example.model.ItemImpl;
import org.eyal.requestvalidation.flow.itemsfilter.filters.Filter;
import org.eyal.requestvalidation.model.Item;

public class NameTooShortFilter implements Filter {
	private final static String ERROR_MESSAGE = "Item has short name. Min = %s";

	private int minLength;

	public NameTooShortFilter(int minLength) {
		this.minLength = minLength;
	}

	@Override
	public boolean apply(Item input) {
		return input.getName().length() > minLength;
	}

	@Override
	public String errorMessage() {
		return String.format(ERROR_MESSAGE, minLength);
	}
	
	public static void main(String[] args) {
		Filter filter = new NameTooShortFilter(4);
		System.out.println(filter.apply(new ItemImpl("23")));
		System.out.println(filter.apply(new ItemImpl("234343434")));
		System.out.println(filter.errorMessage());
	}
}