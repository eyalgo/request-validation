package org.eyal.requestvalidation.flow.example.wiring;

import org.eyal.requestvalidation.flow.Flow;
import org.eyal.requestvalidation.model.Request;
import org.springframework.stereotype.Service;

@Service
public class RequestLogic {

	private Flow flow;

	public RequestLogic(Flow flow) {
		this.flow = flow;
	}

	public void doSomething(Request request) {
		flow.process(request);
	}

}
