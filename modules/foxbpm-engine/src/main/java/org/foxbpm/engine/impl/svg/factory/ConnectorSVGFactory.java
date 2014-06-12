package org.foxbpm.engine.impl.svg.factory;

import org.foxbpm.engine.impl.svg.SVGTypeNameConstant;
import org.foxbpm.engine.impl.svg.vo.SvgVO;
import org.foxbpm.engine.impl.svg.vo.VONode;
import org.foxbpm.engine.impl.svg.vo.build.AbstractSVGBuilder;
import org.foxbpm.kernel.process.KernelFlowElement;
import org.foxbpm.kernel.process.impl.KernelFlowNodeImpl;

/**
 * BPMN2.0事件元素之线条定义
 * 
 * @author MAENLIANG
 * @date 2014-06-10
 * 
 */
public class ConnectorSVGFactory extends AbstractFlowNodeSVGFactory {

	/**
	 * 
	 * @param kernelFlowElement
	 *            连接线对象
	 * @param svgTemplateFileName
	 */
	public ConnectorSVGFactory(KernelFlowElement kernelFlowElement,
			String svgTemplateFileName) {
		super(kernelFlowElement, svgTemplateFileName);
	}

	@Override
	public VONode createSVGVO(String svgType) {
		SvgVO connectorVO = (SvgVO) super.loadSVGVO(svgTemplateFileName);
		return connectorVO;
	}

	@Override
	public VONode createSVGVO() {
		SvgVO connectorVO = (SvgVO) super.loadSVGVO(svgTemplateFileName);
		return connectorVO;
	}

}
