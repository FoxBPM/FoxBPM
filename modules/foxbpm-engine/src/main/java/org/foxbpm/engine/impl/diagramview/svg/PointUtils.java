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
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.foxbpm.engine.impl.diagramview.svg.vo.TextVO;

/**
 * 坐标工具类
 * 
 * @author MAENLIANG
 * @date 2014-06-19
 * 
 */
public final class PointUtils {
	/**
	 * X、Y允许的最大偏移量，超过最大偏移量需要设置文本相对线条的中心位置，如果小于这个值则不需要设置
	 */
	private static final float X_Y_LOCATION_MAXSHIFT = 100F;
	// TODO后续改善成动态规划算法
	/**
	 * 默认圆角的大小,暂时分三个梯度
	 */
	private final static float SEQUENCE_ROUNDCONTROL_FLAG = 15.0f;
	private final static float SEQUENCE_ROUNDCONTROL_MIN_FLAG = 3.0f;
	private final static float NONE_SCALE = 1.0f;

	/**
	 * 计算三次贝塞尔曲线的起始点，控制点以及终点
	 * 
	 * @param startEndPoint
	 * @param center
	 * @param end
	 * @return 起始点 控制点 终点坐标数组
	 */
	public final static Point[] caclBeralPoints(Point start, Point center,
			Point end) {
		float lengthStartHerizon = 0.0f;
		float lengthStartVertical = 0.0f;
		float lengthStartCenter = 0.0f;
		float lengthEndHerizon = 0.0f;
		float lengthEndVertical = 0.0f;
		float lengthEndCenter = 0.0f;

		lengthStartHerizon = Math.abs(start.getX() - center.getX());
		lengthStartVertical = Math.abs(start.getY() - center.getY());
		lengthStartCenter = (float) Math.sqrt(lengthStartHerizon
				* lengthStartHerizon + lengthStartVertical
				* lengthStartVertical);
		lengthEndHerizon = Math.abs(end.getX() - center.getX());
		lengthEndVertical = Math.abs(end.getY() - center.getY());
		lengthEndCenter = (float) Math.sqrt(lengthEndHerizon * lengthEndHerizon
				+ lengthEndVertical * lengthEndVertical);

		float scale = SEQUENCE_ROUNDCONTROL_FLAG;
		if (lengthEndCenter <= SEQUENCE_ROUNDCONTROL_FLAG
				|| lengthStartCenter <= SEQUENCE_ROUNDCONTROL_FLAG) {
			if (lengthEndCenter <= SEQUENCE_ROUNDCONTROL_MIN_FLAG
					|| lengthStartCenter <= SEQUENCE_ROUNDCONTROL_MIN_FLAG) {
				scale = NONE_SCALE;
			} else {
				scale = SEQUENCE_ROUNDCONTROL_MIN_FLAG;
			}

		}
		float controlScale = scale / 3;
		Point[] points = new Point[4];
		points[0] = caclBeralControlPoint(start, center, scale,
				lengthStartHerizon, lengthStartVertical, lengthStartCenter);
		points[1] = caclBeralControlPoint(start, center, controlScale,
				lengthStartHerizon, lengthStartVertical, lengthStartCenter);
		points[2] = caclBeralControlPoint(end, center, controlScale,
				lengthEndHerizon, lengthEndVertical, lengthEndCenter);
		points[3] = caclBeralControlPoint(end, center, scale, lengthEndHerizon,
				lengthEndVertical, lengthEndCenter);
		return points;
	}

	/**
	 * 计算三次贝塞尔曲线控制点
	 * 
	 * @param startEndPoint
	 * @param center
	 * @return
	 */
	private final static Point caclBeralControlPoint(Point startEndPoint,
			Point center, float scale, float lengthHerizon,
			float lengthVertical, float lengthCenter) {
		float startX = 0.0f;
		float startY = 0.0f;

		if (startEndPoint.getX() > center.getX()) {
			startX = ((scale * lengthHerizon) / lengthCenter) + center.getX();
		} else {
			startX = (((lengthCenter - scale) * lengthHerizon) / lengthCenter)
					+ startEndPoint.getX();
		}
		if (startEndPoint.getY() > center.getY()) {
			startY = ((scale * lengthVertical) / lengthCenter) + center.getY();
		} else {
			startY = (((lengthCenter - scale) * lengthVertical) / lengthCenter)
					+ startEndPoint.getY();
		}

		Point resultPoint = new Point(Math.round(startX), Math.round(startY));
		return resultPoint;

	}

	/**
	 * 计算二次贝塞尔曲线
	 * 
	 * @param startPoint
	 * @param center
	 * @param end
	 * @param scale
	 * @return
	 */
	public final static Point caclControlPoint(Point startPoint, Point center,
			Point end, float scale) {
		float x = 0.0f;
		float y = 0.0f;
		x = ((1 - scale) * (1 - scale) * startPoint.getX())
				+ (2 * scale * (1 - scale) * center.getX())
				+ (scale * scale * end.getX());
		y = ((1 - scale) * (1 - scale) * startPoint.getY())
				+ (2 * scale * (1 - scale) * center.getY())
				+ (scale * scale * end.getY());
		return new Point(x, y);
	}

	public final static Point caclDetailCenterPoint(List<Point> pointList,TextVO textVo) {
		Float[][] xyArrays = getXYLocationArray(pointList);
		Float[] xArrays = xyArrays[0];
		Float[] yArrays = xyArrays[1];
		float xShift = calCariance(xArrays);
		float yShift = calCariance(yArrays);

		if (xShift > X_Y_LOCATION_MAXSHIFT && yShift < X_Y_LOCATION_MAXSHIFT) {
			// X坐标拐点幅度大，Y不大,计算线条X中心位置，Y取平均值
			return caclXCenter(xArrays, yArrays,textVo);
		} else {
			return caclCenterPoint(pointList);
		}
	}
	/**
	 * 计算中心点位置，包括复杂情况，和简单情况
	 * 
	 * @param pointList
	 * @return
	 */
	public final static Point caclDetailCenterPoint(List<Point> pointList) {
		Float[][] xyArrays = getXYLocationArray(pointList);
		Float[] xArrays = xyArrays[0];
		Float[] yArrays = xyArrays[1];
		float xShift = calCariance(xArrays);
		float yShift = calCariance(yArrays);

		if (xShift > X_Y_LOCATION_MAXSHIFT && yShift < X_Y_LOCATION_MAXSHIFT) {
			// X坐标拐点幅度大，Y不大,计算线条X中心位置，Y取平均值
			return caclXCenter(xArrays, yArrays);
		} else {
			return caclCenterPoint(pointList);
		}
		// 如果偏差过大就计算中心点
		// if (xShift > X_Y_LOCATION_MAXSHIFT && yShift < X_Y_LOCATION_MAXSHIFT)
		// {
		// // X坐标拐点幅度大，Y不大,计算线条X中心位置，Y取平均值
		// return caclXCenter(xArrays, yArrays);
		//
		// } else if (yShift > X_Y_LOCATION_MAXSHIFT && xShift <
		// X_Y_LOCATION_MAXSHIFT) {
		// // Y坐标拐点幅度大，X不大,计算线条Y中心位置，X取平均值
		// return caclYCenter(xArrays, yArrays);
		//
		// } else if (yShift > X_Y_LOCATION_MAXSHIFT && xShift >
		// X_Y_LOCATION_MAXSHIFT) {
		// // XY拐点幅度都比较大
		// return caclCenterPoint(pointList);
		// }

		// 如果没有计算中心点
		// return null;

	}

	/**
	 * 勾股定理, 取线段长度
	 * 
	 * @param pointA
	 * @param pointB
	 * @return
	 */
	public final static float segmentLength(Point pointA, Point pointB) {
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
	private final static List<Float> getSegmentsLength(List<Point> pointList) {
		List<Float> segList = new ArrayList<Float>();

		float segLen = 0f;
		int size = pointList.size();
		size = size - 1;
		Point pointA = null;
		Point pointB = null;
		for (int i = 0; i < size; i++) {
			pointA = pointList.get(i);
			pointB = pointList.get(i + 1);

			segLen = segmentLength(pointA, pointB);
			segList.add(segLen);
		}

		return segList;
	}

	/**
	 * 计算所有x坐标值或者y坐标值的方差，判断是否需要重新设置所有拐点的中心点
	 * 
	 * @param array
	 * @return
	 */
	public final static Float calCariance(Float[] array) {
		int arrayLength = array.length;
		Float ave = 0.0F;
		for (int i = 0; i < arrayLength; i++) {
			ave += array[i];
		}
		ave /= arrayLength;
		Float sum = 0.0F;
		for (int i = 0; i < arrayLength; i++) {
			sum += (array[i] - ave) * (array[i] - ave);
		}
		sum = sum / arrayLength;
		return sum;
	}

	/**
	 * 获取所有点的XY坐标
	 * 
	 * @param pointList
	 * @return
	 */
	private final static Float[][] getXYLocationArray(List<Point> pointList) {
		int pointListsize = pointList.size();
		Float[][] xyLocationArray = new Float[2][pointListsize];
		Iterator<Point> iterator = pointList.iterator();
		for (int i = 0; i < pointListsize; i++) {
			Point next = iterator.next();
			xyLocationArray[0][i] = next.getX();
			xyLocationArray[1][i] = next.getY();
		}
		return xyLocationArray;
	}

	/**
	 * 计算线条在X方向上的中心位置
	 * 
	 * @param xArrays
	 * @param yArrays
	 * @return
	 */
	public final static Point caclXCenter(Float[] xArrays, Float[] yArrays) {
		Arrays.sort(xArrays);
		int length = yArrays.length;
		Float xCenter = xArrays[0] + (xArrays[xArrays.length - 1] - xArrays[0])
				/ 2;
		Float tempV = 0.0f;
		for (int i = 0; i < length; i++) {
			tempV += yArrays[i];
		}
		Float y = tempV / length;
		return new Point(xCenter, y);
	}
	
	/**
	 * 计算线条在X方向上的中心位置
	 * 
	 * @param xArrays
	 * @param yArrays
	 * @return
	 */
	public final static Point caclXCenter(Float[] xArrays, Float[] yArrays,TextVO textVo) {
		Arrays.sort(xArrays);
		int length = yArrays.length;
		Float xCenter = xArrays[0] + (xArrays[xArrays.length - 1] - xArrays[0])
				/ 2;
		Float tempV = 0.0f;
		for (int i = 0; i < length; i++) {
			tempV += yArrays[i];
		}
		Float y = tempV / length;
		
		int textWidth = SVGUtils.getTextWidth(textVo.getFont(), textVo.getElementValue());
		if (SVGUtils.isChinese(textVo.getElementValue().charAt(0))) {
			textWidth = textWidth + (textWidth / 20) * 5;
		}
		
		xCenter = xCenter - textWidth/2;
		return new Point(xCenter, y-10);
	}

	/**
	 * 计算线条在y方向上的中心位置
	 * 
	 * @param xArrays
	 * @param yArrays
	 * @return
	 */
	public final static Point caclYCenter(Float[] xArrays, Float[] yArrays) {
		Arrays.sort(yArrays);
		Float yCenter = yArrays[0] + (yArrays[yArrays.length - 1] - yArrays[0])
				/ 2;
		Float tempV = 0.0f;
		int length = xArrays.length;
		for (int i = 0; i < length; i++) {
			tempV += xArrays[i];
		}
		Float x = tempV / length;
		return new Point(yCenter, x);
	}

	/**
	 * 计算中心点
	 * 
	 * @param svgLine
	 * @return
	 */
	public final static Point caclCenterPoint(List<Point> pointList) {
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
