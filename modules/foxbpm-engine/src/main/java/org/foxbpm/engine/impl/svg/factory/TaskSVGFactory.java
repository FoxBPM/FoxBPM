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

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.foxbpm.engine.impl.svg.vo.GVO;
import org.foxbpm.engine.impl.svg.vo.RectVO;
import org.foxbpm.engine.impl.svg.vo.SvgVO;
import org.foxbpm.engine.impl.svg.vo.VONode;
import org.foxbpm.kernel.process.KernelFlowElement;

/**
 * 任务SVG对象的工厂类
 * 
 * @author MAENLIANG
 * @date 2014-06-10
 */
public class TaskSVGFactory extends AbstractFlowNodeSVGFactory {
	private static final String SPLIT_SEPERATOR = "/";

	/**
	 * 
	 * @param kernelFlowElement
	 *            任务节点
	 * @param svgTemplateFileName
	 *            SVG模版文件名
	 */
	public TaskSVGFactory(KernelFlowElement kernelFlowElement,
			String svgTemplateFileName) {
		super(kernelFlowElement, svgTemplateFileName);
	}

	@Override
	public VONode createSVGVO() {
		SvgVO taskVO = (SvgVO) super.loadSVGVO(this.svgTemplateFileName);
		return taskVO;
	}

	@Override
	public VONode createSVGVO(String svgType) {
		List<String> gIDList = this.getGIDSFromSvgType(svgType);
		SvgVO taskVO = (SvgVO) super.loadSVGVO(this.svgTemplateFileName);
		this.filterSvgVOByGID(taskVO, gIDList);
		this.filterRectVO(taskVO);

		return taskVO;
	}

	/**
	 * 从模版对象中过滤掉其他类型的G节点
	 * 
	 * @param svgVO
	 * @param gIDList
	 */
	private void filterSvgVOByGID(SvgVO svgVO, List<String> gIDList) {
		GVO gvo = svgVO.getgVo();
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

	/**
	 * 过滤RECT节点
	 * 
	 * @param svgVO
	 */
	private void filterRectVO(SvgVO svgVO) {
		List<RectVO> rectVoList = svgVO.getgVo().getRectVoList();
		Iterator<RectVO> rectVOIter = rectVoList.iterator();
		while (rectVOIter.hasNext()) {
			RectVO rectVo = rectVOIter.next();
			if (StringUtils.equalsIgnoreCase(rectVo.getId(), "callActivity")
					|| StringUtils.equalsIgnoreCase(rectVo.getId(),
							"text_frame")) {
				rectVOIter.remove();
			}
		}
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

	/**
	 * 转化G节点ID为集合
	 * 
	 * @param svgType
	 * @return
	 */
	private List<String> getGIDSFromSvgType(String svgType) {
		return Arrays.asList(svgType.split(SPLIT_SEPERATOR));
	}
}
