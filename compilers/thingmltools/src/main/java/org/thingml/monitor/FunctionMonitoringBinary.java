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
import org.thingml.xtext.thingML.ByteLiteral;
import org.thingml.xtext.thingML.Function;
import org.thingml.xtext.thingML.IntegerLiteral;
import org.thingml.xtext.thingML.LocalVariable;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.Parameter;
import org.thingml.xtext.thingML.PlatformAnnotation;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.PrimitiveType;
import org.thingml.xtext.thingML.Property;
import org.thingml.xtext.thingML.PropertyAssign;
import org.thingml.xtext.thingML.PropertyReference;
import org.thingml.xtext.thingML.ReturnAction;
import org.thingml.xtext.thingML.SendAction;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.ThingMLFactory;
import org.thingml.xtext.thingML.TypeRef;
import org.thingml.xtext.thingML.VariableAssignment;

public class FunctionMonitoringBinary implements MonitoringAspect {
		
	final Thing thing;
	final Property id;
	final Port monitoringPort;
	final Message onFunctionCalled;
	final TypeRef byteTypeRef;
	final Property max;
	final Property array;
	
	public FunctionMonitoringBinary(Thing thing, Property id, Port monitoringPort, Message msg, TypeRef byteTypeRef, Property max, Property array) {
		this.thing = thing;
		this.id = id;
		this.monitoringPort = monitoringPort;
		this.onFunctionCalled = msg;
		this.byteTypeRef = byteTypeRef;
		this.max = max;
		this.array = array;
	}
	
	protected int funcSize(Function f) {
		int size = 3; //b[0]=instance, b[1]=function, b[2]=return type
		if (f.getTypeRef() != null) {
			size += ((PrimitiveType)f.getTypeRef().getType()).getByteSize();
		}
		for(Parameter p : f.getParameters()) {
			size += ((PrimitiveType)p.getTypeRef().getType()).getByteSize();
		}
		return size;
	}
	
	protected int setArray(Function f, ReturnAction ra, ActionBlock block) {
		final VariableAssignment pa1 = ThingMLFactory.eINSTANCE.createVariableAssignment();
		pa1.setProperty(array);
		final IntegerLiteral e1 = ThingMLFactory.eINSTANCE.createIntegerLiteral();
		e1.setIntValue(1);
		pa1.setIndex(e1);
		final ByteLiteral id = ThingMLFactory.eINSTANCE.createByteLiteral();
		id.setByteValue(Byte.parseByte(AnnotatedElementHelper.annotation(f, "id").get(0)));
		pa1.setExpression(id);
		block.getActions().add(0, pa1);
		
		if (ra != null) {
			//TODO
		} else {
			final VariableAssignment pa2 = ThingMLFactory.eINSTANCE.createVariableAssignment();
			pa2.setProperty(array);
			final IntegerLiteral e2 = ThingMLFactory.eINSTANCE.createIntegerLiteral();
			e2.setIntValue(2);
			pa2.setIndex(e2);
			final ByteLiteral id2 = ThingMLFactory.eINSTANCE.createByteLiteral();
			id2.setByteValue((byte)0);
			pa2.setExpression(id2);
			block.getActions().add(1, pa2);
		}
		
		int index = 3;
		int blockIndex = 2;
		for(Parameter param : f.getParameters()) {
			final long size = ((PrimitiveType)param.getTypeRef().getType()).getByteSize();
			//if (size == 1) {
				final VariableAssignment pa = ThingMLFactory.eINSTANCE.createVariableAssignment();
				pa.setProperty(array);
				final IntegerLiteral e = ThingMLFactory.eINSTANCE.createIntegerLiteral();
				e.setIntValue(index);
				pa.setIndex(e);
				final PropertyReference pr = ThingMLFactory.eINSTANCE.createPropertyReference();
				pr.setProperty(param);				
				pa.setExpression(pr);
				block.getActions().add(blockIndex, pa);
			/*} else {
				//TODO: bit shifts and masks
			}*/
			index += size;
			blockIndex++;
		}
		return blockIndex;
	}

	@Override
	public void monitor() {  
		
		if (!AnnotatedElementHelper.hasAnnotation(thing, "id")) {
			final PlatformAnnotation a = ThingMLFactory.eINSTANCE.createPlatformAnnotation();
			a.setName("id");
			a.setValue(String.valueOf(ByteHelper.thingID()));
			thing.getAnnotations().add(a);
		}
		
		int maxSize = 3;
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
    		if (fSize > maxSize) {
    			maxSize = fSize;
    		}
    	}
    	
    	//Setup the byte array containing log info for functions
		final IntegerLiteral il = ThingMLFactory.eINSTANCE.createIntegerLiteral();
		il.setIntValue(maxSize);
		final PropertyAssign pa = ThingMLFactory.eINSTANCE.createPropertyAssign();
		pa.setProperty(max);
		pa.setInit(il);
		thing.getAssign().add(pa);
    		
    		
    	for(Function f : thing.getFunctions()) {
    		if (f.isAbstract()) continue;
    		if (AnnotatedElementHelper.isDefined(f, "monitor", "not")) continue;
    		
        	//Send monitoring message before each return statement (or as the first statement in the function)
        	if (f.getTypeRef() == null) {
        		ActionBlock block;
        		if (f.getBody() instanceof ActionBlock) {
        			block = (ActionBlock) f.getBody();
        		} else {
        			block = ThingMLFactory.eINSTANCE.createActionBlock();
        			block.getActions().add(f.getBody());
        			f.setBody(block);
        		}
           		
        		int index = setArray(f, null, block);
        		final Action send = buildSendAction(f);        		
        		block.getActions().add(index, send);
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
        			
        				        			     			
            		
                	int index = setArray(f, ra, block);                	
        			block.getActions().add(block.getActions().indexOf(ra), var_return);
        			index++;
        			//TODO: Set serialized return value into array
                	final long size = ((PrimitiveType)var_return.getTypeRef().getType()).getByteSize();
        			//if (size == 1) {
        				final VariableAssignment va = ThingMLFactory.eINSTANCE.createVariableAssignment();
        				va.setProperty(array);
        				final IntegerLiteral e = ThingMLFactory.eINSTANCE.createIntegerLiteral();
        				e.setIntValue(maxSize-1);
        				va.setIndex(e);
        				va.setExpression(EcoreUtil.copy(ref_var));
        				block.getActions().add(block.getActions().indexOf(ra), va);
        			/*} else {
        				//TODO: bit shifts and masks
        			}*/
        			
        			
        			final Action send = buildSendAction(f);        		
            		block.getActions().add(block.getActions().indexOf(ra), send);    		            		
        		}
        		
        	}
    	}
    }


	private Action buildSendAction(Function f) {
		final SendAction send = ThingMLFactory.eINSTANCE.createSendAction();
		send.setMessage(onFunctionCalled);
		send.setPort(monitoringPort);
		final PropertyReference va_ref = ThingMLFactory.eINSTANCE.createPropertyReference();
		va_ref.setProperty(array);
		send.getParameters().add(va_ref);
		return send;
	}
	
}
