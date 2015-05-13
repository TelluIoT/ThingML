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
