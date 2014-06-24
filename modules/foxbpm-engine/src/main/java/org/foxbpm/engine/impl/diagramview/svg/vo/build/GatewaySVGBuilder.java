package org.foxbpm.engine.impl.diagramview.svg.vo.build;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.foxbpm.engine.impl.diagramview.svg.Point;
import org.foxbpm.engine.impl.diagramview.svg.SVGUtils;
import org.foxbpm.engine.impl.diagramview.svg.vo.PathVO;
import org.foxbpm.engine.impl.diagramview.svg.vo.SvgVO;

public class GatewaySVGBuilder extends AbstractSVGBuilder {
	private static final String DEFAULT_FILL = "ffffff";
	private PathVO pathVo;
	private float tempX;
	private float tempY;
	private float tempWidth;
	private float tempHeight;

	public GatewaySVGBuilder(SvgVO svgVo) {
		super(svgVo);
		this.pathVo = SVGUtils.getGatewayVOFromSvgVO(svgVo);
	}

	@Override
	public void setWidth(float width) {
		this.tempWidth = width;
	}

	@Override
	public void setHeight(float height) {
		this.tempHeight = height;
	}

	@Override
	public void setXAndY(float x, float y) {
		this.tempX = x;
		this.tempY = y;
		// 设置整体坐标，包括子类型
		this.svgVo.getgVo().setTransform(
				"translate(" + tempX + ", " + tempY + ")");

		// 设置文本坐标
		if (StringUtils.isNotBlank(textVO.getElementValue())) {
			int textWidth = SVGUtils.getTextWidth(this.textVO.getFont(),
					this.textVO.getElementValue());
			int languageShift = -12;
			if (SVGUtils.isChinese(this.textVO.getElementValue().charAt(0))) {
				languageShift = 10;
			}
			super.setTextX(this.tempWidth - textWidth - languageShift);
			super.setTextY(this.tempHeight + 5);
		}

	}

	@Override
	public void setStroke(String stroke) {
		if (StringUtils.isBlank(stroke)) {
			stroke = STROKE_DEFAULT;
		}
		this.pathVo.setStroke(COLOR_FLAG + stroke);
	}

	@Override
	public void setStrokeWidth(float strokeWidth) {
		this.pathVo.setStrokeWidth(strokeWidth);
	}

	@Override
	public void setFill(String fill) {
		if (StringUtils.isBlank(fill)) {
			fill = DEFAULT_FILL;
		}
		this.buildLinearGradient(fill, pathVo, 0, 0, 0, 40);
	}

	@Override
	public void setID(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setStyle(String style) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setTypeStroke(String stroke) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setTypeStrokeWidth(float strokeWidth) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setTypeFill(String fill) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setTypeStyle(String style) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setWayPoints(List<Point> pointList) {
		// TODO Auto-generated method stub

	}
}
