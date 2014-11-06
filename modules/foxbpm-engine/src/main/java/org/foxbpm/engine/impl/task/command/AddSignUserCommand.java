/**
 * 
 */
package org.foxbpm.engine.impl.task.command;

import org.foxbpm.engine.exception.FoxBPMBizException;
import org.foxbpm.engine.impl.util.StringUtil;

/**
 * @author kenshin
 * 
 */
public class AddSignUserCommand extends AbstractCustomExpandTaskCommand {

	public final static String INPUTPARAM_ADDRESOURCES = "addResources";
	public final static String INPUTPARAM_ISPARALLEL = "isParallel";
	public final static String INPUTPARAM_ISRESOLVED = "isResolved";
	public final static String INPUTPARAM_ISMOREADDSIGN = "isMoreAddSign";

	protected String addResources;
	protected boolean isParallel;
	protected boolean isResolved;
	protected boolean isMoreAddSign;

	public String getAddResources() {
		return addResources;
	}

	public void setAddResources(String addResources) {
		this.addResources = addResources;
	}

	public boolean isParallel() {
		return isParallel;
	}

	public void setParallel(boolean isParallel) {
		this.isParallel = isParallel;
	}

	public boolean isResolved() {
		return isResolved;
	}

	public void setResolved(boolean isResolved) {
		this.isResolved = isResolved;
	}

	public boolean isMoreAddSign() {
		return isMoreAddSign;
	}

	public void setMoreAddSign(boolean isMoreAddSign) {
		this.isMoreAddSign = isMoreAddSign; 
	}

	public AddSignUserCommand(ExpandTaskCommand expandTaskCommand) {
		super(expandTaskCommand);
		this.addResources = StringUtil.getString(expandTaskCommand.getParamMap().get(INPUTPARAM_ADDRESOURCES));
		if (StringUtil.isEmpty(this.addResources)) {
			throw new FoxBPMBizException("加签资源不能为空");
		}
		this.isParallel = StringUtil.getBoolean(expandTaskCommand.getParamMap().get(INPUTPARAM_ISPARALLEL));
		this.isResolved = StringUtil.getBoolean(expandTaskCommand.getParamMap().get(INPUTPARAM_ISRESOLVED));
		this.isMoreAddSign = StringUtil.getBoolean(expandTaskCommand.getParamMap().get(INPUTPARAM_ISMOREADDSIGN));
	}

}
