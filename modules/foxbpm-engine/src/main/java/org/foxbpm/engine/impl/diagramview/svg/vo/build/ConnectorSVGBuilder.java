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
 * @author MAENLIANG
 */
package org.foxbpm.engine.impl.diagramview.svg.vo.build;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.impl.diagramview.svg.vo.GVO;
import org.foxbpm.engine.impl.diagramview.svg.vo.PathVO;
import org.foxbpm.engine.impl.diagramview.svg.vo.SvgVO;

/**
 * 默认线条式样构造
 * 
 * @author MAENLIANG
 * @date 2014-06-10
 */
public class ConnectorSVGBuilder extends AbstractSVGBuilder {

	public static final String SEQUENCE_STROKEWIDTH_DEFAULT = "2";
	private static final String FILL_DEFAULT = "none";
	private static final String MOVETO_FLAG = "M";
	private static final String LINETO_FLAG = "L";
	private PathVO pathVo;

	public ConnectorSVGBuilder(SvgVO svgVo) {
		super(svgVo);
		List<GVO> gVoList = svgVo.getgVo().getgVoList();
		if (gVoList != null && gVoList.size() > 0) {
			Iterator<GVO> iterator = gVoList.iterator();
			while (iterator.hasNext()) {
				GVO next = iterator.next();
				if (StringUtils.equalsIgnoreCase(next.getId(), "edge")) {
					List<PathVO> pathVoList = next.getPathVoList();
					Iterator<PathVO> pathIter = pathVoList.iterator();
					while (pathIter.hasNext()) {
						PathVO tempPathVo = pathIter.next();
						if (StringUtils.equalsIgnoreCase(tempPathVo.getId(),
								BPMN_NODE_ID)) {
							this.pathVo = tempPathVo;
							return;
						}
					}

				}
			}
		}
		if (pathVo == null) {
			throw new FoxBPMException("线条元素初始化工厂时候报错，pathVo对象为空");
		}

	}

	/**
	 * 设置线条的拐点信息
	 */
	public void setWayPoints(String[] wayPointArray) {
		String tempD = "";
		for (int i = 0; i < wayPointArray.length; i++) {
			if (i != 0) {
				tempD = tempD + LINETO_FLAG + wayPointArray[i];
			} else {
				tempD = tempD + MOVETO_FLAG + wayPointArray[i];
			}

		}
		this.pathVo.setD(tempD);
	}

	@Override
	public void setStroke(String stroke) {
		if (StringUtils.isBlank(stroke)) {
			this.pathVo.setStroke(STROKE_DEFAULT);
			return;
		}
		this.pathVo.setStroke(COLOR_FLAG + stroke);
	}

	@Override
	public void setStrokeWidth(String strokeWidth) {
		if (StringUtils.isBlank(strokeWidth)) {
			this.pathVo.setStrokeWidth(SEQUENCE_STROKEWIDTH_DEFAULT);
			return;
		}
		this.pathVo.setStrokeWidth(strokeWidth);

	}

	@Override
	public void setFill(String fill) {
		if (StringUtils.isBlank(fill)) {
			this.pathVo.setFill(FILL_DEFAULT);
			return;
		}
		this.pathVo.setFill(COLOR_FLAG + fill);

	}

	@Override
	public void setID(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setStyle(String style) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setTypeStroke(String stroke) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setTypeStrokeWidth(String strokeWidth) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setTypeFill(String fill) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setTypeStyle(String style) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setWidth(String width) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setHeight(String height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setXAndY(String x, String y) {
		// TODO Auto-generated method stub

	}
}
