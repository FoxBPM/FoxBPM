package org.foxbpm.engine.impl.svg.vo;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 
 * @author MAENLIANG
 * @date 2014-06-11
 * 
 */
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class MarkerVO extends VONode {

	@XmlAttribute(name = "refX")
	private String refX;
	@XmlAttribute(name = "refY")
	private String refY;
	@XmlAttribute(name = "markerUnits")
	private String markerUnits;
	@XmlAttribute(name = "markerWidth")
	private String markerWidth;
	@XmlAttribute(name = "markerHeight")
	private String markerHeight;
	@XmlAttribute(name = "orient")
	private String orient;
	@XmlElement(name = "path")
	private List<PathVO> pathVOList;

	public String getRefX() {
		return refX;
	}

	public void setRefX(String refX) {
		this.refX = refX;
	}

	public String getRefY() {
		return refY;
	}

	public void setRefY(String refY) {
		this.refY = refY;
	}

	public String getMarkerUnits() {
		return markerUnits;
	}

	public void setMarkerUnits(String markerUnits) {
		this.markerUnits = markerUnits;
	}

	public String getMarkerWidth() {
		return markerWidth;
	}

	public void setMarkerWidth(String markerWidth) {
		this.markerWidth = markerWidth;
	}

	public String getMarkerHeight() {
		return markerHeight;
	}

	public void setMarkerHeight(String markerHeight) {
		this.markerHeight = markerHeight;
	}

	public String getOrient() {
		return orient;
	}

	public void setOrient(String orient) {
		this.orient = orient;
	}

	public List<PathVO> getPathVOList() {
		return pathVOList;
	}

	public void setPathVOList(List<PathVO> pathVOList) {
		this.pathVOList = pathVOList;
	}
}
