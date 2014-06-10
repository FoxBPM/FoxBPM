/** 
 * Project Name:foxbpm-webapps-base 
 * File Name:AbstractDemoDB.java 
 * Package Name:org.foxbpm.web.db.interfaces 
 * Date:2014年6月10日下午5:04:55 
 * Copyright (c) 2014, yangguangftlp@163.com All Rights Reserved. 
 * 
 */
package org.foxbpm.web.db.impl;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * date: 2014年6月10日 下午5:04:55
 * @author yangguangftlp
 */
public abstract class AbstrJdbcTemplate {
	protected JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
