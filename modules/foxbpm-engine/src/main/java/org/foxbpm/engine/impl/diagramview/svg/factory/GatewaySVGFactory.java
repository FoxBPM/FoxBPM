package org.foxbpm.engine.impl.diagramview.svg.factory;

import java.util.List;

import org.foxbpm.engine.impl.diagramview.vo.VONode;
import org.foxbpm.kernel.process.KernelFlowElement;

public class GatewaySVGFactory extends AbstractFlowNodeSVGFactory {

	public GatewaySVGFactory(KernelFlowElement kernelFlowElement,
			String svgTemplateFileName) {
		super(kernelFlowElement, svgTemplateFileName);
	}

	@Override
	public VONode createSVGVO(String svgType) {
		return this.loadSVGVO(this.voTemplateFileName);
	}

	@Override
	public VONode createSVGVO() {
		return this.loadSVGVO(voTemplateFileName);
	}

	@Override
	public void filterParentVO(VONode voNode, String[] filterCondition) {
		// TODO Auto-generated method stub

	}

	@Override
	public void filterActivityTaskVO(VONode voNode, String[] filterCondition) {
		// TODO Auto-generated method stub

	}

	@Override
	public void filterConnectorVO(VONode voNode, String[] filterCondition) {
		// TODO Auto-generated method stub

	}

	@Override
	public void filterChildVO(VONode voNode, List<String> filterCondition) {
		// TODO Auto-generated method stub

	}
}
