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
