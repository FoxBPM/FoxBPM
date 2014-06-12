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
package org.foxbpm.web.service.impl;

import java.io.IOException;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.apache.commons.fileupload.FileItem;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.engine.repository.DeploymentBuilder;
import org.foxbpm.web.common.exception.FoxbpmWebException;
import org.foxbpm.web.service.interfaces.IFlowManageService;
import org.springframework.stereotype.Service;

/**
 * @author yangguangftlp
 * @date 2014年6月12日
 */
@Service("flowManageServiceImpl")
public class FlowManageServiceImpl extends AbstWorkFlowService implements IFlowManageService {

	@Override
	public void deployByZip(Map<String, Object> params) {
		ZipInputStream zipInputStream = null;
		try {
			FileItem file = (FileItem) params.get("processFile");
			if (null != file) {
				zipInputStream = new ZipInputStream(file.getInputStream());
				String deploymentId = StringUtil.getString(params.get("deploymentId"));
				DeploymentBuilder deploymentBuilder = modelService.createDeployment();
				deploymentBuilder.addZipInputStream(zipInputStream);
				// 有deploymentID则为更新，否则为新增
				if (StringUtil.isNotEmpty(deploymentId)) {
					deploymentBuilder.updateDeploymentId(deploymentId);
				}
				deploymentBuilder.deploy();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new FoxbpmWebException(e.getMessage(), "", e);
		} finally {
			if (null != zipInputStream) {
				try {
					zipInputStream.close();
				} catch (IOException e) {
					throw new FoxbpmWebException(e.getMessage(), "", e);
				}
			}
		}
	}

}
