/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.sintef.thingml.resource.thingml.ui;

public class ThingmlOutlinePageLinkWithEditorAction extends org.sintef.thingml.resource.thingml.ui.AbstractThingmlOutlinePageAction {
	
	public ThingmlOutlinePageLinkWithEditorAction(org.sintef.thingml.resource.thingml.ui.ThingmlOutlinePageTreeViewer treeViewer) {
		super(treeViewer, "Link with Editor", org.eclipse.jface.action.IAction.AS_CHECK_BOX);
		initialize("icons/link_with_editor_icon.gif");
	}
	
	public void runInternal(boolean on) {
		getTreeViewer().setLinkWithEditor(on);
	}
	
}
