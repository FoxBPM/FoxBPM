package org.foxbpm.engine.impl.entity;

import java.io.Serializable;
import java.util.Map;

import org.foxbpm.engine.datavariable.VariableInstance;
import org.foxbpm.engine.db.HasRevision;
import org.foxbpm.engine.db.PersistentObject;
import org.foxbpm.engine.impl.datavariable.DataVariableDefinition;
import org.foxbpm.engine.impl.mgmt.DataVariableMgmtInstance;


public class VariableInstanceEntity implements VariableInstance, PersistentObject, HasRevision, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public VariableInstanceEntity(
			DataVariableDefinition dataVariableDefinition,
			DataVariableMgmtInstance dataVariableMgmtInstance) {
		// TODO Auto-generated constructor stub
	}

	public void setRevision(int revision) {
		// TODO Auto-generated method stub
		
	}

	public int getRevision() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getRevisionNext() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setId(String id) {
		// TODO Auto-generated method stub
		
	}

	public Map<String, Object> getPersistentState() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isModified() {
		// TODO Auto-generated method stub
		return false;
	}

}
