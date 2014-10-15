package org.foxbpm.model;

public class Graphic {

	protected double x;
	protected double y;
	protected double height;
	protected double width;
	protected String bpmnElement;
	protected Boolean expanded;

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public String getBpmnElement() {
		return bpmnElement;
	}

	public void setBpmnElement(String bpmnElement) {
		this.bpmnElement = bpmnElement;
	}

	public Boolean getExpanded() {
		return expanded;
	}

	public void setExpanded(Boolean expanded) {
		this.expanded = expanded;
	}

}
