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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.thingml.xtext.helpers.ActionHelper;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.thingML.Action;
import org.thingml.xtext.thingML.ActionBlock;
import org.thingml.xtext.thingML.ByteLiteral;
import org.thingml.xtext.thingML.EnumLiteralRef;
import org.thingml.xtext.thingML.Enumeration;
import org.thingml.xtext.thingML.EnumerationLiteral;
import org.thingml.xtext.thingML.Expression;
import org.thingml.xtext.thingML.Function;
import org.thingml.xtext.thingML.IntegerLiteral;
import org.thingml.xtext.thingML.LocalVariable;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.Parameter;
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

public class FunctionMonitoringBinary implements MonitoringAspect {
		
	final Thing thing;
	final Property id;
	final Port monitoringPort;
	final Message onFunctionCalled;
	final TypeRef byteTypeRef;
	final EnumerationLiteral lit;
	int counter = 0;
	
	public FunctionMonitoringBinary(Thing thing, Property id, Port monitoringPort, Message msg, TypeRef byteTypeRef) {
		lit = ByteHelper.getLogLiteral(thing, "function_called");
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
		
	protected List<Expression> setArray(Function f, ReturnAction ra) {
		final List<Expression> inits = new ArrayList<Expression>();
						
		final EnumLiteralRef id_ = ThingMLFactory.eINSTANCE.createEnumLiteralRef();
		id_.setLiteral(lit);
		id_.setEnum((Enumeration)lit.eContainer());
		inits.add(id_);
		
		final PropertyReference pr0 = ThingMLFactory.eINSTANCE.createPropertyReference();
		pr0.setProperty(id);
		inits.add(pr0);
		
		final ByteLiteral id = ThingMLFactory.eINSTANCE.createByteLiteral();
		id.setByteValue(Byte.parseByte(AnnotatedElementHelper.annotation(f, "id").get(0)));
		inits.add(id);
		
		if (ra != null) {
			final ByteLiteral id2 = ThingMLFactory.eINSTANCE.createByteLiteral();
			id2.setByteValue(Byte.valueOf(AnnotatedElementHelper.firstAnnotation(f.getTypeRef().getType(), "id")));
			inits.add(id2);
		} else {
			final ByteLiteral id2 = ThingMLFactory.eINSTANCE.createByteLiteral();
			id2.setByteValue((byte)0);
			inits.add(id2);
		}		
		
		for(Parameter param : f.getParameters()) {
			inits.addAll(ByteHelper.serializeParam(byteTypeRef, param));
		}
		return inits;
	}

	@Override
	public void monitor() {  	
    		
    	for(Function f : thing.getFunctions()) {
    		if (f.isAbstract()) continue;
    		if (AnnotatedElementHelper.isDefined(f, "monitor", "not")) continue;
    		
    		if (!(f.getBody() instanceof ActionBlock)) {    		
    			final ActionBlock block = ThingMLFactory.eINSTANCE.createActionBlock();
    			block.getActions().add(f.getBody());
    			f.setBody(block);
    		}
    		
    		int fSize = funcSize(f);
    		
    		//Send monitoring message before each return statement (or as the first statement in the function)
        	if (f.getTypeRef() == null) {
        		ActionBlock block = (ActionBlock)f.getBody();
        		final List<Expression> inits = setArray(f, null);
        		final LocalVariable array = ByteHelper.arrayInit(f.getName() + "_log" + counter++, byteTypeRef, inits);
        		final Action send = buildSendAction(f, fSize, array);        		
        		block.getActions().add(array);
        		block.getActions().add(send);
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
                	
                	final List<Expression> inits = setArray(f, null);                	
                	inits.addAll(ByteHelper.serializeParam(byteTypeRef, ref_var.getProperty()));
            		final LocalVariable array = ByteHelper.arrayInit(f.getName() + "_log" + counter++, byteTypeRef, inits);
            		block.getActions().add(block.getActions().indexOf(ra), array);
                	        			        			
        			final Action send = buildSendAction(f, fSize, array);        		
            		block.getActions().add(block.getActions().indexOf(ra), send);    		            		
        		}
        		
        	}
    	}
    }

	private Action buildSendAction(Function f, int size, LocalVariable array) {
		final SendAction send = ThingMLFactory.eINSTANCE.createSendAction();
		send.setMessage(onFunctionCalled);
		send.setPort(monitoringPort);
		final PropertyReference va_ref = ThingMLFactory.eINSTANCE.createPropertyReference();
		va_ref.setProperty(array);
		send.getParameters().add(va_ref);
		final IntegerLiteral s = ThingMLFactory.eINSTANCE.createIntegerLiteral();
		s.setIntValue(size);
		send.getParameters().add(s);
		return send;
	}
	
}
