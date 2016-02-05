/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.sintef.thingml.resource.thingml.ui;

public class ThingmlOutlinePageActionProvider {
	
	public java.util.List<org.eclipse.jface.action.IAction> getActions(org.sintef.thingml.resource.thingml.ui.ThingmlOutlinePageTreeViewer treeViewer) {
		// To add custom actions to the outline view, set the
		// 'overrideOutlinePageActionProvider' option to <code>false</code> and modify
		// this method.
		java.util.List<org.eclipse.jface.action.IAction> defaultActions = new java.util.ArrayList<org.eclipse.jface.action.IAction>();
		defaultActions.add(new org.sintef.thingml.resource.thingml.ui.ThingmlOutlinePageLinkWithEditorAction(treeViewer));
		defaultActions.add(new org.sintef.thingml.resource.thingml.ui.ThingmlOutlinePageCollapseAllAction(treeViewer));
		defaultActions.add(new org.sintef.thingml.resource.thingml.ui.ThingmlOutlinePageExpandAllAction(treeViewer));
		defaultActions.add(new org.sintef.thingml.resource.thingml.ui.ThingmlOutlinePageAutoExpandAction(treeViewer));
		defaultActions.add(new org.sintef.thingml.resource.thingml.ui.ThingmlOutlinePageLexicalSortingAction(treeViewer));
		defaultActions.add(new org.sintef.thingml.resource.thingml.ui.ThingmlOutlinePageTypeSortingAction(treeViewer));
		return defaultActions;
	}
	
}
