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
 * @author ych
 */
package org.foxbpm.engine.impl.mybatis;

import java.util.List;

/**
 * foxbpm中外部mybatis mapper文件的注册
 * MybatisSqlSessionFactory加载时，会通过serviceLoader加载所有的注册实现
 * @author ych
 */
public interface FoxbpmMapperConfig {

	/**
	 * mybatis mapper文件地址,相对Classes目录
	 * @return 如：["org/foxbpm/User.xml","Dept.xml"]
	 */
	public List<String> getMapperConfig();
	
}
