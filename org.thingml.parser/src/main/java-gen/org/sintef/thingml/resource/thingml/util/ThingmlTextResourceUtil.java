/**
 * Copyright (C) 2014 SINTEF <franck.fleurey@sintef.no>
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
package org.sintef.thingml.resource.thingml.util;

/**
 * Class ThingmlTextResourceUtil can be used to perform common tasks on text
 * resources, such as loading and saving resources, as well as, checking them for
 * errors. This class is deprecated and has been replaced by
 * org.sintef.thingml.resource.thingml.util.ThingmlResourceUtil.
 */
public class ThingmlTextResourceUtil {
	
	@Deprecated	
	public static org.sintef.thingml.resource.thingml.mopp.ThingmlResource getResource(org.eclipse.core.resources.IFile file) {
		return org.sintef.thingml.resource.thingml.util.ThingmlResourceUtil.getResource(file);
	}
	
	@Deprecated	
	public static org.sintef.thingml.resource.thingml.mopp.ThingmlResource getResource(java.io.File file, java.util.Map<?,?> options) {
		return org.sintef.thingml.resource.thingml.util.ThingmlResourceUtil.getResource(file, options);
	}
	
	@Deprecated	
	public static org.sintef.thingml.resource.thingml.mopp.ThingmlResource getResource(org.eclipse.emf.common.util.URI uri) {
		return org.sintef.thingml.resource.thingml.util.ThingmlResourceUtil.getResource(uri);
	}
	
	@Deprecated	
	public static org.sintef.thingml.resource.thingml.mopp.ThingmlResource getResource(org.eclipse.emf.common.util.URI uri, java.util.Map<?,?> options) {
		return org.sintef.thingml.resource.thingml.util.ThingmlResourceUtil.getResource(uri, options);
	}
	
}
