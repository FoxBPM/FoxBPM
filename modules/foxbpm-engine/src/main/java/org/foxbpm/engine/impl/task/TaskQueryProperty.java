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
package org.foxbpm.engine.impl.task;

import java.util.HashMap;
import java.util.Map;

import org.foxbpm.engine.query.QueryProperty;

/**
 *
 * 
 * @author kenshin
 */
public class TaskQueryProperty implements QueryProperty {
  
  private static final Map<String, TaskQueryProperty> properties = new HashMap<String, TaskQueryProperty>();

  public static final TaskQueryProperty TASK_ID = new TaskQueryProperty("RES.ID");
  public static final TaskQueryProperty NAME = new TaskQueryProperty("RES.NAME");
  public static final TaskQueryProperty DESCRIPTION = new TaskQueryProperty("RES.DESCRIPTION");
  public static final TaskQueryProperty PRIORITY = new TaskQueryProperty("RES.PRIORITY");
  public static final TaskQueryProperty ASSIGNEE = new TaskQueryProperty("RES.ASSIGNEE");
  public static final TaskQueryProperty CREATE_TIME = new TaskQueryProperty("RES.CREATE_TIME");
  public static final TaskQueryProperty PROCESS_INSTANCE_ID = new TaskQueryProperty("RES.PROCESSINSTANCE_ID");
  public static final TaskQueryProperty DUE_DATE = new TaskQueryProperty("RES.DUEDATE");
  public static final TaskQueryProperty END_TIME = new TaskQueryProperty("RES.END_TIME");
  private String name;

  public TaskQueryProperty(String name) {
    this.name = name;
    properties.put(name, this);
  }

  public String getName() {
    return name;
  }
  
  public static TaskQueryProperty findByName(String propertyName) {
    return properties.get(propertyName);
  }

}
