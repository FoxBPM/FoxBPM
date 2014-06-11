package org.foxbpm.engine.impl.svg;

import org.foxbpm.engine.impl.svg.factory.AbstractFlowNodeSVGFactory;
import org.foxbpm.engine.impl.svg.factory.TaskSVGFactory;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.XMLFilterImpl;

public class test {

	public static void main(String[] args) {

		try {
			AbstractFlowNodeSVGFactory factory = AbstractFlowNodeSVGFactory
					.createSVGFactory(SVGTemplateNameConstant.TEMPLATE_ACTIVITY_TASK);
//			String svgString = factory
//					.createSVGString(SVGTypeNameConstant.ACTIVITY_BUSINESSRULETASK_SEQUENTIAL);
//			System.out.println(svgString);
		} catch (Exception e) {

		}
	}
}
