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
 * @author ych
 */
package org.foxbpm.engine.impl.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.URL;

import org.foxbpm.engine.exception.FoxBPMException;

public class IoUtil {

	public static byte[] readInputStream(InputStream inputStream, String inputStreamName) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[16 * 1024];
		try {
			int bytesRead = inputStream.read(buffer);
			while (bytesRead != -1) {
				outputStream.write(buffer, 0, bytesRead);
				bytesRead = inputStream.read(buffer);
			}
		} catch (Exception e) {
			throw new FoxBPMException("couldn't read input stream " + inputStreamName, e);
		}
		return outputStream.toByteArray();
	}

	public static String readFileAsString(String filePath) {
		File file = getFile(filePath);
		byte[] buffer = new byte[(int) file.length()];
		BufferedInputStream inputStream = null;
		try {
			inputStream = new BufferedInputStream(new FileInputStream(file));
			inputStream.read(buffer);
		} catch (Exception e) {
			throw new FoxBPMException("Couldn't read file " + filePath + ": " + e.getMessage());
		} finally {
			IoUtil.closeSilently(inputStream);
		}
		return new String(buffer);
	}

	public static File getFile(String filePath) {
		URL url = IoUtil.class.getClassLoader().getResource(filePath);
		try {
			return new File(url.toURI());
		} catch (Exception e) {
			throw new FoxBPMException("Couldn't get file " + filePath + ": " + e.getMessage());
		}
	}

	public static void writeStringToFile(String content, String filePath) {
		BufferedOutputStream outputStream = null;
		try {
			outputStream = new BufferedOutputStream(new FileOutputStream(getFile(filePath)));
			outputStream.write(content.getBytes());
			outputStream.flush();
		} catch (Exception e) {
			throw new FoxBPMException("Couldn't write file " + filePath, e);
		} finally {
			IoUtil.closeSilently(outputStream);
		}
	}

	/**
	 * Closes the given stream. The same as calling {@link InputStream#close()},
	 * but errors while closing are silently ignored.
	 */
	public static void closeSilently(InputStream inputStream) {
		try {
			if (inputStream != null) {
				inputStream.close();
			}
		} catch (IOException ignore) {
			// Exception is silently ignored
		}
	}

	/**
	 * Closes the given stream. The same as calling {@link OutputStream#close()}
	 * , but errors while closing are silently ignored.
	 */
	public static void closeSilently(OutputStream outputStream) {
		try {
			if (outputStream != null) {
				outputStream.close();
			}
		} catch (IOException ignore) {
			// Exception is silently ignored
		}
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
			ois = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
			cloneObject = ois.readObject();
		} catch (Exception e) {
			throw new FoxBPMException("SVG对象G节点克隆出现问题", e);
		} finally {
			try {
				if (bos != null) {
					bos.close();
				}
				if (oos != null) {
					oos.close();
				}
				if (ois != null) {
					ois.close();
				}
			} catch (Exception e) {
				throw new FoxBPMException("克隆之后关闭对象流时出现问题", e);
			}
		}
		
		return cloneObject;
	}
}
