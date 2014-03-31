/**
 * 
 */
package org.foxbpm.kernel.process;

import java.util.List;

/**
 * @author kenshin
 *
 */
public interface KernelDefinitions extends KernelBaseElement {
	
	List<KernelRootElement> getRootElements();

}
