package org.foxbpm.test.engine;

import java.sql.SQLException;

import junit.framework.TestCase;

import org.foxbpm.engine.FormService;
import org.foxbpm.engine.HistoryService;
import org.foxbpm.engine.IdentityService;
import org.foxbpm.engine.ModelService;
import org.foxbpm.engine.ProcessEngine;
import org.foxbpm.engine.ProcessEngineManagement;
import org.foxbpm.engine.RuntimeService;
import org.foxbpm.engine.ScheduleService;
import org.foxbpm.engine.TaskService;
import org.foxbpm.engine.impl.ProcessEngineConfigurationImpl;
import org.foxbpm.engine.impl.ProcessEngineImpl;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.impl.util.DBUtils;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

public abstract class AbstractFixFlowTestCase extends TestCase {

	public static ProcessEngine processEngine=ProcessEngineManagement.getDefaultProcessEngine();
	protected String deploymentId;

	protected ProcessEngineConfigurationImpl processEngineConfiguration;
	protected ModelService modelService;
	protected RuntimeService runtimeService;
	protected TaskService taskService;
	protected FormService formService;
	protected HistoryService historyService;
	protected IdentityService identityService;
	protected ScheduleService scheduleService;
	
	protected CommandContext commandContext;

	protected void initializeServices() {
		processEngineConfiguration = ((ProcessEngineImpl) processEngine).getProcessEngineConfiguration();
		modelService = processEngine.getModelService();
		runtimeService = processEngine.getRuntimeService();
		taskService = processEngine.getTaskService();
		formService = processEngine.getFormService();
		historyService = processEngine.getHistoryService();
		identityService = processEngine.getIdentityService();
		scheduleService = processEngine.getScheduleService();
		
		commandContext = new CommandContext(null, processEngine.getProcessEngineConfiguration());
	}
	
	public void runBare() throws Throwable {

		if (modelService == null) {
			initializeServices();
		}
		DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(DBUtils.getDataSource());
		TransactionTemplate t = new TransactionTemplate(dataSourceTransactionManager);
		t.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				execute();
				try {
					DBUtils.getDataSource().getConnection().rollback();
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
		});
	}
	
	public void execute(){
		try {
			super.runBare();
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}
}
