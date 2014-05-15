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
package org.foxbpm.rest.service.api.connector;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 连接器管理服务
 * 用来创建连接器资源文件的压缩包，供设计器请求返回
 * @author ych
 *
 */
public class ConnectorService {
	public static Logger log = LoggerFactory.getLogger(ConnectorService.class);
	public static final String fileName = "tmpConnectorZipFile.zip";
	
	public static void genarateConnectorZipFile(){
		ZipOutputStream out = null;
		log.info("开始生成connector的临时文件...");
		try{
			String systemPath = ConnectorService.class.getResource("/").getPath();
			String tmpFilePath = systemPath + File.separator + fileName;
			Map<String,String> pathMap = new HashMap<String,String>();
			pathMap.put("flow", "org/foxbpm/connector");
			pathMap.put("conf", "config");
			File outFile = new File(tmpFilePath);
			if(outFile.exists()){
				outFile.delete();
			}
			FileOutputStream fileOutStream = new FileOutputStream(outFile);
			out = new ZipOutputStream(fileOutStream);
			for(String key : pathMap.keySet()){
				String dirPath = pathMap.get(key);
				URL url = ConnectorService.class.getClassLoader().getResource(dirPath);
				if(url == null){
					log.warn("位置:" + dirPath + " 不存在，跳过不处理");
					continue;
				}
				String urlStr = url.toString();
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
						int size = 1024;
						InputStream is = jarFile.getInputStream(entry);
						String tmpEntryName = key + File.separator + name.substring(dirPath.length()+1);
						ZipEntry zipEntry = new ZipEntry(tmpEntryName);
						zipEntry.setMethod(ZipEntry.DEFLATED);// 设置条目的压缩方式
						out.putNextEntry(zipEntry);
						int n = 0;
						byte b[] = new byte[size];
						while((n=is.read(b)) != -1){
							out.write(b , 0 , n);
						}
						out.closeEntry();
						is.close();
					}
				}
				log.debug("位置：" + dirPath + " 处理完毕");
			}
			log.info("成功生成connector临时文件...");
		}catch(Exception ex){
			log.error("生成connector失败", ex);
		}finally{
			if(out != null){
				try {
					out.close();
				} catch (IOException e) {
					log.error("IO关闭失败", e);
				}
			}
		}
	}

}
