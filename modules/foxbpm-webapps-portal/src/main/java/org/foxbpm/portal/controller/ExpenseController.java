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
package org.foxbpm.portal.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.foxbpm.engine.impl.entity.UserEntity;
import org.foxbpm.portal.model.ExpenseEntity;
import org.foxbpm.portal.service.ExpenseService;
import org.foxbpm.rest.common.api.DataResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ExpenseController extends AbstractController{

	Logger log = LoggerFactory.getLogger(ExpenseController.class);
	@Autowired
	private ExpenseService expenseService;

	@RequestMapping(value = { "/", "/expenses" }, method = RequestMethod.POST)
	public void applyExpense(HttpServletResponse response,HttpServletRequest request, @ModelAttribute ExpenseEntity expenseEntity) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		try{
			Map<String,Object> formData = getFormData(request);
			UserEntity userEntity = (UserEntity)request.getSession().getAttribute("user");
			expenseEntity.setOwner(userEntity.getUserId());
			expenseService.applyNewExpense(expenseEntity,formData);
			response.getWriter().print(showMessage("启动成功！",true));
		}catch(Exception ex){
			log.error("报销流程启动失败！",ex);
			response.getWriter().print(showMessage("启动失败，原因:" + ex.getMessage(),false));
		}
		
	}

	@RequestMapping(value = { "/", "/updateExpense" }, method = RequestMethod.POST)
	public void updateExpense(HttpServletResponse response,HttpServletRequest request, @ModelAttribute ExpenseEntity expenseEntity) throws IOException {
		Map<String,Object> formData = getFormData(request);
		expenseService.updateExpense(expenseEntity, formData);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(showMessage("更新成功！",true));
	}
	
	
	@RequestMapping(value = { "/", "/findExpense" }, method = RequestMethod.GET)
	@ResponseBody
	public ExpenseEntity getExpenseById(@RequestParam String expenseId){
		return expenseService.selectExpenseById(expenseId);
	}
	
	@RequestMapping(value = { "/", "/listExpense" }, method = RequestMethod.GET)
	@ResponseBody
	public DataResult getExpenseByPage(@RequestParam int pageIndex,@RequestParam int pageSize){
		return expenseService.selectByPage(pageIndex, pageSize);
	}
	
	public String showMessage(String msg,boolean isCloseWindow){
		String result = "<script>"
				+ "if(self.frameElement != null && self.frameElement.tagName=='IFRAME'){"
				+ "		window.parent.$.smallBox({" 
				+ "				title : '提示!',"
				+ "				content : '"+msg+"',"
				+ "				color : '#296191'," 
				+ "				icon : 'fa fa-bell swing animated',"
				+"				timeout : 2000"
				+ "		});";
				if(isCloseWindow){
					result	+= "		window.parent.$('#remoteModal').modal('hide');";
				}
				result += "}"
				+ "else{"
				+"		alert('"+msg+"');"
				+ "		window.close();"
				+ "}"
				+ "</script>";
		
		return result;
	}
}
