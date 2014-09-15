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
 * 矩形定义
 * 
 * @author MAENLIANG
 * @date 2014-06-10
 * 
 */
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class RectVO extends NamespaceAttributesVO {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 995236243017598074L;
	@XmlAttribute(name = "rx")
	private Float rx;
	@XmlAttribute(name = "ry")
	private Float ry;
	@XmlAttribute(name = "stroke-dasharray")
	private String strokeDasharray;

	public Float getRx() {
		return rx;
	}

	public void setRx(Float rx) {
		this.rx = rx;
	}

	public Float getRy() {
		return ry;
	}

	public void setRy(Float ry) {
		this.ry = ry;
	}
	public String getStrokeDasharray() {
		return strokeDasharray;
	}

	public void setStrokeDasharray(String strokeDasharray) {
		this.strokeDasharray = strokeDasharray;
	}

}
