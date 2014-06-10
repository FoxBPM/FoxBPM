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
package org.foxbpm.engine.impl.svg.vo;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 用于组合SVG中的各个元素
 * @author MAENLIANG 
 * @date 2014-06-10
 *
 */
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class GVO extends NamespaceAttributesVO {
	@XmlAttribute(name = "pointer-events")
	private String pointerEvents;
	@XmlAttribute(name = "transform")
	private String transform;
	@XmlElement(name = "rect")
	private List<RectVO> rectVoList;
	@XmlElement(name = "circle")
	private List<CircleVO> circleVoList;
	@XmlElement(name = "defs")
	private DefsVO defsVo;
	@XmlElement(name = "g")
	private List<GVO> gVoList;
	@XmlElement(name = "path")
	private List<PathVO> pathVoList;
	@XmlElement(name = "text")
	private TextVO textVo;
	@XmlElement(name = "polygon")
	private List<PolygonVO> polygonList;

	public List<PolygonVO> getPolygonList() {
		return polygonList;
	}

	public void setPolygonList(List<PolygonVO> polygonList) {
		this.polygonList = polygonList;
	}

	public String getTransform() {
		return transform;
	}

	public void setTransform(String transform) {
		this.transform = transform;
	}

	public List<RectVO> getRectVoList() {
		return rectVoList;
	}

	public void setRectVoList(List<RectVO> rectVoList) {
		this.rectVoList = rectVoList;
	}

	public List<CircleVO> getCircleVoList() {
		return circleVoList;
	}

	public void setCircleVoList(List<CircleVO> circleVoList) {
		this.circleVoList = circleVoList;
	}

	public String getPointerEvents() {
		return pointerEvents;
	}

	public void setPointerEvents(String pointerEvents) {
		this.pointerEvents = pointerEvents;
	}

	public DefsVO getDefsVo() {
		return defsVo;
	}

	public void setDefsVo(DefsVO defsVo) {
		this.defsVo = defsVo;
	}

	public List<GVO> getgVoList() {
		return gVoList;
	}

	public void setgVoList(List<GVO> gVoList) {
		this.gVoList = gVoList;
	}

	public List<PathVO> getPathVoList() {
		return pathVoList;
	}

	public void setPathVoList(List<PathVO> pathVoList) {
		this.pathVoList = pathVoList;
	}

	public TextVO getTextVo() {
		return textVo;
	}

	public void setTextVo(TextVO textVo) {
		this.textVo = textVo;
	}

}
