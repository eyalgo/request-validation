package org.eyal.requestvalidation.flow.requestaction;

import java.util.List;

import org.eyal.requestvalidation.flow.requestaction.actions.RequestAction;
import org.eyal.requestvalidation.model.Request;

public interface RequestActionsEngine {
	Request applyActions(List<RequestAction> actions, Request request);
}
