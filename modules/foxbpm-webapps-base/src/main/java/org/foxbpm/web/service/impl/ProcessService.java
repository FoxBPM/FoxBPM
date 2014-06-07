package org.foxbpm.web.service.impl;

import java.sql.Connection;

import lombok.Setter;

import org.foxbpm.web.common.exception.FoxbpmWebException;
import org.foxbpm.web.db.factory.FoxbpmDBConnectionFactory;
import org.foxbpm.web.db.interfaces.BizDBInterface;
import org.foxbpm.web.model.BizInfo;
import org.foxbpm.web.model.ProcessDefinition;
import org.foxbpm.web.service.interfaces.IProcessService;

/**
 * 流程服务类
 * 
 * @author MEL
 * @date 2014-06-04
 */
public class ProcessService implements IProcessService {
	@Setter
	private BizDBInterface bizDB;
	@Setter
	private FoxbpmDBConnectionFactory dbfactory;

	/**
	 * spring事物控制方法、所以在方法的开头开启数据库连接、 采用Spring DataSourceUtils类获取连接
	 * 这样数据库的连接就交给事物进行处理，事物处理完毕后会关闭数据库连接
	 * 
	 */
	public ProcessDefinition createProcessDefinition(String parameter)
			throws FoxbpmWebException {
		Connection connection = null;
		try {
			connection = dbfactory.createConnection();
			// 开启数据库连接
			// 开启事物
			// 设置工作流引擎的数据库连接
			// 工作流引擎的API调用
			// 本地业务数据DAO调用
			// 提交或回滚事物
			// 事物处理完毕后会自动关闭数据库连接
			BizInfo bizInfo = new BizInfo();
			bizInfo.setId("id001");
			bizInfo.setName("name001");
			bizInfo.setComment("comment001");
			bizDB.insertBizInfo(bizInfo, connection);

			ProcessDefinition processDefinition = new ProcessDefinition();
			processDefinition.setId(parameter);
			processDefinition.setName(parameter);
			return processDefinition;
		} catch (FoxbpmWebException e) {
			throw e;
		}

	}
}
