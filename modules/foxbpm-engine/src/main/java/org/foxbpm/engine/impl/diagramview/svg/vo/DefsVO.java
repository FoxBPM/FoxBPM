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
package org.foxbpm.engine.impl.diagramview.svg.vo;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.foxbpm.engine.impl.diagramview.vo.VONode;

/**
 * 用于定义SVG中的渲染效果,例如渐变
 * 
 * @author MAENLIANG
 * @date 2014-06-10
 */
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class DefsVO extends VONode {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8584630516155274242L;
	@XmlElement(name = "radialGradient")
	private RadialGradientVO radialGradientVo;
	@XmlElement(name = "linearGradient")
	private LinearGradient linearGradient;
	@XmlElement(name = "marker")
	private List<MarkerVO> markerVOList;

	public LinearGradient getLinearGradient() {
		return linearGradient;
	}

	public void setLinearGradient(LinearGradient linearGradient) {
		this.linearGradient = linearGradient;
	}

	public List<MarkerVO> getMarkerVOList() {
		return markerVOList;
	}

	public void setMarkerVOList(List<MarkerVO> markerVOList) {
		this.markerVOList = markerVOList;
	}

	public RadialGradientVO getRadialGradientVo() {
		return radialGradientVo;
	}

	public void setRadialGradientVo(RadialGradientVO radialGradientVo) {
		this.radialGradientVo = radialGradientVo;
	}
}
