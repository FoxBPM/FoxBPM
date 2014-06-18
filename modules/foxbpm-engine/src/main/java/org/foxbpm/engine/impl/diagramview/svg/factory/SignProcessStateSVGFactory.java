/**
 * Copyright 1996-2014 FoxBPM ORG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * @author MAENLIANG
 */
package org.foxbpm.engine.impl.diagramview.svg.factory;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.foxbpm.engine.impl.diagramview.factory.AbstractFlowNodeVOFactory;
import org.foxbpm.engine.impl.diagramview.svg.SVGUtils;
import org.foxbpm.engine.impl.diagramview.svg.vo.CircleVO;
import org.foxbpm.engine.impl.diagramview.svg.vo.RectVO;
import org.foxbpm.engine.impl.diagramview.svg.vo.SvgVO;
import org.foxbpm.engine.impl.diagramview.vo.VONode;
import org.foxbpm.kernel.process.KernelFlowElement;

/**
 * BPMN2.0状态标记的工厂类，采用非侵入的方式在SVG模版上实现扩展
 * 
 * @author MAENLIANG
 * @date 2014-06-16
 * 
 */
public class SignProcessStateSVGFactory extends AbstractFlowNodeSVGFactory {
	/**
	 * 流程实例所在节点状态色,红色
	 */
	private static final String SIGN_STATE_NOT_ENDE = "red";
	/**
	 * 已完成任务的状态色，绿色
	 */
	private static final String SIGN_STATE_ENDE = "green";
	/**
	 * 标识 线条的宽度
	 */
	private static final float SIGN_STATE_STROKEWIDTH = 2;
	/**
	 * 标识 FILL 默认值
	 */
	private static final String SIGN_STATE_FILL = "none";

	private static final float SIGN_STATE_RX = 10;
	private static final float SIGN_STATE_RY = 10;

	/**
	 * 标识属性
	 */
	private static final float SIGN_STROKE_OFFSET = 5;
	private static final float SIGN_STROKE_OFFSET_X = -5;
	private static final float SIGN_STROKE_OFFSET_y = -5;
	private static final String SIGN_VO_ID_SUFFIX = "_sign_state";
	private static final String TASK_NOTEND_FLAG = "notEnd";
	private static final String TASK_NOTEXISTS_FLAG = "notExists";

	/**
	 * 节点创建工厂
	 */
	private AbstractFlowNodeVOFactory abstractFlowNodeSVGFactory;
	private String taskStateFlag;

	/**
	 * 
	 * @param kernelFlowElement
	 *            流程节点对象
	 * @param svgTemplateFileName
	 *            模版文件名称
	 * @param abstractFlowNodeSVGFactory
	 *            节点创建工厂
	 * @param taskStateFlag
	 *            标识任务状态
	 */
	public SignProcessStateSVGFactory(KernelFlowElement kernelFlowElement,
			String svgTemplateFileName,
			AbstractFlowNodeVOFactory abstractFlowNodeSVGFactory,
			String taskStateFlag) {
		super(kernelFlowElement, svgTemplateFileName);
		this.abstractFlowNodeSVGFactory = abstractFlowNodeSVGFactory;
		this.taskStateFlag = taskStateFlag;
	}

	/**
	 * 创建带有状态标识的流程节点
	 */
	public VONode createFlowElementSVGVO(String svgType) {
		SvgVO svgVo = (SvgVO) abstractFlowNodeSVGFactory
				.createFlowElementSVGVO(svgType);
		// 为已经完成的和当前待完成的任务节点添加标识
		// 装饰模式
		if (!StringUtils.equalsIgnoreCase(TASK_NOTEXISTS_FLAG, taskStateFlag)) {
			if (abstractFlowNodeSVGFactory instanceof TaskSVGFactory) {
				// TODO 矩形坐标是相对值
				RectVO taskVo = SVGUtils.getTaskVOFromSvgVO(svgVo);
				RectVO signStateRect = this.createSignProcessStateVO(
						taskVo.getId() + SIGN_VO_ID_SUFFIX,
						SIGN_STROKE_OFFSET_X, SIGN_STROKE_OFFSET_y,
						taskVo.getWidth() + SIGN_STROKE_OFFSET * 2,
						taskVo.getHeight() + SIGN_STROKE_OFFSET * 2);
				svgVo.getgVo().getRectVoList().add(signStateRect);
			} else if (abstractFlowNodeSVGFactory instanceof EventSVGFactory) {
				// TODO 圆心坐标是绝对值
				// 事件节点没有采用transform设置坐标，所有这边要获取圆心的坐标位置,然后设置绝对位置
				CircleVO circleVo = SVGUtils.getEventVOFromSvgVO(svgVo);
				RectVO signStateRect = this.createSignProcessStateVO(
						circleVo.getId() + SIGN_VO_ID_SUFFIX,
						circleVo.getCx() - SIGN_STROKE_OFFSET * 2
								- circleVo.getR(),
						circleVo.getCy() - SIGN_STROKE_OFFSET * 1
								- circleVo.getR(), circleVo.getR() * 2
								+ SIGN_STROKE_OFFSET * 4, circleVo.getR() * 2
								+ SIGN_STROKE_OFFSET * 2);

				List<RectVO> rectVoList = svgVo.getgVo().getRectVoList();
				if (rectVoList == null) {
					rectVoList = new ArrayList<RectVO>();
					rectVoList.add(signStateRect);
					svgVo.getgVo().setRectVoList(rectVoList);

				}

			}
		}

		return svgVo;
	}

	/**
	 * 创建流程节点标识
	 * 
	 * @param id
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @return
	 */
	private RectVO createSignProcessStateVO(String id, float x, float y,
			float width, float height) {
		RectVO rectVo = new RectVO();
		rectVo.setId(id);
		rectVo.setX(x);
		rectVo.setY(y);
		rectVo.setRx(SIGN_STATE_RX);
		rectVo.setRy(SIGN_STATE_RY);
		rectVo.setY(y);
		rectVo.setWidth(width);
		rectVo.setHeight(height);
		if (StringUtils.equalsIgnoreCase(TASK_NOTEND_FLAG, taskStateFlag)) {
			rectVo.setStroke(SIGN_STATE_NOT_ENDE);
		} else {
			rectVo.setStroke(SIGN_STATE_ENDE);
		}

		rectVo.setStrokeWidth(SIGN_STATE_STROKEWIDTH);
		rectVo.setFill(SIGN_STATE_FILL);
		return rectVo;
	}

	@Override
	public void filterParentVO(VONode voNode, String[] filterCondition) {
		abstractFlowNodeSVGFactory.filterParentVO(voNode, filterCondition);
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
