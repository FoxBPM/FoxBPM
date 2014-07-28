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
import org.foxbpm.engine.impl.diagramview.svg.vo.SvgVO;
import org.foxbpm.engine.impl.diagramview.vo.VONode;
import org.foxbpm.kernel.process.KernelBaseElement;

/**
 * 任务SVG对象的工厂类
 * 
 * @author MAENLIANG
 * @date 2014-06-10
 */
public class TaskSVGFactory extends AbstractFlowElementSVGFactory {
	/**
	 * 
	 * @param kernelFlowElement
	 *            任务节点
	 * @param voTemplateFileName
	 *            SVG模版文件名
	 */
	public TaskSVGFactory(KernelBaseElement kernelBaseElement, String voTemplateFileName) {
		super(kernelBaseElement, voTemplateFileName);
	}
	
	@Override
	public VONode createSVGVO() {
		return (SvgVO) super.loadSVGVO(this.voTemplateFileName);
	}
	
	@Override
	public void filterChildVO(VONode voNode, List<String> gIDList) {
		GVO gvo = ((SvgVO) voNode).getgVo();
		List<GVO> gvoList = gvo.getgVoList();
		Iterator<GVO> gvoIter = gvoList.iterator();
		GVO subGVo = null;
		while (gvoIter.hasNext()) {
			subGVo = gvoIter.next();
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
		String tempID = null;
		while (idIter.hasNext()) {
			tempID = idIter.next();
			if (StringUtils.equalsIgnoreCase(tempID, id)) {
				return true;
			}
		}
		return false;
	}
}
