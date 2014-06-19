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

/**
 * 坐标工具类
 * 
 * @author MAENLIANG
 * @date 2014-06-19
 * 
 */
public final class PointUtils {
	/**
	 * X、Y允许的最大便宜量，超过最大偏移量需要设置文本相对线条的中心位置，如果小于这个值则不需要设置
	 */
	private static final float X_Y_LOCATION_MAXSHIFT = 100F;

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
		// 如果偏差过大就计算中心点
		if (xShift > X_Y_LOCATION_MAXSHIFT && yShift < X_Y_LOCATION_MAXSHIFT) {
			// X坐标拐点幅度大，Y不大,计算线条X中心位置，Y取平均值
			return caclXCenter(xArrays, yArrays);

		} else if (yShift > X_Y_LOCATION_MAXSHIFT
				&& xShift < X_Y_LOCATION_MAXSHIFT) {
			// Y坐标拐点幅度大，X不大,计算线条Y中心位置，X取平均值
			return caclYCenter(xArrays, yArrays);

		} else if (yShift > X_Y_LOCATION_MAXSHIFT
				&& xShift > X_Y_LOCATION_MAXSHIFT) {
			// XY拐点幅度都比较大
			return caclCenterPoint(pointList);
		}

		// 如果没有计算中心点
		return null;

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
		for (int i = 0; i < pointList.size() - 1; i++) {
			Point pointA = pointList.get(i);
			Point pointB = pointList.get(i + 1);

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
		Float xCenter = xArrays[0] + (xArrays[xArrays.length - 1] - xArrays[0])
				/ 2;
		Float tempV = 0.0f;
		for (int i = 0; i < yArrays.length; i++) {
			tempV += yArrays[i];
		}
		Float y = tempV / yArrays.length;
		return new Point(xCenter, y);
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
		for (int i = 0; i < xArrays.length; i++) {
			tempV += xArrays[i];
		}
		Float x = tempV / xArrays.length;
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
