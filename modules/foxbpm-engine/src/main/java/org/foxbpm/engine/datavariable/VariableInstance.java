/**
 * 
 */
package org.foxbpm.engine.datavariable;

/**
 * @author kenshin
 * 
 */
public interface VariableInstance {

	String getId();

	String getProcessInstanceId();

	String getProcessDefinitionId();

	String getProcessDefinitionKey();

	String getKey();

	String getTaskId();

	String getTokenId();

	String getNodeId();

	Object getValueObject();
}
