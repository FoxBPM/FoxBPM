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
 * @author ych
 */
package org.foxbpm.rest.service.api.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.foxbpm.engine.exception.FoxBPMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TemplateGenerator implements IZipGenerator {

	Logger log = LoggerFactory.getLogger(TemplateGenerator.class);
	 
	public void generate(ZipOutputStream out) {
		log.debug("开始处理template...");
		try{
			String dirPath = "template";
			URL url = this.getClass().getClassLoader().getResource(dirPath);
			if(url == null){
				log.warn("template位置:" + dirPath + " 不存在，跳过不处理");
				return;
			}
			String urlStr = url.toString();
			if (!urlStr.startsWith("jar")){
				File dirFile = new File(url.getFile());
				Collection<File> files = FileUtils.listFiles(dirFile, null, true);
				for (File file : files){
					InputStream is = new FileInputStream(file);
					String tmpEntryName = dirPath + file.getPath().replace(dirFile.getPath(), "");
					writeZipFile(out, is, tmpEntryName);
				}
			}else{
				String jarPath = urlStr.substring(0, urlStr.indexOf("!/") + 2);
				URL jarURL = new URL(jarPath);
				JarURLConnection jarCon = (JarURLConnection) jarURL.openConnection();
				JarFile jarFile = jarCon.getJarFile();
				
				Enumeration<JarEntry> jarEntrys = jarFile.entries();
				while (jarEntrys.hasMoreElements()) {
					JarEntry entry = jarEntrys.nextElement();
					// 简单的判断路径，如果想做到想Spring，Ant-Style格式的路径匹配需要用到正则。
					String name = entry.getName();
					if(name.startsWith(dirPath) && !entry.isDirectory() && !name.endsWith(".class")){
						InputStream is = jarFile.getInputStream(entry);
						String tmpEntryName = dirPath + "/" + name.substring(dirPath.length()+1);
						writeZipFile(out, is, tmpEntryName);
					}
				}
			}
			log.debug("处理 template 完毕");
		}catch(Exception ex){
			log.error("转换template时出错",ex);
			throw new FoxBPMException("转换template时出错", ex);
		}
		
	}
	
	private void writeZipFile(ZipOutputStream out, InputStream is, String tmpEntryName) throws IOException{
		ZipEntry zipEntry = new ZipEntry(tmpEntryName);
		zipEntry.setMethod(ZipEntry.DEFLATED);// 设置条目的压缩方式
		out.putNextEntry(zipEntry);
		int n = 0;
		byte b[] = new byte[1024];
		while((n=is.read(b)) != -1){
			out.write(b , 0 , n);
		}
		out.closeEntry();
		is.close();
	}

}
