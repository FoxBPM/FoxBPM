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

import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.foxbpm.engine.exception.FoxBPMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * foxbpm.cfg.xml文件zip生成器 
 * 最终结构：flowResouce/coreconfig/foxbpm.cfg.xml
 * @author ych
 *
 */
public class FoxBpmCfgGenerator implements IZipGenerator {

	Logger log = LoggerFactory.getLogger(FoxBpmCfgGenerator.class);
	@Override
	public void generate(ZipOutputStream out) {
		log.debug("开始处理foxbpm.cfg.xml文件");
		try{
			String path = "config/foxbpm.cfg.xml";
			
			InputStream is = this.getClass().getClassLoader().getResourceAsStream(path);
			String tmpEntryName = "coreconfig/foxbpm.cfg.xml";
			ZipEntry zipEntry = new ZipEntry(tmpEntryName);
			zipEntry.setMethod(ZipEntry.DEFLATED);// 设置条目的压缩方式
			out.putNextEntry(zipEntry);
			int n = 0;
			int size = 1024;
			byte b[] = new byte[size];
			while((n=is.read(b)) != -1){
				out.write(b , 0 , n);
			}
			out.closeEntry();
			is.close();
			log.debug("处理foxbpm.cfg.xml文件完毕");
		}catch(Exception ex){
			log.error("处理foxbpm.cfg.xml文件失败！生成zip文件失败！",ex);
			throw new FoxBPMException("解析foxbpm.cfg.xml文件失败",ex);
		}
		
	}

}
