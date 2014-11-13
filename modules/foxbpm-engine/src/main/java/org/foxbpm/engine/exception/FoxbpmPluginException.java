package org.foxbpm.engine.exception;

public class FoxbpmPluginException extends FoxBPMException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String pluginName;
	
	public FoxbpmPluginException(String message,String pluginName){
		super(message);
		this.pluginName = pluginName;
	}
	public FoxbpmPluginException(String message,String pluginName,Throwable cause) {
		super(message,cause);
		this.pluginName = pluginName;
	}
	
	public String getPluginName(){
		return pluginName;
	}
}
