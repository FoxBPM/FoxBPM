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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.impl.diagramview.svg.Point;
import org.foxbpm.engine.impl.diagramview.svg.SVGUtils;
import org.foxbpm.engine.impl.diagramview.svg.vo.DefsVO;
import org.foxbpm.engine.impl.diagramview.svg.vo.LinearGradient;
import org.foxbpm.engine.impl.diagramview.svg.vo.PathVO;
import org.foxbpm.engine.impl.diagramview.svg.vo.RectVO;
import org.foxbpm.engine.impl.diagramview.svg.vo.StopVO;
import org.foxbpm.engine.impl.diagramview.svg.vo.SvgVO;
import org.foxbpm.engine.impl.diagramview.svg.vo.TextSpanVO;

/**
 * 任务组件式样构造
 * 
 * @author MAENLIANG
 * @date 2014-06-10
 */
public class TaskSVGBuilder extends AbstractSVGBuilder {
	protected static final String FILL_DEFAULT = "ffffcc";
	
	protected static final int TEXT_HEIGHT = 10;
	protected static final int TEXT_LINESPACE = 5;
	/**
	 * 矩形对象
	 */
	protected RectVO rectVO;
	
	/**
	 * 多实例
	 */
	protected PathVO sequentialVO;
	/**
	 * 任务节点Builder、获取节点矩形
	 * 
	 * @param svgVo
	 */
	public TaskSVGBuilder(SvgVO svgVo) {
		super(svgVo);
		rectVO = SVGUtils.getTaskVOFromSvgVO(svgVo);
		sequentialVO = SVGUtils.getSequentialVOFromSvgVO(svgVo);
		if (this.rectVO == null) {
			throw new FoxBPMException("TaskSVGBuilder构造 TASK SVG时，无法获取矩形对象");
		}
		
	}
	
	@Override
	public void setWidth(float width) {
		rectVO.setWidth(width);
	}
	
	@Override
	public void setHeight(float height) {
		this.rectVO.setHeight(height);
	}
	
	@Override
	public void setStroke(String stroke) {
		if (StringUtils.isBlank(stroke)) {
			this.rectVO.setStroke(STROKE_DEFAULT);
			return;
		}
		this.rectVO.setStroke(COLOR_FLAG + stroke);
		if (sequentialVO != null) {
			this.sequentialVO.setStroke(COLOR_FLAG + stroke);
		}
	}
	
	@Override
	public void setStrokeWidth(float strokeWidth) {
		this.rectVO.setStrokeWidth(strokeWidth);
	}
	
	@Override
	public void setFill(String fill) {
		if (StringUtils.isBlank(fill)) {
			fill = FILL_DEFAULT;
		}
		this.buildLinearGradient(fill);
	}
	
	@Override
	public void setXAndY(float x, float y) {
		// 设置整体坐标，包括子类型
		this.svgVo.getgVo().setTransform(new StringBuffer(TRANSLANT_PREFIX).append(x).append(COMMA).append(y).append(BRACKET_SUFFIX).toString());
		// 设置相对位置
		this.rectVO.setX(0.0F);
		this.rectVO.setY(0.0F);
		float rectWidth = this.rectVO.getWidth();
		float rectHeight = this.rectVO.getHeight();
		
		// 设置多实例标示坐标
		if (this.sequentialVO != null) {
			StringBuffer dBuffer = new StringBuffer(50);
			float halfWidth = (rectWidth / 2);
			float sequentialHeigth = rectHeight - 13;
			dBuffer.append("M").append(halfWidth - 4).append(",").append(sequentialHeigth).append("v10").append("M").append(halfWidth).append(",").append(sequentialHeigth).append("v10").append("M").append(halfWidth + 4).append(",").append(sequentialHeigth).append("v10");
			sequentialVO.setD(dBuffer.toString());
		}
		// 设置字体的相对偏移量,X相对是矩形宽度的一半减去文本本身屏宽的一半
		// TODO 目前支持全英文或者全中文、全日文、全韩文。
		String elementValue = textVO.getElementValue();
		if (StringUtils.isNotBlank(elementValue)) {
			int textWidth = SVGUtils.getTextWidth(this.textVO.getFont(), elementValue);
			if (SVGUtils.isChinese(elementValue.charAt(0))) {
				textWidth = textWidth + (textWidth / 20) * 5;
			}
			// 文本折行处理
			if (textWidth >= rectWidth) {
				// 计算行数
				int splitNum = (int) (textWidth / rectWidth);
				if (textWidth % rectWidth != 0) {
					splitNum = splitNum + 1;
				}
				int valueLength = elementValue.length();
				List<TextSpanVO> textSpanList = new ArrayList<TextSpanVO>();
				// 处理第一行
				String tempValue = elementValue.substring(0, valueLength / splitNum);
				textVO.setElementValue(tempValue);
				int tempTextWidth = SVGUtils.getTextWidth(this.textVO.getFont(), tempValue);
				if (SVGUtils.isChinese(elementValue.charAt(0))) {
					tempTextWidth = tempTextWidth + (tempTextWidth / 20) * 5;
				}
				
				int startHeight = (int) ((rectHeight - splitNum * TEXT_HEIGHT / 2 - ((splitNum - 1) * TEXT_LINESPACE) / 2) / 2);
				super.setTextX((rectWidth / 2) - tempTextWidth / 2);
				super.setTextY(startHeight);
				
				// 从第二行开始依次处理
				for (int i = 1; i < splitNum; i++) {
					// 获取文本
					if (i == splitNum - 1) {
						tempValue = elementValue.substring(i * (valueLength / splitNum), valueLength - 1);
					} else {
						tempValue = elementValue.substring(i * (valueLength / splitNum), (i + 1)
						        * (valueLength / splitNum));
					}
					
					tempTextWidth = SVGUtils.getTextWidth(this.textVO.getFont(), tempValue);
					TextSpanVO tspanVo = new TextSpanVO();
					tspanVo.setElementValue(tempValue);
					if (SVGUtils.isChinese(elementValue.charAt(0))) {
						tempTextWidth = tempTextWidth + (tempTextWidth / 20) * 5;
					}
					tspanVo.setX((rectWidth / 2) - tempTextWidth / 2);
					tspanVo.setY((float) startHeight + i * TEXT_HEIGHT);
					textSpanList.add(tspanVo);
				}
				
				textVO.setTextSpanList(textSpanList);
			} else {
				super.setTextX((rectWidth / 2) - textWidth / 2);
				super.setTextY(rectHeight / 2 + 5);
			}
			
		}
		
	}
	
	@Override
	public void setID(String id) {
		this.rectVO.setId(id);
	}
	
	@Override
	public void setName(String name) {
		
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
	
	/**
	 * 线性渐变
	 * 
	 * @param fill
	 */
	private void buildLinearGradient(String fill) {
		DefsVO defsVo = this.svgVo.getgVo().getDefsVo();
		if (defsVo != null) {
			LinearGradient linearGradient = defsVo.getLinearGradient();
			if (linearGradient != null) {
				String backGroudUUID = UUID.randomUUID().toString();
				linearGradient.setId(backGroudUUID);
				linearGradient.setX1(0.0F);
				linearGradient.setX2(0.0F);
				linearGradient.setY1(0.0F);
				linearGradient.setY2(this.rectVO.getHeight());
				List<StopVO> stopVoList = linearGradient.getStopVoList();
				if (stopVoList != null && stopVoList.size() > 0) {
					StopVO stopVO = stopVoList.get(LINEARGRADIENT_INDEX);
					this.rectVO.setFill(new StringBuffer(BACK_GROUND_PREFIX).append(backGroudUUID).append(BRACKET_SUFFIX).toString());
					stopVO.setStopColor(COLOR_FLAG + fill);
				}
			}
			
		}
	}
}
