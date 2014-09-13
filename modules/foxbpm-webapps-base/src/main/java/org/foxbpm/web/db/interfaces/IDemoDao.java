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
package org.foxbpm.web.db.interfaces;

import java.util.List;

import org.foxbpm.web.model.TDemo;

/**
 * 对表demotable 数据操作Dao
 * @author yangguangftlp
 * @date 2014年6月10日
 */
public interface IDemoDao {

	/**
	 * 查询demoTable 数据
	 * 
	 * @param sqpParams
	 *            sql 查询条件
	 * @return 返回查询结果
	 */
	public List<TDemo> queryDemoData(List<Object> sqlParams);

	/**
	 * 插入一条数据到demoTable中
	 * 
	 * @param tdemo
	 *            数据
	 * @return
	 */
	public void saveDemoData(TDemo tdemo);
}
