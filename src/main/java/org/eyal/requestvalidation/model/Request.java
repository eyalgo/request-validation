package org.eyal.requestvalidation.model;

import java.util.List;
import java.util.Set;

public interface Request {
	Set<String> getFlags();

	List<Item> getItems();
}
