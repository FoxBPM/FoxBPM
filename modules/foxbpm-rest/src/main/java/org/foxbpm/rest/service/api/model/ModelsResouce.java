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
import java.util.HashMap;
import java.util.Map;

import org.foxbpm.engine.ModelService;
import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.exception.FoxBPMObjectNotFoundException;
import org.foxbpm.engine.impl.util.FileUtil;
import org.foxbpm.engine.repository.DeploymentBuilder;
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

//	private static final String targetPath = "D:\\TMP";
	private static final String SEP = "-";
	private static final String PREFIX_ADD = "insert";
	private static final String PREFIX_UPDATE = "update";
	private static Logger log = LoggerFactory.getLogger(ModelsResouce.class);
	@Post
	public String deploy(Representation entity){
		FileOutputStream fileOutputStream  = null;
		Map<String,InputStream> resourceMap = new HashMap<String, InputStream>();
		try {
			ModelService modelService = FoxBpmUtil.getProcessEngine().getModelService();
			File file = File.createTempFile(System.currentTimeMillis() + "flowres", ".zip");
			if(file.exists()){
				file.delete();
			}
			fileOutputStream = new FileOutputStream(file);
			entity.write(fileOutputStream);
			String targetPath = this.getClass().getClassLoader().getResource("/").getPath();
			targetPath = targetPath+File.separator+"Temp";
			System.out.println(targetPath);
			FileUtil.unZip(file.getPath(),targetPath);
			File modelsPath = new File(targetPath);
			for(File tmpFile : modelsPath.listFiles()){
				if(tmpFile.isDirectory()){
					DeploymentBuilder deploymentBuilder = modelService.createDeployment();
					String fileName = tmpFile.getName();
					if(fileName.indexOf(SEP) == -1){
						throw new FoxBPMException("上传文件夹内容格式不正确");
					}
					//解析文件夹名，获得对应信息  如“insert-processExpens-1”  insert：操作码，processExpens：流程key,1：流程版本
					String operation = fileName.substring(0, fileName.indexOf(SEP));
					String processKey = fileName.substring(fileName.indexOf(SEP)+1, fileName.lastIndexOf(SEP));
					int version = Integer.parseInt(fileName.substring(fileName.lastIndexOf(SEP)+1));
					File [] files = tmpFile.listFiles();
					for(File t : files){
						InputStream input =  new FileInputStream(t);
						//放到map中，用完之后一一关闭
						resourceMap.put(t.getName(), input);
						deploymentBuilder.addInputStream(t.getName(),input, version);
					}
					if(PREFIX_ADD.equals(operation)){
						log.debug("发布--发布"+resourceMap);
						deploymentBuilder.deploy();
					}else if(PREFIX_UPDATE.equals(operation)){
						ProcessDefinition processDefinition = null;
						try{
							processDefinition = modelService.getProcessDefinition(processKey, version);
							String deploymentId = processDefinition.getDeploymentId();
							deploymentBuilder.updateDeploymentId(deploymentId);
							deploymentBuilder.deploy();
						}catch(FoxBPMObjectNotFoundException ex){
							deploymentBuilder.deploy();
						}
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
		}finally{
			if(fileOutputStream != null){
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					log.error("关闭流失败", e);
				}
			}
			for(String name : resourceMap.keySet()){
				InputStream is = resourceMap.get(name);
				if(is != null){
					try {
						is.close();
					} catch (IOException e) {
						log.error("关闭流失败", e);
					}
				}
			}
		}
		return null;
	}
	
}
