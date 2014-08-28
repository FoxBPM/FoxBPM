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
 * @author ych
 */
package org.foxbpm.engine.impl.mybatis;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ServiceLoader;

import javax.sql.DataSource;

import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.managed.ManagedTransactionFactory;
import org.foxbpm.engine.exception.FoxBPMDbException;
import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.impl.interceptor.Session;
import org.foxbpm.engine.sqlsession.ISqlSession;
import org.foxbpm.engine.sqlsession.ISqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyBatisSqlSessionFactory implements ISqlSessionFactory {

	Logger log = LoggerFactory.getLogger(MyBatisSqlSessionFactory.class);
	private SqlSessionFactory sqlSessionFactory;
	
	protected static final Map<String, Map<String, String>> databaseSpecificStatements = new HashMap<String, Map<String,String>>();
	  
	public static final Map<String, String> databaseSpecificLimitBeforeStatements = new HashMap<String, String>();
	public static final Map<String, String> databaseSpecificLimitAfterStatements = new HashMap<String, String>();
	public static final Map<String, String> databaseSpecificLimitBetweenStatements = new HashMap<String, String>();
	public static final Map<String, String> databaseSpecificOrderByStatements = new HashMap<String, String>();
	public static final Map<String, String> databaseOuterJoinLimitBetweenStatements = new HashMap<String, String>();
	public static final Map<String, String> databaseSpecificLimitBeforeNativeQueryStatements = new HashMap<String, String>();
	
	protected static Properties databaseTypeMappings = new Properties();

	static{
		
		String defaultOrderBy = " order by ${orderBy} ";
		 //mysql specific
	    databaseSpecificLimitBeforeStatements.put("mysql", "");
	    databaseSpecificLimitAfterStatements.put("mysql", "LIMIT #{maxResults} OFFSET #{firstResult}");
	    databaseSpecificLimitBetweenStatements.put("mysql", "");
	    databaseOuterJoinLimitBetweenStatements.put("mysql", "");
	    databaseSpecificOrderByStatements.put("mysql", defaultOrderBy);
	    
	    // oracle
	    databaseSpecificLimitBeforeStatements.put("oracle", "select * from ( select a.*, ROWNUM rnum from (");
	    databaseSpecificLimitAfterStatements.put("oracle", "  ) a where ROWNUM < #{lastRow}) where rnum  >= #{firstRow}");
	    databaseSpecificLimitBetweenStatements.put("oracle", "");
	    databaseOuterJoinLimitBetweenStatements.put("oracle", "");
	    databaseSpecificOrderByStatements.put("oracle", defaultOrderBy);
	    
	    
		databaseTypeMappings.setProperty("MySQL", "mysql");
		databaseTypeMappings.setProperty("Oracle", "oracle");
		databaseTypeMappings.setProperty("Microsoft SQL Server", "mssql");
	}
	
	public void init(DataSource dataSource,String prefix) {
		Connection connection = null;
		String databaseType = null;
		try {
			connection = dataSource.getConnection();
			DatabaseMetaData databaseMetaData = connection.getMetaData();
			String databaseProductName = databaseMetaData.getDatabaseProductName();
			log.debug("database product name: '{}'", databaseProductName);
			databaseType = databaseTypeMappings.getProperty(databaseProductName);
			if (databaseType == null) {
				throw new FoxBPMException("couldn't deduct database type from database product name '" + databaseProductName + "'");
			}

		} catch (SQLException e) {
			log.error("Exception while initializing Database connection", e);
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				log.error("Exception while closing the Database connection", e);
			}
		}
		if (sqlSessionFactory == null) {
			InputStream inputStream = null;
			try {
				inputStream = Resources.getResourceAsStream("mybatis/mapping/mappings.xml");
				TransactionFactory transactionFactory = new ManagedTransactionFactory();
				Environment environment = new Environment("default", transactionFactory, dataSource);
		        Reader reader = new InputStreamReader(inputStream);
		        Properties properties = new Properties();
		        if(databaseType != null) {
					properties.put("limitBefore", databaseSpecificLimitBeforeStatements.get(databaseType));
					properties.put("limitAfter", databaseSpecificLimitAfterStatements.get(databaseType));
					properties.put("limitBetween", databaseSpecificLimitBetweenStatements.get(databaseType));
					properties.put("limitOuterJoinBetween", databaseOuterJoinLimitBetweenStatements.get(databaseType));
					properties.put("orderBy" , databaseSpecificOrderByStatements.get(databaseType));
					properties.put("prefix" , prefix);
		        }
		        XMLConfigBuilder parser = new XMLConfigBuilder(reader,"", properties);
		        Configuration configuration = parser.getConfiguration();
		        configuration.setEnvironment(environment);
		        configuration = parser.parse();
		        
		        ServiceLoader<FoxbpmMapperConfig> mapperConfig =  ServiceLoader.load(FoxbpmMapperConfig.class);
		        Iterator<FoxbpmMapperConfig> mapperIterator = mapperConfig.iterator();
		        while(mapperIterator.hasNext()){
		        	FoxbpmMapperConfig tmpMapper = mapperIterator.next();
		        	log.debug("发现注册mapperConifg：{};",tmpMapper.getClass());
		        	List<String> mapperPaths = tmpMapper.getMapperConfig();
		        	if(mapperPaths != null){
		        		for(String mapPath : mapperPaths){
		        			InputStream input = Resources.getResourceAsStream(mapPath);
		        			if(input != null){
		        				XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(input, configuration, mapPath, configuration.getSqlFragments());
			    		        xmlMapperBuilder.parse();
			    		        log.debug("发现注册mapper文件：{};",mapPath);
		        			}else{
		        				throw new FoxBPMDbException("mapper文件:"+mapPath+"不存在;");
		        			}
		        		}
		        	}
		        }
		        sqlSessionFactory = new DefaultSqlSessionFactory(configuration);
			} catch (Exception e) {
				throw new RuntimeException(
						"Error while building ibatis SqlSessionFactory: "
								+ e.getMessage(), e);
			} finally {
				if (inputStream != null) {
					try {
						inputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	private SqlSession getSqlSession(){
		SqlSession sqlSession = null;
		if(sqlSessionFactory == null){
			throw new FoxBPMException("mybatis sqlsession工厂创建失败");
		}
		sqlSession = sqlSessionFactory.openSession();
		if(sqlSession == null){
			throw  new FoxBPMException("mybatis sqlSession创建失败");
		}
		return sqlSession;
	}
	
	public Class<?> getSessionType() {
		return ISqlSession.class;
	}

	public Session openSession() {
		return new MybatisSqlSession(getSqlSession());
	}
	
	
}
