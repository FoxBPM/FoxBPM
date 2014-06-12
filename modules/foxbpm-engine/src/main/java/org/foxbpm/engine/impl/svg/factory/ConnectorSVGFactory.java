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
package org.foxbpm.engine.impl.svg.factory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.foxbpm.engine.impl.svg.SVGUtils;
import org.foxbpm.engine.impl.svg.vo.GVO;
import org.foxbpm.engine.impl.svg.vo.MarkerVO;
import org.foxbpm.engine.impl.svg.vo.PathVO;
import org.foxbpm.engine.impl.svg.vo.SvgVO;
import org.foxbpm.engine.impl.svg.vo.VONode;
import org.foxbpm.kernel.process.KernelFlowElement;

/**
 * BPMN2.0事件元素之线条定义
 * 
 * @author MAENLIANG
 * @date 2014-06-10
 * 
 */
public class ConnectorSVGFactory extends AbstractFlowNodeSVGFactory {

	/**
	 * 
	 * @param kernelFlowElement
	 *            连接线对象
	 * @param svgTemplateFileName
	 */
	public ConnectorSVGFactory(KernelFlowElement kernelFlowElement,
			String svgTemplateFileName) {
		super(kernelFlowElement, svgTemplateFileName);
	}

	@Override
	public VONode createSVGVO(String svgType) {
		SvgVO connectorVO = (SvgVO) super.loadSVGVO(svgTemplateFileName);
		return connectorVO;
	}

	@Override
	public VONode createSVGVO() {
		SvgVO connectorVO = (SvgVO) super.loadSVGVO(svgTemplateFileName);
		GVO newGVO = new GVO();
		List<GVO> gVoList = new ArrayList<GVO>();
		gVoList.add(SVGUtils.cloneGVO(connectorVO.getgVo()));

		newGVO.setgVoList(gVoList);
		newGVO.setDefsVo(SVGUtils.cloneDefsVO(connectorVO.getDefsVo()));
		connectorVO.setgVo(newGVO);
		return connectorVO;
	}

	@Override
	public void filterPathVO(VONode voNode, String[] filterCondition) {
		for (int i = 0; i < filterCondition.length; i++) {
			List<MarkerVO> markerVOList = ((SvgVO) voNode).getgVo().getDefsVo()
					.getMarkerVOList();
			Iterator<MarkerVO> markterIter = markerVOList.iterator();
			while (markterIter.hasNext()) {
				MarkerVO maker = markterIter.next();
				List<PathVO> pathVOList = maker.getPathVOList();
				Iterator<PathVO> pathVoIter = pathVOList.iterator();
				while (pathVoIter.hasNext()) {
					PathVO pathVo = pathVoIter.next();
					if (StringUtils.equalsIgnoreCase(pathVo.getId(),
							filterCondition[i])) {
						pathVoIter.remove();
					}
				}
			}
		}
	}

	@Override
	public void filterSvgVO(VONode voNode, String[] filterCondition) {
		// TODO Auto-generated method stub

	}

	@Override
	public void filterRectVO(VONode voNode, String[] filterCondition) {
		// TODO Auto-generated method stub

	}

	@Override
	public void filterGVO(VONode voNode, List<String> filterCondition) {
		// TODO Auto-generated method stub

	}
}
