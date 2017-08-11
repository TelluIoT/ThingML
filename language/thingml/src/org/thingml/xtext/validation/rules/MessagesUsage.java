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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingml.xtext.validation.rules;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.constraints.Types;
import org.thingml.xtext.helpers.ActionHelper;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.helpers.ConfigurationHelper;
import org.thingml.xtext.helpers.StateHelper;
import org.thingml.xtext.helpers.TyperHelper;
import org.thingml.xtext.thingML.AbstractConnector;
import org.thingml.xtext.thingML.CompositeState;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.Expression;
import org.thingml.xtext.thingML.ExternalConnector;
import org.thingml.xtext.thingML.Instance;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.ObjectType;
import org.thingml.xtext.thingML.Parameter;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.SendAction;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.ThingMLModel;
import org.thingml.xtext.thingML.Type;
import org.thingml.xtext.validation.Checker;
import org.thingml.xtext.validation.Rule;

/**
 *
 * @author sintef
 */
public class MessagesUsage extends Rule {

	@Override
	public Checker.InfoType getHighestLevel() {
		return Checker.InfoType.NOTICE;
	}

	@Override
	public String getName() {
		return "Messages Usage";
	}

	@Override
	public String getDescription() {
		return "Check that each message declared as to be sent in port declaration can be sent by the state machine.";
	}

	@Override
	public void check(ThingMLModel model, Checker checker) {
		for (Thing t : ThingMLHelpers.allThings(model)) {
			check(t, checker);
		}
	}

	@Override
	public void check(Configuration cfg, Checker checker) {
		for (Thing t : ConfigurationHelper.allThings(cfg)) {
			check(t, checker);
		}
	}

	private void check(Thing t, Checker checker) {
		for (Port p : ThingMLHelpers.allPorts(t)) {
			for (Message m : p.getSends()) {
				boolean found = false;
				for (SendAction a : ActionHelper.getAllActions(t, SendAction.class)) {
					if (EcoreUtil.equals(a.getMessage(), m)) {
						found = true;
						if (m.getParameters().size() != a.getParameters().size()) {
							final String msg = "Message " + m.getName() + " of Thing " + t.getName()
									+ " is sent with wrong number of parameters. Expected " + m.getParameters().size()
									+ ", called with " + a.getParameters().size();
							checker.addGenericError(msg, a);
						} else {
							for (Parameter pa : m.getParameters()) {
								Expression e = a.getParameters().get(m.getParameters().indexOf(pa));
								Type expected = TyperHelper.getBroadType(pa.getTypeRef().getType());
								Type actual = checker.typeChecker.computeTypeOf(e);
								if (actual != null) {
									if (actual.equals(Types.ERROR_TYPE)) {
										final String msg = "Message " + m.getName() + " of Thing " + t.getName()
												+ " is sent with an erroneous parameter. Expected "
												+ TyperHelper.getBroadType(expected).getName() + ", called with "
												+ TyperHelper.getBroadType(actual).getName();
										checker.addGenericError(msg, a);
									} else if (actual.equals(Types.ANY_TYPE)) {
										final String msg = "Message " + m.getName() + " of Thing " + t.getName()
												+ " is sent with a parameter which cannot be typed. Consider using a cast <exp> as <type>.";
										checker.addGenericWarning(msg, a);
									} else if (!TyperHelper.isA(actual, expected)) {
										final String msg = "Message " + m.getName() + " of Thing " + t.getName()
												+ " is sent with an erroneous parameter. Expected "
												+ TyperHelper.getBroadType(expected).getName() + ", called with "
												+ TyperHelper.getBroadType(actual).getName();
										checker.addGenericError(msg, a);
									}
								}
							}
						}
					}
				}
				if (!found && !t.isFragment()) {
					final String msg = "Port " + p.getName() + " of Thing " + t.getName() + " defines a Message "
							+ m.getName() + " that is never sent. Consider removing message from port " + p.getName()
							+ " or send the message in your logic.";
					checker.addGenericWarning(msg, m);
				}
			}
			if (!t.isFragment()) {
				for (Message m : p.getReceives()) {
					for (CompositeState sm : ThingMLHelpers.allStateMachines(t)) {
						if (StateHelper.allMessageHandlers(sm).get(p) == null
								|| StateHelper.allMessageHandlers(sm).get(p).get(m) == null) {
							final String msg = "Port " + p.getName() + " of Thing " + t.getName()
									+ " defines a Message " + m.getName()
									+ " that is never received. Consider removing message from port " + p.getName()
									+ " or define a handler for the message.";
							checker.addGenericWarning(msg, m);
						}
					}
				}
			}
		}

		// check if message is serializable, only if it exists and instance
		// connected to an external connector
		for (Configuration c : ThingMLHelpers.allConfigurations(ThingMLHelpers.findContainingModel(t))) {
			for (Instance i : c.getInstances()) {
				if (EcoreUtil.equals(i.getType(), t)) {
					for (AbstractConnector conn : c.getConnectors()) {
						if (conn instanceof ExternalConnector) {
							ExternalConnector ext = (ExternalConnector) conn;
							if (EcoreUtil.equals(ext.getInst(), t)) {
								for (Message m : ext.getPort().getSends()) {
									for (Parameter pa : m.getParameters()) {
										if ((pa.getTypeRef().getType() instanceof ObjectType)
												&& !AnnotatedElementHelper.isDefined(pa, "serializable", "true")) {
											checker.addGenericNotice(
													"Message " + m.getName() + " of Thing " + t.getName()
															+ " is not serializable. Parameter " + pa.getName()
															+ " (at least) is not a primitive datatype. If this message is to be sent out on the network, please use only primitive datatypes.",
													pa);
											break;
										}
									}
								}
								for (Message m : ext.getPort().getReceives()) {
									for (Parameter pa : m.getParameters()) {
										if ((pa.getTypeRef().getType() instanceof ObjectType)
												&& !AnnotatedElementHelper.isDefined(pa, "serializable", "true")) {
											checker.addGenericNotice(
													"Message " + m.getName() + " of Thing " + t.getName()
															+ " is not serializable. Parameter " + pa.getName()
															+ " (at least) is not a primitive datatype. If this message is to be sent out on the network, please use only primitive datatypes.",
													pa);
											break;
										}
									}
								}
							}
						}
					}
				}
			}
		}

	}

}
