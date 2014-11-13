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
package org.foxbpm.engine.impl.task.cmd;

import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.impl.task.command.AbstractCustomExpandTaskCommand;
import org.foxbpm.engine.impl.task.command.ExpandTaskCommand;
import org.foxbpm.engine.impl.util.ExceptionUtil;
import org.foxbpm.engine.impl.util.ReflectUtil;
import org.foxbpm.engine.task.TaskCommandDefinition;

public class ExpandTaskCompleteCmd<T> implements Command<T>{

	protected ExpandTaskCommand expandTaskCommand;
	
	public ExpandTaskCompleteCmd (ExpandTaskCommand expandTaskCommand){
		this.expandTaskCommand=expandTaskCommand;
	}
	
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public T execute(CommandContext commandContext) {
		
		if(Authentication.getAuthenticatedUserId()==null||Authentication.getAuthenticatedUserId().equals("")){
			throw ExceptionUtil.getException("10501001");
		}
		// TODO Auto-generated method stub
		Object[] obj = new Object[] {expandTaskCommand};  
		
		
		TaskCommandDefinition taskCommandDef= commandContext.getProcessEngineConfigurationImpl().getTaskCommandDefinition(this.expandTaskCommand.getCommandType());
		if(taskCommandDef!=null){
			String classNameString=taskCommandDef.getCmdClass();
			if(classNameString==null||classNameString.equals("")){
				throw ExceptionUtil.getException("10512001",this.expandTaskCommand.getCommandType());
			}
			
			String commandClassNameString=taskCommandDef.getCommandClass();
			if(commandClassNameString==null||commandClassNameString.equals("")){
				throw ExceptionUtil.getException("10512002",this.expandTaskCommand.getCommandType());
			}
			
			//commandClassNameString
			AbstractCustomExpandTaskCommand abstractCustomExpandTaskCommand= null;
			try{
				abstractCustomExpandTaskCommand = (AbstractCustomExpandTaskCommand)ReflectUtil.instantiate(commandClassNameString, obj);
			}catch(Exception ex){
				throw ExceptionUtil.getException("10505001",ex);
			}
			
			Object[] objTemp = new Object[] {abstractCustomExpandTaskCommand};  
			AbstractExpandTaskCmd abstractExpandTaskCmd=null;
			try{
				abstractExpandTaskCmd = (AbstractExpandTaskCmd)ReflectUtil.instantiate(classNameString, objTemp);
			}catch(Exception ex){
				throw ExceptionUtil.getException("10505002",ex);
			}
			
			return (T) abstractExpandTaskCmd.execute(commandContext);
			
		}
		else{
			throw ExceptionUtil.getException("10512003",this.expandTaskCommand.getCommandType());
		}
	

	}


}
