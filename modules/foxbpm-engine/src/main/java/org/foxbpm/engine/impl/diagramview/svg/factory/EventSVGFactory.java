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
 * @author MAENLIANG
 */
package org.foxbpm.engine.impl.diagramview.svg.factory;

import java.util.List;

import org.foxbpm.engine.impl.diagramview.svg.vo.SvgVO;
import org.foxbpm.engine.impl.diagramview.vo.VONode;
import org.foxbpm.kernel.process.KernelBaseElement;

/**
 * BPMN2.0事件元素的工厂类
 * 
 * @author MAENLIANG
 * @date 2014-06-10
 * 
 */
public class EventSVGFactory extends AbstractFlowElementSVGFactory {
	
	/**
	 * 
	 * @param kernelFlowElement
	 *            事件节点
	 * @param voTemplateFileName
	 *            SVG模版文件名
	 */
	public EventSVGFactory(KernelBaseElement kernelBaseElement, String voTemplateFileName) {
		super(kernelBaseElement, voTemplateFileName);
	}
	@Override
	public VONode createSVGVO() {
		return (SvgVO) super.loadSVGVO(voTemplateFileName);
	}
	@Override
	public void filterChildVO(VONode voNode, List<String> filterCondition) {
		// TODO Auto-generated method stub
		
	}
	
}
