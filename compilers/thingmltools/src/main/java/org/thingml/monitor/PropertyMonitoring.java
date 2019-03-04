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

import org.thingml.xtext.constraints.ThingMLHelpers;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.thingML.Message;
import org.thingml.xtext.thingML.Port;
import org.thingml.xtext.thingML.Property;
import org.thingml.xtext.thingML.Thing;
import org.thingml.xtext.thingML.TypeRef;

public class PropertyMonitoring implements MonitoringAspect {
	
	final Thing thing;
	final Property id;
	final Port monitoringPort;
	final Message msg;
	final TypeRef stringTypeRef;

	public PropertyMonitoring(Thing thing, Property id, Port monitoringPort, Message msg, TypeRef stringTypeRef) {
		this.thing = thing;
		this.id = id;
		this.monitoringPort = monitoringPort;
		this.msg = msg;
		this.stringTypeRef = stringTypeRef;
	}
	
	@Override
	public void monitor() {
		for(Property p : ThingMLHelpers.allProperties(thing)) {
			if (AnnotatedElementHelper.isDefined(p, "monitoring", "not")) continue;
			if (p.getTypeRef().getCardinality() != null) continue;//FIXME: handle arrays
        	
        	//TODO: look for all property assigment and send monitoring message
		}
    }


}
