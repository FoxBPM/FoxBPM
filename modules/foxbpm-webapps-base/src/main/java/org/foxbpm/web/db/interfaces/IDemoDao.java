/** 
 * Project Name:foxbpm-webapps-base 
 * File Name:IDemoDB.java 
 * Package Name:org.foxbpm.web.db.interfaces 
 * Date:2014年6月10日下午5:04:26 
 * Copyright (c) 2014, yangguangftlp@163.com All Rights Reserved. 
 * 
 */
package org.foxbpm.web.db.interfaces;

import java.util.List;

import org.foxbpm.web.model.TDemo;

/**
 * date: 2014年6月10日 下午5:04:26
 * 
 * @author yangguangftlp
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
