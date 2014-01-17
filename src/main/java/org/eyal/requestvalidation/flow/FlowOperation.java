package org.eyal.requestvalidation.flow;

import com.google.common.base.Predicate;

public interface FlowOperation<T> extends Predicate<T> {
	String errorMessage();
}
