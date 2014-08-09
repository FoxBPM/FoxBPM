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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.foxbpm.engine.expression.Expression;
import org.foxbpm.engine.impl.bpmn.behavior.GatewayBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.SequenceFlowBehavior;
import org.foxbpm.engine.impl.diagramview.svg.SVGUtils;
import org.foxbpm.engine.impl.diagramview.svg.vo.GVO;
import org.foxbpm.engine.impl.diagramview.svg.vo.PathVO;
import org.foxbpm.engine.impl.diagramview.svg.vo.SvgVO;
import org.foxbpm.engine.impl.diagramview.vo.VONode;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.kernel.behavior.KernelFlowNodeBehavior;
import org.foxbpm.kernel.process.KernelBaseElement;
import org.foxbpm.kernel.process.impl.KernelFlowNodeImpl;
import org.foxbpm.kernel.process.impl.KernelSequenceFlowImpl;

/**
 * BPMN2.0事件元素之线条定义
 * 
 * @author MAENLIANG
 * @date 2014-06-10
 * 
 */
public class ConnectorSVGFactory extends AbstractFlowElementSVGFactory {
	
	/**
	 * 
	 * @param kernelFlowElement
	 *            连接线对象
	 * @param voTemplateFileName
	 */
	public ConnectorSVGFactory(KernelBaseElement kernelBaseElement, String voTemplateFileName) {
		super(kernelBaseElement, voTemplateFileName);
	}
	
	@Override
	public VONode createSVGVO() {
		SvgVO connectorVO = (SvgVO) super.loadSVGVO(voTemplateFileName);
		GVO newGVO = new GVO();
		List<GVO> gVoList = new ArrayList<GVO>();
		gVoList.add(SVGUtils.cloneGVO(connectorVO.getgVo()));
		
		newGVO.setgVoList(gVoList);
		newGVO.setDefsVo(SVGUtils.cloneDefsVO(connectorVO.getDefsVo()));
		connectorVO.setgVo(newGVO);
		return connectorVO;
	}
	
	@Override
	public void filterChildVO(VONode voNode, List<String> filterCondition) {
		KernelSequenceFlowImpl kernelSequenceFlowImpl = (KernelSequenceFlowImpl) this.kernelBaseElement;
		KernelFlowNodeImpl sourceRef = kernelSequenceFlowImpl.getSourceRef();
		KernelFlowNodeBehavior sourceRefBehavior = sourceRef.getKernelFlowNodeBehavior();
		
		SequenceFlowBehavior sequenceFlowBehavior = (SequenceFlowBehavior) kernelSequenceFlowImpl.getSequenceFlowBehavior();
		Expression conditionExpression = sequenceFlowBehavior.getConditionExpression();
		if (conditionExpression != null
		        & StringUtil.isNotBlank(conditionExpression.getExpressionText())
		        && !(sourceRefBehavior instanceof GatewayBehavior)) {
			SvgVO connectorVO = (SvgVO) voNode;
			PathVO pathVo = null;
			List<PathVO> pathVOList = connectorVO.getgVo().getgVoList().get(0).getPathVoList();
			Iterator<PathVO> iterator = pathVOList.iterator();
			while (iterator.hasNext()) {
				pathVo = iterator.next();
				if (StringUtil.equals(pathVo.getId(), "bg_frame")) {
					pathVo.setMarkerStart("url(#start)");
				}
			}
			
		}
	}
}
