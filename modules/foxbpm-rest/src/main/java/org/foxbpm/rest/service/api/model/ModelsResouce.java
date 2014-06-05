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

	private static final String targetPath = "D:\\TMP";
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
			unZip(file.getPath(),targetPath);
			File modelsPath = new File(targetPath);
			for(File tmpFile : modelsPath.listFiles()){
				if(tmpFile.isDirectory()){
					DeploymentBuilder deploymentBuilder = modelService.createDeployment();
					String fileName = tmpFile.getName();
					if(fileName.indexOf(SEP) == -1){
						throw new FoxBPMException("上传文件夹内容格式不正确");
					}
					String operation = fileName.substring(0, fileName.indexOf(SEP));
					String processKey = fileName.substring(fileName.indexOf(SEP)+1, fileName.lastIndexOf(SEP));
					int version = Integer.parseInt(fileName.substring(fileName.lastIndexOf(SEP)+1));
					File [] files = tmpFile.listFiles();
					for(File t : files){
						InputStream input =  new FileInputStream(t);
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
							log.debug("更新--更新"+resourceMap);
							String deploymentId = processDefinition.getDeploymentId();
							deploymentBuilder.updateDeploymentId(deploymentId);
							deploymentBuilder.deploy();
						}catch(FoxBPMObjectNotFoundException ex){
							deploymentBuilder.deploy();
							log.debug("更新--发布"+resourceMap);
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
					e.printStackTrace();
				}
			}
			for(String name : resourceMap.keySet()){
				InputStream is = resourceMap.get(name);
				if(is != null){
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return null;
	}
	
	public void unZip(String zipfile, String destDir) {
		byte b[] = new byte[1024];
		OutputStream outputStream = null;
		InputStream inputStream = null;
		
		int length;
		try {
			DeleteFolder(destDir);
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
					if(outputStream != null){
						outputStream.close();
					}
				}
			}
		} catch (IOException e) {
			throw new FoxBPMException("解压文件："+zipfile+"失败！", e);
		} finally {
			try {
				if(outputStream != null){
					outputStream.close();
				}
				if(inputStream != null){
					inputStream.close();
				}
			} catch (Exception e2) {
				log.warn("关闭输出流失败",e2);
			}
			
		}

	}
    
    /**
     *  根据路径删除指定的目录或文件，无论存在与否
     *@param sPath  要删除的目录或文件
     *@return 删除成功返回 true，否则返回 false。
     */
    public boolean DeleteFolder(String sPath) {
        File file = new File(sPath);
        // 判断目录或文件是否存在
        if (!file.exists()) {  // 不存在返回 false
            return true;
        } else {
            // 判断是否为文件
            if (file.isFile()) {  // 为文件时调用删除文件方法
                return deleteFile(sPath);
            } else {  // 为目录时调用删除目录方法
                return deleteDirectory(sPath);
            }
        }
    }
    
    /**
     * 删除单个文件
     * @param   sPath    被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }
    
    /**
     * 删除目录（文件夹）以及目录下的文件
     * @param   sPath 被删除目录的文件路径
     * @return  目录删除成功返回true，否则返回false
     */
    public boolean deleteDirectory(String sPath) {
        //如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        //如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        boolean flag = true;
        //删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            //删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) break;
            } //删除子目录
            else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) break;
            }
        }
        if (!flag) return false;
        //删除当前目录
        if (dirFile.delete()) {
            return true;
        } else {
            return false;
        }
    }
}
