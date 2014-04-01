/**
 * Copyright 1996-2014 FoxBPM Co.,Ltd.
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
package org.foxbpm.engine.database;

public enum DataBaseTableEnum {

	fixflow_run_processinstance,
	fixflow_run_token, 
	fixflow_run_taskinstance, 
	fixflow_run_taskidentitylink, 
	fixflow_run_variable, 
	
	fixflow_his_processinstance, 
	fixflow_his_token, 
	fixflow_his_taskinstance, 
	fixflow_his_taskidentitylink, 
	fixflow_his_variable,
	
	
	fixflow_def_deployment,
	fixflow_def_bytearray,
	fixflow_def_processdefinition,
	

	fixflow_agent_agentinfo,
	fixflow_agent_agentdetails,
	
	
	fixflow_mail

}
