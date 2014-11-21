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
package org.foxbpm.engine.impl.util;

import java.util.Iterator;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.foxbpm.kernel.runtime.ProcessInstanceStatus;

/**
 * 默认location解析器
 * 将processLocation的json串解析成特定显示格式
 * @author ych
 *
 */
public class LocationUtil {

	
	public static String parseProcessLocation(String processLocationJson){
		ObjectMapper objectMapper = new ObjectMapper();
		String processStatus = "";
		JsonNode jsonNode= null ;
		if(StringUtil.isEmpty(processLocationJson)){
			return "";
		}
		try {
			jsonNode = objectMapper.readTree(processLocationJson);
		}catch (Exception e) {
			throw ExceptionUtil.getException("10806001", e,processLocationJson);
		}
		
		processStatus = jsonNode.get("processStatus").getTextValue();
		if(ProcessInstanceStatus.RUNNING.equals(processStatus)){
			ArrayNode nodes = (ArrayNode)jsonNode.get("nodes");
			if(nodes != null){
				StringBuilder sb = new StringBuilder();
				Iterator<JsonNode> nodeIterator = nodes.iterator();
				sb.append("<div>");
				while(nodeIterator.hasNext()){
					JsonNode tmpNode = nodeIterator.next();
					
					sb.append("<span title='");
					sb.append("处理者:[");
					if(tmpNode.get("users") != null){
						JsonNode users = tmpNode.get("users");
						if(users instanceof ArrayNode){
							sb.append("用户:");
							Iterator<JsonNode> userIterator = users.iterator();
							while(userIterator.hasNext()){
								JsonNode userNode= userIterator.next();
								sb.append(userNode.get("userName").getTextValue());
								sb.append(",");
							}
							sb.deleteCharAt(sb.length()-1);
						}
						
					}
					
					if(tmpNode.get("groups") != null){
						JsonNode group = tmpNode.get("groups");
						if(group instanceof ArrayNode){
							if(group != null){
								sb.append("部门:");
								Iterator<JsonNode> userIterator = group.iterator();
								while(userIterator.hasNext()){
									JsonNode userNode= userIterator.next();
									sb.append(userNode.get("groupName").getTextValue());
									sb.append(",");
								}
								sb.deleteCharAt(sb.length()-1);
							}
						}
						
					}
					sb.append("]'>");
					sb.append(tmpNode.get("nodeName").getTextValue());
					sb.append("</span>");
				}
				sb.append("</div>");
				return sb.toString();
			}
		}
		
		if(ProcessInstanceStatus.COMPLETE.equals(processStatus)){
			return "已完成";
		}else if(ProcessInstanceStatus.ABORT.equals(processStatus)){
			return "已终止";
		}
		return "未知状态";
	}
}
