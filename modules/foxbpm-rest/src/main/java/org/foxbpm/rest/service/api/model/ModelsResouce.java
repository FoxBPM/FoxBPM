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
package org.foxbpm.rest.service.api.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.foxbpm.engine.ModelService;
import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.exception.FoxBPMObjectNotFoundException;
import org.foxbpm.engine.repository.ProcessDefinition;
import org.foxbpm.rest.common.api.AbstractRestResource;
import org.foxbpm.rest.common.api.FoxBpmUtil;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 流程定义批量操作
 * 主要给EMAP平台作为批量发布用
 * @author ych
 */
public class ModelsResouce extends AbstractRestResource{

	private static final String targetPath = "D:\\TMP";
	private static final String SEP = "-";
	private static final String PREFIX_ADD = "insert";
	private static final String PREFIX_UPDATE = "update";
	private static Logger log = LoggerFactory.getLogger(ModelsResouce.class);
	@Post
	public String deploy(Representation entity){
		FileOutputStream fileOutputStream  = null;
		try {
			ModelService modelService = FoxBpmUtil.getProcessEngine().getModelService();
			File file = File.createTempFile(System.currentTimeMillis() + "flowres", ".zip");
			if(file.exists()){
				file.delete();
			}
			fileOutputStream = new FileOutputStream(file);
			entity.write(fileOutputStream);
			
			unZip(file.getPath(),targetPath);
			
			File modelsPath = new File(targetPath + File.separator + "Test");
			
			for(File tmpFile : modelsPath.listFiles()){
				if(tmpFile.isDirectory()){
					String fileName = tmpFile.getName();
					String operation = fileName.substring(0, fileName.indexOf(SEP));
					String processKey = fileName.substring(fileName.indexOf(SEP)+1, fileName.lastIndexOf(SEP));
					int version = Integer.parseInt(fileName.substring(fileName.lastIndexOf(SEP)+1));
					
					System.out.println(tmpFile.getPath());
					System.out.println(processKey);
					System.out.println(operation);
					System.out.println(version);
					
					File [] files = tmpFile.listFiles();
					Map<String,InputStream> resourceMap = new HashMap<String, InputStream>();
					
					for(File t : files){
						System.out.println(t.getPath());
						resourceMap.put(t.getName(), new FileInputStream(t));
					}
					System.out.println(resourceMap);
					
					if(PREFIX_ADD.equals(operation)){
						modelService.deployByInputStream(resourceMap);
					}else if(PREFIX_UPDATE.equals(operation)){
						ProcessDefinition processDefinition = modelService.getProcessDefinition(processKey, version);
						if(processDefinition == null){
							throw new FoxBPMObjectNotFoundException("未查到 key: "+processKey +" version: "+version + " 的流程定义");
						}
						String deploymentId = processDefinition.getDeploymentId();
						modelService.updateByStreamMap(deploymentId, resourceMap);
					}else{
						throw new FoxBPMException("发布文件中不包含操作码");
					}
				}
			}
			
			setStatus(Status.SUCCESS_CREATED);
		} catch (Exception e) {
			if (e instanceof FoxBPMException) {
				throw (FoxBPMException) e;
			}
			throw new FoxBPMException(e.getMessage(), e);
		}		
		
		return null;
	}
	
	public static void main(String[] args) throws Exception {
		unZip("D:\\Test.zip","D:\\TMP");
	}
	
	public static void unZip(String zipfile, String destDir) {
		byte b[] = new byte[1024];
		OutputStream outputStream = null;
		InputStream inputStream = null;
		int length;
		try {
			ZipFile zipFile = new ZipFile(zipfile);
			Enumeration<? extends ZipEntry> enumeration = zipFile.entries();
			ZipEntry zipEntry = null;
			while (enumeration.hasMoreElements()) {
				zipEntry = enumeration.nextElement();
				File loadFile = new File(destDir + File.separator + zipEntry.getName());
				if (zipEntry.isDirectory()) {
					loadFile.mkdirs();
				} else {
					if (!loadFile.getParentFile().exists()) {
						loadFile.getParentFile().mkdirs();
					}
					outputStream = new FileOutputStream(loadFile);
					inputStream = zipFile.getInputStream(zipEntry);
					while ((length = inputStream.read(b)) > 0) {
						outputStream.write(b, 0, length);
					}
				}
			}
		} catch (IOException e) {
			throw new FoxBPMException("解压文件："+zipfile+"失败！", e);
		} finally {
			try {
				outputStream.close();
				inputStream.close();
			} catch (Exception e2) {
				log.warn("关闭输出流失败",e2);
			}
			
		}

	}
}
