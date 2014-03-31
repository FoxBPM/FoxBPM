/**
 * 
 */
package org.foxbpm.kernel.runtime;

import java.io.Serializable;


/**
 * @author kenshin
 * 
 */
public interface KernelProcessInstance extends Serializable, KernelVariable {

	void start();


	KernelToken findToken(String tokenId);


	boolean isEnded();

	void deleteCascade(String deleteReason);

}
