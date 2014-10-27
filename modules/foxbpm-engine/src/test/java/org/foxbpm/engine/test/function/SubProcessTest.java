package org.foxbpm.engine.test.function;

import org.foxbpm.engine.test.AbstractFoxBpmTestCase;
import org.foxbpm.engine.test.Deployment;
import org.junit.Test;

public class SubProcessTest extends AbstractFoxBpmTestCase {

	@Test
	@Deployment(resources={"org/foxbpm/test/function/skipStrategy_1.bpmn"})
	public void subProcessTest(){
		
	}
}
