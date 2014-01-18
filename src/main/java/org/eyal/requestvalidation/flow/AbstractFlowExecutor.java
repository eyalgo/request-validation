package org.eyal.requestvalidation.flow;

import java.util.List;

import org.eyal.requestvalidation.model.Request;

public abstract class AbstractFlowExecutor<T extends FlowOperation<?>, R> implements FlowExecutor<T,R> {

	private MapperByFlag<T> mapper;
	private FlowEngine<R, T> engine;

	public AbstractFlowExecutor(MapperByFlag<T> mapper, FlowEngine<R, T> engine) {
		this.mapper = mapper;
		this.engine = engine;
	}

	@Override
	public final R execute(Request request) {
		List<T> operations = mapper.getOperations(request);
		return engine.applyOperations(operations, request);
	}

}
