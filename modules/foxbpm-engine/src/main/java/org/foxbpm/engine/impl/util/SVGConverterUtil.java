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
 * @author yangguangftlp
 */
package org.foxbpm.engine.impl.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.foxbpm.engine.exception.FoxBPMException;

/**
 * svg 转换操作工具类
 * 
 * @author yangguangftlp
 * @date 2014年7月17日
 */
public class SVGConverterUtil {

	/** 实例 */
	private static SVGConverterUtil instance;

	private SVGConverterUtil() {
	}

	/**
	 * 获取实例
	 * 
	 * @return 返回实例
	 */
	public static SVGConverterUtil getInstance() {
		if (null == instance) {
			synchronized (SVGConverterUtil.class) {
				if (null == instance) {
					instance = new SVGConverterUtil();
				}
			}
		}
		return instance;
	}

	/**
	 * 将svg转换成png svgCode参数不能为空
	 * 
	 * @param svgCode
	 *            svg 内容
	 * @throws FoxBPMException
	 * @return 返回转换后的png 字节数据
	 */
	public byte[] convertToPng(String svgCode) {
		// 创建内存输入流
		ByteArrayInputStream in = null;
		// 创建内存输出流
		ByteArrayOutputStream out = null;
		try {
			if (StringUtil.isEmpty(svgCode)) {
				throw ExceptionUtil.getException("转换png内容为空");
			}
			PNGTranscoder transcoder = new PNGTranscoder();
			in = new ByteArrayInputStream(svgCode.getBytes("utf-8"));
			out = new ByteArrayOutputStream(1024);
			transcoder.transcode(new TranscoderInput(in), new TranscoderOutput(out));
			out.flush();
			return out.toByteArray();
		} catch (Exception e) {
			throw ExceptionUtil.getException("png转换失败",e);
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					throw ExceptionUtil.getException("png输入流关闭失败",e);
				}
			}
			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {
					throw ExceptionUtil.getException("png输出流关闭失败",e);
				}
			}
		}
	}
}
