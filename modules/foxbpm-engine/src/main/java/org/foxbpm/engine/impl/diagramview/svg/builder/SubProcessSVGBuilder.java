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
package org.foxbpm.engine.impl.diagramview.svg.builder;

import org.foxbpm.engine.impl.diagramview.svg.vo.SvgVO;

/**
 * 
 * 
 * SubProcessSVGBuilder 内部子流程建造器
 * 
 * MAENLIANG 2014年7月17日 下午8:58:04
 * 
 * @version 1.0.0
 * 
 */
public class SubProcessSVGBuilder extends TaskSVGBuilder {

	public SubProcessSVGBuilder(SvgVO svgVo) {
		super(svgVo);
	}

	@Override
	public void setXAndY(float x, float y) {
		super.setXAndY(x, y);
		// 设置内部子流程文本Y坐标的相对值
		super.setTextY(20);
	}
}
