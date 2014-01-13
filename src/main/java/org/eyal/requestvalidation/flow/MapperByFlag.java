package org.eyal.requestvalidation.flow;

import java.util.List;

import org.eyal.requestvalidation.model.Request;

public interface MapperByFlag<T> {
	List<T> getOperations(Request request);
}
