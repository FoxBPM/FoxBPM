/**
 * 
 */
package org.foxbpm.web.service.interfaces;

import java.util.Map;

import org.foxbpm.engine.runtime.ProcessInstance;

/**
 * 
 * date: 2014年6月10日 下午4:18:01
 * 
 * @author yangguangftlp
 */
public interface IWebappDemoService {

	/**
	 * 开启一个任务
	 * 
	 * @param params
	 *            查询需要处理的任务
	 * @return 获取任务详细信息
	 */
	public Map<String, Object> startTask(Map<String, Object> params);

	/**
	 * 执行任务
	 * 
	 * @param params
	 *            任务信息
	 * @return 返回完成后的任务信息
	 */
	public ProcessInstance executeTask(Map<String, Object> params);
}
