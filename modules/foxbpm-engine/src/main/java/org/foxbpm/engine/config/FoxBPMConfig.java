package org.foxbpm.engine.config;

import java.util.List;

import org.foxbpm.engine.impl.datavariable.DataObjectDefinitionImpl;
import org.foxbpm.engine.impl.event.EventListenerImpl;
import org.foxbpm.engine.impl.task.TaskCommandDefinitionImpl;

public class FoxBPMConfig {
	
	protected List<TaskCommandDefinitionImpl> taskCommandDefinitions;
	protected List<EventListenerImpl> eventListeners;
	protected List<DataObjectDefinitionImpl> dataObjectDefinitions;
	
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
	
	public void addConfig(FoxBPMConfig other){
		if(other == null){
			return;
		}
		
		if(other.getTaskCommandDefinitions() != null){
			if(taskCommandDefinitions == null){
				taskCommandDefinitions = other.getTaskCommandDefinitions();
			}else{
				taskCommandDefinitions.addAll(other.getTaskCommandDefinitions());
			}
		}
		
		if(other.getEventListeners() != null){
			if(eventListeners == null){
				eventListeners = other.getEventListeners();
			}else{
				eventListeners.addAll(other.getEventListeners());
			}
		}
		
		if(other.getDataObjectDefinitions() != null){
			if(dataObjectDefinitions == null){
				dataObjectDefinitions = other.getDataObjectDefinitions();
			}else{
				dataObjectDefinitions.addAll(other.getDataObjectDefinitions());
			}
		}
		
	}
}
