package org.foxbpm.engine.impl.diagramview.svg.factory;

import java.util.List;

import org.foxbpm.engine.impl.diagramview.factory.AbstractFlowNodeVOFactory;
import org.foxbpm.engine.impl.diagramview.svg.vo.RectVO;
import org.foxbpm.engine.impl.diagramview.svg.vo.SvgVO;
import org.foxbpm.engine.impl.diagramview.svg.vo.VONode;
import org.foxbpm.kernel.process.KernelFlowElement;

public class SignProcessStateSVGFactory extends AbstractFlowNodeSVGFactory {
	private static final String SIGN_STATE_NOT_ENDE = "red";
	private static final String SIGN_STATE_ENDE = "black";
	private static final String SIGN_STATE_STROKEWIDTH = "3";
	private AbstractFlowNodeVOFactory abstractFlowNodeSVGFactory;
	private boolean taskStateFlag;

	public SignProcessStateSVGFactory(KernelFlowElement kernelFlowElement,
			String svgTemplateFileName,
			AbstractFlowNodeVOFactory abstractFlowNodeSVGFactory,
			boolean taskStateFlag) {
		super(kernelFlowElement, svgTemplateFileName);
		this.abstractFlowNodeSVGFactory = abstractFlowNodeSVGFactory;
		this.taskStateFlag = taskStateFlag;
	}

	@Override
	public VONode createFlowElementSVGVO(String svgType) {
		SvgVO svgVo = (SvgVO) abstractFlowNodeSVGFactory
				.createFlowElementSVGVO(svgType);
		// 为任务节点添加标识
		if (abstractFlowNodeSVGFactory instanceof TaskSVGFactory) {
			RectVO signStateRect = this.createSignProcessStateVO("bg_frame_sign_state", "5", "5",
					"120", "60");
			svgVo.getgVo().getRectVoList().add(signStateRect);
		}
		return svgVo;
	}

	private RectVO createSignProcessStateVO(String id, String x, String y,
			String width, String height) {
		RectVO rectVo = new RectVO();
		rectVo.setId(id);
		rectVo.setX(x);
		rectVo.setY(y);
		rectVo.setWidth(width);
		rectVo.setHeight(height);
		if (taskStateFlag) {
			rectVo.setStroke(SIGN_STATE_NOT_ENDE);
		} else {
			rectVo.setStroke(SIGN_STATE_ENDE);
		}

		rectVo.setStrokeWidth(SIGN_STATE_STROKEWIDTH);
		return rectVo;
	}

	@Override
	public void filterParentVO(VONode voNode, String[] filterCondition) {
		this.filterParentVO(voNode, filterCondition);
	}

	@Override
	public void filterActivityTaskVO(VONode voNode, String[] filterCondition) {
		abstractFlowNodeSVGFactory
				.filterActivityTaskVO(voNode, filterCondition);
	}

	@Override
	public void filterConnectorVO(VONode voNode, String[] filterCondition) {
		abstractFlowNodeSVGFactory.filterConnectorVO(voNode, filterCondition);
	}

	@Override
	public void filterChildVO(VONode voNode, List<String> filterCondition) {
		abstractFlowNodeSVGFactory.filterChildVO(voNode, filterCondition);
	}

	@Override
	public VONode createSVGVO(String svgType) {
		return abstractFlowNodeSVGFactory.createSVGVO(svgType);
	}

	@Override
	public VONode createSVGVO() {
		return abstractFlowNodeSVGFactory.createSVGVO();
	}

}
