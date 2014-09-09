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
package org.foxbpm.portal.manager;

import java.util.List;
import java.util.Map;

import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.portal.dao.ExpenseDao;
import org.foxbpm.portal.model.ExpenseEntity;
import org.foxbpm.portal.service.WorkFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExpenseManager {

	@Autowired
	private ExpenseDao expenseDao;
	@Autowired
	private WorkFlowService workFlowService;
	
	public void applyNewExpense(ExpenseEntity expenseEntity,String flowCommandInfo){
		expenseDao.saveExpenseEntity(expenseEntity);
		
		if(StringUtil.isEmpty(flowCommandInfo)){
			throw new RuntimeException("流程命令参数确实，请检查请求参数");
		}
		//调用api执行任务命令
		workFlowService.executeTaskCommandJson(flowCommandInfo);
	}
	
	public List<Map<String,Object>> selectByPage(int pageIndex,int pageSize){
		return null;
	}
	
	public Map<String,Object> selectExpenseById(String expenseId){
		return null;
	}
}
