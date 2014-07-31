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
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.foxbpm.engine.ModelService;
import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.exception.FoxBPMObjectNotFoundException;
import org.foxbpm.engine.impl.util.DBUtils;
import org.foxbpm.engine.impl.util.FileUtil;
import org.foxbpm.engine.repository.DeploymentBuilder;
import org.foxbpm.engine.repository.ProcessDefinition;
import org.foxbpm.rest.common.api.AbstractRestResource;
import org.foxbpm.rest.common.api.FoxBpmUtil;
import org.restlet.data.Status;
import org.restlet.ext.fileupload.RestletFileUpload;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 流程定义批量操作
 * 主要给EMAP平台作为批量发布用
 * @author ych
 */
public class ModelsResouce extends AbstractRestResource{

//	private static final String targetPath = "D:\\TMP";
	private static final String SEP = "-";
	private static final String PREFIX_ADD = "New";
	private static final String PREFIX_UPDATE = "Update";
	private static final String PREFIX_DELETE = "Delete";
	private static Logger log = LoggerFactory.getLogger(ModelsResouce.class);
	@Post
	public String deploy(Representation entity){
		FileOutputStream fileOutputStream  = null;
		final Map<String,InputStream> resourceMap = new HashMap<String, InputStream>();
		InputStream is = null;
		try {
			File file = File.createTempFile(System.currentTimeMillis() + "flowres", ".zip");
			
			fileOutputStream = new FileOutputStream(file);
		    DiskFileItemFactory factory = new DiskFileItemFactory();  
	        RestletFileUpload upload = new RestletFileUpload(factory); 
	        List<FileItem> items = null;  
	        try {  
	            items = upload.parseRepresentation(entity);  
	        } catch (FileUploadException e) {  
	           throw new FoxBPMException("上传文件格式不正确");
	        }
	        FileItem fileItem = items.get(0);
	        fileItem.write(file);
	        
	        String sysTemp = System.getProperty("java.io.tmpdir");  
	        final File targetDir = new File(sysTemp + File.separator + "ModelsTempFile");  
	        targetDir.mkdirs();  
			FileUtil.unZip(file.getPath(),targetDir.getPath());
			
			PlatformTransactionManager transactionManager = new DataSourceTransactionManager(DBUtils.getDataSource());
			TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					try{
						ModelService modelService = FoxBpmUtil.getProcessEngine().getModelService();
						for(File tmpFile : targetDir.listFiles()){
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
									deploymentBuilder.deploy();
								}else if(PREFIX_UPDATE.equals(operation)){
									ProcessDefinition processDefinition = null;
									processDefinition = modelService.getProcessDefinition(processKey, version);
									if(processDefinition != null){
										String deploymentId = processDefinition.getDeploymentId();
										deploymentBuilder.updateDeploymentId(deploymentId);
									}
									deploymentBuilder.deploy();
								}else if(PREFIX_DELETE.equals(operation)){
									try{//查询是否已经存在，已存在，则更新，否则新增
										ProcessDefinition processDefinitionNew = modelService.getProcessDefinition(processKey, version);
										String deploymentId = processDefinitionNew.getDeploymentId();
										modelService.deleteDeployment(deploymentId);
									}catch(FoxBPMObjectNotFoundException ex){
										//此异常代表数据库中不存在此流程定义,则不进行删除操作
										log.warn("数据库中不存在key:"+processKey+"，version:"+version+"的流程定义，忽略此删除操作");
									}
								}else if("NotModify".equals(operation)){
									log.debug(processKey + "未修改，不做处理！");
								}else{
									throw new FoxBPMException("未识别的操作码："+operation);
								}
							}
						}
					}catch(Exception ex){
						if(ex instanceof FoxBPMException){
							throw (FoxBPMException)ex;
						}else{
							throw new FoxBPMException("执行保存错误", ex);
						}
					}
				}
			});
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
				InputStream isTmp = resourceMap.get(name);
				if(isTmp != null){
					try {
						isTmp.close();
					}catch (IOException e) {
						log.error("关闭流失败", e);
					}
				}
			}
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	@Get
	public String test(){
		return "success";
	}
}
