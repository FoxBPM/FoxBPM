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
 * @author yangguangftlp
 */
package org.foxbpm.engine.exception;

/**
 * 锁异常
 * 
 * @author yangguangftlp
 * @date 2014年8月19日
 */
public class FoxBPMOptimisticLockException extends FoxBPMException {

	/**
	 * 
	 */
    private static final long serialVersionUID = 1L;

	public FoxBPMOptimisticLockException(String exceptionCode, Object... args) {
	    super(exceptionCode, args);
    }

	public FoxBPMOptimisticLockException(String exceptionCode, Object[] args, Throwable cause) {
	    super(exceptionCode, args, cause);
    }

	public FoxBPMOptimisticLockException(String exceptionCode, Throwable cause, Object... args) {
	    super(exceptionCode, cause, args);
    }

	public FoxBPMOptimisticLockException(String exceptionCode, Throwable cause) {
	    super(exceptionCode, cause);
    }

	public FoxBPMOptimisticLockException(String exceptionCode) {
	    super(exceptionCode);
    }
}
