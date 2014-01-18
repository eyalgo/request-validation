package org.eyal.requestvalidation.flow.example.model;

import java.util.List;
import java.util.Set;

import org.eyal.requestvalidation.model.Item;
import org.eyal.requestvalidation.model.Request;

public class RequestImpl implements Request {

	private final List<Item> items;
	private final Set<String> flags;
	private final String id;

	public RequestImpl(String id, List<Item> items, Set<String> flags) {
		this.id = id;
		this.items = items;
		this.flags = flags;
	}

	@Override
	public Set<String> getFlags() {
		return flags;
	}

	@Override
	public List<Item> getItems() {
		return items;
	}

	@Override
	public String getId() {
		return id;
	}
}
