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
 * @author ych
 */
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
