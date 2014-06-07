package org.foxbpm.web.db.interfaces;

import java.sql.Connection;

import org.foxbpm.web.common.exception.FoxbpmWebException;
import org.foxbpm.web.model.BizInfo;

public interface BizDBInterface {
	public void insertBizInfo(BizInfo bizInfo, Connection connection)
			throws FoxbpmWebException;
}
