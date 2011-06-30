/**
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
 * A utility class for pixel conversion.
 */
public class ThingmlPixelConverter {
	
	private org.eclipse.swt.graphics.FontMetrics fFontMetrics;
	
	public ThingmlPixelConverter(org.eclipse.swt.widgets.Control control) {
		org.eclipse.swt.graphics.GC gc = new org.eclipse.swt.graphics.GC(control);
		gc.setFont(control.getFont());
		fFontMetrics= gc.getFontMetrics();
		gc.dispose();
	}
	
	public int convertHeightInCharsToPixels(int chars) {
		return org.eclipse.jface.dialogs.Dialog.convertHeightInCharsToPixels(fFontMetrics, chars);
	}
	
	public int convertHorizontalDLUsToPixels(int dlus) {
		return org.eclipse.jface.dialogs.Dialog.convertHorizontalDLUsToPixels(fFontMetrics, dlus);
	}
	
	public int convertVerticalDLUsToPixels(int dlus) {
		return org.eclipse.jface.dialogs.Dialog.convertVerticalDLUsToPixels(fFontMetrics, dlus);
	}
	
	public int convertWidthInCharsToPixels(int chars) {
		return org.eclipse.jface.dialogs.Dialog.convertWidthInCharsToPixels(fFontMetrics, chars);
	}
	
}
