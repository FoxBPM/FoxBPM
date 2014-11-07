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
 * @author kenshin
 * @author ych
 */
package org.foxbpm.engine.impl.query;

import java.util.List;
import java.util.Map;

import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.impl.interceptor.CommandExecutor;
import org.foxbpm.engine.query.NativeTaskQuery;
import org.foxbpm.engine.task.Task;

public class NativeTaskQueryImpl extends AbstractNativeQuery<NativeTaskQuery, Task> implements NativeTaskQuery {

  private static final long serialVersionUID = 1L;
  
  public NativeTaskQueryImpl(CommandContext commandContext) {
    super(commandContext);
  }

  public NativeTaskQueryImpl(CommandExecutor commandExecutor) {
    super(commandExecutor);
  }


 //results ////////////////////////////////////////////////////////////////
  
  public List<Task> executeList(CommandContext commandContext, Map<String, Object> parameterMap, int firstResult, int maxResults) {
    return commandContext
      .getTaskManager()
      .findTasksByNativeQuery(parameterMap, firstResult, maxResults);
  }
  
  public long executeCount(CommandContext commandContext, Map<String, Object> parameterMap) {
    return commandContext
      .getTaskManager()
      .findTaskCountByNativeQuery(parameterMap);
  }

}
