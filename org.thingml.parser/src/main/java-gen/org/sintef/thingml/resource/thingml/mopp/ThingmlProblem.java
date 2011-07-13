/**
 * Copyright (C) 2011 SINTEF <franck.fleurey@sintef.no>
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3, 29 June 2007;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sintef.thingml.resource.thingml.mopp;

public class ThingmlProblem implements org.sintef.thingml.resource.thingml.IThingmlProblem {
	
	private String message;
	private org.sintef.thingml.resource.thingml.ThingmlEProblemType type;
	private org.sintef.thingml.resource.thingml.ThingmlEProblemSeverity severity;
	private java.util.Collection<org.sintef.thingml.resource.thingml.IThingmlQuickFix> quickFixes;
	
	public ThingmlProblem(String message, org.sintef.thingml.resource.thingml.ThingmlEProblemType type, org.sintef.thingml.resource.thingml.ThingmlEProblemSeverity severity) {
		this(message, type, severity, java.util.Collections.<org.sintef.thingml.resource.thingml.IThingmlQuickFix>emptySet());
	}
	
	public ThingmlProblem(String message, org.sintef.thingml.resource.thingml.ThingmlEProblemType type, org.sintef.thingml.resource.thingml.ThingmlEProblemSeverity severity, org.sintef.thingml.resource.thingml.IThingmlQuickFix quickFix) {
		this(message, type, severity, java.util.Collections.singleton(quickFix));
	}
	
	public ThingmlProblem(String message, org.sintef.thingml.resource.thingml.ThingmlEProblemType type, org.sintef.thingml.resource.thingml.ThingmlEProblemSeverity severity, java.util.Collection<org.sintef.thingml.resource.thingml.IThingmlQuickFix> quickFixes) {
		super();
		this.message = message;
		this.type = type;
		this.severity = severity;
		this.quickFixes = new java.util.LinkedHashSet<org.sintef.thingml.resource.thingml.IThingmlQuickFix>();
		this.quickFixes.addAll(quickFixes);
	}
	
	public org.sintef.thingml.resource.thingml.ThingmlEProblemType getType() {
		return type;
	}
	
	public org.sintef.thingml.resource.thingml.ThingmlEProblemSeverity getSeverity() {
		return severity;
	}
	
	public String getMessage() {
		return message;
	}
	
	public java.util.Collection<org.sintef.thingml.resource.thingml.IThingmlQuickFix> getQuickFixes() {
		return quickFixes;
	}
	
}
