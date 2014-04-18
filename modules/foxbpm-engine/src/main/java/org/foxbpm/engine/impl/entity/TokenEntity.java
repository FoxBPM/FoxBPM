package org.foxbpm.engine.impl.entity;

import java.util.Map;

import org.foxbpm.engine.db.HasRevision;
import org.foxbpm.engine.db.PersistentObject;
import org.foxbpm.engine.runtime.Token;
import org.foxbpm.kernel.runtime.impl.KernelTokenImpl;

public class TokenEntity extends KernelTokenImpl implements Token,PersistentObject,HasRevision{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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

	public void setId(String id) {
		// TODO Auto-generated method stub
		
	}

	public Map<String, Object> getPersistentState() {
		// TODO Auto-generated method stub
		return null;
	}

}
