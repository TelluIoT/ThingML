/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.sintef.thingml.resource.thingml.ui;

public class ThingmlOutlinePageLexicalSortingAction extends org.sintef.thingml.resource.thingml.ui.AbstractThingmlOutlinePageAction {
	
	public ThingmlOutlinePageLexicalSortingAction(org.sintef.thingml.resource.thingml.ui.ThingmlOutlinePageTreeViewer treeViewer) {
		super(treeViewer, "Sort alphabetically", org.eclipse.jface.action.IAction.AS_CHECK_BOX);
		initialize("icons/sort_lexically_icon.gif");
	}
	
	public void runInternal(boolean on) {
		getTreeViewerComparator().setSortLexically(on);
		getTreeViewer().refresh();
	}
	
}
