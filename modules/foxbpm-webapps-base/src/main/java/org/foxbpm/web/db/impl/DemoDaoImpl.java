/** 
 * Project Name:foxbpm-webapps-base 
 * File Name:DemoDaoImpl.java 
 * Package Name:org.foxbpm.web.db.impl 
 * Date:2014年6月10日下午5:07:03 
 * Copyright (c) 2014, yangguangftlp@163.com All Rights Reserved. 
 * 
 */
package org.foxbpm.web.db.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.foxbpm.web.db.interfaces.IDemoDao;
import org.foxbpm.web.model.TDemo;
import org.springframework.jdbc.core.RowMapper;

/**
 * date: 2014年6月10日 下午5:07:03
 * 
 * @author yangguangftlp
 */
@SuppressWarnings("unchecked")
public class DemoDaoImpl extends AbstrJdbcTemplate implements IDemoDao {

	private static final String QUERY_DEMOSQL = "select * from demotable where pak = ?";
	private static final String INSERT_DEMOSQL = "insert into demotable (pak,infor) values(?,?)";

	@Override
	public List<TDemo> queryDemoData(List<Object> sqpParams) {
		List<TDemo> tdList = this.jdbcTemplate.query(QUERY_DEMOSQL, sqpParams.toArray(), new TDemoMapper<TDemo>());
		return tdList;
	}

	@Override
	public void saveDemoData(TDemo tdemo) {
		List<Object> args = new ArrayList<Object>();
		args.add(tdemo.getPak());
		args.add(tdemo.getInfor());
		jdbcTemplate.update(INSERT_DEMOSQL, args);
	}

	/**
	 * 将查询结果进行转换 date: 2014年6月10日 下午5:21:46
	 * 
	 * @author yangguangftlp
	 * @param <T>
	 */
	protected class TDemoMapper<T> implements RowMapper<T> {
		@Override
		public T mapRow(java.sql.ResultSet rs, int arg1) throws SQLException {
			TDemo tDemo = new TDemo();
			tDemo.setPak(rs.getString("pkd"));
			tDemo.setInfor(rs.getString("infor"));
			return (T) tDemo;
		}
	}
}
