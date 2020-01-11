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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.ActionHelper;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.helpers.StateHelper;
import org.thingml.xtext.thingML.ActionBlock;
import org.thingml.xtext.thingML.AndExpression;
import org.thingml.xtext.thingML.BooleanLiteral;
import org.thingml.xtext.thingML.ByteLiteral;
import org.thingml.xtext.thingML.CastExpression;
import org.thingml.xtext.thingML.CompositeState;
import org.thingml.xtext.thingML.EnumLiteralRef;
import org.thingml.xtext.thingML.Enumeration;
import org.thingml.xtext.thingML.EnumerationLiteral;
import org.thingml.xtext.thingML.EventReference;
import org.thingml.xtext.thingML.Expression;
import org.thingml.xtext.thingML.ExpressionGroup;
import org.thingml.xtext.thingML.Function;
import org.thingml.xtext.thingML.Handler;
import org.thingml.xtext.thingML.IntegerLiteral;
import org.thingml.xtext.thingML.InternalTransition;
import org.thingml.xtext.thingML.LocalVariable;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.NotExpression;
import org.thingml.xtext.thingML.Parameter;
import org.thingml.xtext.thingML.PlusExpression;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.PrimitiveType;
import org.thingml.xtext.thingML.Property;
import org.thingml.xtext.thingML.PropertyReference;
import org.thingml.xtext.thingML.ReceiveMessage;
import org.thingml.xtext.thingML.SendAction;
import org.thingml.xtext.thingML.State;
import org.thingml.xtext.thingML.StateContainer;
import org.thingml.xtext.thingML.StringLiteral;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.ThingMLFactory;
import org.thingml.xtext.thingML.Transition;
import org.thingml.xtext.thingML.TypeRef;
import org.thingml.xtext.thingML.Variable;
import org.thingml.xtext.thingML.VariableAssignment;

public class EventMonitoringBinary implements MonitoringAspect {
	
	final Thing thing;
	final Property id;
	final Port monitoringPort;
	final Message log_msg;
	final TypeRef byteTypeRef;
	
	private static int counter = 0;

	public EventMonitoringBinary(Thing thing, Property id, Port monitoringPort, Message log_msg, TypeRef byteTypeRef) {
		this.thing = thing;
		this.id = id;
		this.monitoringPort = monitoringPort;
		this.byteTypeRef = byteTypeRef;
		this.log_msg = log_msg;
	}
	
	@Override
	public void monitor() {
		logSentMessages(thing);
		logHandledMessages(thing.getBehaviour());
		catchLostMessages(thing.getBehaviour());
	}
	
	protected byte getPortID(Port port) {
		final String a = AnnotatedElementHelper.firstAnnotation(port, "id");
		return Byte.valueOf(a);
	}
	
	protected byte getStateID(State state) {
		final String a = AnnotatedElementHelper.firstAnnotation(state, "id");
		return Byte.valueOf(a);
	}
	
	protected byte getMessageID(Message msg) {
		final String a = AnnotatedElementHelper.firstAnnotation(msg, "id");
		return Byte.valueOf(a);
	}
	
	protected int messageSize(Message m) {
		int size = 4;		
		for(Parameter p : m.getParameters()) {
			size += ((PrimitiveType)p.getTypeRef().getType()).getByteSize();
		}
		return size;
	}
		
	private void logSentMessages(Thing root) {
		for(SendAction s : ActionHelper.getAllActions(root, SendAction.class)) {
			if (AnnotatedElementHelper.isDefined(s.getPort(), "monitor", "not")) continue;
			
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
				lv.setName(s.getMessage().getParameters().get(s.getParameters().indexOf(p)).getName() + "_" + counter++);
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
			send.setMessage(log_msg);
								
			final Message m = updatedSend.getMessage();
			final int size = messageSize(m);
			final LocalVariable array = ByteHelper.arrayInit(size, m.getName() + "sent_log_" + counter++, EcoreUtil.copy(byteTypeRef));
			block.getActions().add(array);
			final PropertyReference pr = ThingMLFactory.eINSTANCE.createPropertyReference();
			pr.setProperty(array);
			send.getParameters().add(pr);
			final IntegerLiteral is = ThingMLFactory.eINSTANCE.createIntegerLiteral();
			is.setIntValue(size);
			send.getParameters().add(is);
			
			int index = 0;
			final EnumerationLiteral lit = ByteHelper.getLogLiteral(thing, "message_sent");
			final EnumLiteralRef litRef = ThingMLFactory.eINSTANCE.createEnumLiteralRef();
			litRef.setLiteral(lit);
			litRef.setEnum((Enumeration)lit.eContainer());
			final VariableAssignment va = ByteHelper.insertAt(array, index++, litRef);
			block.getActions().add(va);
			
			final PropertyReference id_ref = ThingMLFactory.eINSTANCE.createPropertyReference();
			id_ref.setProperty(id);
			final VariableAssignment va0 = ByteHelper.insertAt(array, index++, id_ref);
			block.getActions().add(va0);
						
			final ByteLiteral port_exp = ThingMLFactory.eINSTANCE.createByteLiteral();
			port_exp.setByteValue(getPortID(s.getPort()));
			final VariableAssignment va1 = ByteHelper.insertAt(array, index++, port_exp);
			block.getActions().add(va1);
						
			final ByteLiteral m_exp = ThingMLFactory.eINSTANCE.createByteLiteral();
			m_exp.setByteValue(getMessageID(s.getMessage()));
			final VariableAssignment va2 = ByteHelper.insertAt(array, index++, m_exp);
			block.getActions().add(va2);
			
			for(LocalVariable param : lvs) {
				ByteHelper.serializeParam(byteTypeRef, param, block, block.getActions().size(), index++, array);
			}
    		
    		block.getActions().add(send);			
		}
	}
	
	private void logHandledMessages(CompositeState root) {
		final Map<Port, Map<Message, List<Handler>>> handlers = StateHelper.allMessageHandlers(root);
		for(Map<Message, List<Handler>> e : handlers.values()) {
			for(List<Handler> l : e.values()) {
				for(Handler h : l) {
					if (h.getEvent() != null && (AnnotatedElementHelper.isDefined(((ReceiveMessage)h.getEvent()).getPort(), "monitor", "not"))) continue;
					
					final ActionBlock block = ThingMLFactory.eINSTANCE.createActionBlock();
					final SendAction send = ThingMLFactory.eINSTANCE.createSendAction();					
					send.setPort(monitoringPort);
					send.setMessage(log_msg);
					
					final int size = ((h.getEvent() == null)? 4 : messageSize(((ReceiveMessage)h.getEvent()).getMessage())) + 2; //+2 for source and target states
					final String name = (h.getEvent() == null)? "empty_handled_log" + counter++ : ((ReceiveMessage)h.getEvent()).getMessage().getName() + "handled_log_" + counter++;
					
					final LocalVariable array = ByteHelper.arrayInit(size, name, EcoreUtil.copy(byteTypeRef));
					block.getActions().add(array);
					final PropertyReference pr = ThingMLFactory.eINSTANCE.createPropertyReference();
					pr.setProperty(array);
					send.getParameters().add(pr);
					final IntegerLiteral is = ThingMLFactory.eINSTANCE.createIntegerLiteral();
					is.setIntValue(size);
					send.getParameters().add(is);
					
					int index = 0;
					final EnumerationLiteral lit = ByteHelper.getLogLiteral(thing, "message_handled");
					final EnumLiteralRef litRef = ThingMLFactory.eINSTANCE.createEnumLiteralRef();
					litRef.setLiteral(lit);
					litRef.setEnum((Enumeration)lit.eContainer());
					final VariableAssignment va = ByteHelper.insertAt(array, index++, litRef);
					block.getActions().add(va);
					
					final PropertyReference id_ref = ThingMLFactory.eINSTANCE.createPropertyReference();
					id_ref.setProperty(id);
					final VariableAssignment va0 = ByteHelper.insertAt(array, index++, id_ref);
					block.getActions().add(va0);
					
					final byte portID = (h.getEvent() == null)? 0 : getPortID(((ReceiveMessage)h.getEvent()).getPort());
					final byte msgID = (h.getEvent() == null)? 0 : getMessageID(((ReceiveMessage)h.getEvent()).getMessage());
					
					final ByteLiteral port_exp = ThingMLFactory.eINSTANCE.createByteLiteral();
					port_exp.setByteValue(portID);
					final VariableAssignment va1 = ByteHelper.insertAt(array, index++, port_exp);
					block.getActions().add(va1);
								
					final ByteLiteral m_exp = ThingMLFactory.eINSTANCE.createByteLiteral();
					m_exp.setByteValue(msgID);
					final VariableAssignment va2 = ByteHelper.insertAt(array, index++, m_exp);
					block.getActions().add(va2);
					
					final ByteLiteral source_exp = ThingMLFactory.eINSTANCE.createByteLiteral();
					source_exp.setByteValue(getStateID((State)h.eContainer()));
					final VariableAssignment va3 = ByteHelper.insertAt(array, index++, source_exp);
					block.getActions().add(va3);
					
					if (h instanceof InternalTransition) {
						final ByteLiteral target_exp = ThingMLFactory.eINSTANCE.createByteLiteral();
						target_exp.setByteValue((byte)0);
						final VariableAssignment va4 = ByteHelper.insertAt(array, index++, target_exp);
						block.getActions().add(va4);
					} else {
						final ByteLiteral target_exp = ThingMLFactory.eINSTANCE.createByteLiteral();
						target_exp.setByteValue(getStateID(((Transition)h).getTarget()));
						final VariableAssignment va4 = ByteHelper.insertAt(array, index++, target_exp);
						block.getActions().add(va4);
					}							
					
					if (h.getEvent() != null) {
						final ReceiveMessage rm = (ReceiveMessage)h.getEvent();
						for(Parameter param : rm.getMessage().getParameters()) {
							final EventReference r_ref = ThingMLFactory.eINSTANCE.createEventReference();
							r_ref.setParameter(param);
							r_ref.setReceiveMsg(rm);
							final LocalVariable var = ThingMLFactory.eINSTANCE.createLocalVariable();
							var.setName("ref_" + param.getName() + counter++);
							var.setInit(r_ref);
							var.setReadonly(true);
							var.setTypeRef(EcoreUtil.copy(param.getTypeRef()));
							block.getActions().add(var);
							ByteHelper.serializeParam(byteTypeRef, var, block, block.getActions().size(), index, array);
							final long s = ((PrimitiveType)param.getTypeRef().getType()).getByteSize();
							index += s;
						}
					}
																
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
				if (AnnotatedElementHelper.isDefined(p, "monitor", "not") || AnnotatedElementHelper.isDefined(m, "monitor", "not")) continue;
				
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
							final ExpressionGroup group = ThingMLFactory.eINSTANCE.createExpressionGroup();
							final AndExpression and = ThingMLFactory.eINSTANCE.createAndExpression();
							and.setLhs(guard);
							final NotExpression not = ThingMLFactory.eINSTANCE.createNotExpression();
							final ExpressionGroup not_group = ThingMLFactory.eINSTANCE.createExpressionGroup();
							not_group.setTerm(EcoreUtil.copy(h.getGuard()));
							not.setTerm(not_group);
							and.setRhs(not);
							group.setTerm(and);
							guard = group;
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
					
					//Update the event references in the guard
					for(EventReference ref : ThingMLHelpers.getAllExpressions(guard, EventReference.class)) {
						final Parameter currentParam = ref.getParameter();
						Parameter newParam = null;
						for(Parameter param : m.getParameters()) {
							if (param.getName().equals(currentParam.getName())) {
								newParam = param;
								break;
							}
						}
						ref.setReceiveMsg(rm);
						ref.setParameter(newParam);
					}
					
					final ActionBlock block = ThingMLFactory.eINSTANCE.createActionBlock();
					final SendAction send = ThingMLFactory.eINSTANCE.createSendAction();					
					send.setPort(monitoringPort);
					send.setMessage(log_msg);
					
					final int size = messageSize(m);
					final String name = m.getName() + "lost_log_" + counter++;
					
					final LocalVariable array = ByteHelper.arrayInit(size, name, EcoreUtil.copy(byteTypeRef));
					block.getActions().add(array);
					final PropertyReference pr = ThingMLFactory.eINSTANCE.createPropertyReference();
					pr.setProperty(array);
					send.getParameters().add(pr);
					final IntegerLiteral is = ThingMLFactory.eINSTANCE.createIntegerLiteral();
					is.setIntValue(size);
					send.getParameters().add(is);
					
					int index = 0;
					final EnumerationLiteral lit = ByteHelper.getLogLiteral(thing, "message_lost");
					final EnumLiteralRef litRef = ThingMLFactory.eINSTANCE.createEnumLiteralRef();
					litRef.setLiteral(lit);
					litRef.setEnum((Enumeration)lit.eContainer());
					final VariableAssignment va = ByteHelper.insertAt(array, index++, litRef);
					block.getActions().add(va);
					
					final PropertyReference id_ref = ThingMLFactory.eINSTANCE.createPropertyReference();
					id_ref.setProperty(id);
					final VariableAssignment va0 = ByteHelper.insertAt(array, index++, id_ref);
					block.getActions().add(va0);
					
					final byte portID = getPortID(p);
					final byte msgID = getMessageID(m);
					
					final ByteLiteral port_exp = ThingMLFactory.eINSTANCE.createByteLiteral();
					port_exp.setByteValue(portID);
					final VariableAssignment va1 = ByteHelper.insertAt(array, index++, port_exp);
					block.getActions().add(va1);
								
					final ByteLiteral m_exp = ThingMLFactory.eINSTANCE.createByteLiteral();
					m_exp.setByteValue(msgID);
					final VariableAssignment va2 = ByteHelper.insertAt(array, index++, m_exp);
					block.getActions().add(va2);										
											
					for(Parameter param : rm.getMessage().getParameters()) {
						final EventReference r_ref = ThingMLFactory.eINSTANCE.createEventReference();
						r_ref.setParameter(param);
						r_ref.setReceiveMsg(rm);
						final LocalVariable var = ThingMLFactory.eINSTANCE.createLocalVariable();
						var.setName("ref_" + param.getName() + counter++);
						var.setInit(r_ref);
						var.setReadonly(true);
						var.setTypeRef(EcoreUtil.copy(param.getTypeRef()));
						block.getActions().add(var);
						ByteHelper.serializeParam(byteTypeRef, var, block, block.getActions().size(), index, array);
						final long s = ((PrimitiveType)param.getTypeRef().getType()).getByteSize();
						index += s;
					}
							    		
		    		block.getActions().add(send);					
					it.setAction(block);										
					root.getInternal().add(it);
				}
			}
		}
	}
	
}
