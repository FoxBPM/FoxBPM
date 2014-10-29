/**
 * Copyright 1996-2014 FoxBPM ORG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * @author kenshin
 */
package org.foxbpm.engine.task;

import java.util.List;
import java.util.Map;

import org.foxbpm.engine.expression.Expression;
import org.foxbpm.engine.impl.bpmn.behavior.UserTaskBehavior;
import org.foxbpm.engine.impl.task.CommandParam;
import org.foxbpm.kernel.runtime.FlowNodeExecutionContext;

/**
 * @author kenshin
 *
 */
public interface TaskCommand {
	

	public String getId();

	public String getName();

	public Expression getExpression();
	
	public Object getExpressionValue(FlowNodeExecutionContext executionContext);

	public Expression getExpressionParam();

	public String getTaskCommandType();

	public UserTaskBehavior getUserTask();

	public String getTaskCommandDefType();

	public Map<String, Object> getPersistentState();
	
	public List<CommandParam> getCommandParamsByType(CommandParamType commandParamType);
	
	public CommandParam getCommandParam(String paramKey);

	public List<CommandParam> getCommandParams();

}
