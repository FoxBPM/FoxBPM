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
	@XmlAttribute(name = "d")
	private String d;
	@XmlAttribute(name = "marker-start")
	private String markerStart;
	@XmlAttribute(name = "marker-end")
	private String markerEnd;

	public String getD() {
		return d;
	}

	public void setD(String d) {
		this.d = d;
	}
}
