package org.eyal.requestvalidation.flow;

import java.util.List;

import org.eyal.requestvalidation.model.Request;

public interface FlowEngine<R, T extends FlowOperation<?>> {
	R applyOperations(List<T> operations, Request request);
}
