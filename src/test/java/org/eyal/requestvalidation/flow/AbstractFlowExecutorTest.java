package org.eyal.requestvalidation.flow;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.eyal.requestvalidation.model.Request;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AbstractFlowExecutorTest {
	@Mock
	private MapperByFlag<DummyOperation> mapper;
	@Mock
	private DummyEngine engine;

	private AbstractFlowExecutor<DummyOperation, Integer> executor;

	@Before
	public void setup() {
		executor = new AbstractFlowExecutor<DummyOperation, Integer>(mapper, engine) {
		};
	}

	@SuppressWarnings("unchecked")
	@Test
	public void verifyCallToMapperEngineShouldReturnResponse() {
		Request request = mock(Request.class);

		List<DummyOperation> filters = mock(List.class);
		Integer response = 12121;

		when(mapper.getOperations(request)).thenReturn(filters);
		when(engine.applyOperations(filters, request)).thenReturn(response);

		assertThat(executor.execute(request), equalTo(response));
	}

	private interface DummyOperation extends FlowOperation<String> {

	}

	private interface DummyEngine extends FlowEngine<Integer, DummyOperation> {
	}
}
