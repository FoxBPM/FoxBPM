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
package org.foxbpm.engine.impl.cmd;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.foxbpm.engine.exception.FoxBPMIllegalArgumentException;
import org.foxbpm.engine.impl.entity.ResourceEntity;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;

/**
 * 根据发布号和资源名称获取资源流
 * @author ych
 *
 */
public class GetResouceByDeployIdAndNameCmd implements Command<InputStream> {

	private String deployId;
	private String resourceName;
	public GetResouceByDeployIdAndNameCmd(String deployId,String resourceName){
		this.deployId = deployId;
		this.resourceName = resourceName;
	}
	
	 
	public InputStream execute(CommandContext commandContext) {
		if(deployId == null || resourceName == null){
			throw new FoxBPMIllegalArgumentException("查询资源名称时参数错误：deployId"+deployId+";ResourceName"+resourceName);
		}
		ResourceEntity resourceEntity=commandContext.getResourceManager().selectResourceByDeployIdAndName(deployId, resourceName);
		InputStream inputStream = new ByteArrayInputStream(resourceEntity.getBytes()); 
		return inputStream;
	}

}
