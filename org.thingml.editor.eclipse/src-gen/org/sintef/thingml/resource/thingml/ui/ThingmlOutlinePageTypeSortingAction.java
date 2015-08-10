/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.sintef.thingml.resource.thingml.ui;

public class ThingmlOutlinePageTypeSortingAction extends org.sintef.thingml.resource.thingml.ui.AbstractThingmlOutlinePageAction {
	
	public ThingmlOutlinePageTypeSortingAction(org.sintef.thingml.resource.thingml.ui.ThingmlOutlinePageTreeViewer treeViewer) {
		super(treeViewer, "Group types", org.eclipse.jface.action.IAction.AS_CHECK_BOX);
		initialize("icons/group_types_icon.gif");
	}
	
	public void runInternal(boolean on) {
		getTreeViewerComparator().setGroupTypes(on);
		getTreeViewer().refresh();
	}
	
}
