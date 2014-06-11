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
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.foxbpm.engine.impl.svg.vo.GVO;
import org.foxbpm.engine.impl.svg.vo.SvgVO;
import org.foxbpm.engine.impl.svg.vo.VONode;
import org.foxbpm.engine.impl.svg.vo.build.AbstractSVGBuilder;
import org.foxbpm.kernel.process.impl.KernelFlowNodeImpl;

/**
 * 任务SVG对象的工厂类
 * 
 * @author MAENLIANG
 * @date 2014-06-10
 */
public class TaskSVGFactory extends AbstractFlowNodeSVGFactory {
	private static final String SPLIT_SEPERATOR = "/";
	public TaskSVGFactory(String svgTemplateFileName) {
		super(svgTemplateFileName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public VONode createSVGVO(KernelFlowNodeImpl kernelFlowNodeImpl,String svgType) {
		// 分析svgType
		// 加载svgvo
		// 创建svgbuilder
		// builder
		List<String> gIDList = this.getGIDSFromSvgType(svgType);
		SvgVO taskVO = (SvgVO) super.loadSVGVO(this.svgTemplateFileName);
		this.filterSvgVOByGID(taskVO, gIDList);
		AbstractSVGBuilder svgBuilder = AbstractSVGBuilder.createSVGBuilder(
				taskVO, svgType);
		svgBuilder.setText("");
		// 构造
		// 构造
		return taskVO;
	}

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

	private List<String> getGIDSFromSvgType(String svgType) {
		return Arrays.asList(svgType.split(SPLIT_SEPERATOR));
	}

	@Override
	public VONode createSVGVO(KernelFlowNodeImpl kernelFlowNodeImpl) {
		// TODO Auto-generated method stub
		return null;
	}

}
