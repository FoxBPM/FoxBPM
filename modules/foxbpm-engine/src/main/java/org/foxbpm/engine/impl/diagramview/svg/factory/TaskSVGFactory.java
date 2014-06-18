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

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.foxbpm.engine.impl.diagramview.svg.vo.GVO;
import org.foxbpm.engine.impl.diagramview.svg.vo.RectVO;
import org.foxbpm.engine.impl.diagramview.svg.vo.SvgVO;
import org.foxbpm.engine.impl.diagramview.vo.VONode;
import org.foxbpm.kernel.process.KernelFlowElement;

/**
 * 任务SVG对象的工厂类
 * 
 * @author MAENLIANG
 * @date 2014-06-10
 */
public class TaskSVGFactory extends AbstractFlowNodeSVGFactory {
	/**
	 * 
	 * @param kernelFlowElement
	 *            任务节点
	 * @param voTemplateFileName
	 *            SVG模版文件名
	 */
	public TaskSVGFactory(KernelFlowElement kernelFlowElement,
			String voTemplateFileName) {
		super(kernelFlowElement, voTemplateFileName);
	}

	@Override
	public VONode createSVGVO() {
		SvgVO taskVO = (SvgVO) super.loadSVGVO(this.voTemplateFileName);
		return taskVO;
	}

	@Override
	public VONode createSVGVO(String svgType) {
		SvgVO taskVO = (SvgVO) super.loadSVGVO(this.voTemplateFileName);
		return taskVO;
	}

	/**
	 * 判断当前配置的ID是否存在
	 * 
	 * @param gIDList
	 * @param id
	 * @return
	 */
	private boolean confirmGVOExistsByID(List<String> gIDList, String id) {
		Iterator<String> idIter = gIDList.iterator();
		while (idIter.hasNext()) {
			String tempID = idIter.next();
			if (StringUtils.equalsIgnoreCase(tempID, id)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void filterActivityTaskVO(VONode svgVO, String[] filterCondition) {
		for (int i = 0; i < filterCondition.length; i++) {
			List<RectVO> rectVoList = ((SvgVO) svgVO).getgVo().getRectVoList();
			Iterator<RectVO> rectVOIter = rectVoList.iterator();
			while (rectVOIter.hasNext()) {
				RectVO rectVo = rectVOIter.next();
				if (StringUtils.equalsIgnoreCase(rectVo.getId(),
						filterCondition[i])) {
					rectVOIter.remove();
				}
			}
		}
	}

	@Override
	public void filterChildVO(VONode voNode, List<String> gIDList) {
		GVO gvo = ((SvgVO) voNode).getgVo();
		List<GVO> gvoList = gvo.getgVoList();
		Iterator<GVO> gvoIter = gvoList.iterator();
		while (gvoIter.hasNext()) {
			GVO subGVo = gvoIter.next();
			if (!this.confirmGVOExistsByID(gIDList, subGVo.getId())) {
				gvoIter.remove();
				continue;
			}
		}

	}

	@Override
	public void filterConnectorVO(VONode voNode, String[] filterCondition) {
		// TODO Auto-generated method stub

	}

	@Override
	public void filterParentVO(VONode voNode, String[] filterCondition) {
		// TODO Auto-generated method stub

	}
}
