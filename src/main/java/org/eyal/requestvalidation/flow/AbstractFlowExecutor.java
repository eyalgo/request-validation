package org.eyal.requestvalidation.flow;

import java.util.List;

import org.eyal.requestvalidation.model.Request;

public abstract class AbstractFlowExecutor<T extends FlowOperation<?>, R> {

	private MapperByFlag<T> mapper;
	private FlowEngine<R,T> engine;

	public AbstractFlowExecutor(MapperByFlag<T> mapper, FlowEngine<R,T> engine) {
		this.mapper = mapper;
		this.engine = engine;
	}
	
	public final R execute(Request request) {
		List<T> validations = mapper.getOperations(request);
		return engine.applyOperations(validations, request);
	}

}
