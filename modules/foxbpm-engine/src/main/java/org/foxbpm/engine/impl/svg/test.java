package org.foxbpm.engine.impl.svg;

import org.foxbpm.engine.impl.svg.factory.AbstractSVGFactory;
import org.foxbpm.engine.impl.svg.factory.TaskSVGFactory;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.XMLFilterImpl;

public class test {

	public static void main(String[] args) {

		try {
			AbstractSVGFactory factory = AbstractSVGFactory
					.createSVGFactory(SVGTemplateNameConstant.TEMPLATE_ACTIVITY_TASK);
			String svgString = factory
					.createSVGString(SVGTypeNameConstant.ACTIVITY_BUSINESSRULETASK_PARALLEL);
			System.out.println(svgString);
		} catch (Exception e) {

		}
	}
}
