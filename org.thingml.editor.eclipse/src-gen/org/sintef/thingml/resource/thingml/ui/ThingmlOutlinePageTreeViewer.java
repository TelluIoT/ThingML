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
 * This custom implementation of a TreeViewer expands the tree automatically up to
 * a specified depth.
 */
public class ThingmlOutlinePageTreeViewer extends org.eclipse.jface.viewers.TreeViewer {
	
	boolean suppressNotifications = false;
	
	public ThingmlOutlinePageTreeViewer(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
	}
	
	public void setSelection(org.eclipse.jface.viewers.ISelection selection, boolean reveal) {
		if (selection instanceof org.sintef.thingml.resource.thingml.ui.ThingmlEObjectSelection) {
			suppressNotifications = true;
			super.setSelection(selection, reveal);
			suppressNotifications = false;
		}
		else {
			super.setSelection(selection, reveal);
		}
	}
	
	protected void handleSelect(org.eclipse.swt.events.SelectionEvent event) {
		if (event.item == null) {
			// In the cases of an invalid document, the tree widget in the outline might fire
			// an event (with item == null) without user interaction. We do not want to react
			// to that event.
		}
		else {
			super.handleSelect(event);
		}
	}
	
	protected void handleInvalidSelection(org.eclipse.jface.viewers.ISelection selection, org.eclipse.jface.viewers.ISelection newSelection) {
		// this may not fire a selection changed event to avoid cyclic events between
		// editor and outline
	}
	
	public void refresh(Object element, boolean updateLabels) {
		super.refresh(element, updateLabels);
		expandToLevel(getAutoExpandLevel());
	}
	
	public void refresh(Object element) {
		super.refresh(element);
		expandToLevel(getAutoExpandLevel());
	}
	
	public void refresh() {
		super.refresh();
		expandToLevel(getAutoExpandLevel());
	}
	
	public void refresh(boolean updateLabels) {
		super.refresh(updateLabels);
		expandToLevel(getAutoExpandLevel());
	}
	
	public void expandToLevel(int level) {
		// we need to catch exceptions here, because refreshing the outline does sometimes
		// cause the LabelProviders to throw exceptions, if the model is in some
		// inconsistent state.
		try {
			super.expandToLevel(level);
		} catch (Exception e) {
			org.sintef.thingml.resource.thingml.mopp.ThingmlPlugin.logError("Exception while refreshing outline view", e);
		}
	}
	
	protected void fireSelectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent event) {
		if (suppressNotifications == true) return;
		super.fireSelectionChanged(event);
	}
	
}
