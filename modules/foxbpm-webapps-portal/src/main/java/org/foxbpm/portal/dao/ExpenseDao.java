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

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.foxbpm.portal.model.ExpenseEntity;
import org.springframework.stereotype.Component;

@Component
public class ExpenseDao {

	@PersistenceContext
	private EntityManager entityManager;

	public void saveExpenseEntity(ExpenseEntity expenseEntity){
		entityManager.persist(expenseEntity);
	}
	
	public void updateExpenseEntity(ExpenseEntity expenseEntity){
		entityManager.merge(expenseEntity);
	}
	
	
	public ExpenseEntity selectExpenseById(String entityId){
		return entityManager.find(ExpenseEntity.class, entityId);
	}
	
	public List<ExpenseEntity> selectExpenseByPage(int pageIndex,int pageSize){
		int begin = (pageIndex -1)*pageSize;
		int end = pageIndex * pageSize;
		String sql = "select expense from ExpenseEntity expense order by expense.createTime desc";
		List<ExpenseEntity> list = entityManager.createQuery(sql,ExpenseEntity.class).setFirstResult(begin).setMaxResults(end).getResultList();
		return list;
	}
	
	public int selectCount(){
		CriteriaBuilder critBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> critQuery = critBuilder.createQuery(Long.class);
		Root<ExpenseEntity> root = critQuery.from(ExpenseEntity.class);
		critQuery.select(critBuilder.countDistinct(root));
		int count = entityManager.createQuery(critQuery).getSingleResult().intValue();
		return count;
	}
}
