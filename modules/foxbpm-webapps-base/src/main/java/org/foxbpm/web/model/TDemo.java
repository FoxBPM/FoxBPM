/** 
 * Project Name:foxbpm-webapps-base 
 * File Name:TDemo.java 
 * Package Name:org.foxbpm.web.model 
 * Date:2014年6月10日下午5:16:00 
 * Copyright (c) 2014, yangguangftlp@163.com All Rights Reserved. 
 * 
 */
package org.foxbpm.web.model;

import java.util.HashMap;
import java.util.Map;

/**
 * date: 2014年6月10日 下午5:16:00
 * 
 * @author yangguangftlp
 */
public class TDemo {
	/**
	 * 业务key
	 */
	private String bKey;
	/**
	 * 信息
	 */
	private String infor;

	public String getbKey() {
		return bKey;
	}

	public void setbKey(String bKey) {
		this.bKey = bKey;
	}

	public String getInfor() {
		return infor;
	}

	public void setInfor(String infor) {
		this.infor = infor;
	}

	public Map<String, Object> getPersistentState() {
		Map<String, Object> persistentState = new HashMap<String, Object>();
		persistentState.put("bKey", bKey);
		persistentState.put("infor", infor);
		return persistentState;
	}
}
