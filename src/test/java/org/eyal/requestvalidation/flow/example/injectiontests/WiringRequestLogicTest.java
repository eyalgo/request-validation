package org.eyal.requestvalidation.flow.example.injectiontests;
import static org.eyal.requestvalidation.flow.ReflectionUtils.realObjectFromField;
import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.eyal.requestvalidation.flow.Flow;
import org.eyal.requestvalidation.flow.example.flow.RequestLogic;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = { "classpath:spring/module-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class WiringRequestLogicTest {
	@Inject
	private RequestLogic requestLogic;
	
	@Test
	public void verifyFlowIsInjected() {
		Flow flow = realObjectFromField(RequestLogic.class, "flow", requestLogic);
		assertNotNull(flow);
	}
}
