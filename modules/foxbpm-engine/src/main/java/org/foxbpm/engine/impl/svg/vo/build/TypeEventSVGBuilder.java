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
package org.foxbpm.engine.impl.svg.vo.build;

import java.util.List;

import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.impl.svg.vo.PathVO;
import org.foxbpm.engine.impl.svg.vo.SvgVO;

/**
 * 具体事件类型的构造，比如error、message类型，事件中间都有特定标志，对该标志式样进行构造
 * 
 * @author MAENLIANG
 * @date 2014-06-10
 * 
 */
public class TypeEventSVGBuilder extends EventSVGBuilder {
	/**
	 * 事件类型对象
	 */
	private PathVO pathVo;

	public TypeEventSVGBuilder(SvgVO voNode) {
		super(voNode);
		List<PathVO> pathVoList = this.svgVo.getgVo().getPathVoList();
		if (pathVoList == null || pathVoList.size() == 0) {
			throw new FoxBPMException(
					"the even svg has no type like errorType signalType");
		}
		pathVo = pathVoList.get(0);
	}

	public void setTypeStyle(String typeStyle) {
		pathVo.setStyle(typeStyle);
	}

	@Override
	public void setTypeStroke(String stroke) {
		this.pathVo.setStroke(stroke);
	}

	@Override
	public void setTypeStrokeWidth(String strokeWidth) {
		this.pathVo.setStrokeWidth(strokeWidth);
	}

	@Override
	public void setTypeFill(String fill) {
		this.pathVo.setFill(fill);
	}

}
