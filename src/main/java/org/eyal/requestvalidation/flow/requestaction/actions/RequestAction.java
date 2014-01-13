package org.eyal.requestvalidation.flow.requestaction.actions;

import org.eyal.requestvalidation.model.Request;

public interface RequestAction {
	Request apply(Request maiaInsertRequest);
}
