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

import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.thingml.xtext.helpers.StateHelper;
import org.thingml.xtext.thingML.ActionBlock;
import org.thingml.xtext.thingML.AndExpression;
import org.thingml.xtext.thingML.BooleanLiteral;
import org.thingml.xtext.thingML.CompositeState;
import org.thingml.xtext.thingML.EventReference;
import org.thingml.xtext.thingML.Expression;
import org.thingml.xtext.thingML.Handler;
import org.thingml.xtext.thingML.InternalTransition;
import org.thingml.xtext.thingML.LocalVariable;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.NotExpression;
import org.thingml.xtext.thingML.Parameter;
import org.thingml.xtext.thingML.PlusExpression;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.Property;
import org.thingml.xtext.thingML.PropertyReference;
import org.thingml.xtext.thingML.ReceiveMessage;
import org.thingml.xtext.thingML.SendAction;
import org.thingml.xtext.thingML.StringLiteral;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.ThingMLFactory;
import org.thingml.xtext.thingML.TypeRef;
import org.thingml.xtext.thingML.VariableAssignment;

public class EventMonitoring implements MonitoringAspect {

	final StringLiteral empty;
	
	final Thing thing;
	final Property id;
	final Port monitoringPort;
	final Message lost_msg;
	final TypeRef stringTypeRef;

	public EventMonitoring(Thing thing, Property id, Port monitoringPort, Message lost_msg, TypeRef stringTypeRef) {
		this.thing = thing;
		this.id = id;
		this.monitoringPort = monitoringPort;
		this.lost_msg = lost_msg;
		this.stringTypeRef = stringTypeRef;
		
		empty = ThingMLFactory.eINSTANCE.createStringLiteral();
		empty.setStringValue("");
	}
	
	@Override
	public void monitor() {
		catchLostMessages(thing.getBehaviour());
		//TODO: 
	}
	
	/**
	 * Adds handler in the top state machine for all events.
	 * This will catch all events that are not handled at a lower level
	 */
	private void catchLostMessages(CompositeState root) {
		for(Port p : thing.getPorts()) {
			for(Message m : p.getReceives()) {
				Expression guard = ThingMLFactory.eINSTANCE.createBooleanLiteral();
				((BooleanLiteral) guard).setBoolValue(true);
				for(Handler h : root.getInternal()) {
					if (h.getEvent() instanceof ReceiveMessage) {
						final ReceiveMessage rm = (ReceiveMessage) h.getEvent();
						if (!(rm.getPort() == p) || !(rm.getMessage() == m)) continue;
						if (h.getGuard() == null) { //if there exists a non-guard event for p,m, the event will always be handled (or never lost)
							guard = null;
							break;
						} else {//if there is a guarded event for p,m, it is lost iff guard is false (note: there might be several handlers for the same p,m, hence the guard = guard && !h.guard)
							final AndExpression and = ThingMLFactory.eINSTANCE.createAndExpression();
							and.setLhs(guard);
							final NotExpression not = ThingMLFactory.eINSTANCE.createNotExpression();
							not.setTerm(EcoreUtil.copy(h.getGuard()));
							and.setRhs(not);
							guard = and;
						}
					}
				}
				
				if (guard != null) {
					final InternalTransition it = ThingMLFactory.eINSTANCE.createInternalTransition();
					final ReceiveMessage rm = ThingMLFactory.eINSTANCE.createReceiveMessage();
					rm.setName("e");
					rm.setPort(p);
					rm.setMessage(m);
					it.setEvent(rm);
					it.setGuard(guard);
					final SendAction send = ThingMLFactory.eINSTANCE.createSendAction();
					send.setPort(monitoringPort);
					send.setMessage(lost_msg);
										
					final PropertyReference id_ref = ThingMLFactory.eINSTANCE.createPropertyReference();
					id_ref.setProperty(id);
					send.getParameters().add(id_ref);
					final StringLiteral port_exp = ThingMLFactory.eINSTANCE.createStringLiteral();
					port_exp.setStringValue(p.getName());        	
					send.getParameters().add(port_exp);
					final StringLiteral m_exp = ThingMLFactory.eINSTANCE.createStringLiteral();
					m_exp.setStringValue(m.getName());        	
					send.getParameters().add(m_exp);
																				
					final LocalVariable lv = ThingMLFactory.eINSTANCE.createLocalVariable();
					lv.setName("params");
					lv.setTypeRef(EcoreUtil.copy(stringTypeRef));
					lv.setInit(EcoreUtil.copy(empty));					
					final ActionBlock block = ThingMLFactory.eINSTANCE.createActionBlock();
		    		block.getActions().add(lv);
					
					for(Parameter param : m.getParameters()) {
						final VariableAssignment assig = ThingMLFactory.eINSTANCE.createVariableAssignment();
						final PlusExpression concat = ThingMLFactory.eINSTANCE.createPlusExpression();
						final PropertyReference l_ref = ThingMLFactory.eINSTANCE.createPropertyReference();
						l_ref.setProperty(lv);
						final EventReference r_ref = ThingMLFactory.eINSTANCE.createEventReference();
						r_ref.setParameter(param);
						r_ref.setReceiveMsg(rm);
						concat.setLhs(l_ref);
						concat.setRhs(r_ref);
						assig.setProperty(lv);
						assig.setExpression(concat);
						block.getActions().add(assig);
					}
					
		    		final PropertyReference lv_ref = ThingMLFactory.eINSTANCE.createPropertyReference();
		    		lv_ref.setProperty(lv);
		    		send.getParameters().add(lv_ref);    		    	
		    		block.getActions().add(send);					
					it.setAction(block);										
					root.getInternal().add(it);
				}
			}
		}
	}
	
}
