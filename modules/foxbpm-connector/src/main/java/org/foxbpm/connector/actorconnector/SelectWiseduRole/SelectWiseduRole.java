package org.foxbpm.connector.actorconnector.SelectWiseduRole;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.commons.lang3.StringUtils;
import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.connector.ActorConnectorHandler;
import org.foxbpm.engine.task.DelegateTask;
import org.foxbpm.engine.task.IdentityLinkType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 选择金智用户角色
 * 
 * @author xpzeng
 * 
 */
public class SelectWiseduRole extends ActorConnectorHandler {
	private static Logger LOG = LoggerFactory.getLogger(SelectWiseduRole.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private java.lang.String wiseduRole;

	public void assign(DelegateTask task) throws Exception {
		if (wiseduRole == null) {
			LOG.warn("金智用戶角色选择器角色编号表达式为空 ! 节点编号：" + task.getNodeId());
			return;
		}

		// TODO 当前应用编号是否能获取到
		List<String> appRoleGroups = getAppRoleGroups(Context.getAppId(),
				wiseduRole);
		if (appRoleGroups != null && appRoleGroups.size() > 0) {
			for (String group : appRoleGroups) {
				// groupType需明确
				task.addGroupIdentityLink(group, "good",
						IdentityLinkType.CANDIDATE);
			}
		}
	}

	/**
	 * 根据应用编号和角色编号，获取该角色对应的用户组集合
	 * 
	 * @param appId
	 *            应用编号
	 * @param roleId
	 *            角色编号
	 * @return
	 * @throws Exception
	 */
	private List<String> getAppRoleGroups(String appId, String roleId)
			throws Exception {
		Properties prop = loadConfigProperties();
		Service service = new Service();
		Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(prop.getProperty("webservice.url"));
		QName qName = new QName(prop.getProperty("webservice.targetNamespace"),
				prop.getProperty("webservice.operationName"));
		// 调用的方法名
		call.setOperationName(qName);

		// 设置参数名
		String inputParams = prop.getProperty("webservice.inputParams");
		if (StringUtils.isNotEmpty(inputParams)) {
			for (String inputParam : inputParams.split(",")) {
				call.addParameter(inputParam, XMLType.XSD_STRING,
						ParameterMode.IN);
			}
		}
		// 设置返回值类型
		call.setReturnType(XMLType.XSD_STRING); // 返回值类型：String
		@SuppressWarnings("unchecked")
		List<String> groups = (List<String>) call.invoke(new Object[] { appId,
				roleId });
		return groups;
	}

	/**
	 * 加载配置文件
	 * 
	 * @return
	 * @throws IOException
	 */
	private Properties loadConfigProperties() throws IOException {
		Properties properties = new Properties();
		properties.load(this.getClass()
				.getResourceAsStream("config.properties"));
		return properties;
	}

	public void setWiseduRole(java.lang.String wiseduRole) {
		this.wiseduRole = wiseduRole;
	}

}