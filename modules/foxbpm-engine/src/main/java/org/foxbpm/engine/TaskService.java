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
 * @author kenshin
 * @author ych
 */
package org.foxbpm.engine;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.foxbpm.engine.impl.task.command.ExpandTaskCommand;
import org.foxbpm.engine.query.NativeTaskQuery;
import org.foxbpm.engine.task.Task;
import org.foxbpm.engine.task.TaskCommand;
import org.foxbpm.engine.task.TaskQuery;

public interface TaskService {

	/**
	 * 创建任务
	 * @return
	 */
	Task newTask();

	/**
	 * 创建带taskId的任务
	 * @param taskId 任务编号
	 * @return
	 */
	Task newTask(String taskId);

	/**
	 * 查询任务
	 * @param taskId 任务编号
	 * @return
	 */
	Task findTask(String taskId);

	/**
	 * 保存任务
	 * @param task
	 */
	void saveTask(Task task);

	/**
	 * 删除任务
	 * <p>默认级联删除任务相关所有信息</p>
	 * @param taskId 任务编号
	 */
	void deleteTask(String taskId);

	/**
	 * 循环删除任务
	 * <p>默认级联删除任务相关所有信息</p>
	 * @param taskIds 任务编号集合
	 */
	void deleteTasks(Collection<String> taskIds);

	/**
	 * 删除任务
	 * @param taskId 任务编号
	 * @param cascade 是否级联删除
	 */
	void deleteTask(String taskId, boolean cascade);

	/**
	 * 循环删除任务
	 * @param taskIds 任务编号集合
	 * @param cascade 是否级联删除
	 */
	void deleteTasks(Collection<String> taskIds, boolean cascade);

	/**
	 * 领取任务
	 * @param taskId 任务编号
	 * @param userId 处理人编号
	 * @exception FoxbpmBizException
	 * 如果任务已经被领取、任务被暂停，任务已结束时会抛出异常
	 */
	void claim(String taskId, String userId);

	/**
	 * <p>释放任务,将自己领取的任务释放</p>
	 * <p>处理：将assignee和claimTime设置为null</p>
	 * @param taskId 任务编号
	 * @exception FoxbpmBizException
	 * <p>如果当前引擎操作人不是assgine，则抛出异常(不能释放别人的任务或共享任务)</p>
	 * <p>如果任务没有被领取，任务被暂停，任务已结束时会抛出异常(不能处理暂停，已结束任务)</p>
	 * <p>如果任务没有其他共享用户时，会抛出异常（不能释放独占任务）</p>
	 */
	void unclaim(String taskId);

	/**
	 * <p>完成任务</p>
	 * <p>直接complete任务，不会执行command命令，也不会执行表达式</p>
	 * <p>结束任务，如果是多实例，则判断是否满足完成表达式，然后决定是否将令牌向下驱动</p>
	 * <p>与runtimeService.signal(tokenId)的区别：本方法是完成任务，判断是否驱动令牌，后者直接驱动令牌向下</p>
	 * @param taskId
	 * @exception FoxbpmBizException
	 * 如果任务没有被领取，任务被暂停，任务已结束时会抛出异常(不能处理暂停，已结束任务)
	 */
	void complete(String taskId);
	
	/**
	 * <p>完成任务</p>
	 * <p>直接complete任务，不会执行command命令，也不会执行表达式</p>
	 * <p>结束任务，如果是多实例，则判断是否满足完成表达式，然后决定是否将令牌向下驱动</p>
	 * @param taskId 任务编号
	 * @param transientVariables 瞬态变量
	 * @param persistenceVariables 持久化变量
	 * @exception FoxbpmBizException
	 * 如果任务没有被领取，任务被暂停，任务已结束时会抛出异常(不能处理暂停，已结束任务)
	 */
	void complete(String taskId,Map<String, Object> transientVariables,Map<String, Object> persistenceVariables);
	
	/**
	 * 自定义扩展方式完成任务的处理命令调用的方法
	 * 
	 * @param expandTaskCommand
	 * @param classReturn
	 * @return
	 */
	<T> T expandTaskComplete(ExpandTaskCommand expandTaskCommand, T classReturn);
	
	/**
	 * 获取任务节点上的toolbar
	 * @param taskId
	 * @return 任务命令集合
	 */
	List<TaskCommand> getTaskCommandByTaskId(String taskId);
	
	/**
	 * <p>获取提交节点上的toolbar</p>
	 * <p>用于流程开始界面上，此时实例未生成，取定义上的toolbar配置</p>
	 * @param key 流程定义key，会根据key去当前流程定义的最新版本key,因为启动流程总是启动最新版本的流程
	 * @return 任务命令集合
	 */
	List<TaskCommand> getSubTaskCommandByKey(String Key);
	
	/**
	 * 获取任务命令信息
	 * @param taskId 任务命令
	 * @param isProcessTracking 是否流程追踪，流程追踪状态下，会存在追回等事后操作型命令
	 * @return 任务命令集合
	 */
	List<TaskCommand> getTaskCommandByTaskId(String taskId,boolean isProcessTracking);
	
	/**
	 * 创建任务查询对象
	 * @return
	 */
	TaskQuery createTaskQuery();

	/**
	 * 创建自定义查询对象，自己拼接sql,参数等信息
	 * @return
	 */
	NativeTaskQuery createNativeTaskQuery();

}
