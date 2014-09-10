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

import javax.servlet.http.HttpServletResponse;

import org.foxbpm.portal.manager.ExpenseManager;
import org.foxbpm.portal.model.ExpenseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ExpenseController {

	@Autowired
	private ExpenseManager expenseManager;

	@RequestMapping(value = { "/", "/expenses" }, method = RequestMethod.POST)
	public void applyExpense(HttpServletResponse response, @ModelAttribute ExpenseEntity expenseEntity, @RequestParam String flowCommandInfo) throws IOException {
		expenseManager.applyNewExpense(expenseEntity, flowCommandInfo);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(
				"<script>"
				+ "if(self.frameElement.tagName=='IFRAME'){"
				+ "		window.parent.$.smallBox({" 
				+ "				title : '提示!',"
				+ "				content : '保存成功！',"
				+ "				color : '#296191'," 
				+ "				icon : 'fa fa-bell swing animated',"
				+"				timeout : 2000"
				+ "		});"
				+ "		window.parent.$('#remoteModal').modal('hide');"
				+ "}"
				+ "else{"
				+"		alert('保存成功！');"
				+ "		window.close();"
				+ "}"
				+ "</script>");
	}
	
	@RequestMapping(value = { "/", "/findExpense" }, method = RequestMethod.GET)
	@ResponseBody
	public ExpenseEntity getExpenseById(@RequestParam String expenseId){
		return expenseManager.selectExpenseById(expenseId);
	}
}
