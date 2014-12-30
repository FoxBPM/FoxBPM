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
package org.foxbpm.portal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.foxbpm.engine.impl.entity.GroupEntity;
import org.foxbpm.engine.impl.entity.UserEntity;
import org.foxbpm.engine.impl.identity.Authentication;


/**
 * 报销单 业务实体
 * @author ych
 *
 */
@Entity
@Table(name="TB_EXPENSE")
public class ExpenseEntity {

	/**
	 * 报销单编号
	 */
	@Id
	@Column(name = "ID")
	private String expenseId;
	
	/**
	 * 报销单申请人
	 */
	private String owner;
	
	/**
	 * 申请人部门
	 */
	private String dept;
	
	/**
	 * 合计金额
	 */
	private double account;
	
	/**
	 * 发票类型
	 */
	private String invoiceType;
	
	/**
	 * 报销事由
	 */
	private String reason;
	
	/**
	 * 创建时间
	 */
	@Column(name = "CREATE_TIME")
	private String createTime;
	
	@OneToOne(optional=true)
	@JoinColumn(name="PROCESSINSTANCEID")
	private ProcessInfoEntity processInfo;

	public String getExpenseId() {
		return expenseId;
	}

	public void setExpenseId(String expenseId) {
		this.expenseId = expenseId;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public double getAccount() {
		return account;
	}

	public void setAccount(double account) {
		this.account = account;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	public String getDeptName() {
		GroupEntity group = Authentication.findGroupById(dept, "dept");
		if(group != null){
			return group.getGroupName();
		}
		return "未知部门";
	}
	
	public String getOwnerName() {
		UserEntity user = Authentication.selectUserByUserId(owner);
		if(user != null){
			return user.getUserName();
		}
		return "未知用户";
	}
	
	public void setProcessInfo(ProcessInfoEntity processInfo) {
		this.processInfo = processInfo;
	}
	
	public ProcessInfoEntity getProcessInfo() {
		return processInfo;
	}
}
