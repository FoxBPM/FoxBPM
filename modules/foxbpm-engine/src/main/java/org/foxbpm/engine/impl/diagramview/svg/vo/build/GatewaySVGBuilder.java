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

	public GatewaySVGBuilder(SvgVO svgVo) {
		super(svgVo);
		this.pathVo = SVGUtils.getGatewayVOFromSvgVO(svgVo);
	}

	@Override
	public void setXAndY(float x, float y) {
		// 设置整体坐标，包括子类型
		this.svgVo.getgVo().setTransform("translate(" + x + ", " + y + ")");
	}

	@Override
	public void setStroke(String stroke) {
		if (StringUtils.isBlank(stroke)) {
			stroke = STROKE_DEFAULT;
		}
		this.pathVo.setStroke(stroke);
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
		this.buildRadialGradient(fill, this.pathVo);
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

	@Override
	public void setWidth(float width) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setHeight(float height) {
		// TODO Auto-generated method stub

	}

}
