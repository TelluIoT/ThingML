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
package org.sintef.thingml.resource.thingml.ui.debug;

public class ThingmlDebugModelPresentation implements org.eclipse.debug.ui.IDebugModelPresentation {
	
	public ThingmlDebugModelPresentation() {
		super();
	}
	
	public void addListener(org.eclipse.jface.viewers.ILabelProviderListener listener) {
		// do nothing
	}
	
	public void dispose() {
		// do nothing
	}
	
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}
	
	public void removeListener(org.eclipse.jface.viewers.ILabelProviderListener listener) {
		// do nothing
	}
	
	public org.eclipse.ui.IEditorInput getEditorInput(Object element) {
		if (element instanceof org.eclipse.core.resources.IFile) {
			return new org.eclipse.ui.part.FileEditorInput((org.eclipse.core.resources.IFile) element);
		} else if (element instanceof org.eclipse.debug.core.model.ILineBreakpoint) {
			return new org.eclipse.ui.part.FileEditorInput((org.eclipse.core.resources.IFile) ((org.eclipse.debug.core.model.ILineBreakpoint) element).getMarker().getResource());
		} else {
			return null;
		}
	}
	
	public String getEditorId(org.eclipse.ui.IEditorInput input, Object element) {
		if (element instanceof org.eclipse.core.resources.IFile || element instanceof org.eclipse.debug.core.model.ILineBreakpoint) {
			return org.sintef.thingml.resource.thingml.ui.ThingmlUIPlugin.EDITOR_ID;
		}
		return null;
	}
	
	public void setAttribute(String attribute, Object value) {
		// not supported
	}
	
	public org.eclipse.swt.graphics.Image getImage(Object element) {
		return null;
	}
	
	public String getText(Object element) {
		return null;
	}
	
	public void computeDetail(org.eclipse.debug.core.model.IValue value, org.eclipse.debug.ui.IValueDetailListener listener) {
		// listener.detailComputed(value, "detail");
	}
	
}
