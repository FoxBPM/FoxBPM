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
package org.foxbpm.rest.service.api.identity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.foxbpm.engine.IdentityService;
import org.foxbpm.engine.ProcessEngine;
import org.foxbpm.engine.impl.cache.CacheUtil;
import org.foxbpm.engine.impl.entity.UserEntity;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.rest.common.api.AbstractRestResource;
import org.foxbpm.rest.common.api.FoxBpmUtil;
import org.restlet.ext.fileupload.RestletFileUpload;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 常量类
 * 
 * @author yangguangftlp
 * @date 2014年8月26日
 */
public class UserPictureResource extends AbstractRestResource {
	private static final Logger LOG = LoggerFactory.getLogger(UserPictureResource.class);
	@Put
	@Post
	public void update(Representation multipartForm) throws Exception {
		String path = getPath();
		LOG.debug("开始处理上传资源...");
		// 获取参数
		String userId = getAttribute("userId");
		Representation a = getRequest().getEntity();
		System.out.println((multipartForm == a));
		getRequest();
		// 创建上传文件的解析工厂
		DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
		RestletFileUpload fileUpload = new RestletFileUpload(fileItemFactory);
		if (null != multipartForm) {
			// 获取引擎
			ProcessEngine processEngine = FoxBpmUtil.getProcessEngine();
			// 获取身份服务
			IdentityService identityService = processEngine.getIdentityService();
			UserEntity userEntity = identityService.getUser(userId);
			List<FileItem> fileItems = fileUpload.parseRepresentation(multipartForm);
			String fileName = null;
			String imgName = null;
			for (FileItem fileItem : fileItems) {
				fileName = fileItem.getName();
				if (StringUtil.isEmpty(fileName)) {
					continue;
				}
				LOG.debug("Save image ... " + fileName);
				/* 新建一个图片文件 */
				String extName = fileName.substring(fileName.lastIndexOf("."));
				createDir(path);
				imgName = userId + extName;
				deleteFile(new File(path + userEntity.getImage()));
				File file = new File(path + imgName);
				createFile(file);
				fileItem.write(file);
			}
			userEntity = new UserEntity(userId);
			userEntity.setImage(imgName);
			identityService.updateUser(userEntity);
			CacheUtil.getIdentityCache().remove("user_" + userEntity.getUserId());
		}
	}
	private void createDir(String path) {
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
	}
	
	@Get
	public InputStream getResource() throws FileNotFoundException {
		String path = getPath();
		String userId = getAttribute("userId");
		IdentityService identityService = FoxBpmUtil.getProcessEngine().getIdentityService();
		UserEntity userEntity = identityService.getUser(userId);
		// 获取图片
		InputStream in = null;
		File file = null;
		if (null != userEntity) {
			file = new File(path + userEntity.getImage());
		}
		if (null != file && file.exists()) {
			in = new FileInputStream(file);
		} else {
			// 提供默认图像
			file = new File(path + "sunny-big.png");
			in = new FileInputStream(file);
		}
		return in;
	}
	
	/**
	 * 删除已经存在的资源
	 * 
	 * @param file
	 */
	private void deleteFile(File file) {
		if (file.exists()) {
			file.delete();
		}
	}
	/**
	 * 创建文件
	 * 
	 * @param file
	 * @throws IOException
	 */
	private void createFile(File file) throws IOException {
		if (!file.exists()) {
			File parentFile = file.getParentFile();
			// 如果父目录不存在 需要创建
			if (!parentFile.exists()) {
				parentFile.mkdirs();
			}
			file.createNewFile();
		}
	}
	private String getPath() {
		return this.getClass().getResource("/").getPath().replace("/WEB-INF/classes", "/img");
	}
}
