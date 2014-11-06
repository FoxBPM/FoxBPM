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
package org.foxbpm.connector.flowconnector.WebServiceCall;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;

import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;

import org.foxbpm.engine.exception.FoxBPMConnectorException;
import org.foxbpm.engine.execution.ConnectorExecutionContext;
import org.foxbpm.engine.impl.connector.FlowConnectorHandler;
import org.foxbpm.engine.impl.util.StringUtil;

/**
 * WebService 调用
 * 
 * @author yangguangftlp
 * @date 2014年7月7日
 */
public class WebServiceCall implements FlowConnectorHandler {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -748190599446833382L;
	
	private java.lang.String namespaceURI;
	
	private java.lang.String serviceName;
	
	private java.lang.String portName;
	
	private java.lang.String endpointAddress;
	
	private java.lang.String request;
	
	private java.lang.String response;
	
	public void execute(ConnectorExecutionContext executionContext) throws Exception {
		if (StringUtil.isEmpty(namespaceURI)) {
			throw new FoxBPMConnectorException("连接器(执行一个Web服务)命名空间表达式为空!");
		}
		if (StringUtil.isEmpty(portName)) {
			throw new FoxBPMConnectorException("连接器(执行一个Web服务)端口名表达式为空!");
		}
		if (StringUtil.isEmpty(serviceName)) {
			throw new FoxBPMConnectorException("连接器(执行一个Web服务)服务名表达式为空!");
		}
		
		if (StringUtil.isEmpty(endpointAddress)) {
			throw new FoxBPMConnectorException("连接器(执行一个Web服务)端点地址表达式为空!");
		}
		if (StringUtil.isEmpty(request)) {
			throw new FoxBPMConnectorException("连接器(执行一个Web服务)请求内容表达式为空!");
		}
		
		QName portQName = new QName(namespaceURI, portName);
		Service service = Service.create(new QName(namespaceURI, serviceName));
		service.addPort(portQName, SOAPBinding.SOAP11HTTP_BINDING, endpointAddress);
		StreamSource xmlSource = new StreamSource(new StringReader(request));
		Dispatch<Source> dispatchSource = service.createDispatch(portQName, Source.class, Service.Mode.MESSAGE);
		Source source = dispatchSource.invoke(xmlSource);
		StreamResult result = new StreamResult(new ByteArrayOutputStream());
		Transformer trans = TransformerFactory.newInstance().newTransformer();
		trans.transform(source, result);
		ByteArrayOutputStream baos = (ByteArrayOutputStream) result.getOutputStream();
		response = new String(baos.toByteArray());
	}
	
	public void setNamespaceURI(java.lang.String namespaceURI) {
		this.namespaceURI = namespaceURI;
	}
	
	public void setServiceName(java.lang.String serviceName) {
		this.serviceName = serviceName;
	}
	
	public void setPortName(java.lang.String portName) {
		this.portName = portName;
	}
	
	public void setEndpointAddress(java.lang.String endpointAddress) {
		this.endpointAddress = endpointAddress;
	}
	
	public void setRequest(java.lang.String request) {
		this.request = request;
	}
	
	public java.lang.String getResponse() {
		return response;
	}
}