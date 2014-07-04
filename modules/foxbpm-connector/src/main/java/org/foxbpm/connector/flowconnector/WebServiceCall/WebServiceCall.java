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

import org.foxbpm.engine.execution.ConnectorExecutionContext;
import org.foxbpm.engine.impl.connector.FlowConnectorHandler;

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