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
 * A utility class that encapsulates some case operations that need to be
 * performed unchecked, because of Java's type erasure.
 */
public class ThingmlListUtil {
	
	@SuppressWarnings("unchecked")	
	public static <T> java.util.List<T> castListUnchecked(Object list) {
		return (java.util.List<T>) list;
	}
	
	public static java.util.List<Object> copySafelyToObjectList(java.util.List<?> list) {
		java.util.Iterator<?> it = list.iterator();
		java.util.List<Object> castedCopy = new java.util.ArrayList<Object>();
		while (it.hasNext()) {
			castedCopy.add(it.next());
		}
		return castedCopy;
	}
}
