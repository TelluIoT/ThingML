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
package org.sintef.thingml.resource.thingml.mopp;

/**
 * A basic implementation of the
 * org.sintef.thingml.resource.thingml.IThingmlURIMapping interface that can map
 * identifiers to URIs.
 * 
 * @param <ReferenceType> unused type parameter which is needed to implement
 * org.sintef.thingml.resource.thingml.IThingmlURIMapping.
 */
public class ThingmlURIMapping<ReferenceType> implements org.sintef.thingml.resource.thingml.IThingmlURIMapping<ReferenceType> {
	
	private org.eclipse.emf.common.util.URI uri;
	private String identifier;
	private String warning;
	
	public ThingmlURIMapping(String identifier, org.eclipse.emf.common.util.URI newIdentifier, String warning) {
		super();
		this.uri = newIdentifier;
		this.identifier = identifier;
		this.warning = warning;
	}
	
	public org.eclipse.emf.common.util.URI getTargetIdentifier() {
		return uri;
	}
	
	public String getIdentifier() {
		return identifier;
	}
	
	public String getWarning() {
		return warning;
	}
	
}
