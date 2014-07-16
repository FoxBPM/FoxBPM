package org.foxbpm.engine.impl.diagramview.svg.builder;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.foxbpm.engine.impl.diagramview.svg.vo.GVO;
import org.foxbpm.engine.impl.diagramview.svg.vo.PolygonVO;
import org.foxbpm.engine.impl.diagramview.svg.vo.SvgVO;

/**
 * 
 * 
 * ServiceTaskSVGBuilder
 * 
 * MAENLIANG 2014年7月16日 下午3:54:23
 * 
 * @version 1.0.0
 * 
 */
public class ServiceTaskSVGBuilder extends TaskSVGBuilder {

	private PolygonVO polygonAVO = null;
	PolygonVO polygonBVO = null;
	public ServiceTaskSVGBuilder(SvgVO svgVo) {
		super(svgVo);
		List<GVO> getgVoList = svgVo.getgVo().getgVoList();
		Iterator<GVO> iterator = getgVoList.iterator();
		GVO next = null;
		while (iterator.hasNext()) {
			next = iterator.next();
			if (StringUtils.equals(next.getId(), "serviceTask")) {
				break;
			}
		}
		if (next != null) {
			List<PolygonVO> polygonList = next.getPolygonList();
			polygonAVO = polygonList.get(0);
			polygonBVO = polygonList.get(1);
		}

	}
	@Override
	public void setFill(String fill) {
		super.setFill(fill);
		if (StringUtils.isBlank(fill)) {
			fill = FILL_DEFAULT;
		}
		this.polygonAVO.setFill(COLOR_FLAG + fill);
		this.polygonBVO.setFill(COLOR_FLAG + fill);
	}

}
