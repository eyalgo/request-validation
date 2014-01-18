package org.eyal.requestvalidation.flow;

import org.eyal.requestvalidation.model.Request;

public interface FlowExecutor<T extends FlowOperation<?>, R> {
	R execute(Request request);
}
