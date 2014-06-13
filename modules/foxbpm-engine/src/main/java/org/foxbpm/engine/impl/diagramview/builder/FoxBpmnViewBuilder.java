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
package org.foxbpm.engine.impl.diagramview.builder;

/**
 * 流程图形信息构造接口
 * 
 * @author MAENLIANG
 * @date 2014-06-10
 */
public interface FoxBpmnViewBuilder {
	/**
	 * 设置节点信息
	 */
	public void setWayPoints(String[] wayPointArray);

	public void setWidth(String width);

	public void setHeight(String height);

	public void setXAndY(String x, String y);

	public void setStroke(String stroke);

	public void setStrokeWidth(String strokeWidth);

	public void setFill(String fill);

	public void setID(String id);

	public void setName(String name);

	public void setStyle(String style);

	/**
	 * 设置文本信息
	 */
	public void setText(String text);

	public void setTextFont(String font);

	public void setTextStrokeWidth(String textStrokeWidth);

	public void setTextX(String textX);

	public void setTextY(String textY);

	public void setTextFontSize(String textFontSize);

	public void setTextStroke(String textStroke);

	/**
	 * 设置子类型的信息
	 */
	public void setTypeStroke(String stroke);

	public void setTypeStrokeWidth(String strokeWidth);

	public void setTypeFill(String fill);

	public void setTypeStyle(String style);
}
