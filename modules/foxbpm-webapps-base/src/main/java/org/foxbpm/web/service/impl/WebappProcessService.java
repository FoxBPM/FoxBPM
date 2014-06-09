package org.foxbpm.web.service.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.foxbpm.engine.ModelService;
import org.foxbpm.engine.RuntimeService;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.engine.repository.ProcessDefinition;
import org.foxbpm.engine.repository.ProcessDefinitionQuery;
import org.foxbpm.web.common.exception.FoxbpmWebException;
import org.foxbpm.web.common.util.Pagination;
import org.foxbpm.web.db.factory.FoxbpmDBConnectionFactory;
import org.foxbpm.web.db.interfaces.BizDBInterface;
import org.foxbpm.web.service.interfaces.IWebappProcessService;

/**
 * 流程服务类
 * 
 * @author MEL
 * @date 2014-06-04
 */
public class WebappProcessService implements IWebappProcessService {
	private BizDBInterface bizDB;
	private FoxbpmDBConnectionFactory dbfactory;
	private ModelService modelService;
	private RuntimeService runtimeService;

	/**
	 * spring事物控制方法、所以在方法的开头开启数据库连接、 采用Spring DataSourceUtils类获取连接
	 * 这样数据库的连接就交给事物进行处理，事物处理完毕后会关闭数据库连接
	 * 
	 */
	public ProcessDefinition createProcessDefinition(String parameter) throws FoxbpmWebException {
		Connection connection = null;
		try {
			// connection = dbfactory.createConnection();
			// 开启数据库连接
			// 开启事物
			// 设置工作流引擎的数据库连接
			// 工作流引擎的API调用
			// 本地业务数据DAO调用
			// 提交或回滚事物
			// 事物处理完毕后会自动关闭数据库连接
			// BizInfo bizInfo = new BizInfo();
			// bizInfo.setId("id001");
			// bizInfo.setName("name001");
			// bizInfo.setComment("comment001");
			// bizDB.insertBizInfo(bizInfo, connection);
			return null;
		} catch (FoxbpmWebException e) {
			throw e;
		}

	}

	@Override
	public List<ProcessDefinition> queryProcessDefinition() {
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
			// BizInfo bizInfo = new BizInfo();
			// bizInfo.setId("id001");
			// bizInfo.setName("name001");
			// bizInfo.setComment("comment001");
			// bizDB.insertBizInfo(bizInfo, connection);
			List<ProcessDefinition> processDefinitionList = modelService.createProcessDefinitionQuery().list();
			System.out.println(processDefinitionList.size());
			return processDefinitionList;
		} catch (Exception e) {
			throw new FoxbpmWebException(e.getMessage(), "", e);
		}
		// return modelService.createProcessDefinitionQuery().list();
	}

	@Override
	public Map<String, List<Map<String, Object>>> queryAllProcessDef(Pagination<String> pageInfor, Map<String, Object> params) throws FoxbpmWebException {
		Map<String, List<Map<String, Object>>> resultMap = new HashMap<String, List<Map<String, Object>>>();
		try {
			// 创建流程定义查询
			ProcessDefinitionQuery pdq = modelService.createProcessDefinitionQuery();

			List<ProcessDefinition> pdList = null;
			if (null == pageInfor) {
				pdList = pdq.list();
			} else {
				pdList = pdq.listPagination(pageInfor.getPageIndex(), pageInfor.getPageSize());
				pageInfor.setTotal(StringUtil.getInt(pdq.count()));
			}
			Map<String, Object> instances = null;
			for (int i = 0, size = (null == pdList) ? 0 : pdList.size(); i < size; i++) {
				instances = new HashMap<String, Object>();
				instances.putAll(pdList.get(i).getPersistentState());
				// instanceMaps.add(instances);
				// String formUrl = (String) instances.get("startFormKey");
				instances.put("formUrl", "www.baidu.com");
				String category = StringUtil.getString(instances.get("category"));
				if (StringUtil.isEmpty(category)) {
					category = "默认分类";
				}
				List<Map<String, Object>> tlist = resultMap.get(category);
				if (tlist == null) {
					tlist = new ArrayList<Map<String, Object>>();
					resultMap.put(category, tlist);
				}
				tlist.add(instances);
			}
		} catch (Exception e) {
			throw new FoxbpmWebException(e.getMessage(), "", e);
		}
		return resultMap;
	}

	public void setBizDB(BizDBInterface bizDB) {
		this.bizDB = bizDB;
	}

	public void setDbfactory(FoxbpmDBConnectionFactory dbfactory) {
		this.dbfactory = dbfactory;
	}

	public void setModelService(ModelService modelService) {
		this.modelService = modelService;
	}

	public void setRuntimeService(RuntimeService runtimeService) {
		this.runtimeService = runtimeService;
	}

}
