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
package org.foxbpm.kernel.advanced.util;



public class StringUtil {
	
	/**
     * 获得对象描述字符串
     * @param obj
     * @return 字符串
     */
    public static String getString(Object obj){
    	if(obj!=null){
    		return obj.toString();
    	}else{
    		return null;
    	}
    }
    
   
    
    /**
     * 判断字符串中有无内容
     * @param str 字符串
     * @return 结果
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }
    
    public static boolean getBoolean(Object booleanString)
    {
    	if(booleanString==null){
    		return false;
    	}
    	String booleanStringTemp=booleanString.toString();
    	return Boolean.parseBoolean(booleanStringTemp);
    }

	public static boolean isNotEmpty(String str) {
		
		if(str==null||str.equals("")){
			return false;
		}
		else{
			return true;
		}
	}
	

	
	
	public static boolean verifySameValueToUpper(String valueA,String valueB){
		boolean verifyValue=valueA.toUpperCase().equals(valueB.toUpperCase());
		return verifyValue;
	}


}
