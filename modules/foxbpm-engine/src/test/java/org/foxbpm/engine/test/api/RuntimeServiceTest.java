package org.foxbpm.engine.test.api;

import java.util.List;

import org.foxbpm.engine.runtime.Token;
import org.foxbpm.engine.runtime.TokenQuery;
import org.foxbpm.engine.test.AbstractFoxBpmTestCase;

public class RuntimeServiceTest extends AbstractFoxBpmTestCase {
	
	public void testTokenQuery(){
		TokenQuery tokenQuery = runtimeService.createTokenQuery();
		tokenQuery.tokenId("2222");
		List<Token> tokens = tokenQuery.list();
		System.out.println("********************"+tokens.size());
		
		tokenQuery = runtimeService.createTokenQuery();
		tokenQuery.processInstanceId("18aebfe6-c30a-40a8-ba6d-ce6c19114ba8");
		tokens = tokenQuery.list();
		System.out.println("********************"+tokens.size());
		
		tokenQuery = runtimeService.createTokenQuery();
		tokenQuery.tokenIsEnd();
		tokens = tokenQuery.list();
		System.out.println("********************"+tokens.size());
		
		tokenQuery = runtimeService.createTokenQuery();
		tokenQuery.tokenNotEnd();
		tokens = tokenQuery.list();
		System.out.println("********************"+tokens.size());
	
	}
}
