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

import org.apache.commons.lang3.StringUtils;
import org.foxbpm.engine.impl.diagramview.svg.SVGUtils;
import org.foxbpm.engine.impl.diagramview.svg.vo.SvgVO;
import org.foxbpm.engine.impl.diagramview.svg.vo.TextSpanVO;

/**
 * 
 * 
 * CallActivitySVGBuilder 共有子流程建造器
 * 
 * MAENLIANG 2014年7月17日 下午8:58:48
 * 
 * @version 1.0.0
 * 
 */
public class CallActivitySVGBuilder extends TaskSVGBuilder {
	private final static float DEFAULT_TEXT_HEIGHT = 15;
	public CallActivitySVGBuilder(SvgVO svgVo) {
		super(svgVo);
	}
	
	 
	public void setXAndY(float x, float y) {
		// 设置整体坐标，包括子类型
		this.svgVo.getgVo().setTransform(new StringBuffer(TRANSLANT_PREFIX).append(x).append(COMMA).append(y).append(BRACKET_SUFFIX).toString());
		// 设置相对位置
		this.rectVO.setX(0.0F);
		this.rectVO.setY(0.0F);
		float rectWidth = this.rectVO.getWidth();
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
				
				super.setTextX((rectWidth / 2) - tempTextWidth / 2);
				super.setTextY(DEFAULT_TEXT_HEIGHT);
				
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
					tspanVo.setY(DEFAULT_TEXT_HEIGHT + (float) i * TEXT_HEIGHT);
					textSpanList.add(tspanVo);
				}
				
				textVO.setTextSpanList(textSpanList);
			} else {
				super.setTextX((rectWidth / 2) - textWidth / 2);
				super.setTextY(DEFAULT_TEXT_HEIGHT);
			}
			
		}
	}
	
}
