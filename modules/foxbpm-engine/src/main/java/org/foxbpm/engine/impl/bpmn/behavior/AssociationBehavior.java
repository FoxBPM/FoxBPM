package org.foxbpm.engine.impl.bpmn.behavior;


public class AssociationBehavior extends ArtifactBehavior {

	private static final long serialVersionUID = 1L;
	
	String sourceRefId;
	
	String targetRefId;

    public String getSourceRef(){
    	return sourceRefId;
    }


    public void setSourceRef(String value){
    	this.sourceRefId=value;
    }


    public String getTargetRef(){
    	return targetRefId;
    }

    public void setTargetRef(String value){
    	this.targetRefId=value;
    }

}
