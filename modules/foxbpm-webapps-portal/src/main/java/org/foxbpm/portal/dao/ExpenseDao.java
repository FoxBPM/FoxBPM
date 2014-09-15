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
package org.foxbpm.portal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.foxbpm.portal.model.ExpenseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class ExpenseDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	public void saveExpenseEntity(ExpenseEntity expenseEntity){
		String sqlInsert = "insert into tb_expense(id,owner,dept,account,invoiceType,reason,create_Time) values(?,?,?,?,?,?,?)";
		jdbcTemplate.update(sqlInsert,expenseEntity.getExpenseId(),expenseEntity.getOwner(),expenseEntity.getDept(),expenseEntity.getAccount(),expenseEntity.getInvoiceType(),expenseEntity.getReason(),expenseEntity.getCreateTime());
	}
	
	public void updateExpenseEntity(ExpenseEntity expenseEntity){
		String sqlUpdate = "update tb_expense set owner=?,dept=?,account=?,invoiceType=?,reason=?,create_Time=? where id=?";
		jdbcTemplate.update(sqlUpdate,expenseEntity.getOwner(),expenseEntity.getDept(),expenseEntity.getAccount(),expenseEntity.getInvoiceType(),expenseEntity.getReason(),expenseEntity.getCreateTime(),expenseEntity.getExpenseId());
	}
	
	
	public ExpenseEntity selectExpenseById(String entityId){
		String sqlSel = "select * from tb_expense where id=?";
		List<ExpenseEntity> resultList = jdbcTemplate.query(sqlSel, new Object[]{entityId}, new RowMapper<ExpenseEntity>(){
			@Override
			public ExpenseEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
				ExpenseEntity expenseEntity = new ExpenseEntity();
				expenseEntity.setExpenseId(rs.getString("id"));
				expenseEntity.setOwner(rs.getString("owner"));
				expenseEntity.setAccount(rs.getDouble("account"));
				expenseEntity.setCreateTime(rs.getString("create_time"));
				expenseEntity.setReason(rs.getString("reason"));
				expenseEntity.setDept(rs.getString("dept"));
				expenseEntity.setInvoiceType(rs.getString("invoiceType"));
				return expenseEntity;
			}
		});
		if(resultList != null && resultList.size() >0){
			return resultList.get(0);
		}
		return null;
	}
	
	public List<ExpenseEntity> selectExpenseByPage(int pageIndex,int pageSize){
		
		return null;
	}
}
