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
 * @author yangguangftlp
 */
package org.foxbpm.model;

import java.util.List;
import java.util.Map;

/**
 * 流程容器 流程、子流程
 * 
 * @author yangguangftlp
 * @date 2014年10月20日
 */
public interface FlowContainer {
	/**
	 * 添加流程元素
	 * 
	 * @param flowElement
	 *            flowElement
	 */
	void addFlowElement(FlowElement flowElement);
	/**
	 * 线条元素
	 * 
	 * @param sequenceFlow
	 *            sequenceFlow
	 */
	void addSequenceFlow(SequenceFlow sequenceFlow);
	/**
	 * 获取流程节点
	 * 
	 * @return 返回容器节点集合
	 */
	public List<FlowElement> getFlowElements();
	/**
	 * 获取线条
	 * 
	 * @return 返回容器线条集合
	 */
	public Map<String, SequenceFlow> getSequenceFlows();
	
	FlowContainer getParentContainer();
	
	void setParentContainer(FlowContainer container);
	
}
