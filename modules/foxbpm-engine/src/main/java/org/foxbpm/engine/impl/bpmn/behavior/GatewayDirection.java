/**
 * 
 */
package org.foxbpm.engine.impl.bpmn.behavior;

/**
 * @author kenshin
 * 
 */
public enum GatewayDirection {
	/** 未知 */
	UNSPECIFIED,
	/** 聚合 */
	CONVERGING,
	/** 发散 */
	DIVERGING,
	/** 混合 */
	MIXED
}
