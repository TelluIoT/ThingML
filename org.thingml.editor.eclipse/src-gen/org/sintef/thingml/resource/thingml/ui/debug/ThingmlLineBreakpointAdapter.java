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
package org.sintef.thingml.resource.thingml.ui.debug;

public class ThingmlLineBreakpointAdapter implements org.eclipse.debug.ui.actions.IToggleBreakpointsTarget {
	
	public void toggleLineBreakpoints(org.eclipse.ui.IWorkbenchPart part, org.eclipse.jface.viewers.ISelection selection) throws org.eclipse.core.runtime.CoreException {
		org.eclipse.ui.texteditor.ITextEditor textEditor = getEditor(part);
		if (textEditor != null) {
			org.eclipse.core.resources.IResource resource = (org.eclipse.core.resources.IResource) textEditor.getEditorInput().getAdapter(org.eclipse.core.resources.IResource.class);
			org.eclipse.jface.text.ITextSelection textSelection = (org.eclipse.jface.text.ITextSelection) selection;
			int lineNumber = textSelection.getStartLine();
			org.eclipse.debug.core.model.IBreakpoint[] breakpoints = org.eclipse.debug.core.DebugPlugin.getDefault().getBreakpointManager().getBreakpoints(org.sintef.thingml.resource.thingml.mopp.ThingmlPlugin.DEBUG_MODEL_ID);
			for (int i = 0; i < breakpoints.length; i++) {
				org.eclipse.debug.core.model.IBreakpoint breakpoint = breakpoints[i];
				if (resource.equals(breakpoint.getMarker().getResource())) {
					if (((org.eclipse.debug.core.model.ILineBreakpoint)breakpoint).getLineNumber() == (lineNumber + 1)) {
						// remove
						breakpoint.delete();
						return;
					}
				}
			}
			// create line breakpoint (document line numbers start at 0)
			org.sintef.thingml.resource.thingml.debug.ThingmlLineBreakpoint lineBreakpoint = new org.sintef.thingml.resource.thingml.debug.ThingmlLineBreakpoint(resource, lineNumber + 1);
			org.eclipse.debug.core.DebugPlugin.getDefault().getBreakpointManager().addBreakpoint(lineBreakpoint);
		}
	}
	
	public boolean canToggleLineBreakpoints(org.eclipse.ui.IWorkbenchPart part, org.eclipse.jface.viewers.ISelection selection) {
		org.eclipse.ui.texteditor.ITextEditor editor = getEditor(part);
		return editor != null;
	}
	
	/**
	 * Returns the editor being used, associated with the given part, or
	 * <code>null</code> if none.
	 * 
	 * @param part workbench part
	 */
	private org.eclipse.ui.texteditor.ITextEditor getEditor(org.eclipse.ui.IWorkbenchPart part) {
		if (part instanceof org.eclipse.ui.texteditor.ITextEditor) {
			org.eclipse.ui.texteditor.ITextEditor editorPart = (org.eclipse.ui.texteditor.ITextEditor) part;
			org.eclipse.core.resources.IResource resource = (org.eclipse.core.resources.IResource) editorPart.getEditorInput().getAdapter(org.eclipse.core.resources.IResource.class);
			if (resource != null) {
				String extension = resource.getFileExtension();
				if (extension != null && extension.equals(new org.sintef.thingml.resource.thingml.mopp.ThingmlMetaInformation().getSyntaxName())) {
					return editorPart;
				}
			}
		}
		return null;
	}
	
	public void toggleMethodBreakpoints(org.eclipse.ui.IWorkbenchPart part, org.eclipse.jface.viewers.ISelection selection) throws org.eclipse.core.runtime.CoreException {
	}
	
	public boolean canToggleMethodBreakpoints(org.eclipse.ui.IWorkbenchPart part, org.eclipse.jface.viewers.ISelection selection) {
		return false;
	}
	
	public void toggleWatchpoints(org.eclipse.ui.IWorkbenchPart part, org.eclipse.jface.viewers.ISelection selection) throws org.eclipse.core.runtime.CoreException {
	}
	
	public boolean canToggleWatchpoints(org.eclipse.ui.IWorkbenchPart part, org.eclipse.jface.viewers.ISelection selection) {
		return false;
	}
	
}
