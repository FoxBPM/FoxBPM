package org.foxbpm.kernel.advanced.db;

import java.util.Map;

public interface PersistentObject {
	

	  String getId();
	  void setId(String id);

	  Map<String, Object> getPersistentState();

}
