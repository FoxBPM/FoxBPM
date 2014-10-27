package org.foxbpm.engine.config;

public class FoxBPMConfig {
	
	protected TaskCommandConfig taskCommands;
	protected EventListenerConfig eventListeners;
	protected BizDataObjectConfig bizDataObject;
	
	public TaskCommandConfig getTaskCommands() {
		return taskCommands;
	}
	public void setTaskCommands(TaskCommandConfig taskCommands) {
		this.taskCommands = taskCommands;
	}
	public EventListenerConfig getEventListeners() {
		return eventListeners;
	}
	public void setEventListeners(EventListenerConfig eventListeners) {
		this.eventListeners = eventListeners;
	}
	public BizDataObjectConfig getBizDataObject() {
		return bizDataObject;
	}
	public void setBizDataObject(BizDataObjectConfig bizDataObject) {
		this.bizDataObject = bizDataObject;
	}
}
