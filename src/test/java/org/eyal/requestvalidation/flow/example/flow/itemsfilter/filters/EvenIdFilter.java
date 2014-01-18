package org.eyal.requestvalidation.flow.example.flow.itemsfilter.filters;

import org.eyal.requestvalidation.flow.example.model.ItemImpl;
import org.eyal.requestvalidation.flow.itemsfilter.filters.Filter;
import org.eyal.requestvalidation.model.Item;

public class EvenIdFilter implements Filter {
	private final static String ERROR_MESSAGE = "The item has even ID";

	public EvenIdFilter() {
	}

	@Override
	public String errorMessage() {
		return ERROR_MESSAGE;
	}

	@Override
	public boolean apply(Item input) {
		return input.getName().length() % 2 == 1;
	}
	
	public static void main(String[] args) {
		Filter filter = new EvenIdFilter();
		
		Item itemEven = new ItemImpl("1234");
		System.out.println(filter.apply(itemEven));

		Item oddEven = new ItemImpl("123");
		System.out.println(filter.apply(oddEven));
	}

}
