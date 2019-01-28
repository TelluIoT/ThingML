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
import org.thingml.xtext.thingML.ExpressionGroup;
import org.thingml.xtext.thingML.Function;
import org.thingml.xtext.thingML.LocalVariable;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.Parameter;
import org.thingml.xtext.thingML.PlusExpression;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.Property;
import org.thingml.xtext.thingML.PropertyReference;
import org.thingml.xtext.thingML.ReturnAction;
import org.thingml.xtext.thingML.SendAction;
import org.thingml.xtext.thingML.StringLiteral;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.ThingMLFactory;
import org.thingml.xtext.thingML.TypeRef;

public class FunctionMonitoring implements MonitoringAspect {
	
	final StringLiteral empty;
	final StringLiteral void_;
	
	final Message onFunctionCalled;
	
	final Thing thing;
	final Property id;
	final Port monitoringPort;
	final Thing monitoringMsgs;
	final TypeRef stringTypeRef;
	
	public FunctionMonitoring(Thing thing, Property id, Port monitoringPort, Thing monitoringMsgs, TypeRef stringTypeRef) {
		this.thing = thing;
		this.id = id;
		this.monitoringPort = monitoringPort;
		this.monitoringMsgs = monitoringMsgs;
		this.stringTypeRef = stringTypeRef;
		
		empty = ThingMLFactory.eINSTANCE.createStringLiteral();
		empty.setStringValue("");
	
		void_ = ThingMLFactory.eINSTANCE.createStringLiteral();
		void_.setStringValue("void_");
		
		//Update monitoring API
		onFunctionCalled = ThingMLFactory.eINSTANCE.createMessage();
    	onFunctionCalled.setName("function_called");
    	monitoringMsgs.getMessages().add(onFunctionCalled);
    	monitoringPort.getSends().add(onFunctionCalled);
    	final Parameter onFunctionCalled_id = ThingMLFactory.eINSTANCE.createParameter();
    	onFunctionCalled_id.setName("id");        
    	onFunctionCalled_id.setTypeRef(EcoreUtil.copy(stringTypeRef));
    	onFunctionCalled.getParameters().add(onFunctionCalled_id);
    	final Parameter onFunctionCalled_name = ThingMLFactory.eINSTANCE.createParameter();
    	onFunctionCalled_name.setName("name");        
    	onFunctionCalled_name.setTypeRef(EcoreUtil.copy(stringTypeRef));
    	onFunctionCalled.getParameters().add(onFunctionCalled_name);
    	final Parameter onFunctionCalled_type = ThingMLFactory.eINSTANCE.createParameter();
    	onFunctionCalled_type.setName("ty");        
    	onFunctionCalled_type.setTypeRef(EcoreUtil.copy(stringTypeRef));
    	onFunctionCalled.getParameters().add(onFunctionCalled_type);
    	final Parameter onFunctionCalled_return = ThingMLFactory.eINSTANCE.createParameter();
    	onFunctionCalled_return.setName("returns");        
    	onFunctionCalled_return.setTypeRef(EcoreUtil.copy(stringTypeRef));
    	onFunctionCalled.getParameters().add(onFunctionCalled_return);
    	final Parameter onFunctionCalled_p = ThingMLFactory.eINSTANCE.createParameter();
    	onFunctionCalled_p.setName("params");        
    	onFunctionCalled_p.setTypeRef(EcoreUtil.copy(stringTypeRef));
    	onFunctionCalled.getParameters().add(onFunctionCalled_p);
	}

	@Override
	public void monitor() {    	
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
        		}        		
        		final Action send = buildSendAction(f, null);        		
        		block.getActions().add(0, send);
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
        			
        			PropertyReference ref_var = ThingMLFactory.eINSTANCE.createPropertyReference();
                	ref_var.setProperty(var_return);
                	ra.setExp(ref_var);   
        			
        			final LocalVariable var = ThingMLFactory.eINSTANCE.createLocalVariable();
        			var.setName("return_as_string" + ra.eContainer().eContents().indexOf(ra));
        			var.setReadonly(true);
        			var.setTypeRef(EcoreUtil.copy(stringTypeRef));
        			final PlusExpression string_return = ThingMLFactory.eINSTANCE.createPlusExpression();
        			string_return.setLhs(EcoreUtil.copy(empty));
        			final ExpressionGroup g = ThingMLFactory.eINSTANCE.createExpressionGroup();
        			g.setTerm(EcoreUtil.copy(ref_var));
        			string_return.setRhs(g);
        			var.setInit(string_return);
        			block.getActions().add(block.getActions().indexOf(ra), var_return);
        			block.getActions().add(block.getActions().indexOf(ra), var);
        				        			     			
            		PropertyReference ref_var_string = ThingMLFactory.eINSTANCE.createPropertyReference();
                	ref_var_string.setProperty(var);
                	
        			final Action send = buildSendAction(f, ref_var_string);        		
            		block.getActions().add(block.getActions().indexOf(ra), send);    		            		
        		}
        		
        	}
    	}
    }


	private Action buildSendAction(Function f, PropertyReference ref_var) {
		final SendAction send = ThingMLFactory.eINSTANCE.createSendAction();
		send.setMessage(onFunctionCalled);
		send.setPort(monitoringPort);
		final PropertyReference id_ref = ThingMLFactory.eINSTANCE.createPropertyReference();
		id_ref.setProperty(id);
		send.getParameters().add(id_ref);
		final StringLiteral name_exp = ThingMLFactory.eINSTANCE.createStringLiteral();
		name_exp.setStringValue(f.getName());        	
		send.getParameters().add(name_exp);
		if (f.getTypeRef() == null) {
			send.getParameters().add(EcoreUtil.copy(void_));
			send.getParameters().add(EcoreUtil.copy(empty));
		} else {
			final StringLiteral type_exp = ThingMLFactory.eINSTANCE.createStringLiteral();
    		type_exp.setStringValue(f.getTypeRef().getType().getName());
			send.getParameters().add(type_exp);
			send.getParameters().add(ref_var);
		}
		
		if (f.getParameters().isEmpty()) {
			send.getParameters().add(EcoreUtil.copy(empty));
			return send;
		} else {//FIXME: this will only work for target languages where we can + strings (all except posix/arduino as of now)
			final LocalVariable lv = ThingMLFactory.eINSTANCE.createLocalVariable();
			lv.setName("params");
			lv.setTypeRef(EcoreUtil.copy(stringTypeRef));
			PlusExpression concat = ThingMLFactory.eINSTANCE.createPlusExpression();
			concat.setLhs(EcoreUtil.copy(empty));    				
			for(Parameter p : f.getParameters()) { //FIXME
				final String name = (f.getParameters().indexOf(p)==0)? p.getName() + "=" : ", " + p.getName() + "="; 				
				final ExpressionGroup group = ThingMLFactory.eINSTANCE.createExpressionGroup();				
				PlusExpression concat_p = ThingMLFactory.eINSTANCE.createPlusExpression();
    			final StringLiteral p_i_name = ThingMLFactory.eINSTANCE.createStringLiteral();
    			p_i_name.setStringValue(name);
    			final PropertyReference p_i_ref = ThingMLFactory.eINSTANCE.createPropertyReference();
    			p_i_ref.setProperty(p);
    			concat_p.setLhs(p_i_name);
    			concat_p.setRhs(p_i_ref);
    			group.setTerm(concat_p);
    			concat.setRhs(group);    			    			
    			if (f.getParameters().indexOf(p) != f.getParameters().size()-1) {
    				final PlusExpression concat_up = ThingMLFactory.eINSTANCE.createPlusExpression();
    				concat_up.setLhs(concat);
    				concat = concat_up;
    			}
    		}
			
			lv.setInit(concat);
    		final PropertyReference lv_ref = ThingMLFactory.eINSTANCE.createPropertyReference();
    		lv_ref.setProperty(lv);
    		send.getParameters().add(lv_ref);
    		
    		final ActionBlock block = ThingMLFactory.eINSTANCE.createActionBlock();
    		block.getActions().add(lv);
    		block.getActions().add(send);
    		return block;
		}
	}
	
}
