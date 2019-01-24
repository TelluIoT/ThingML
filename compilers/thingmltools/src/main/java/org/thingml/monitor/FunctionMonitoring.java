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
import org.thingml.xtext.thingML.ActionBlock;
import org.thingml.xtext.thingML.Function;
import org.thingml.xtext.thingML.LocalVariable;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.Parameter;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.PropertyReference;
import org.thingml.xtext.thingML.ReturnAction;
import org.thingml.xtext.thingML.SendAction;
import org.thingml.xtext.thingML.StringLiteral;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.ThingMLFactory;
import org.thingml.xtext.thingML.TypeRef;

public class FunctionMonitoring implements MonitoringAspect {

	@Override
	public void monitor(Thing thing, Port monitoringPort, Thing monitoringMsgs, TypeRef stringTypeRef) {
    	for(Function f : thing.getFunctions()) {
    		if (f.isAbstract()) continue;
    		if (AnnotatedElementHelper.isDefined(f, "monitor", "not")) continue;
    		
    		//Update monitoring API
    		final Message onFunctionCalled = ThingMLFactory.eINSTANCE.createMessage();
        	onFunctionCalled.setName(f.getName() + "_called");
        	monitoringMsgs.getMessages().add(onFunctionCalled);
        	monitoringPort.getSends().add(onFunctionCalled);
        	final Parameter onFunctionCalled_name = ThingMLFactory.eINSTANCE.createParameter(); //FIXME?: somehow a useless parameter... repeating the name of message
        	onFunctionCalled_name.setName("name");        
        	onFunctionCalled_name.setTypeRef(EcoreUtil.copy(stringTypeRef));
        	onFunctionCalled.getParameters().add(onFunctionCalled_name);
        	if (f.getTypeRef()!=null) {
            	final Parameter onFunctionCalled_type = ThingMLFactory.eINSTANCE.createParameter();
            	onFunctionCalled_type.setName("type");        
            	onFunctionCalled_type.setTypeRef(EcoreUtil.copy(stringTypeRef));
            	onFunctionCalled.getParameters().add(onFunctionCalled_type);
            	
            	final Parameter onFunctionCalled_return = ThingMLFactory.eINSTANCE.createParameter();
            	onFunctionCalled_return.setName("returns");        
            	onFunctionCalled_return.setTypeRef(EcoreUtil.copy(f.getTypeRef()));
            	onFunctionCalled.getParameters().add(onFunctionCalled_return);
        	}
        	for(Parameter p : f.getParameters()) {
        		final Parameter onFunctionCalled_p = ThingMLFactory.eINSTANCE.createParameter();
            	onFunctionCalled_p.setName(p.getName());        
            	onFunctionCalled_p.setTypeRef(EcoreUtil.copy(p.getTypeRef()));
            	onFunctionCalled.getParameters().add(onFunctionCalled_p);
        	}
        	
        	//Send monitoring message before each return statement (or as the first statement in the function)
        	if (f.getTypeRef() == null) {
        		ActionBlock block;
        		if (f.getBody() instanceof ActionBlock) {
        			block = (ActionBlock) f.getBody();
        		} else {
        			block = ThingMLFactory.eINSTANCE.createActionBlock();
        			block.getActions().add(f.getBody());
        		}
        		final SendAction send = ThingMLFactory.eINSTANCE.createSendAction();
        		send.setMessage(onFunctionCalled);
        		send.setPort(monitoringPort);
        		final StringLiteral name_exp = ThingMLFactory.eINSTANCE.createStringLiteral();
        		name_exp.setStringValue(f.getName());
        		send.getParameters().add(name_exp);
        		
        		for(Parameter p : f.getParameters()) {
        			final PropertyReference p_exp = ThingMLFactory.eINSTANCE.createPropertyReference();
        			p_exp.setProperty(p);
        			send.getParameters().add(p_exp);
        		}
        		
        		block.getActions().add(0, send);
        	} else {//FIXME: store return expression in a variable, so as to avoid side effect e.g. if it returns a function call (which would then be called twice)
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
        			PropertyReference ref_var = null;
        			if (ra.getExp() instanceof PropertyReference) {
        				ref_var = (PropertyReference) EcoreUtil.copy(ra.getExp());
        			} else {
        				final LocalVariable var = ThingMLFactory.eINSTANCE.createLocalVariable();
        				var.setName("return_" + ra.eContainer().eContents().indexOf(ra));
        				var.setReadonly(true);
        				var.setTypeRef(EcoreUtil.copy(f.getTypeRef()));
        				var.setInit(ra.getExp());
        				block.getActions().add(block.getActions().indexOf(ra), var);
        				
        				//Assign ref to this local variable to the former return
                		ref_var = ThingMLFactory.eINSTANCE.createPropertyReference();
                		ref_var.setProperty(var);
                		ra.setExp(ref_var);
        			}
            		            		            		
            		//Send monitoring message
            		final SendAction send = ThingMLFactory.eINSTANCE.createSendAction();
            		send.setMessage(onFunctionCalled);
            		send.setPort(monitoringPort);
            		final StringLiteral name_exp = ThingMLFactory.eINSTANCE.createStringLiteral();
            		name_exp.setStringValue(f.getName());
            		send.getParameters().add(name_exp);
            		final StringLiteral type_exp = ThingMLFactory.eINSTANCE.createStringLiteral();
            		type_exp.setStringValue(f.getTypeRef().getType().getName());
            		send.getParameters().add(type_exp);
            		send.getParameters().add(EcoreUtil.copy(ref_var));
            		block.getActions().add(block.getActions().indexOf(ra), send);
            		
            		for(Parameter p : f.getParameters()) {
            			final PropertyReference p_exp = ThingMLFactory.eINSTANCE.createPropertyReference();
            			p_exp.setProperty(p);
            			send.getParameters().add(p_exp);
            		}            		            		
        		}
        		
        	}
    	}
    }


}
