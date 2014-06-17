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

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

/**
 * SVG对象的超类,包括 节点SVG对象，连接线SVG对象
 * 
 * @author MAENLIANG
 * @date 2014-06-10
 * 
 */
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class VONode implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -817550474604039751L;
	@XmlAttribute(name = "id")
	protected String id;
	@XmlAttribute(name = "name")
	protected String name;
	@XmlAttribute(name = "style")
	protected String style;
	@XmlAttribute(name = "fill")
	protected String fill;
	@XmlAttribute(name = "stroke")
	protected String stroke;
	// 设置成引用类型，引用类型为null不映射
	@XmlAttribute(name = "stroke-width")
	protected Float strokeWidth;
	@XmlAttribute(name = "stroke-linecap")
	protected String strokeLinecap;
	@XmlAttribute(name = "stroke-linejoin")
	protected String strokeLinejoin;
	@XmlAttribute(name = "width")
	protected Float width;
	@XmlAttribute(name = "height")
	protected Float height;
	@XmlAttribute(name = "x")
	protected Float x;
	@XmlAttribute(name = "y")
	protected Float y;
	@XmlValue
	protected String elementValue;

	public String getElementValue() {
		return elementValue;
	}

	public void setElementValue(String elementValue) {
		this.elementValue = elementValue;
	}

	public Float getX() {
		return x;
	}

	public void setX(Float x) {
		this.x = x;
	}

	public Float getY() {
		return y;
	}

	public void setY(Float y) {
		this.y = y;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getFill() {
		return fill;
	}

	public void setFill(String fill) {
		this.fill = fill;
	}

	public String getStroke() {
		return stroke;
	}

	public void setStroke(String stroke) {
		this.stroke = stroke;
	}

	public float getStrokeWidth() {
		return strokeWidth;
	}

	public void setStrokeWidth(float strokeWidth) {
		this.strokeWidth = strokeWidth;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStrokeLinecap() {
		return strokeLinecap;
	}

	public void setStrokeLinecap(String strokeLinecap) {
		this.strokeLinecap = strokeLinecap;
	}

	public String getStrokeLinejoin() {
		return strokeLinejoin;
	}

	public void setStrokeLinejoin(String strokeLinejoin) {
		this.strokeLinejoin = strokeLinejoin;
	}
}
