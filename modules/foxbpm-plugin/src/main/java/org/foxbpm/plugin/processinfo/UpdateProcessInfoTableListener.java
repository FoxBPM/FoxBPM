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
 * @author yangguangftlp
 */
package org.foxbpm.plugin.processinfo;

import java.util.Date;

import org.foxbpm.engine.impl.db.SqlCommand;
import org.foxbpm.engine.impl.entity.ProcessInstanceEntity;
import org.foxbpm.engine.impl.util.DBUtils;
import org.foxbpm.engine.impl.util.LocationUtil;
import org.foxbpm.kernel.event.KernelListener;
import org.foxbpm.kernel.runtime.ListenerExecutionContext;
import org.foxbpm.kernel.runtime.impl.KernelTokenImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 流程保存时更新流程信息
 * 
 * @author yangguangftlp
 * @date 2014年11月20日
 */
public class UpdateProcessInfoTableListener implements KernelListener {
	private static Logger LOG = LoggerFactory.getLogger(UpdateProcessInfoTableListener.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = -7673078102858714126L;
	
	public void notify(ListenerExecutionContext executionContext) throws Exception {
		try {
			KernelTokenImpl kernelTokenImpl = (KernelTokenImpl) executionContext;
			ProcessInstanceEntity processInstanceEntity = (ProcessInstanceEntity) kernelTokenImpl.getProcessInstance();
			String sql = "UPDATE FOXBPM_RUN_PROCESS_INFO SET UPDATE_TIME = ?,PROCESS_STATUS= ?,PROCESS_STEP= ?,INITIATOR= ?,BIZKEY= ? WHERE PROCESSINSTANCEID = ?";
			Object[] params = new Object[]{new Date(), processInstanceEntity.getInstanceStatus(), LocationUtil.parseProcessLocation(processInstanceEntity.getProcessLocation()), processInstanceEntity.getInitiator(), processInstanceEntity.getBizKey(), processInstanceEntity.getId()};
			SqlCommand sqlCommand = new SqlCommand(DBUtils.getConnection());
			sqlCommand.execute(sql, params);
		} catch (Exception e) {
			LOG.error("流程保存时更新流程信息失败!", e);
		}
	}
	
}
