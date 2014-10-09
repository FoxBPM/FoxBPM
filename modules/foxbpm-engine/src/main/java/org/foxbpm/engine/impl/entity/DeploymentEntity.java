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
 */
package org.foxbpm.engine.impl.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.foxbpm.engine.db.PersistentObject;
import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.util.GuidUtil;
import org.foxbpm.engine.repository.Deployment;

public class DeploymentEntity implements Serializable, Deployment, PersistentObject {

	private static final long serialVersionUID = 1L;

	protected String id;
	protected String name;
	protected String category;
	protected Map<String, ResourceEntity> resources;
	protected Date deploymentTime;
	protected boolean isNew = true;
	protected String updateDeploymentId;
	/** 扩展属性 */
	protected Map<String, Object> properties;
	/**
	 * Will only be used during actual deployment to pass deployed artifacts (eg
	 * process definitions). Will be null otherwise.
	 */
	protected Map<Class<?>, List<Object>> deployedArtifacts;

	public DeploymentEntity() {
		this.id = GuidUtil.CreateGuid();
	}

	public ResourceEntity getResource(String resourceName) {
		return getResources().get(resourceName);
	}

	public void addResource(ResourceEntity resource) {
		if (resources == null) {
			resources = new HashMap<String, ResourceEntity>();
		}
		resources.put(resource.getName(), resource);
	}

	// lazy loading
	// /////////////////////////////////////////////////////////////
	public Map<String, ResourceEntity> getResources() {
		if (resources == null && id != null) {
			List<ResourceEntity> resourcesList = Context.getCommandContext().getResourceManager().findResourcesByDeploymentId(id);
			resources = new HashMap<String, ResourceEntity>();
			if (resourcesList != null) {
				for (ResourceEntity resource : resourcesList) {
					resources.put(resource.getName(), resource);
				}
			}
		}
		return resources;
	}

	public Map<String, Object> getPersistentState() {
		Map<String, Object> persistentState = new HashMap<String, Object>();
		persistentState.put("id", this.id);
		persistentState.put("name", this.name);
		persistentState.put("deploymentTime", this.deploymentTime);
		return persistentState;
	}

	// Deployed artifacts manipulation
	// //////////////////////////////////////////
	public void addDeployedArtifact(Object deployedArtifact) {
		if (deployedArtifacts == null) {
			deployedArtifacts = new HashMap<Class<?>, List<Object>>();
		}

		Class<?> clazz = deployedArtifact.getClass();
		List<Object> artifacts = deployedArtifacts.get(clazz);
		if (artifacts == null) {
			artifacts = new ArrayList<Object>();
			deployedArtifacts.put(clazz, artifacts);
		}

		artifacts.add(deployedArtifact);
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> getDeployedArtifacts(Class<T> clazz) {
		return (List<T>) deployedArtifacts.get(clazz);
	}

	public void addProperty(String key, Object value) {
		if (properties == null) {
			properties = new HashMap<String, Object>();
		}
		properties.put(key, value);

	}

	public Object getProperty(String key) {
		if (properties == null) {
			return null;
		}
		return properties.get(key);
	}

	// getters and setters
	// //////////////////////////////////////////////////////

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setResources(Map<String, ResourceEntity> resources) {
		this.resources = resources;
	}

	public Date getDeploymentTime() {
		return deploymentTime;
	}

	public void setDeploymentTime(Date deploymentTime) {
		this.deploymentTime = deploymentTime;
	}

	public boolean isNew() {
		return isNew;
	}

	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}

	// common methods //////////////////////////////////////////////////////////

	public String getUpdateDeploymentId() {
		return updateDeploymentId;
	}

	public void setUpdateDeploymentId(String setUpdateDeploymentId) {
		this.updateDeploymentId = setUpdateDeploymentId;
	}

	 
	public String toString() {
		return "DeploymentEntity[id=" + id + ", name=" + name + "]";
	}

	public boolean isModified() {
		// TODO Auto-generated method stub
		return false;
	}

}
