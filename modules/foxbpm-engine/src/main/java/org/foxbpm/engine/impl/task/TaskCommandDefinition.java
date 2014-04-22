/**
 * Copyright 1996-2014 FoxBPM Co.,Ltd.
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
 * @author ych
 */
package org.foxbpm.engine.impl.task;

public class TaskCommandDefinition {

	private String id;
	private String name;
	private String commandType;
	private boolean isVerification;
	private boolean isSaveData;
	private boolean isSimulationRun;
	private String expression;
	private String paraExpression;
	
	public TaskCommandDefinition() {
		// TODO Auto-generated constructor stub
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCommandType() {
		return commandType;
	}
	public void setCommandType(String commandType) {
		this.commandType = commandType;
	}
	public boolean isVerification() {
		return isVerification;
	}
	public void setVerification(boolean isVerification) {
		this.isVerification = isVerification;
	}
	public boolean isSaveData() {
		return isSaveData;
	}
	public void setSaveData(boolean isSaveData) {
		this.isSaveData = isSaveData;
	}
	public boolean isSimulationRun() {
		return isSimulationRun;
	}
	public void setSimulationRun(boolean isSimulationRun) {
		this.isSimulationRun = isSimulationRun;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public String getParaExpression() {
		return paraExpression;
	}

	public void setParaExpression(String paraExpression) {
		this.paraExpression = paraExpression;
	}
	
	
	
}
