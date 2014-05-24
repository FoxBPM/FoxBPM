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
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 用来生成引擎配置文件的ZIP包用
 * 一般在服务启动时生成
 * @author ych
 *
 */
public class FlowResourceService {
	
	public static Logger log = LoggerFactory.getLogger(FlowResourceService.class);
	public static final String fileName = "tmpConnectorZipFile.zip";
	
	private List<IZipGenerator> generators = new ArrayList<IZipGenerator>();
	
	public FlowResourceService() {
		generators.add(new ConnectorGenerator());
		generators.add(new FoxBpmCfgGenerator());
		generators.add(new TemplateGenerator());
	}
	public void generateFlowResouceZipFile(){
		ZipOutputStream out = null;
		log.info("开始生成connector的临时文件...");
		try{
			String systemPath = this.getClass().getResource("/").getPath();
			String tmpFilePath = systemPath + File.separator + fileName;
			File outFile = new File(tmpFilePath);
			if(outFile.exists()){
				outFile.delete();
			}
			FileOutputStream fileOutStream = new FileOutputStream(outFile);
			out = new ZipOutputStream(fileOutStream);
			for(IZipGenerator generator : generators){
				generator.generate(out);
			}
		}catch(Exception ex){
			log.error("生成flowResouceZip.zip文件失败", ex);
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
