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

import java.util.ArrayList;
import java.util.List;

public class PointUtils {
	/**
	 * 勾股定理, 取线段长度
	 * 
	 * @param pointA
	 * @param pointB
	 * @return
	 */
	public static float segmentLength(Point pointA, Point pointB) {
		double length = 0;
		float width = pointA.getX() - pointA.getX();
		float height = pointA.getY() - pointB.getY();
		double dWidth = (double) width;
		double dHeight = (double) height;

		length = Math.sqrt(Math.pow(dWidth, 2) + Math.pow(dHeight, 2));
		return (float) length;
	}

	/**
	 * 统计所有线段的长度
	 * 
	 * @param pointList
	 * @return
	 */
	private static List<Float> getSegmentsLength(List<Point> pointList) {
		List<Float> segList = new ArrayList<Float>();

		float segLen = 0f;
		for (int i = 0; i < pointList.size() - 1; i++) {
			Point pointA = pointList.get(i);
			Point pointB = pointList.get(i + 1);

			segLen = segmentLength(pointA, pointB);
			segList.add(segLen);
		}

		return segList;
	}

	/**
	 * 计算中心点
	 * 
	 * @param svgLine
	 * @return
	 */
	public static Point caclCenterPoint(List<Point> pointList) {
		List<Float> segList = PointUtils.getSegmentsLength(pointList);
		float totalLength = 0f;
		for (float seg : segList) {
			totalLength += seg;
		}

		float centerLen = totalLength / 2;

		int keySegIndex = 0;

		float keySeg = 0f;
		float tempLen = 0f;
		for (float seg : segList) {
			keySeg = seg;
			keySegIndex++;
			if ((tempLen + keySeg) > centerLen) {
				break;
			} else {
				tempLen += keySeg;
			}

		}

		float lastLen = centerLen - tempLen;

		Point startPoint = pointList.get(keySegIndex - 1);
		Point endPoint = pointList.get(keySegIndex);

		float scale = lastLen / keySeg;

		float x = startPoint.getX() + (endPoint.getX() - startPoint.getX())
				* scale;
		float y = startPoint.getY() + (endPoint.getY() - startPoint.getY())
				* scale;
		Point point = new Point(x, y);
		return point;
	}
}
