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
package org.foxbpm.engine.impl.diagramview.svg.builder;

import java.awt.Font;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.impl.diagramview.builder.FoxBpmnViewBuilder;
import org.foxbpm.engine.impl.diagramview.svg.Point;
import org.foxbpm.engine.impl.diagramview.svg.SVGTypeNameConstant;
import org.foxbpm.engine.impl.diagramview.svg.vo.DefsVO;
import org.foxbpm.engine.impl.diagramview.svg.vo.LinearGradient;
import org.foxbpm.engine.impl.diagramview.svg.vo.RadialGradientVO;
import org.foxbpm.engine.impl.diagramview.svg.vo.StopVO;
import org.foxbpm.engine.impl.diagramview.svg.vo.SvgVO;
import org.foxbpm.engine.impl.diagramview.svg.vo.TextVO;
import org.foxbpm.engine.impl.diagramview.vo.VONode;

/**
 * 组件式样的构造，包括EVENT、ACTIVITY等组件
 * 需要设置的式样或属性包括ID、NAM、填充色、边框宽度、边框颜色、组件文本、组件文本式样、组件坐标、组件大小
 * 
 * @author MAENLIANG
 * @date 2014-06-10
 */
public abstract class AbstractSVGBuilder implements FoxBpmnViewBuilder {
	/**
	 * static relation
	 */
	private final static Map<String, Class<?>> svgBuildMap = initSvgBuildMap();
	/**
	 * SVG 对象
	 */
	protected SvgVO svgVo;
	/**
	 * 文本对象
	 */
	protected TextVO textVO;
	
	public AbstractSVGBuilder(SvgVO svgVo) {
		this.svgVo = svgVo;
		this.textVO = svgVo.getgVo().getTextVo();
	}
	
	/**
	 * 根据type创建Builder对象，例如任务SVG，事件SVG，连接线SVG，，，
	 * 
	 * @param svgVo
	 * @param type
	 * @return
	 */
	public static AbstractSVGBuilder createSVGBuilder(VONode svgVo, String type) {
		try {
			return (AbstractSVGBuilder) svgBuildMap.get(type).getConstructor(SvgVO.class).newInstance(svgVo);
		} catch (Exception e) {
			throw new FoxBPMException("createSVGBuilder exception", e);
		}
	}
	
	/**
	 * 
	 * initSvgBuildMap(static relation)
	 * 
	 * @return Map<String,Class<?>>
	 * @exception
	 * @since 1.0.0
	 */
	private static Map<String, Class<?>> initSvgBuildMap() {
		Map<String, Class<?>> tempSvgBuildMap = new HashMap<String, Class<?>>();
		tempSvgBuildMap.put(SVGTypeNameConstant.SVG_TYPE_EVENT, EventSVGBuilder.class);
		tempSvgBuildMap.put(SVGTypeNameConstant.SVG_TYPE_EVENT_BOUNDARY_INTERRUPTING_TIME, BoundaryEventSVGBuilder.class);
		tempSvgBuildMap.put(SVGTypeNameConstant.SVG_TYPE_EVENT_BOUNDARY_NONEINTERRUPTING_TIME, BoundaryEventSVGBuilder.class);
		tempSvgBuildMap.put(SVGTypeNameConstant.SVG_TYPE_EVENT_END_TERMINATE, EndTerminateEventSVGBuilder.class);
		tempSvgBuildMap.put(SVGTypeNameConstant.SVG_TYPE_EVENT_START_TIMER, TimerStartEventSVGBuilder.class);
		
		tempSvgBuildMap.put(SVGTypeNameConstant.SVG_TYPE_TASK, TaskSVGBuilder.class);
		
		tempSvgBuildMap.put(SVGTypeNameConstant.ACTIVITY_SERVICETASK, ServiceTaskSVGBuilder.class);
		tempSvgBuildMap.put(SVGTypeNameConstant.ACTIVITY_USERTASK, TaskSVGBuilder.class);
		tempSvgBuildMap.put(SVGTypeNameConstant.ACTIVITY_SENDTASK, TaskSVGBuilder.class);
		tempSvgBuildMap.put(SVGTypeNameConstant.ACTIVITY_MANUALTASK, TaskSVGBuilder.class);
		tempSvgBuildMap.put(SVGTypeNameConstant.ACTIVITY_BUSINESSRULETASK, TaskSVGBuilder.class);
		tempSvgBuildMap.put(SVGTypeNameConstant.ACTIVITY_RECEIVETASK, TaskSVGBuilder.class);
		tempSvgBuildMap.put(SVGTypeNameConstant.ACTIVITY_SCRIPTTASK, TaskSVGBuilder.class);
		
		tempSvgBuildMap.put(SVGTypeNameConstant.SVG_TYPE_TASK_SEQUENTIAL, TaskSVGBuilder.class);
		
		tempSvgBuildMap.put(SVGTypeNameConstant.ACTIVITY_SERVICETASK_SEQUENTIAL, ServiceTaskSVGBuilder.class);
		tempSvgBuildMap.put(SVGTypeNameConstant.ACTIVITY_USERTASK_SEQUENTIAL, TaskSVGBuilder.class);
		tempSvgBuildMap.put(SVGTypeNameConstant.ACTIVITY_SENDTASK_SEQUENTIAL, TaskSVGBuilder.class);
		tempSvgBuildMap.put(SVGTypeNameConstant.ACTIVITY_MANUALTASK_SEQUENTIAL, TaskSVGBuilder.class);
		tempSvgBuildMap.put(SVGTypeNameConstant.ACTIVITY_BUSINESSRULETASK_SEQUENTIAL, TaskSVGBuilder.class);
		tempSvgBuildMap.put(SVGTypeNameConstant.ACTIVITY_RECEIVETASK_SEQUENTIAL, TaskSVGBuilder.class);
		tempSvgBuildMap.put(SVGTypeNameConstant.ACTIVITY_SCRIPTTASK_SEQUENTIAL, TaskSVGBuilder.class);
		
		tempSvgBuildMap.put(SVGTypeNameConstant.SVG_TYPE_CONNECTOR, ConnectorSVGBuilder.class);
		tempSvgBuildMap.put(SVGTypeNameConstant.SVT_TYPE_GATEWAY, GatewaySVGBuilder.class);
		tempSvgBuildMap.put(SVGTypeNameConstant.SVG_TYPE_LANE, LanesetSVGBuilder.class);
		tempSvgBuildMap.put(SVGTypeNameConstant.SVG_TYPE_GROUP, GroupSVGBuilder.class);
		tempSvgBuildMap.put(SVGTypeNameConstant.SVG_TYPE_TEXTANNOTATION, TextAnnotationSVGBuilder.class);
		tempSvgBuildMap.put(SVGTypeNameConstant.SVG_TYPE_CONNECTOR_ASSOCIATION_UNDIRECTED, AssociationSVGBuilder.class);
		tempSvgBuildMap.put(SVGTypeNameConstant.SVG_TYPE_CALLACTIVITY, CallActivitySVGBuilder.class);
		tempSvgBuildMap.put(SVGTypeNameConstant.SVG_TYPE_SUBPROCESS, SubProcessSVGBuilder.class);
		return tempSvgBuildMap;
	}
	
	/**
	 * 设置线条的乖点
	 * 
	 * @param wayPointArray
	 */
	public abstract void setWayPoints(List<Point> pointList);
	
	/**
	 * 设置SVG的宽度
	 * 
	 * @param width
	 */
	public abstract void setWidth(float width);
	
	/**
	 * 设置SVG的高度
	 * 
	 * @param width
	 */
	public abstract void setHeight(float height);
	
	/**
	 * 设置元素的坐标
	 * 
	 * @param x
	 * @param y
	 */
	public abstract void setXAndY(float x, float y);
	
	/**
	 * 设置元素的边框色
	 * 
	 * @param stroke
	 */
	public abstract void setStroke(String stroke);
	
	/**
	 * 设置元素的边框宽度
	 * 
	 * @param strokeWidth
	 */
	public abstract void setStrokeWidth(float strokeWidth);
	
	/**
	 * 设置元素的填充色，即背景色
	 * 
	 * @param fill
	 */
	public abstract void setFill(String fill);
	
	/**
	 * 设置元素的显示名
	 * 
	 * @param text
	 */
	public void setText(String text) {
		this.textVO.setElementValue(text);
	}
	
	public void setTextFont(String fontStr) {
		if (StringUtils.isBlank(fontStr)) {
			Font font = new Font(ARIAL, Font.CENTER_BASELINE, 11);
			this.textVO.setFont(font);
			return;
		}
		String[] fonts = fontStr.split(COMMA);
		String style = new StringBuffer("font-family:").append(fonts[0]).append(";font-size:").append(fonts[1]).toString();
		Font font = new Font(fonts[0], Font.PLAIN, Integer.valueOf(fonts[1]));
		this.textVO.setFont(font);
		this.textVO.setStyle(style);
		this.textVO.setFontSize(fonts[1]);
	}
	
	public void setTextStrokeWidth(float textStrokeWidth) {
		this.textVO.setStrokeWidth(textStrokeWidth);
	}
	
	/**
	 * 设置显示名的X坐标
	 * 
	 * @param textX
	 */
	public void setTextX(float textX) {
		this.textVO.setX(textX);
	}
	
	/**
	 * 设置显示名的Y坐标
	 * 
	 * @param textY
	 */
	public void setTextY(float textY) {
		this.textVO.setY(textY);
	}
	
	/**
	 * 泳道负责重写该方法 setTextLocationByVerticalFlag(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 –
	 * 可选)
	 * 
	 * @param verticalFlag
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	public void setTextLocationByHerizonFlag(boolean herizonFlag) {
	}
	
	/**
	 * 设置显示名的字体大小
	 * 
	 * @param textFontSize
	 */
	public void setTextFontSize(String textFontSize) {
		this.textVO.setFontSize(textFontSize);
	}
	
	/**
	 * 设置显示名的边框色
	 * 
	 * @param textStroke
	 */
	public void setTextStroke(String textStroke) {
		if (StringUtils.isBlank(textStroke)) {
			this.textVO.setStroke(STROKE_DEFAULT);
			return;
		}
		this.textVO.setStroke(COLOR_FLAG + textStroke);
	}
	
	public void setTextFill(String fill) {
		if (StringUtils.isBlank(fill)) {
			this.textVO.setFill(STROKE_DEFAULT);
		}
		this.textVO.setFill(COLOR_FLAG + fill);
	}
	
	/**
	 * 设置元素ID
	 * 
	 * @param id
	 */
	public abstract void setID(String id);
	
	/**
	 * 设置元素Name
	 * 
	 * @param name
	 */
	public abstract void setName(String name);
	
	/**
	 * 设置元素的式样
	 * 
	 * @param style
	 */
	public abstract void setStyle(String style);
	
	/**
	 * 设置子类型的边框颜色
	 * 
	 * @param stroke
	 */
	public abstract void setTypeStroke(String stroke);
	
	/**
	 * 设置子类型的边框宽度
	 * 
	 * @param strokeWidth
	 */
	public abstract void setTypeStrokeWidth(float strokeWidth);
	
	/**
	 * 设置子类型的填充式样
	 * 
	 * @param fill
	 */
	public abstract void setTypeFill(String fill);
	
	/**
	 * 设置子类型的式样
	 * 
	 * @param style
	 */
	public abstract void setTypeStyle(String style);
	
	// TODO 放射性渐变,目前采用的是线性渐变
	/**
	 * 
	 * 
	 * @param fill
	 */
	protected void buildRadialGradient(String fill, VONode svgVo) {
		DefsVO defsVo = this.svgVo.getgVo().getDefsVo();
		if (defsVo != null) {
			RadialGradientVO radialGradientVo = defsVo.getRadialGradientVo();
			if (radialGradientVo != null) {
				String backGroudUUID = UUID.randomUUID().toString();
				radialGradientVo.setId(backGroudUUID);
				svgVo.setFill(new StringBuffer(BACK_GROUND_PREFIX).append(backGroudUUID).append(BACK_GROUND_SUFFIX).append(fill).toString());
				return;
			}
		}
	}
	
	/**
	 * 线性渐变
	 * 
	 * @param fill
	 */
	protected void buildLinearGradient(String fill, VONode svgVo, float x1, float x2, float y1,
	    float y2) {
		DefsVO defsVo = this.svgVo.getgVo().getDefsVo();
		if (defsVo != null) {
			LinearGradient linearGradient = defsVo.getLinearGradient();
			if (linearGradient != null) {
				String backGroudUUID = UUID.randomUUID().toString();
				linearGradient.setId(backGroudUUID);
				linearGradient.setX1(x1);
				linearGradient.setX2(x2);
				linearGradient.setY1(y1);
				linearGradient.setY2(y2);
				List<StopVO> stopVoList = linearGradient.getStopVoList();
				if (stopVoList != null && stopVoList.size() > 0) {
					StopVO stopVO = stopVoList.get(LINEARGRADIENT_INDEX);
					svgVo.setFill(BACK_GROUND_PREFIX + backGroudUUID + BRACKET_SUFFIX);
					stopVO.setStopColor(COLOR_FLAG + fill);
				}
			}
			
		}
	}
	
	public TextVO getTextVo(){
		return this.textVO;
	}
	
}
