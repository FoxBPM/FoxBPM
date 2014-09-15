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
package org.foxbpm.engine.exception;

/**
 * 异常信息的国际化key
 * 
 * @author ych
 * 
 */
public interface ExceptionCode {
	/** 默认异常 */
	String EXCEPTION_DEFAULT = "exception_default";

	/** 文件加载类异常 */
	String CLASSLOAD_EXCEPTION_DEFAULT = "classloadException_default";
	String CLASSLOAD_EXCEPTION_FILENOTFOUND = "classloadException_filenotfound";
	String CLASSLOAD_EXCEPTION_ENCODING = "classloadException_encoding";
	String CLASSLOAD_EXCEPTION = "classloadException";
	String CLASSLOAD_EXCEPTION_DCUMENT = "classloadException_document";
	String JOB_EXCEPTION_DEFAULT = "jobException_default";
	String QUARZTEXCEPTION_ISENABLE = "quarztException_isEnable";

	/* 规则类 异常 */
	/** 规则解释类没有找到 */
	String CLASSLOADEXCEPTION_RULECLASS = "classloadException_ruleClass";
	/** 归档异常 */
	/* 表达式执行异常 */

	String EXCEPTION_ARCHIVE = "archive";
	String EXCEPTION_ARCHIVE_PARAMS = "archive_params";

	/** 跳过策略表达式异常编码 */
	String EXPRESSION_EXCEPTION_SKIPSTRATEGY = "expressionException_skipStrategy";

	/** 输出结果集表达式集合验证 */
	String EXPRESSION_EXCEPTION_LOOPDATAOUTPUTCOLLECTION_COLLECTIONCHECK = "expressionException_loopDataOutputCollection_collectionCheck";

	/** 多实例输入数据集异常编码 */
	String EXPRESSION_EXCEPTION_LOOPDATAINPUTCOLLECTIONEXPRESSION = "expressionException_loopDataInputCollectionExpression";

	/** 多实例输入数据集集合数量为0 */
	String EXPRESSION_EXCEPTION_LOOPDATAINPUTCOLLECTIONEMPTY = "expressionException_loopDataInputCollectionEmpty";

	/** 多实例输入数据集集合循环插入输入项目 */
	String EXPRESSIONEXCEPTION_COLLECTIONININPUTDATAITEM = "expressionException_collectionInInputDataItem";

	/** 完成条件表达式为空 */
	String EXPRESSIONEXCEPTION_CONDITIONEXPRESSIONEMPTY = "expressionException_conditionExpressionEmpty";

	/** 完成条件表达式执行出错 */
	String EXPRESSIONEXCEPTION_CONDITIONEXPRESSIONERROR = "expressionException_conditionExpressionError";

	/** 节点离开的时候清理quartz出错 */
	String QUARZTEXCEPTION_NODELEAVECLEANQUARTZ = "quarztException_nodeLeaveCleanQuartz";

	/** 节点后面没有配置线条 */
	String SEQUENCEFLOWEXCEPTION_NODENOSEQUENCEFLOW = "sequenceFlowException_nodeNoSequenceFlow";

	/** 节点后面没有验证通过的线条 */
	String SEQUENCEFLOWEXCEPTION_NODENOVERIFIEDSEQUENCEFLOW = "sequenceFlowException_nodeNoVerifiedSequenceFlow";

	/** 规则类执行异常 */
	String RULEEXCEPTION_CLASSEXEC = "ruleException_classExec";

	/** 规则脚本执行异常 */
	String RULEEXCEPTION_RULESCRIPTEXEC = "ruleException_ruleScriptExec";

	/** 规则执行类、脚本都为空 */
	String RULEEXCEPTION_CLASSANDSCRIPTEMPTY = "ruleException_classAndScriptEmpty";

	/** 流程定义对象没找到 */
	String OBJECTNOTFOUNDEXCEPTION_FINDDEFINITIONBYID = "objectNotFoundException_findDefinitionById";

	/** 流程定义对象没找到 */
	String OBJECTNOTFOUNDEXCEPTION_FINDDEFINITIONBYKEY = "objectNotFoundException_findDefinitionByKey";

	/** 参数异常为空 */
	String ILLEGALARGUMENTEXCEPTION_ISNULL = "IllegalArgumentException_isNull";

	/** 转换SVG出现错误 */
	String CONVERTERTER_SVG_ERROR = "ConverterterSVG_isError";

	/** 关闭流出现错误 */
	String IO_CLOSE_ERROR = "IOClose_isError";
}
