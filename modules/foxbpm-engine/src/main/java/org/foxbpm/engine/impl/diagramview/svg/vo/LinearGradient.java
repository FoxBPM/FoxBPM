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
	private String x1;
	@XmlAttribute(name = "x2")
	private String x2;
	@XmlAttribute(name = "y1")
	private String y1;
	@XmlAttribute(name = "y2")
	private String y2;
	@XmlAttribute(name = "gradientUnits")
	private String gradientUnits;
	@XmlAttribute(name = "spreadMethod")
	private String spreadMethod;
	@XmlElement(name = "stop")
	private List<StopVO> stopVoList;

	public String getX1() {
		return x1;
	}

	public void setX1(String x1) {
		this.x1 = x1;
	}

	public String getX2() {
		return x2;
	}

	public void setX2(String x2) {
		this.x2 = x2;
	}

	public String getY1() {
		return y1;
	}

	public void setY1(String y1) {
		this.y1 = y1;
	}

	public String getY2() {
		return y2;
	}

	public void setY2(String y2) {
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
