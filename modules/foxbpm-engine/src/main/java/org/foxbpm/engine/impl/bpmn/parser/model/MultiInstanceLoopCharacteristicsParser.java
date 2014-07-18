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
package org.foxbpm.engine.impl.bpmn.parser.model;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.DataInput;
import org.eclipse.bpmn2.DataOutput;
import org.eclipse.bpmn2.ExtensionAttributeValue;
import org.eclipse.bpmn2.FormalExpression;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap.Entry;
import org.foxbpm.engine.impl.bpmn.behavior.BaseElementBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.MultiInstanceLoopCharacteristics;
import org.foxbpm.engine.impl.util.BpmnModelUtil;
import org.foxbpm.model.bpmn.foxbpm.Expression;
import org.foxbpm.model.bpmn.foxbpm.FoxBPMPackage;
import org.foxbpm.model.bpmn.foxbpm.LoopDataInputCollection;
import org.foxbpm.model.bpmn.foxbpm.LoopDataOutputCollection;

public class MultiInstanceLoopCharacteristicsParser extends LoopCharacteristicsParser {

	@Override
	public BaseElementBehavior parser(BaseElement baseElement) {

		org.eclipse.bpmn2.MultiInstanceLoopCharacteristics mLoopBpmn = (org.eclipse.bpmn2.MultiInstanceLoopCharacteristics) baseElement;
		MultiInstanceLoopCharacteristics mLoop = (MultiInstanceLoopCharacteristics) baseElementBehavior;
		

		// 输出数据集
		String loopDataOutputCollectionExpressionValue = getLoopDataOutputCollectionExpression(mLoopBpmn);

		// 多实例输入数据集
		String loopDataInputCollectionExpressionValue = getLoopDataInputCollectionExpression(mLoopBpmn);

		// 多实例输入项
		String inputDataItemExpressionValue = getInputDataItemExpression(mLoopBpmn);

		// 多实例输出项
		String outputDataItemExpressionValue = getOutputDataItemExpression(mLoopBpmn);

		// 完成条件
		String completionConditionExpressionValue = getCompletionConditionExpression(mLoopBpmn);
		
		
		mLoop.setInputDataItem(inputDataItemExpressionValue);
		
		mLoop.setOutputDataItem(outputDataItemExpressionValue);
		
		mLoop.setLoopDataInputCollection(loopDataInputCollectionExpressionValue);
		
		mLoop.setLoopDataOutputCollection(loopDataOutputCollectionExpressionValue);
		
		mLoop.setCompletionCondition(completionConditionExpressionValue);
		
		mLoop.setSequential(mLoopBpmn.isIsSequential());

		return super.parser(baseElement);
	}
 
	@Override
	public void init() {
		baseElementBehavior = new MultiInstanceLoopCharacteristics();
	}

	public String getLoopDataInputCollectionExpression(org.eclipse.bpmn2.LoopCharacteristics loopCharacteristics) {

		String loopDataInputCollectionExpression = null;

		LoopDataInputCollection loopDataInputCollection = BpmnModelUtil.getExtensionElementOne(LoopDataInputCollection.class,
				loopCharacteristics, FoxBPMPackage.Literals.DOCUMENT_ROOT__LOOP_DATA_INPUT_COLLECTION);

		if (loopDataInputCollection != null) {
			loopDataInputCollectionExpression = loopDataInputCollection.getExpression() != null ? loopDataInputCollection.getExpression()
					.getValue() : null;

		}

		return loopDataInputCollectionExpression;

	}

	public String getInputDataItemExpression(org.eclipse.bpmn2.LoopCharacteristics loopCharacteristics) {

		String inputDataItemExpression = null;

		org.eclipse.bpmn2.MultiInstanceLoopCharacteristics multiInstanceLoopCharacteristics = (org.eclipse.bpmn2.MultiInstanceLoopCharacteristics) loopCharacteristics;
		DataInput dataInput = multiInstanceLoopCharacteristics.getInputDataItem();
		Expression expression = getExtensionExpression(dataInput);
		if (expression != null) {
			inputDataItemExpression = expression.getValue();
		}

		return inputDataItemExpression;
	}

	public String getLoopDataOutputCollectionExpression(org.eclipse.bpmn2.LoopCharacteristics loopCharacteristics) {

		String loopDataOutputCollectionExpression = null;

		LoopDataOutputCollection loopDataOutputCollection = BpmnModelUtil.getExtensionElementOne(LoopDataOutputCollection.class,
				loopCharacteristics, FoxBPMPackage.Literals.DOCUMENT_ROOT__LOOP_DATA_OUTPUT_COLLECTION);
		if (loopDataOutputCollection != null) {
			loopDataOutputCollectionExpression = loopDataOutputCollection.getExpression() != null ? loopDataOutputCollection
					.getExpression().getValue() : null;

		}

		return loopDataOutputCollectionExpression;
	}

	public String getOutputDataItemExpression(org.eclipse.bpmn2.LoopCharacteristics loopCharacteristics) {

		String outputDataItemExpression = null;

		org.eclipse.bpmn2.MultiInstanceLoopCharacteristics multiInstanceLoopCharacteristics = (org.eclipse.bpmn2.MultiInstanceLoopCharacteristics) loopCharacteristics;
		DataOutput dataOutput = multiInstanceLoopCharacteristics.getOutputDataItem();
		Expression expression = getExtensionExpression(dataOutput);
		if (expression != null) {
			outputDataItemExpression = expression.getValue();
		}

		return outputDataItemExpression;

	}

	public String getCompletionConditionExpression(org.eclipse.bpmn2.LoopCharacteristics loopCharacteristics) {

		String completionConditionExpression = null;

		org.eclipse.bpmn2.MultiInstanceLoopCharacteristics multiInstanceLoopCharacteristics = (org.eclipse.bpmn2.MultiInstanceLoopCharacteristics) loopCharacteristics;

		FormalExpression completionConditionExpressionObj = (FormalExpression) multiInstanceLoopCharacteristics.getCompletionCondition();
		if (completionConditionExpressionObj != null) {
			String evalue = completionConditionExpressionObj.getBody();
			completionConditionExpression = evalue;
		}

		return completionConditionExpression;
	}

	private Expression getExtensionExpression(BaseElement baseElement) {

		if (baseElement.getExtensionValues().size() > 0) {

			for (ExtensionAttributeValue extensionAttributeValue : baseElement.getExtensionValues()) {

				FeatureMap extensionElements = extensionAttributeValue.getValue();

				for (Entry entry : extensionElements) {
					if (entry.getValue() instanceof Expression) {
						Expression expression = (Expression) entry.getValue();
						return expression;

					}

				}

			}

		}
		return null;

	}

}
