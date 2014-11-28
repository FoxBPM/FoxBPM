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
 * @author yangguangftlp
 */
package org.foxbpm.engine.config;

import java.util.List;

import org.foxbpm.engine.impl.datavariable.DataObjectDefinitionImpl;
import org.foxbpm.engine.impl.event.EventListenerImpl;
import org.foxbpm.engine.impl.task.TaskCommandDefinitionImpl;
/**
 * 
 * foxbpm配置文件模型
 * 
 * @author yangguangftlp
 * @date 2014年10月29日
 */
public class FoxBPMConfig {
	
	protected List<TaskCommandDefinitionImpl> taskCommandDefinitions;
	protected List<EventListenerImpl> eventListeners;
	protected List<DataObjectDefinitionImpl> dataObjectDefinitions;
	protected List<ProcessEngineConfigurator> configurators;
	
	public List<TaskCommandDefinitionImpl> getTaskCommandDefinitions() {
		return taskCommandDefinitions;
	}
	
	public void setTaskCommandDefinitions(List<TaskCommandDefinitionImpl> taskCommandDefinitions) {
		this.taskCommandDefinitions = taskCommandDefinitions;
	}
	
	public List<EventListenerImpl> getEventListeners() {
		return eventListeners;
	}
	
	public void setEventListeners(List<EventListenerImpl> eventListeners) {
		this.eventListeners = eventListeners;
	}
	
	public void setDataObjectDefinitions(List<DataObjectDefinitionImpl> dataObjectDefinitions) {
		this.dataObjectDefinitions = dataObjectDefinitions;
	}
	
	public List<DataObjectDefinitionImpl> getDataObjectDefinitions() {
		return dataObjectDefinitions;
	}
	
	public void setConfigurators(List<ProcessEngineConfigurator> configurators) {
		this.configurators = configurators;
	}
	
	public List<ProcessEngineConfigurator> getConfigurators() {
		return configurators;
	}
	
	public void addConfig(FoxBPMConfig other) {
		if (other == null) {
			return;
		}
		
		if (other.getTaskCommandDefinitions() != null) {
			if (taskCommandDefinitions == null) {
				taskCommandDefinitions = other.getTaskCommandDefinitions();
			} else {
				taskCommandDefinitions.addAll(other.getTaskCommandDefinitions());
			}
		}
		
		if (other.getEventListeners() != null) {
			if (eventListeners == null) {
				eventListeners = other.getEventListeners();
			} else {
				eventListeners.addAll(other.getEventListeners());
			}
		}
		
		if (other.getDataObjectDefinitions() != null) {
			if (dataObjectDefinitions == null) {
				dataObjectDefinitions = other.getDataObjectDefinitions();
			} else {
				dataObjectDefinitions.addAll(other.getDataObjectDefinitions());
			}
		}
		
		if (other.getConfigurators() != null) {
			if (configurators == null) {
				configurators = other.getConfigurators();
			} else {
				configurators.addAll(other.getConfigurators());
			}
		}
	}
}
