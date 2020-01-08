/**
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
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 */
package org.thingml.monitor;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.thingml.xtext.helpers.ActionHelper;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.thingML.Action;
import org.thingml.xtext.thingML.ActionBlock;
import org.thingml.xtext.thingML.ArrayInit;
import org.thingml.xtext.thingML.ByteLiteral;
import org.thingml.xtext.thingML.Function;
import org.thingml.xtext.thingML.FunctionCallExpression;
import org.thingml.xtext.thingML.IntegerLiteral;
import org.thingml.xtext.thingML.LocalVariable;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.Parameter;
import org.thingml.xtext.thingML.PlatformAnnotation;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.PrimitiveType;
import org.thingml.xtext.thingML.Property;
import org.thingml.xtext.thingML.PropertyReference;
import org.thingml.xtext.thingML.ReturnAction;
import org.thingml.xtext.thingML.SendAction;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.ThingMLFactory;
import org.thingml.xtext.thingML.TypeRef;
import org.thingml.xtext.thingML.Variable;
import org.thingml.xtext.thingML.VariableAssignment;

public class FunctionMonitoringBinary implements MonitoringAspect {
		
	final Thing thing;
	final Property id;
	final Port monitoringPort;
	final Message onFunctionCalled;
	final TypeRef byteTypeRef;
	final Function reset;
	
	public FunctionMonitoringBinary(Thing thing, Property id, Port monitoringPort, Message msg, TypeRef byteTypeRef) {
		this.reset = ByteHelper.getNewLog(thing);
		this.thing = thing;
		this.id = id;
		this.monitoringPort = monitoringPort;
		this.onFunctionCalled = msg;
		this.byteTypeRef = byteTypeRef;
	}
	
	protected int funcSize(Function f) {
		int size = 4; //b[0]=0 (log function), b[1]=instance, b[2]=function, b[3]=return type
		if (f.getTypeRef() != null) {
			size += ((PrimitiveType)f.getTypeRef().getType()).getByteSize();
		}
		for(Parameter p : f.getParameters()) {
			size += ((PrimitiveType)p.getTypeRef().getType()).getByteSize();
		}
		return size;
	}
	
	protected void serializeParam(PropertyReference param, ActionBlock block, int blockIndex, int index, Variable array) {				
		ByteHelper.serializeParam(byteTypeRef, param.getProperty(), block, blockIndex, index, array);		
	}
		
	protected ActionBlock setArray(Function f, ReturnAction ra) {
		final LocalVariable array = (LocalVariable)((ActionBlock)f.getBody()).getActions().get(0);
		
		final ActionBlock block = ThingMLFactory.eINSTANCE.createActionBlock();
		int blockIndex = 0;
		int index = 0;					
		
		final VariableAssignment pa_ = ThingMLFactory.eINSTANCE.createVariableAssignment();
		pa_.setProperty(array);
		final IntegerLiteral e_ = ThingMLFactory.eINSTANCE.createIntegerLiteral();
		e_.setIntValue(index++);
		pa_.setIndex(e_);
		final ByteLiteral id_ = ThingMLFactory.eINSTANCE.createByteLiteral();
		id_.setByteValue((byte)0);
		pa_.setExpression(id_);
		block.getActions().add(blockIndex++, pa_);
		
		final VariableAssignment pa0 = ThingMLFactory.eINSTANCE.createVariableAssignment();
		pa0.setProperty(array);
		final PropertyReference pr0 = ThingMLFactory.eINSTANCE.createPropertyReference();
		pr0.setProperty(id);
		final IntegerLiteral e0 = ThingMLFactory.eINSTANCE.createIntegerLiteral();
		e0.setIntValue(index++);
		pa0.setIndex(e0);
		pa0.setExpression(pr0);
		block.getActions().add(blockIndex++, pa0);
		
		final VariableAssignment pa1 = ThingMLFactory.eINSTANCE.createVariableAssignment();
		pa1.setProperty(array);
		final IntegerLiteral e1 = ThingMLFactory.eINSTANCE.createIntegerLiteral();
		e1.setIntValue(index++);
		pa1.setIndex(e1);
		final ByteLiteral id = ThingMLFactory.eINSTANCE.createByteLiteral();
		id.setByteValue(Byte.parseByte(AnnotatedElementHelper.annotation(f, "id").get(0)));
		pa1.setExpression(id);
		block.getActions().add(blockIndex++, pa1);
		
		if (ra != null) {
			//TODO: Assign on byte code to each datatype (instead of just using 0 for void and 1 for not void...)
			final VariableAssignment pa2 = ThingMLFactory.eINSTANCE.createVariableAssignment();
			pa2.setProperty(array);
			final IntegerLiteral e2 = ThingMLFactory.eINSTANCE.createIntegerLiteral();
			e2.setIntValue(index++);
			pa2.setIndex(e2);
			final ByteLiteral id2 = ThingMLFactory.eINSTANCE.createByteLiteral();
			id2.setByteValue((byte)1);
			pa2.setExpression(id2);
			block.getActions().add(blockIndex++, pa2);
		} else {
			final VariableAssignment pa2 = ThingMLFactory.eINSTANCE.createVariableAssignment();
			pa2.setProperty(array);
			final IntegerLiteral e2 = ThingMLFactory.eINSTANCE.createIntegerLiteral();
			e2.setIntValue(index++);
			pa2.setIndex(e2);
			final ByteLiteral id2 = ThingMLFactory.eINSTANCE.createByteLiteral();
			id2.setByteValue((byte)0);
			pa2.setExpression(id2);
			block.getActions().add(blockIndex++, pa2);
		}		
		
		for(Parameter param : f.getParameters()) {
			ByteHelper.serializeParam(byteTypeRef, param, block, blockIndex, index, array);
			blockIndex++;
			final long size = ((PrimitiveType)param.getTypeRef().getType()).getByteSize();
			index += size;
		}
		return block;
	}

	@Override
	public void monitor() {  
		
		if (!AnnotatedElementHelper.hasAnnotation(thing, "id")) {
			final PlatformAnnotation a = ThingMLFactory.eINSTANCE.createPlatformAnnotation();
			a.setName("id");
			a.setValue(String.valueOf(ByteHelper.thingID()));
			thing.getAnnotations().add(a);
		}
		
    	for(Function f : thing.getFunctions()) {
    		if (f.isAbstract()) continue;
    		if (AnnotatedElementHelper.isDefined(f, "monitor", "not")) continue;    		    		
    		    		
    		if (!AnnotatedElementHelper.hasAnnotation(f, "id")) {
    			final PlatformAnnotation a = ThingMLFactory.eINSTANCE.createPlatformAnnotation();
    			a.setName("id");
    			a.setValue(String.valueOf(ByteHelper.functionID()));
    			f.getAnnotations().add(a);
    		}
    		
    		int fSize = funcSize(f);
    		final ArrayInit arrayInit = ThingMLFactory.eINSTANCE.createArrayInit();
    		for(int i = 0; i < fSize; i++) {
    			final IntegerLiteral s = ThingMLFactory.eINSTANCE.createIntegerLiteral();
        		s.setIntValue(0);
        		arrayInit.getValues().add(s);
    		}    		
    		final IntegerLiteral s = ThingMLFactory.eINSTANCE.createIntegerLiteral();
    		s.setIntValue(fSize);
    		final LocalVariable array = ThingMLFactory.eINSTANCE.createLocalVariable();
    		array.setName(f.getName() + "_log");
    		final TypeRef byteArray = EcoreUtil.copy(byteTypeRef);
    		byteArray.setIsArray(true);
    		byteArray.setCardinality(s);
    		array.setTypeRef(byteArray);
    		array.setInit(arrayInit);
    		
    		ActionBlock block = null;
    		if (f.getBody() instanceof ActionBlock) {
    			block = (ActionBlock) f.getBody();
    		} else {
    			block = ThingMLFactory.eINSTANCE.createActionBlock();
    			block.getActions().add(f.getBody());
    			f.setBody(block);
    		}
    		block.getActions().add(0, array);
    	}    		
    		
    	for(Function f : thing.getFunctions()) {
    		if (f.isAbstract()) continue;
    		if (AnnotatedElementHelper.isDefined(f, "monitor", "not")) continue;
    		
    		final LocalVariable array = (LocalVariable)((ActionBlock)f.getBody()).getActions().get(0);
    		
    		//Send monitoring message before each return statement (or as the first statement in the function)
        	if (f.getTypeRef() == null) {
        		ActionBlock block = (ActionBlock)f.getBody();
        		final ActionBlock b = setArray(f, null);
        		final Action send = buildSendAction(f);        		
        		b.getActions().add(send);
        		block.getActions().add(b);
        	} else {
        		for(ReturnAction ra : ActionHelper.getAllActions(f, ReturnAction.class)) {
        			ActionBlock block;
        			if (ra.eContainer() instanceof ActionBlock) {
        				block = (ActionBlock) ra.eContainer();
        			} else {
            			block = ThingMLFactory.eINSTANCE.createActionBlock();            			
            			final Object parent = ra.eContainer().eGet(ra.eContainingFeature());
            			if (parent instanceof EList) {
            				EList list = (EList) parent;
            				final int index = list.indexOf(ra);
            				list.add(index, block);
            				list.remove(ra);
            			} else {
            				final EObject o = ra.eContainer();
            				o.eSet(ra.eContainingFeature(), block);
            			}
            			block.getActions().add(ra);
        			}
        			
        			//Assign return expression to a readonly local variable
        			final LocalVariable var_return = ThingMLFactory.eINSTANCE.createLocalVariable();
        			var_return.setName("return_" + ra.eContainer().eContents().indexOf(ra));
        			var_return.setReadonly(true);
        			var_return.setTypeRef(EcoreUtil.copy(f.getTypeRef()));
        			var_return.setInit(EcoreUtil.copy(ra.getExp()));
        			
        			final PropertyReference ref_var = ThingMLFactory.eINSTANCE.createPropertyReference();
                	ref_var.setProperty(var_return);
                	ra.setExp(ref_var);
                	block.getActions().add(block.getActions().indexOf(ra), var_return);
        				        			     			
            		int fSize = funcSize(f);
                	
                	final ActionBlock b = setArray(f, ra);
                	final long size = ((PrimitiveType)var_return.getTypeRef().getType()).getByteSize();
                	serializeParam(ref_var, b, b.getActions().size()-1, (int)(fSize-size), array);                	        			
        			block.getActions().add(block.getActions().indexOf(ra), b);
        			        			
        			final Action send = buildSendAction(f);        		
            		block.getActions().add(block.getActions().indexOf(ra), send);    		            		
        		}
        		
        	}
    	}
    }


	private Action buildSendAction(Function f) {
		final LocalVariable array = (LocalVariable)((ActionBlock)f.getBody()).getActions().get(0);
		final SendAction send = ThingMLFactory.eINSTANCE.createSendAction();
		send.setMessage(onFunctionCalled);
		send.setPort(monitoringPort);
		final PropertyReference va_ref = ThingMLFactory.eINSTANCE.createPropertyReference();
		va_ref.setProperty(array);
		send.getParameters().add(va_ref);
		return send;
	}
	
}
