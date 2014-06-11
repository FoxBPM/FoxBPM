package org.foxbpm.engine.impl.svg.factory;

import org.foxbpm.engine.impl.svg.SVGTypeNameConstant;
import org.foxbpm.engine.impl.svg.vo.SvgVO;
import org.foxbpm.engine.impl.svg.vo.VONode;
import org.foxbpm.engine.impl.svg.vo.build.AbstractSVGBuilder;
import org.foxbpm.kernel.process.impl.KernelFlowNodeImpl;

/**
 * BPMN2.0事件元素之线条定义
 * 
 * @author MAENLIANG
 * @date 2014-06-10
 * 
 */
public class ConnectorSVGFactory extends AbstractFlowNodeSVGFactory {

	public ConnectorSVGFactory(String svgTemplateFileName) {
		super(svgTemplateFileName);
	}

	@Override
	public VONode createSVGVO(KernelFlowNodeImpl kernelFlowNodeImpl,
			String svgType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VONode createSVGVO(KernelFlowNodeImpl kernelFlowNodeImpl) {
		SvgVO connectorVO = (SvgVO) super.loadSVGVO(svgTemplateFileName);
		// 根据式样构造SVG图像
		AbstractSVGBuilder svgBuilder = AbstractSVGBuilder.createSVGBuilder(
				connectorVO, SVGTypeNameConstant.SVG_TYPE_CONNECTOR);

		return connectorVO;
	}

}
