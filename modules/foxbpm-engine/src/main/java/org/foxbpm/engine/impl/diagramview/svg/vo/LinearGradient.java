package org.foxbpm.engine.impl.diagramview.svg.vo;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 渐变定义
 * 
 * @author MAENLIANG
 * @date 2014-06-10
 * 
 */
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class LinearGradient extends VONode {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -868070769119888506L;
	@XmlAttribute(name = "x1")
	private Float x1;
	@XmlAttribute(name = "x2")
	private Float x2;
	@XmlAttribute(name = "y1")
	private Float y1;
	@XmlAttribute(name = "y2")
	private Float y2;
	@XmlAttribute(name = "gradientUnits")
	private String gradientUnits;
	@XmlAttribute(name = "spreadMethod")
	private String spreadMethod;
	@XmlElement(name = "stop")
	private List<StopVO> stopVoList;

	public Float getX1() {
		return x1;
	}

	public void setX1(Float x1) {
		this.x1 = x1;
	}

	public Float getX2() {
		return x2;
	}

	public void setX2(Float x2) {
		this.x2 = x2;
	}

	public Float getY1() {
		return y1;
	}

	public void setY1(Float y1) {
		this.y1 = y1;
	}

	public Float getY2() {
		return y2;
	}

	public void setY2(Float y2) {
		this.y2 = y2;
	}

	public String getGradientUnits() {
		return gradientUnits;
	}

	public void setGradientUnits(String gradientUnits) {
		this.gradientUnits = gradientUnits;
	}

	public String getSpreadMethod() {
		return spreadMethod;
	}

	public void setSpreadMethod(String spreadMethod) {
		this.spreadMethod = spreadMethod;
	}

	public List<StopVO> getStopVoList() {
		return stopVoList;
	}

	public void setStopVoList(List<StopVO> stopVoList) {
		this.stopVoList = stopVoList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
