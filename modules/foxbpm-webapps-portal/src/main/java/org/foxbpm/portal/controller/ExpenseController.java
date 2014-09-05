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

import org.foxbpm.portal.manager.ExpenseManager;
import org.foxbpm.portal.model.ExpenseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ExpenseController {

	@Autowired
	private ExpenseManager expenseManager;
	
	@RequestMapping(value={"/","/expenses"},method=RequestMethod.POST)
	public String applyExpense(@ModelAttribute ExpenseEntity expenseEntity,@RequestParam String flowCommandInfo){
		expenseManager.applyNewExpense(expenseEntity,flowCommandInfo);
		return "redirect:portal/index.html#ajaxpage/dashboard.html";
	}
}
