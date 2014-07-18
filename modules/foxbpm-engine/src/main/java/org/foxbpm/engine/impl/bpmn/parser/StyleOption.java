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
 * @author kenshin
 */
package org.foxbpm.engine.impl.bpmn.parser;

/**
 * 流程图元素样式
 * 
 * @author kenshin
 */
public class StyleOption {
	/** 背景色 */
	public static String Background = "background";
	/** 前景色 */
	public static String Foreground = "foreground";
	/** 字体 */
	public static String Font = "font";
	/** 选中中间颜色 */
	public static String MulitSelectedColor = "mulitSelectedColor";
	/** 选中颜色 */
	public static String SelectedColor = "selectedColor";
	/** 样式对象名称 */
	public static String StyleObject = "styleObject";
	/** 文字颜色 */
	public static String TextColor = "textColor";

	/** 是否水平 */
	public static String IsHorizontal = "isHorizontal";

	/** 子流程是否展开 */
	public static String IsExpanded = "IsExpanded";

}
