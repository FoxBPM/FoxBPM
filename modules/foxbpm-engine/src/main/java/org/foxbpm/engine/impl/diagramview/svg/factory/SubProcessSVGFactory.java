package org.foxbpm.engine.impl.diagramview.svg.factory;

import java.util.List;

import org.foxbpm.engine.impl.diagramview.svg.vo.SvgVO;
import org.foxbpm.engine.impl.diagramview.vo.VONode;
import org.foxbpm.kernel.process.KernelBaseElement;

public class SubProcessSVGFactory extends AbstractFlowElementSVGFactory {

	public SubProcessSVGFactory(KernelBaseElement kernelBaseElement, String svgTemplateFileName) {
		super(kernelBaseElement, svgTemplateFileName);
	}

	@Override
	public VONode createSVGVO(String svgType) {
		SvgVO taskVO = (SvgVO) super.loadSVGVO(this.voTemplateFileName);
		return taskVO;
	}

	@Override
	public VONode createSVGVO() {
		SvgVO taskVO = (SvgVO) super.loadSVGVO(this.voTemplateFileName);
		return taskVO;
	}

	@Override
	public void filterParentVO(VONode voNode, String[] filterCondition) {
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
