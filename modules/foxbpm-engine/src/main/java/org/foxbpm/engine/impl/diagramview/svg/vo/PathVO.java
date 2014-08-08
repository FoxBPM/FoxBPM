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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * 用于定义 组件的子类型
 * 
 * @author MAENLIANG
 * @date 2014-06-10
 * 
 */
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class PathVO extends NamespaceAttributesVO {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 6232272472033577074L;
	@XmlAttribute(name = "d")
	private String d;
	@XmlAttribute(name = "marker-start")
	private String markerStart;
	public String getMarkerStart() {
		return markerStart;
	}

	public void setMarkerStart(String markerStart) {
		this.markerStart = markerStart;
	}

	public String getMarkerEnd() {
		return markerEnd;
	}

	public void setMarkerEnd(String markerEnd) {
		this.markerEnd = markerEnd;
	}

	@XmlAttribute(name = "marker-end")
	private String markerEnd;
	@XmlAttribute(name = "stroke-dasharray")
	private String strokeDasharray;

	public String getD() {
		return d;
	}

	public void setD(String d) {
		this.d = d;
	}
	public String getStrokeDasharray() {
		return strokeDasharray;
	}

	public void setStrokeDasharray(String strokeDasharray) {
		this.strokeDasharray = strokeDasharray;
	}
}
