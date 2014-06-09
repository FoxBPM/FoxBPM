package org.foxbpm.web.model;

import lombok.Data;

/**
 * 业务数据定义
 * 
 * @author MEL
 * @date 2014-06-04
 */
public class BizInfo {
	private String id = "";
	private String name = "";
	private String comment = "";

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
