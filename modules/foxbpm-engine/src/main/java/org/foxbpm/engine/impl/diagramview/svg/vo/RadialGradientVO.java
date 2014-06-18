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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.foxbpm.engine.impl.diagramview.vo.VONode;

/**
 * 渐变定义
 * 
 * @author MAENLIANG
 * @date 2014-06-10
 * 
 */
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class RadialGradientVO extends VONode {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 3727786287753881069L;
	@XmlAttribute(name = "cx")
	private String cx;
	@XmlAttribute(name = "cy")
	private String cy;
	@XmlAttribute(name = "r")
	private String r;
	@XmlAttribute(name = "fx")
	private String fx;
	@XmlAttribute(name = "fy")
	private String fy;

	@XmlElement(name = "stop")
	private List<StopVO> stopVoList;

	public String getCx() {
		return cx;
	}

	public void setCx(String cx) {
		this.cx = cx;
	}

	public String getCy() {
		return cy;
	}

	public void setCy(String cy) {
		this.cy = cy;
	}

	public String getR() {
		return r;
	}

	public void setR(String r) {
		this.r = r;
	}

	public String getFx() {
		return fx;
	}

	public void setFx(String fx) {
		this.fx = fx;
	}

	public String getFy() {
		return fy;
	}

	public void setFy(String fy) {
		this.fy = fy;
	}

	public List<StopVO> getStopVoList() {
		return stopVoList;
	}

	public void setStopVoList(List<StopVO> stopVoList) {
		this.stopVoList = stopVoList;
	}

}
