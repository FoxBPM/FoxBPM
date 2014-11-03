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

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.foxbpm.engine.ProcessEngineManagement;
import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.impl.ProcessEngineConfigurationImpl;
import org.foxbpm.engine.impl.util.ReflectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 连接器zip文件生成器
 * 解析foxbpm.cfg.xml中配置的连接器包，扫描包下所有非java文件
 * 最终为flowResouce/connector/flowconnector结构
 * 用来给设计器同步connector配置用
 * @author ych
 *
 */
public class ConnectorGenerator implements IZipGenerator {

	Logger log = LoggerFactory.getLogger(ConnectorGenerator.class);
	
	private static final int SIZE = 1024;
	@SuppressWarnings("unchecked")
	private void parseNode(Element nodeElement , ZipOutputStream out,String type,String folderName) throws Exception{
		if(nodeElement != null){
			Iterator<Element> iterator = nodeElement.elements("node").iterator();
			while(iterator.hasNext()){
				Element nodeTmpElement = iterator.next();
				parseNode(nodeTmpElement,out,type,folderName);
			}
			
			iterator = nodeElement.elements("connector").iterator();
			while(iterator.hasNext()){
				Element tmp = iterator.next();
				parseConnector(tmp,out,type,folderName);
			}
		}
	}
	
	private void parseConnector(Element tmp,ZipOutputStream out,String type,String folderName) throws Exception{
		String id = tmp.attributeValue("id");
		String packageName = tmp.attributeValue("package");
		
		String connectorXmlName = "";
		if(type.equals("flowConnector")){
			connectorXmlName = "FlowConnector.xml";
		}else if(type.equals("actorConnector")){
			connectorXmlName = "ActorConnector.xml";
		}else{
			throw new FoxBPMException("不支持的连接器类型："+type);
		}
		
		String xmlFileName = packageName + "/" + connectorXmlName;
		String pngFileName = packageName + "/" + id + ".png";
		String xmlEntryName = folderName + "/" + id + "/" + connectorXmlName;
		String pngEntryName = folderName + "/" + id + "/" + id + ".png";
		
		InputStream xmlInputStream = ReflectUtil.getResourceAsStream(xmlFileName);
		generZip(xmlInputStream,xmlEntryName,out);
		
		InputStream pngInputStream = ReflectUtil.getResourceAsStream(pngFileName);
		if(pngInputStream == null){
			pngFileName = packageName + "/" + id + ".jpg";
			pngInputStream = ReflectUtil.getResourceAsStream(pngFileName);
		}
		generZip(pngInputStream,pngEntryName,out);
	}
	
	private void generZip(InputStream stream,String entryName,ZipOutputStream out) throws Exception{
		ZipEntry zipEntry = new ZipEntry(entryName);
		zipEntry.setMethod(ZipEntry.DEFLATED);// 设置条目的压缩方式
		out.putNextEntry(zipEntry);
		int n = 0;
		byte b[] = new byte[SIZE];
		while((n=stream.read(b)) != -1){
			out.write(b , 0 , n);
		}
		out.closeEntry();
		stream.close();
	}
	
	private void generatorConnector(String prefix,InputStream stream ,ZipOutputStream out) throws Exception{
		SAXReader reader = null;
		reader = new SAXReader();
		Document doc = reader.read(stream);
		generZip(stream, prefix+"/ConnectorMenu.xml", out);
		Element flowConnectorElement =doc.getRootElement().element("flowConnector");
		if(flowConnectorElement != null){
			parseNode(flowConnectorElement, out, "flowConnector",prefix + "/flowConnector");
		}
		Element actorConnectorElement = doc.getRootElement().element("actorConnector");
		if(actorConnectorElement != null){
			parseNode(actorConnectorElement, out, "actorConnector",prefix + "/actorConnector");
		}
	}
	
	
	public void generate(ZipOutputStream out) {
		ProcessEngineConfigurationImpl processEngineConfigurationImpl = ProcessEngineManagement.getDefaultProcessEngine().getProcessEngineConfiguration();
		
		InputStream stream = ReflectUtil.getResourceAsStream("org/foxbpm/connector/ConnectorMenu.xml");
		String connectorMenuPath = processEngineConfigurationImpl.getConnectorMenuPath();
		InputStream connectorStream = null;
		if(connectorMenuPath != null){
			connectorStream = ReflectUtil.getResourceAsStream(connectorMenuPath);
		}
		try {
			if(stream != null){
				generatorConnector("connector/defaultConnector",stream,out);
			}
			if(connectorStream != null){
				generatorConnector("connector/custormConnector",connectorStream,out);
			}
		} catch (Exception e) {
			log.error("同步连接器失败，失败原因："+e.getMessage(),e);
			throw new FoxBPMException("同步连接器失败，失败原因："+e.getMessage(),e);
		}finally{
			try {
				if(stream != null){
					stream.close();
				}
				if(connectorStream != null){
					stream.close();
				}
			} catch (IOException e) {
				log.error("生成连接器时流关闭失败，失败原因："+e.getMessage(),e);
			}
		}
	}
}
