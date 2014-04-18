package org.foxbpm.engine.impl.entity;

import java.util.Map;

import org.foxbpm.engine.db.HasRevision;
import org.foxbpm.engine.db.PersistentObject;
import org.foxbpm.engine.task.Task;
import org.foxbpm.kernel.runtime.impl.KernelVariableScopeImpl;

public class TaskEntity extends KernelVariableScopeImpl implements Task, PersistentObject, HasRevision {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;

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
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Map<String, Object> getPersistentState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected KernelVariableScopeImpl getParentVariableScope() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void ensureParentInitialized() {
		// TODO Auto-generated method stub
		
	}



}
