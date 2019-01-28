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

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.Parameter;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.Property;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.ThingMLFactory;
import org.thingml.xtext.thingML.TypeRef;

public class PropertyMonitoring implements MonitoringAspect {
	
	final Thing thing;
	final Property id;
	final Port monitoringPort;
	final Thing monitoringMsgs;
	final TypeRef stringTypeRef;

	public PropertyMonitoring(Thing thing, Property id, Port monitoringPort, Thing monitoringMsgs, TypeRef stringTypeRef) {
		this.thing = thing;
		this.id = id;
		this.monitoringPort = monitoringPort;
		this.monitoringMsgs = monitoringMsgs;
		this.stringTypeRef = stringTypeRef;
	}
	
	@Override
	public void monitor() {
		for(Property p : ThingMLHelpers.allProperties(thing)) {
			if (AnnotatedElementHelper.isDefined(p, "monitoring", "not")) continue;
			if (p.getTypeRef().getCardinality() != null) continue;//FIXME: handle arrays
			
			//Update monitoring API
			final Message onUpdate = ThingMLFactory.eINSTANCE.createMessage();
        	onUpdate.setName(p.getName() + "_updated");
        	monitoringMsgs.getMessages().add(onUpdate);
        	monitoringPort.getSends().add(onUpdate);
        	final Parameter onUpdate_old = ThingMLFactory.eINSTANCE.createParameter();
        	onUpdate_old.setName("old");        
        	onUpdate_old.setTypeRef(EcoreUtil.copy(p.getTypeRef()));
        	onUpdate.getParameters().add(onUpdate_old);
        	final Parameter onUpdate_new = ThingMLFactory.eINSTANCE.createParameter();
        	onUpdate_new.setName("current");        
        	onUpdate_new.setTypeRef(EcoreUtil.copy(p.getTypeRef()));
        	onUpdate.getParameters().add(onUpdate_new);
        	
        	//TODO: look for all property assigment and send monitoring message
		}
    }


}
