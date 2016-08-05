/**
 * <copyright>
 * </copyright>
 *
 * 
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
