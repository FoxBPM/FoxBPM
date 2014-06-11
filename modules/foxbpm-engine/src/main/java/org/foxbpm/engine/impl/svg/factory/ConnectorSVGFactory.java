package org.foxbpm.engine.impl.svg.factory;

import org.foxbpm.engine.impl.svg.SVGTypeNameConstant;
import org.foxbpm.engine.impl.svg.vo.SvgVO;
import org.foxbpm.engine.impl.svg.vo.VONode;
import org.foxbpm.engine.impl.svg.vo.build.AbstractSVGBuilder;

public class ConnectorSVGFactory extends AbstractSVGFactory {

	public ConnectorSVGFactory(String svgTemplateFileName) {
		super(svgTemplateFileName);
	}

	@Override
	public VONode createSVGVO(String svgType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VONode createSVGVO() {
		SvgVO connectorVO = (SvgVO) super.loadSVGVO(svgTemplateFileName);
		// 根据式样构造SVG图像
		AbstractSVGBuilder svgBuilder = AbstractSVGBuilder.createSVGBuilder(
				connectorVO, SVGTypeNameConstant.SVG_TYPE_CONNECTOR);

		return connectorVO;
	}

}
