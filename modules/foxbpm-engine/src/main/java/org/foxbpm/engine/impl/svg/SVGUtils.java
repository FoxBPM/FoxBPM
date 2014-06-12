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
package org.foxbpm.engine.impl.svg;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.impl.svg.vo.GVO;
import org.foxbpm.engine.impl.svg.vo.SvgVO;

/**
 * SVG工具类
 * 
 * @author MAENLIANG
 * @date 2014-06-10
 */
public final class SVGUtils {
	/**
	 * GVO对象需要添加到其他的集合中，所以要克隆，避免产生问题
	 * 
	 * @param gVo
	 *            原对象
	 * @return clone之后的对象
	 */
	public static GVO cloneGVO(GVO gVo) {
		return (GVO) clone(gVo);
	}

	/**
	 * SvgVO模板对象需要多次引用，所以要克隆，避免产生问题
	 * 
	 * @param SvgVO
	 *            原对象
	 * @return clone之后的对象
	 */
	public static SvgVO cloneSVGVo(SvgVO svgVo) {
		return (SvgVO) clone(svgVo);
	}

	/**
	 * 克隆对象
	 * 
	 * @param object
	 *            原对象
	 * @return 目标对象
	 */
	public static Object clone(Object object) {
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
