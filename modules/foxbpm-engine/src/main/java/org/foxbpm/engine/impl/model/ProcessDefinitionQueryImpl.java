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
package org.foxbpm.engine.impl.model;

import java.util.List;

import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.impl.interceptor.CommandExecutor;
import org.foxbpm.engine.impl.query.AbstractQuery;
import org.foxbpm.engine.repository.ProcessDefinition;
import org.foxbpm.engine.repository.ProcessDefinitionQuery;

/**
 * @author kenshin
 */
public class ProcessDefinitionQueryImpl extends AbstractQuery<ProcessDefinitionQuery, ProcessDefinition> implements ProcessDefinitionQuery {

	private static final long serialVersionUID = 1L;
	protected String id;
	protected String category;
	protected String categoryLike;
	protected String name;
	protected String nameLike;
	protected String deploymentId;
	protected String key;
	protected String keyLike;
	protected Integer version;
	protected boolean latest = false;
	protected String processDefinitionId;

	public ProcessDefinitionQueryImpl() {
	}

	public ProcessDefinitionQueryImpl(CommandContext commandContext) {
		super(commandContext);
	}

	public ProcessDefinitionQueryImpl(CommandExecutor commandExecutor) {
		super(commandExecutor);
	}

	public ProcessDefinitionQueryImpl processDefinitionId(String processDefinitionId) {
		this.id = processDefinitionId;
		return this;
	}

	public ProcessDefinitionQueryImpl processDefinitionCategory(String category) {
		if (category == null) {
			return this;
		}
		this.category = category;
		return this;
	}

	public ProcessDefinitionQueryImpl processDefinitionCategoryLike(String categoryLike) {
		if (categoryLike == null) {
			return this;
		}
		this.categoryLike = categoryLike;
		return this;
	}

	public ProcessDefinitionQueryImpl processDefinitionName(String name) {
		if (name == null) {
			return this;
		}
		this.name = name;
		return this;
	}

	public ProcessDefinitionQueryImpl processDefinitionNameLike(String nameLike) {
		if (nameLike == null) {
			return this;
		}
		this.nameLike = nameLike;
		return this;
	}

	public ProcessDefinitionQueryImpl deploymentId(String deploymentId) {
		if (deploymentId == null) {
			return this;
		}
		this.deploymentId = deploymentId;
		return this;
	}

	public ProcessDefinitionQueryImpl processDefinitionKey(String key) {
		if (key == null) {
			return this;
		}
		this.key = key;
		return this;
	}

	public ProcessDefinitionQueryImpl processDefinitionKeyLike(String keyLike) {
		if (keyLike == null) {
			return this;
		}
		this.keyLike = keyLike;
		return this;
	}

	public ProcessDefinitionQueryImpl processDefinitionVersion(Integer version) {
		if (version == null) {
			return this;
		}
		this.version = version;
		return this;
	}

	public ProcessDefinitionQueryImpl latestVersion() {
		this.latest = true;
		return this;
	}

	// sorting ////////////////////////////////////////////

	public ProcessDefinitionQuery orderByDeploymentId() {
		return orderBy(ProcessDefinitionQueryProperty.DEPLOYMENT_ID);
	}

	public ProcessDefinitionQuery orderByProcessDefinitionKey() {
		return orderBy(ProcessDefinitionQueryProperty.PROCESS_DEFINITION_KEY);
	}

	public ProcessDefinitionQuery orderByProcessDefinitionCategory() {
		return orderBy(ProcessDefinitionQueryProperty.PROCESS_DEFINITION_CATEGORY);
	}

	public ProcessDefinitionQuery orderByProcessDefinitionId() {
		return orderBy(ProcessDefinitionQueryProperty.PROCESS_DEFINITION_ID);
	}

	public ProcessDefinitionQuery orderByProcessDefinitionVersion() {
		return orderBy(ProcessDefinitionQueryProperty.PROCESS_DEFINITION_VERSION);
	}

	public ProcessDefinitionQuery orderByProcessDefinitionName() {
		return orderBy(ProcessDefinitionQueryProperty.PROCESS_DEFINITION_NAME);
	}

	// results ////////////////////////////////////////////

	public long executeCount(CommandContext commandContext) {
		checkQueryOk();
		return commandContext.getProcessDefinitionManager().findProcessDefinitionCountByQueryCriteria(this);
	}

	public List<ProcessDefinition> executeList(CommandContext commandContext) {
		checkQueryOk();
		return commandContext.getProcessDefinitionManager().findProcessDefinitionsByQueryCriteria(this);
	}

	public void checkQueryOk() {
		super.checkQueryOk();

		// latest() makes only sense when used with key() or keyLike()
		if (latest && ((id != null) || (name != null) || (nameLike != null) || (version != null) || (deploymentId != null))) {
			throw new FoxBPMException("Calling latest() can only be used in combination with key(String) and keyLike(String)");
		}
	}

	// getters ////////////////////////////////////////////

	public String getDeploymentId() {
		return deploymentId;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getNameLike() {
		return nameLike;
	}

	public String getKey() {
		return key;
	}

	public String getKeyLike() {
		return keyLike;
	}

	public Integer getVersion() {
		return version;
	}

	public boolean isLatest() {
		return latest;
	}

	public String getCategory() {
		return category;
	}

	public String getCategoryLike() {
		return categoryLike;
	}
}
