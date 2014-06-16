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
package org.foxbpm.engine.impl.diagramview.svg;

import java.awt.Font;
import java.awt.FontMetrics;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JLabel;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.commons.lang3.StringUtils;
import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.impl.diagramview.svg.vo.CircleVO;
import org.foxbpm.engine.impl.diagramview.svg.vo.DefsVO;
import org.foxbpm.engine.impl.diagramview.svg.vo.GVO;
import org.foxbpm.engine.impl.diagramview.svg.vo.PathVO;
import org.foxbpm.engine.impl.diagramview.svg.vo.RectVO;
import org.foxbpm.engine.impl.diagramview.svg.vo.SvgVO;
import org.foxbpm.engine.impl.diagramview.svg.vo.VONode;

/**
 * SVG工具类
 * 
 * @author MAENLIANG
 * @date 2014-06-10
 */
public final class SVGUtils {

	/**
	 * BPMN节点类型(例如：矩形，圆形)在SVG文档中的ID
	 */
	public static final String BPMN_NODE_ID = "bg_frame";

	public final static RectVO getTaskVOFromSvgVO(SvgVO svgVo) {
		List<RectVO> rectVoList = svgVo.getgVo().getRectVoList();
		Iterator<RectVO> iterator = rectVoList.iterator();
		while (iterator.hasNext()) {
			RectVO next = iterator.next();
			if (StringUtils.equalsIgnoreCase(next.getId(), BPMN_NODE_ID)) {
				return next;
			}
		}
		return null;
	}

	public final static CircleVO getEventVOFromSvgVO(SvgVO svgVo) {
		List<CircleVO> circleVoList = svgVo.getgVo().getCircleVoList();
		Iterator<CircleVO> iterator = circleVoList.iterator();
		while (iterator.hasNext()) {
			CircleVO next = iterator.next();
			if (StringUtils.equalsIgnoreCase(next.getId(), BPMN_NODE_ID)) {
				return next;
			}
		}
		return null;
	}

	public final static PathVO getSequenceVOFromSvgVO(SvgVO svgVo) {
		List<GVO> gVoList = svgVo.getgVo().getgVoList();
		if (gVoList != null && gVoList.size() > 0) {
			Iterator<GVO> iterator = gVoList.iterator();
			while (iterator.hasNext()) {
				GVO next = iterator.next();
				if (StringUtils.equalsIgnoreCase(next.getId(), "edge")) {
					List<PathVO> pathVoList = next.getPathVoList();
					Iterator<PathVO> pathIter = pathVoList.iterator();
					while (pathIter.hasNext()) {
						PathVO tempPathVo = pathIter.next();
						if (StringUtils.equalsIgnoreCase(tempPathVo.getId(),
								BPMN_NODE_ID)) {
							return tempPathVo;
						}
					}

				}
			}
		}
		return null;
	}

	public final static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {

			return true;
		}
		return false;
	}

	/**
	 * 根据文本的式样，以及文本内容，获取文本在屏幕上展示的像素宽
	 * 
	 * @param font
	 *            字体式样
	 * @param text
	 *            文本
	 * @return 文本宽度
	 */
	public final static int getTextWidth(Font font, String text) {
		JLabel label = new JLabel(text);
		label.setFont(font);
		FontMetrics metrics = label.getFontMetrics(label.getFont());
		return metrics.stringWidth(label.getText());
	}

	/**
	 * 将所有的线条的所有点信息，转化成点的坐标
	 * 
	 * @param waypoints
	 * @return
	 */
	public final static List<Point> convertWaypointsTOPointList(
			List<Integer> waypoints) {
		if (waypoints != null && waypoints.size() > 0
				&& waypoints.size() % 2 == 0) {
			List<Point> pointList = new ArrayList<Point>();
			Point point = null;
			for (int i = 0; i < waypoints.size(); i++) {
				if (i % 2 != 0) {
					point = new Point(Float.valueOf(String.valueOf(waypoints
							.get(i - 1))), Float.valueOf(String
							.valueOf(waypoints.get(i))));
					pointList.add(point);
				}
			}
			return pointList;
		} else {
			throw new FoxBPMException("线条节点有问题 waypoints不符合规则！");
		}
	}

	/**
	 * 创建waypoint节点数组
	 * 
	 * @param waypoints
	 * @return
	 */
	public final static String[] getSequenceFLowWayPointArrayByWayPointList(
			List<Integer> waypoints) {
		if (waypoints != null && waypoints.size() > 0
				&& waypoints.size() % 2 == 0) {
			String[] wayPointArray = new String[waypoints.size() / 2];
			int arrayIndex = 0;
			for (int i = 0; i < waypoints.size(); i++) {
				if (i % 2 != 0) {
					wayPointArray[arrayIndex] = String.valueOf(waypoints
							.get(i - 1))
							+ " "
							+ String.valueOf(waypoints.get(i)) + " ";
					arrayIndex++;
				}
			}
			return wayPointArray;
		} else {
			throw new FoxBPMException("线条节点有问题 waypoints不符合规则！");
		}
	}

	/**
	 * 操作之后的SVG转化成String字符串
	 * 
	 * @param svgVo
	 * @return
	 */
	public final static String createSVGString(VONode svgVo) {
		try {
			JAXBContext context = JAXBContext.newInstance(SvgVO.class);
			Marshaller marshal = context.createMarshaller();
			marshal.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			StringWriter writer = new StringWriter();

			marshal.marshal(svgVo, writer);
			return writer.toString();
		} catch (Exception e) {
			throw new FoxBPMException("svg object convert to String exception",
					e);
		}
	}

	/**
	 * DefsVO克隆
	 * 
	 * @param DefsVO
	 *            原对象
	 * @return clone之后的对象
	 */
	public final static GVO cloneGVO(GVO gVo) {
		return (GVO) clone(gVo);
	}

	public final static DefsVO cloneDefsVO(DefsVO defsVo) {
		return (DefsVO) clone(defsVo);
	}

	/**
	 * SvgVO模板对象需要多次引用，所以要克隆，避免产生问题
	 * 
	 * @param SvgVO
	 *            原对象
	 * @return clone之后的对象
	 */
	public final static SvgVO cloneSVGVo(SvgVO svgVo) {
		return (SvgVO) clone(svgVo);
	}

	/**
	 * 克隆对象
	 * 
	 * @param object
	 *            原对象
	 * @return 目标对象
	 */
	public final static Object clone(Object object) {
		ByteArrayOutputStream bos = null;
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;
		Object cloneObject = null;
		try {
			bos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(bos);
			oos.writeObject(object);
			ois = new ObjectInputStream(new ByteArrayInputStream(
					bos.toByteArray()));
			cloneObject = ois.readObject();
		} catch (Exception e) {
			throw new FoxBPMException("SVG对象G节点克隆出现问题", e);
		} finally {
			try {
				bos.close();
				oos.close();
				ois.close();
			} catch (Exception e) {
				throw new FoxBPMException("关闭对象流时出现问题", e);
			}
		}

		return cloneObject;
	}
}
