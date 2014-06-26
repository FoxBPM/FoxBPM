package org.foxbpm.rest.service.api.config;

import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.foxbpm.engine.exception.FoxBPMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StyleGenerator implements IZipGenerator {
	Logger log = LoggerFactory.getLogger(StyleGenerator.class);
	@Override
	public void generate(ZipOutputStream out) {
		log.debug("开始处理style.xml文件");
		try{
			String path = "config/style.xml";
			
			InputStream is = this.getClass().getClassLoader().getResourceAsStream(path);
			String tmpEntryName = "coreconfig/style.xml";
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
			log.debug("处理style.xml文件完毕");
		}catch(Exception ex){
			log.error("解析style.xml文件失败！生成zip文件失败！");
			throw new FoxBPMException("解析style.xml文件失败",ex);
		}
	}

}
