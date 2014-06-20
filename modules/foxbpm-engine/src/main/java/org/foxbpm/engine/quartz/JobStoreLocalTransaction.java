package org.foxbpm.engine.quartz;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.foxbpm.engine.ProcessEngineManagement;
import org.foxbpm.engine.impl.ProcessEngineImpl;
import org.quartz.SchedulerConfigException;
import org.quartz.impl.jdbcjobstore.JobStoreCMT;
import org.quartz.spi.ClassLoadHelper;
import org.quartz.spi.SchedulerSignaler;
import org.quartz.utils.ConnectionProvider;
import org.quartz.utils.DBConnectionManager;
import org.springframework.jdbc.datasource.DataSourceUtils;

/**
 * 修改QUARTZ获取CONNECTION 的方式，参照 BASE 系统 采用SPRING
 * DataSourceUtils工具类，保证获取的CONNECTION和引擎DataSource处于同一个事物
 * 
 * @author MAENLIANG
 * @date 2014-06-19
 * 
 */
class JobStoreLocalTransaction extends JobStoreCMT {

	private DataSource dataSource;

	@Override
	public void initialize(ClassLoadHelper loadHelper,
			SchedulerSignaler signaler) throws SchedulerConfigException {
		// 通过引擎获取datasourcemanager，获取datasource
		this.dataSource = ((ProcessEngineImpl) ProcessEngineManagement
				.getDefaultProcessEngine()).getProcessEngineConfiguration()
				.getDataSourceManager().getDataSource();

		// 设置是否需要自动提交
		setDontSetAutoCommitFalse(true);

		// 设置datasource名称 保证操作
		setDataSource(dataSource.toString());
		// 参照JobStoreSurpot 760行代码
		DBConnectionManager quartzDBMng = DBConnectionManager.getInstance();
		ConnectionProvider myConnectionProvider = new ConnectionProvider() {
			public Connection getConnection() throws SQLException {
				// 通过spring DataSourceUtils获取connection从而保证和base处于同一个事物
				// 仿照base系统操作
				return DataSourceUtils.doGetConnection(dataSource);
			}

			public void shutdown() {
				// TODO
			}
		};
		quartzDBMng.addConnectionProvider(dataSource.toString(),
				myConnectionProvider);
		super.initialize(loadHelper, signaler);
		getLog().info(
				"JobStoreLocalTransaction which extends JobStoreCMT initialized.");

	}

	@Override
	protected void closeConnection(Connection con) {
		// 仿照base操作
		DataSourceUtils.releaseConnection(con, this.dataSource);
	}

}
