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
public class UpdateCompletionRateCommand extends AbstractCustomExpandTaskCommand {
	
	public final static String INPUTPARAM_COMPLETION_RATE="completionRate";
	
	protected double completionRate;

	
	public double getCompletionRate() {
		return completionRate;
	}


	public void setCompletionRate(double completionRate) {
		this.completionRate = completionRate;
	}


	public UpdateCompletionRateCommand(ExpandTaskCommand expandTaskCommand) {
		super(expandTaskCommand);
		this.completionRate=StringUtil.getInt(expandTaskCommand.getParam(INPUTPARAM_COMPLETION_RATE));
		if(this.completionRate<=100&&this.completionRate>=0){
			throw new FoxBPMBizException("完成率必须在0-100之间");
		}
	}

	
}
