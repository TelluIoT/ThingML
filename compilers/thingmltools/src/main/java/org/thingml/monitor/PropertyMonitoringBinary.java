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
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.thingml.xtext.helpers.ActionHelper;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.helpers.ThingHelper;
import org.thingml.xtext.thingML.Action;
import org.thingml.xtext.thingML.ActionBlock;
import org.thingml.xtext.thingML.ByteLiteral;
import org.thingml.xtext.thingML.Decrement;
import org.thingml.xtext.thingML.EnumLiteralRef;
import org.thingml.xtext.thingML.Enumeration;
import org.thingml.xtext.thingML.EnumerationLiteral;
import org.thingml.xtext.thingML.Expression;
import org.thingml.xtext.thingML.Increment;
import org.thingml.xtext.thingML.IntegerLiteral;
import org.thingml.xtext.thingML.LocalVariable;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.PrimitiveType;
import org.thingml.xtext.thingML.Property;
import org.thingml.xtext.thingML.PropertyReference;
import org.thingml.xtext.thingML.SendAction;
import org.thingml.xtext.thingML.StringLiteral;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.ThingMLFactory;
import org.thingml.xtext.thingML.TypeRef;
import org.thingml.xtext.thingML.Variable;
import org.thingml.xtext.thingML.VariableAssignment;

public class PropertyMonitoringBinary implements MonitoringAspect {
	
	final Thing thing;
	final Property id;
	final Port monitoringPort;
	final Message msg;
	final TypeRef byteTypeRef;
	final EnumerationLiteral lit;
	
	private int counter = 0;
	
	final StringLiteral empty;

	public PropertyMonitoringBinary(Thing thing, Property id, Port monitoringPort, Message msg, TypeRef byteTypeRef) {
		this.lit = ByteHelper.getLogLiteral(thing, "property_changed");
		this.thing = thing;
		this.id = id;
		this.monitoringPort = monitoringPort;
		this.msg = msg;
		this.byteTypeRef = byteTypeRef;
		
		empty = ThingMLFactory.eINSTANCE.createStringLiteral();
		empty.setStringValue("");
	}
	
	protected int varSize(Variable v) {
		int size = 3; //b[0]=0 (log property), b[1]=instance, b[2]=function
		size += 2*((PrimitiveType)v.getTypeRef().getType()).getByteSize();		
		return size;
	}
	
	@Override
	public void monitor() {
		for(Property p : ThingHelper.allPropertiesInDepth(thing)) {
			if (AnnotatedElementHelper.isDefined(p, "monitor", "not")) continue;
			if (p.getTypeRef().getCardinality() != null) continue;//FIXME: handle arrays			
			
			for(Increment assign : ActionHelper.getAllActions(thing, Increment.class)) {
				if (!(assign.getVar() == p)) continue;
				catchChanges(assign, p);
			}
			
			for(Decrement assign : ActionHelper.getAllActions(thing, Decrement.class)) {
				if (!(assign.getVar() == p)) continue;
				catchChanges(assign, p);
			}
			
			for(VariableAssignment assign : ActionHelper.getAllActions(thing, VariableAssignment.class)) {
				if (!(assign.getProperty() == p)) continue;
				catchChanges(assign, p);
			}

		}
    }

	private void catchChanges(Action assign, Property p) {				
		final ActionBlock block = ThingMLFactory.eINSTANCE.createActionBlock();
    	if (assign.eContainingFeature().getUpperBound() == -1) {//Collection
            final EList list = (EList) assign.eContainer().eGet(assign.eContainingFeature());
            final int index = list.indexOf(assign);
            list.add(index, block);
            list.remove(assign);
        } else {
        	assign.eContainer().eSet(assign.eContainingFeature(), block);
        }
    	
    	final List<Expression> inits = new ArrayList<>();
		
		//before
		final LocalVariable lv = ThingMLFactory.eINSTANCE.createLocalVariable();
		lv.setName("old_" + p.getName() + "_" + counter);
		lv.setTypeRef(EcoreUtil.copy(p.getTypeRef()));
		lv.setReadonly(true);
		final PropertyReference ref = ThingMLFactory.eINSTANCE.createPropertyReference();
		ref.setProperty(p);		
		lv.setInit(ref);
		block.getActions().add(lv);
		
		block.getActions().add(assign);
			
		final int varSize = varSize(p);
		
		final EnumLiteralRef id_ = ThingMLFactory.eINSTANCE.createEnumLiteralRef();
		id_.setLiteral(lit);
		id_.setEnum((Enumeration)lit.eContainer());
		inits.add(id_);
		
		final PropertyReference pr0 = ThingMLFactory.eINSTANCE.createPropertyReference();
		pr0.setProperty(id);
		inits.add(pr0);
		
		final ByteLiteral id = ThingMLFactory.eINSTANCE.createByteLiteral();
		id.setByteValue(Byte.parseByte(AnnotatedElementHelper.annotation(p, "id").get(0)));
		inits.add(id);
		
		inits.addAll(ByteHelper.serializeParam(byteTypeRef, lv));
		final PropertyReference ref2 = ThingMLFactory.eINSTANCE.createPropertyReference();
		ref2.setProperty(p);
		inits.addAll(ByteHelper.serializeParam(byteTypeRef, ref2));
		final LocalVariable array = ByteHelper.arrayInit(p.getName() + "_log_" + counter, byteTypeRef, inits);
		block.getActions().add(array);
								
		final SendAction send = ThingMLFactory.eINSTANCE.createSendAction();
		send.setMessage(msg);
		send.setPort(monitoringPort);
		
		final PropertyReference array_ref = ThingMLFactory.eINSTANCE.createPropertyReference();
		array_ref.setProperty(array);
		send.getParameters().add(array_ref);
		block.getActions().add(send);
		final IntegerLiteral si = ThingMLFactory.eINSTANCE.createIntegerLiteral();
		si.setIntValue(varSize);
		send.getParameters().add(si);
		
		counter++;
	}
}
