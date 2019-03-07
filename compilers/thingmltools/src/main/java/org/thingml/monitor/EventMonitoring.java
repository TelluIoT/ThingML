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
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.thingml.xtext.helpers.ActionHelper;
import org.thingml.xtext.helpers.StateHelper;
import org.thingml.xtext.helpers.ToString;
import org.thingml.xtext.thingML.ActionBlock;
import org.thingml.xtext.thingML.AndExpression;
import org.thingml.xtext.thingML.BooleanLiteral;
import org.thingml.xtext.thingML.CompositeState;
import org.thingml.xtext.thingML.EventReference;
import org.thingml.xtext.thingML.Expression;
import org.thingml.xtext.thingML.ExpressionGroup;
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
import org.thingml.xtext.thingML.State;
import org.thingml.xtext.thingML.StringLiteral;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.ThingMLFactory;
import org.thingml.xtext.thingML.Transition;
import org.thingml.xtext.thingML.TypeRef;
import org.thingml.xtext.thingML.VariableAssignment;

public class EventMonitoring implements MonitoringAspect {

	final StringLiteral empty;
	final StringLiteral comma;
	
	final Thing thing;
	final Property id;
	final Port monitoringPort;
	final Message sent_msg;
	final Message lost_msg;
	final Message handled_msg;
	final TypeRef stringTypeRef;
	
	private static int counter = 0;

	public EventMonitoring(Thing thing, Property id, Port monitoringPort, Message lost_msg, Message handled_msg, Message sent_msg, TypeRef stringTypeRef) {
		this.thing = thing;
		this.id = id;
		this.monitoringPort = monitoringPort;
		this.lost_msg = lost_msg;
		this.handled_msg = handled_msg;
		this.sent_msg = sent_msg;
		this.stringTypeRef = stringTypeRef;
		
		empty = ThingMLFactory.eINSTANCE.createStringLiteral();
		empty.setStringValue("");
		
		comma = ThingMLFactory.eINSTANCE.createStringLiteral();
		comma.setStringValue(",");
	}
	
	@Override
	public void monitor() {
		logSentMessages(thing);
		logHandledMessages(thing.getBehaviour());
		catchLostMessages(thing.getBehaviour());
	}
	
	private void logSentMessages(Thing root) {
		for(SendAction s : ActionHelper.getAllActions(root, SendAction.class)) {
			
			final ActionBlock block = ThingMLFactory.eINSTANCE.createActionBlock();
	    	if (s.eContainingFeature().getUpperBound() == -1) {//Collection
	            final EList list = (EList) s.eContainer().eGet(s.eContainingFeature());
	            final int index = list.indexOf(s);
	            list.add(index, block);
	            list.remove(s);
	        } else {
	        	s.eContainer().eSet(s.eContainingFeature(), block);
	        }

			//create a readonly local variable for each parameter of s (to avoid side-effects e.g. if a param is a function call)
			List<LocalVariable> lvs = new ArrayList<>();
			for(Expression p : s.getParameters()) {
				final LocalVariable lv = ThingMLFactory.eINSTANCE.createLocalVariable();
				lv.setReadonly(true);
				lv.setTypeRef(EcoreUtil.copy(s.getMessage().getParameters().get(s.getParameters().indexOf(p)).getTypeRef()));
				lv.setName(s.getMessage().getParameters().get(s.getParameters().indexOf(p)).getName() + "_" + counter);
				counter++;
				lv.setInit(EcoreUtil.copy(p));
				lvs.add(lv);
				block.getActions().add(lv);
			}						
			
			//update s to use those local variables instead
			final SendAction updatedSend = ThingMLFactory.eINSTANCE.createSendAction();
			updatedSend.setPort(s.getPort());
			updatedSend.setMessage(s.getMessage());
			for(LocalVariable lv : lvs) {
				final PropertyReference pr = ThingMLFactory.eINSTANCE.createPropertyReference();
				pr.setProperty(lv);
				updatedSend.getParameters().add(pr);
			}
			block.getActions().add(updatedSend);
			
			//send log message, also using those local variables
			final SendAction send = ThingMLFactory.eINSTANCE.createSendAction();
			send.setPort(monitoringPort);
			send.setMessage(sent_msg);
								
			final PropertyReference id_ref = ThingMLFactory.eINSTANCE.createPropertyReference();
			id_ref.setProperty(id);
			send.getParameters().add(id_ref);
			final StringLiteral port_exp = ThingMLFactory.eINSTANCE.createStringLiteral();
			port_exp.setStringValue(s.getPort().getName());        	
			send.getParameters().add(port_exp);
			final StringLiteral m_exp = ThingMLFactory.eINSTANCE.createStringLiteral();
			m_exp.setStringValue(s.getMessage().getName());        	
			send.getParameters().add(m_exp);
			
			if (s.getMessage().getParameters().size() > 0) {
				final LocalVariable lv = ThingMLFactory.eINSTANCE.createLocalVariable();
				lv.setName("params_" + counter);
				counter++;
				lv.setTypeRef(EcoreUtil.copy(stringTypeRef));
				lv.setReadonly(true);
				Expression init = EcoreUtil.copy(empty);							
				for(LocalVariable param : lvs) {
					final PlusExpression concat = ThingMLFactory.eINSTANCE.createPlusExpression();	
					final ExpressionGroup group = ThingMLFactory.eINSTANCE.createExpressionGroup();
					final PlusExpression plus_comma = ThingMLFactory.eINSTANCE.createPlusExpression();					
					final PropertyReference r_ref = ThingMLFactory.eINSTANCE.createPropertyReference();
					r_ref.setProperty(param);
					plus_comma.setLhs(r_ref);
					plus_comma.setRhs(EcoreUtil.copy(comma));
					group.setTerm(plus_comma);
					concat.setLhs(init);
					concat.setRhs(group);
					init = concat;
				}
				lv.setInit(init);
				block.getActions().add(lv);
			
				final PropertyReference lv_ref = ThingMLFactory.eINSTANCE.createPropertyReference();
				lv_ref.setProperty(lv);
				send.getParameters().add(lv_ref);  
			} else {
				send.getParameters().add(EcoreUtil.copy(empty));
			}
    		
    		
    		block.getActions().add(send);			
		}
	}
	
	private void logHandledMessages(CompositeState root) {
		final Map<Port, Map<Message, List<Handler>>> handlers = StateHelper.allMessageHandlers(root);
		for(Map<Message, List<Handler>> e : handlers.values()) {
			for(List<Handler> l : e.values()) {
				for(Handler h : l) {
					final ActionBlock block = ThingMLFactory.eINSTANCE.createActionBlock();
					final SendAction send = ThingMLFactory.eINSTANCE.createSendAction();
					
					send.setPort(monitoringPort);
					send.setMessage(handled_msg);
										
					final PropertyReference id_ref = ThingMLFactory.eINSTANCE.createPropertyReference();
					id_ref.setProperty(id);
					send.getParameters().add(id_ref);
					final StringLiteral source_exp = ThingMLFactory.eINSTANCE.createStringLiteral();
					source_exp.setStringValue(((State)h.eContainer()).getName());        	
					send.getParameters().add(source_exp);
					final StringLiteral target_exp = ThingMLFactory.eINSTANCE.createStringLiteral();
					if (h instanceof InternalTransition) {
						target_exp.setStringValue("_");
					} else {
						target_exp.setStringValue(((Transition)h).getTarget().getName());
					}										        	
					send.getParameters().add(target_exp);
					final StringLiteral port_exp = ThingMLFactory.eINSTANCE.createStringLiteral();
					final StringLiteral msg_exp = ThingMLFactory.eINSTANCE.createStringLiteral();
					Expression params_exp = ThingMLFactory.eINSTANCE.createStringLiteral();
					if (h.getEvent() == null) {
						port_exp.setStringValue("_");
						msg_exp.setStringValue("_");
						((StringLiteral)params_exp).setStringValue("_");
					} else {
						final ReceiveMessage rm = (ReceiveMessage)h.getEvent();
						port_exp.setStringValue(rm.getPort().getName());
						msg_exp.setStringValue(rm.getMessage().getName());
						if (rm.getMessage().getParameters().isEmpty()) {
							((StringLiteral)params_exp).setStringValue("_");	
						} else {		
							final LocalVariable lv = ThingMLFactory.eINSTANCE.createLocalVariable();
							lv.setName("params");
							lv.setTypeRef(EcoreUtil.copy(stringTypeRef));
							lv.setReadonly(true);
							Expression init = EcoreUtil.copy(empty);							
							for(Parameter param : rm.getMessage().getParameters()) {
								final PlusExpression concat = ThingMLFactory.eINSTANCE.createPlusExpression();
								final ExpressionGroup group = ThingMLFactory.eINSTANCE.createExpressionGroup();
								final PlusExpression plus_comma = ThingMLFactory.eINSTANCE.createPlusExpression();					
								final EventReference r_ref = ThingMLFactory.eINSTANCE.createEventReference();
								r_ref.setParameter(param);
								r_ref.setReceiveMsg(rm);
								plus_comma.setLhs(r_ref);
								plus_comma.setRhs(EcoreUtil.copy(comma));					
								group.setTerm(plus_comma);
								concat.setLhs(init);
								concat.setRhs(group);
								init = concat;
							}
							lv.setInit(init);
							block.getActions().add(lv);						
							params_exp = ThingMLFactory.eINSTANCE.createPropertyReference();
			    			((PropertyReference)params_exp).setProperty(lv);			    		
						}
					}
					send.getParameters().add(port_exp);
					send.getParameters().add(msg_exp);
					send.getParameters().add(params_exp);											
					
					block.getActions().add(send);
					if (h.getAction() != null) {
						block.getActions().add(h.getAction());
					}
					h.setAction(block);
				}
			}
		}
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
					
					final ActionBlock block = ThingMLFactory.eINSTANCE.createActionBlock();
					if (m.getParameters().size() > 0) {
						final LocalVariable lv = ThingMLFactory.eINSTANCE.createLocalVariable();
						lv.setName("params");
						lv.setTypeRef(EcoreUtil.copy(stringTypeRef));
						lv.setInit(EcoreUtil.copy(empty));											
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
					} else {
						send.getParameters().add(EcoreUtil.copy(empty));
					}
		    		
		    		
		    		block.getActions().add(send);					
					it.setAction(block);										
					root.getInternal().add(it);
				}
			}
		}
	}
	
}
