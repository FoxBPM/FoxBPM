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

import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 连接器zip文件生成器
 * 解析foxbpm.cfg.xml中配置的连接器包，扫描包下所有非java文件
 * 最终为flowResouce/connector/flowconnector结构
 * 用来给设计器同步connector配置用
 * @author ych
 *
 */
public class ConnectorGenerator implements IZipGenerator {

	Logger log = LoggerFactory.getLogger(ConnectorGenerator.class);
	public void generate(ZipOutputStream out) {
//		log.debug("开始处理connector。。.");
//		try{
//			Map<String,Map<String,String>> pathMap = new HashMap<String,Map<String,String>>();
//			ProcessEngineConfigurationImpl processEngineConfigurationImpl = ProcessEngineManagement.getDefaultProcessEngine().getProcessEngineConfiguration();
//			
//			ResourcePathConfig resourcePathConfig = processEngineConfigurationImpl.getResourcePathConfig();
//			List<ResourcePath> resourcePaths = resourcePathConfig.getResourcePath();
//			
//			if(resourcePaths != null){
//				for(ResourcePath path : resourcePaths){
//					if("flowConnector".equals(path.getType()) || "actorConnector".equals(path.getType())){
//						Map<String,String> pathList = pathMap.get(path.getProjectName());
//						if(pathList == null){
//							pathList = new HashMap<String, String>();
//							pathMap.put(path.getProjectName(), pathList);
//						}
//						pathList.put(path.getType(), path.getSrc());
//					}
//				}
//			}
//			for(String key : pathMap.keySet()){
//				Map<String,String> pathList = pathMap.get(key);
//				if(pathList ==null){
//					continue;
//				}
//				
//				for(String tmp : pathList.keySet()){
//					String dirPath = pathList.get(tmp);
//					URL url = this.getClass().getClassLoader().getResource(dirPath);
//					if(url == null){
//						log.warn("位置:" + dirPath + " 不存在，跳过不处理");
//						continue;
//					}
//					String urlStr = url.toString();
//					String jarPath = urlStr.substring(0, urlStr.indexOf("!/") + 2);
//					URL jarURL = new URL(jarPath);
//					JarURLConnection jarCon = (JarURLConnection) jarURL.openConnection();
//					JarFile jarFile = jarCon.getJarFile();
//					
//					Enumeration<JarEntry> jarEntrys = jarFile.entries();
//					while (jarEntrys.hasMoreElements()) {
//						JarEntry entry = jarEntrys.nextElement();
//						String name = entry.getName();
//						if(name.startsWith(dirPath) && !entry.isDirectory() && !name.endsWith(".class")){
//							int size = 1024;
//							InputStream is = jarFile.getInputStream(entry);
//							String tmpEntryName = "connector/" + key + "/" + tmp + "/" + name.substring(dirPath.length());
//							ZipEntry zipEntry = new ZipEntry(tmpEntryName);
//							zipEntry.setMethod(ZipEntry.DEFLATED);// 设置条目的压缩方式
//							out.putNextEntry(zipEntry);
//							int n = 0;
//							byte b[] = new byte[size];
//							while((n=is.read(b)) != -1){
//								out.write(b , 0 , n);
//							}
//							out.closeEntry();
//							is.close();
//						}
//					}
//					log.debug("位置：" + dirPath + " 处理完毕");
//				}
//			}
//			log.debug("处理connector完毕!");
//		}catch(Exception ex){
//			log.error("转换connector时出错",ex);
//			throw new FoxBPMException("转换connector时出错", ex);
//		}
	}
}
