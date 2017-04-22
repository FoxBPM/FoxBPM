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
package org.foxbpm.engine.impl.util;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;

import org.foxbpm.engine.exception.FoxBPMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExceptionUtil {

	private static Logger log = LoggerFactory.getLogger(ExceptionUtil.class);
	private static Properties exceptionResource;
	
	static{
		InputStream inputStream = ReflectUtil.getResourceAsStream("config/exception.properties");
		exceptionResource = new Properties();
		if(inputStream == null){
			log.error("未发现exception.properties文件");
		}else{
			try {
				exceptionResource.load(inputStream);
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	
	public static FoxBPMException getException(String exceptionCode){
		return createException(getMessage(exceptionCode),null);
	}
	
	public static FoxBPMException getException(String exceptionCode,String ...args){
		
		return createException(getMessage(exceptionCode,args),null);
	}
	
	public static FoxBPMException getException(String exceptionCode,Throwable ex){
		if(ex instanceof FoxBPMException){
			return (FoxBPMException)ex;
		}
		return createException(getMessage(exceptionCode)+"，错误类型"+ex.getClass(),ex);
	}
	
	public static FoxBPMException getException(String exceptionCode,Throwable ex, String ...args){
		if(ex instanceof FoxBPMException){
			return (FoxBPMException)ex;
		}
		return createException(getMessage(exceptionCode,args)+" "+ex.getClass(),ex);
	}
	
	private static FoxBPMException createException(String message,Throwable ex){
		if(ex == null){
			return new FoxBPMException(message);
		}
		return new FoxBPMException(message,ex);
		
	}
	
	private static String getMessage(String exceptionCode,Object[] args){
		String mes = exceptionResource.getProperty(exceptionCode);
		if(StringUtil.isNotEmpty(mes)){
			return exceptionCode + ":" + MessageFormat.format(mes, args);
		}
		return exceptionCode;
	}
	
	private static String getMessage(String exceptionCode){
		String mes = exceptionResource.getProperty(exceptionCode);
		if(StringUtil.isNotEmpty(mes)){
			return exceptionCode + ":" + mes;
		}
		return exceptionCode;
	}
}
