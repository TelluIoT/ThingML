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
package org.sintef.thingml.resource.thingml.ui;

/**
 * A class for RGB-based color objects.
 */
public class ThingmlColorManager {
	
	protected java.util.Map<org.eclipse.swt.graphics.RGB, org.eclipse.swt.graphics.Color> fColorTable = new java.util.LinkedHashMap<org.eclipse.swt.graphics.RGB, org.eclipse.swt.graphics.Color>(10);
	
	/**
	 * Disposes all colors in the cache.
	 */
	public void dispose() {
		java.util.Iterator<org.eclipse.swt.graphics.Color> e = fColorTable.values().iterator();
		while (e.hasNext()) {
			e.next().dispose();
		}
	}
	
	/**
	 * Constructs and caches the given color.
	 * 
	 * @param rgb The color as org.eclipse.swt.graphics.RGB
	 * 
	 * @return The color (from cache or newly constructed)
	 */
	public org.eclipse.swt.graphics.Color getColor(org.eclipse.swt.graphics.RGB rgb) {
		org.eclipse.swt.graphics.Color color = fColorTable.get(rgb);
		if (color == null) {
			color = new org.eclipse.swt.graphics.Color(org.eclipse.swt.widgets.Display.getCurrent(), rgb);
			fColorTable.put(rgb, color);
		}
		return color;
	}
}
