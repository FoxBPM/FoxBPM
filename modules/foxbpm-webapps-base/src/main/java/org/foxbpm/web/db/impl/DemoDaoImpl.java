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
package org.foxbpm.web.db.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.foxbpm.web.db.interfaces.IDemoDao;
import org.foxbpm.web.model.TDemo;
import org.springframework.jdbc.core.RowMapper;

/**
 * 对表demotable 数据操作Dao实现
 * 
 * @author yangguangftlp
 * @date 2014年6月10日
 */
@SuppressWarnings("unchecked")
public class DemoDaoImpl extends AbstrJdbcTemplate implements IDemoDao {

	private static final String QUERY_DEMOSQL = "select * from demotable where bKey = ?";
	private static final String INSERT_DEMOSQL = "insert into demotable (bKey,infor) values(?,?)";

	@Override
	public List<TDemo> queryDemoData(List<Object> sqpParams) {
		List<TDemo> tdList = this.jdbcTemplate.query(QUERY_DEMOSQL, (Object[]) sqpParams.toArray(), new TDemoMapper<TDemo>());
		return tdList;
	}

	@Override
	public void saveDemoData(TDemo tdemo) {
		List<Object> args = new ArrayList<Object>();
		args.add(tdemo.getbKey());
		args.add(tdemo.getInfor());
		jdbcTemplate.update(INSERT_DEMOSQL, args.toArray());
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
			tDemo.setbKey(rs.getString("bKey"));
			tDemo.setInfor(rs.getString("infor"));
			return (T) tDemo;
		}
	}
}
