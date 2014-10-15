package org.foxbpm.model;

public class SkipStrategy extends BaseElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String skipExpression;
	protected boolean isEnable = false;
	protected boolean isCreateSkipTaskRecord = true;
	protected String skipAssignee;
	protected String skipComment;

	public String getSkipExpression() {
		return skipExpression;
	}

	public void setSkipExpression(String skipExpression) {
		this.skipExpression = skipExpression;
	}

	public boolean isEnable() {
		return isEnable;
	}

	public void setEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}

	public boolean isCreateSkipTaskRecord() {
		return isCreateSkipTaskRecord;
	}

	public void setCreateSkipTaskRecord(boolean isCreateSkipTaskRecord) {
		this.isCreateSkipTaskRecord = isCreateSkipTaskRecord;
	}

	public String getSkipAssignee() {
		return skipAssignee;
	}

	public void setSkipAssignee(String skipAssignee) {
		this.skipAssignee = skipAssignee;
	}

	public String getSkipComment() {
		return skipComment;
	}

	public void setSkipComment(String skipComment) {
		this.skipComment = skipComment;
	}

}
