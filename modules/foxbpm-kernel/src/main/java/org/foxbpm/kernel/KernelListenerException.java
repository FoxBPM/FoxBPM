package org.foxbpm.kernel;

public class KernelListenerException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String tokenId;
	private String nodeId;
	private String listenerId;
	private String eventeName;
	
	public KernelListenerException(String message,Throwable cause,String tokenId,String nodeId,String listenerId,String eventName) {
		super(message,cause);
		this.tokenId = tokenId;
		this.nodeId = nodeId;
		this.listenerId = listenerId;
		this.eventeName = eventName;
	}
	
	public String getTokenId(){
		return tokenId;
	}
	
	public String getNodeId(){
		return nodeId;
	}
	
	public String getListenerId(){
		return listenerId;
	}
	
	public String getEventName(){
		return eventeName;
	}
}
