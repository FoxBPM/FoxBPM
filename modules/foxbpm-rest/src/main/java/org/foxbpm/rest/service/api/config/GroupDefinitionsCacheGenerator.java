package org.foxbpm.rest.service.api.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.identity.GroupDefinition;
import org.foxbpm.rest.common.api.FoxBpmUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GroupDefinitionsCacheGenerator implements IZipGenerator {

	private static Logger log = LoggerFactory.getLogger(GroupDefinitionsCacheGenerator.class);
	 
	public void generate(ZipOutputStream out) {
		log.debug("开始处理GroupDefinitions.data...");
		try{
			List<GroupDefinition> groupDefinitions = FoxBpmUtil.getProcessEngine().getIdentityService().getAllGroupDefinitions();
			Map<String,Object> resultMap = new HashMap<String, Object>();
			resultMap.put("data", groupDefinitions);
			ObjectMapper objectMapper = new ObjectMapper();
			JsonGenerator jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(out, JsonEncoding.UTF8);
			String tmpEntryName = "cache/allGroupDefinitions.data";
			ZipEntry zipEntry = new ZipEntry(tmpEntryName);
			zipEntry.setMethod(ZipEntry.DEFLATED);// 设置条目的压缩方式
			out.putNextEntry(zipEntry);
			jsonGenerator.writeObject(resultMap);
			out.closeEntry();
			log.debug("处理GroupDefinitions.data文件完毕");
		}catch(Exception ex){
			log.error("解析GroupDefinitions.data文件失败！生成zip文件失败！");
			throw new FoxBPMException("解析GroupDefinitions.data文件失败",ex);
		}
	}

}
