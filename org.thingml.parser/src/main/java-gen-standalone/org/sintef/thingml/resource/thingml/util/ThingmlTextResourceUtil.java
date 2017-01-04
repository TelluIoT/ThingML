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
package org.sintef.thingml.resource.thingml.util;

/**
 * Class ThingmlTextResourceUtil can be used to perform common tasks on text
 * resources, such as loading and saving resources, as well as, checking them for
 * errors. This class is deprecated and has been replaced by
 * org.sintef.thingml.resource.thingml.util.ThingmlResourceUtil.
 */
public class ThingmlTextResourceUtil {
	
	/**
	 * Use org.sintef.thingml.resource.thingml.util.ThingmlResourceUtil.getResource()
	 * instead.
	 */
	@Deprecated	
	public static org.sintef.thingml.resource.thingml.mopp.ThingmlResource getResource(java.io.File file, java.util.Map<?,?> options) {
		return org.sintef.thingml.resource.thingml.util.ThingmlResourceUtil.getResource(file, options);
	}
	
	/**
	 * Use org.sintef.thingml.resource.thingml.util.ThingmlResourceUtil.getResource()
	 * instead.
	 */
	@Deprecated	
	public static org.sintef.thingml.resource.thingml.mopp.ThingmlResource getResource(org.eclipse.emf.common.util.URI uri) {
		return org.sintef.thingml.resource.thingml.util.ThingmlResourceUtil.getResource(uri);
	}
	
	/**
	 * Use org.sintef.thingml.resource.thingml.util.ThingmlResourceUtil.getResource()
	 * instead.
	 */
	@Deprecated	
	public static org.sintef.thingml.resource.thingml.mopp.ThingmlResource getResource(org.eclipse.emf.common.util.URI uri, java.util.Map<?,?> options) {
		return org.sintef.thingml.resource.thingml.util.ThingmlResourceUtil.getResource(uri, options);
	}
	
}
