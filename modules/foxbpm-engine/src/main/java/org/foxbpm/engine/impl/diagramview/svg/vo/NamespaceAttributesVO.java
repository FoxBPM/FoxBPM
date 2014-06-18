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

import org.foxbpm.engine.impl.diagramview.vo.VONode;

/**
 * ORYX命名空间属性定义
 * 
 * @author MAENLIANG
 * @date 2014-06-10
 * 
 */
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class NamespaceAttributesVO extends VONode {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -6775939784953491038L;
	@XmlAttribute(name = "cx", namespace = "http://www.b3mn.org/oryx")
	protected String oryxCx;
	@XmlAttribute(name = "cy", namespace = "http://www.b3mn.org/oryx")
	protected String oryxCy;
	@XmlAttribute(name = "anchors", namespace = "http://www.b3mn.org/oryx")
	protected String oryxAnchors;
	@XmlAttribute(name = "default", namespace = "http://www.b3mn.org/oryx")
	protected String oryxDefault;
	@XmlAttribute(name = "resize", namespace = "http://www.b3mn.org/oryx")
	protected String oryxResize;
	@XmlAttribute(name = "align", namespace = "http://www.b3mn.org/oryx")
	protected String oryxAlign;
	@XmlAttribute(name = "fittoelem", namespace = "http://www.b3mn.org/oryx")
	protected String oryxFittoelem;
	@XmlAttribute(name = "minimumSize", namespace = "http://www.b3mn.org/oryx")
	protected String oryxMinimumSize;
	@XmlAttribute(name = "maximumSize", namespace = "http://www.b3mn.org/oryx")
	protected String oryxMaximumSize;

	public String getOryxAlign() {
		return oryxAlign;
	}

	public void setOryxAlign(String oryxAlign) {
		this.oryxAlign = oryxAlign;
	}

	public String getOryxFittoelem() {
		return oryxFittoelem;
	}

	public void setOryxFittoelem(String oryxFittoelem) {
		this.oryxFittoelem = oryxFittoelem;
	}

	public String getOryxMinimumSize() {
		return oryxMinimumSize;
	}

	public void setOryxMinimumSize(String oryxMinimumSize) {
		this.oryxMinimumSize = oryxMinimumSize;
	}

	public String getOryxMaximumSize() {
		return oryxMaximumSize;
	}

	public void setOryxMaximumSize(String oryxMaximumSize) {
		this.oryxMaximumSize = oryxMaximumSize;
	}

	public String getOryxDefault() {
		return oryxDefault;
	}

	public void setOryxDefault(String oryxDefault) {
		this.oryxDefault = oryxDefault;
	}

	public String getOryxResize() {
		return oryxResize;
	}

	public void setOryxResize(String oryxResize) {
		this.oryxResize = oryxResize;
	}

	public String getOryxCx() {
		return oryxCx;
	}

	public void setOryxCx(String oryxCx) {
		this.oryxCx = oryxCx;
	}

	public String getOryxCy() {
		return oryxCy;
	}

	public void setOryxCy(String oryxCy) {
		this.oryxCy = oryxCy;
	}

	public String getOryxAnchors() {
		return oryxAnchors;
	}

	public void setOryxAnchors(String oryxAnchors) {
		this.oryxAnchors = oryxAnchors;
	}
}
