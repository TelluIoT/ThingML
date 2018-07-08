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
package org.thingml.eclipse.ui.menu;

import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.internal.ui.DebugUIPlugin;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.actions.AbstractLaunchToolbarAction;
import org.eclipse.e4.ui.workbench.renderers.swt.HandledContributionItem;
import org.eclipse.jface.bindings.TriggerSequence;
import org.eclipse.swt.events.MenuAdapter;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.keys.IBindingService;

@SuppressWarnings("restriction")
public class LaunchToolbarAction extends AbstractLaunchToolbarAction  {
	
	private String launchGroupId;
	private String toolbarItemId;
	private String toolbarCommandId;
	private String toolTipPrefix;

	public LaunchToolbarAction(String type, String toolTipPrefix) {
		super("org.thingml.eclipse.ui.launchgroup."+type);
		this.launchGroupId = "org.thingml.eclipse.ui.launchgroup."+type;
		this.toolbarItemId = "thingml.ui.toolbar."+type;
		this.toolbarCommandId = "thingml.ui.toolbar.commands."+type;
		this.toolTipPrefix = toolTipPrefix;
		DebugUIPlugin.getDefault().getLaunchConfigurationManager().addLaunchHistoryListener(this);
	}
	
	protected boolean shouldUpdateMenu = false;
	protected Menu currentMenu = null;
	protected ToolItem currentToolItem = null;
	
	private ToolItem getAssociatedToolItem(Composite comp) {
		for (Control cont : comp.getChildren()) {
			if (cont instanceof ToolBar) {
				for (ToolItem item : ((ToolBar)cont).getItems()) {
					Object data = item.getData();
					if (data instanceof HandledContributionItem) {
						if (toolbarItemId.equals(((HandledContributionItem)data).getId()))
							return item;
					}
				}
			}
			if (cont instanceof Composite) {
				ToolItem child = getAssociatedToolItem((Composite)cont);
				if (child != null)
					return child;
			}
		}
		return null;
	}
	
	
	public void setMenu(Menu menu) {
		if (menu != currentMenu) {
			if (currentMenu != null) currentMenu.dispose();
			
			fillMenu(menu);
			menu.addMenuListener(new MenuAdapter() {
				@Override
				public void menuShown(MenuEvent e) {
					if (shouldUpdateMenu) {
						MenuItem[] items = menu.getItems();
						for (int i = 0; i < items.length; i++) {
							items[i].dispose();
						}
						fillMenu(menu);
						shouldUpdateMenu = false;
					}
				}
			});
			
			currentMenu = menu;
			currentToolItem = getAssociatedToolItem(menu.getParent());
			updateToolTipText();
		}
	}
	
	@Override
	protected void fillMenu(Menu menu) {
		super.fillMenu(menu);
		// Remove the "Run As"/"Debug As" entry
		MenuItem[] items = menu.getItems();
		if (items.length >= 3)
			items[items.length-3].dispose();
	}
	
	@Override
	public void launchHistoryChanged() {
		shouldUpdateMenu = true;
		updateToolTipText();
	}
	
	protected void updateToolTipText() {
		if (currentToolItem != null) {
			ToolItem item = currentToolItem;
			item.getDisplay().asyncExec(new Runnable() {
				@Override
				public void run() {
					String text = getToolTipText();
					// Append the key binding text if we can find one
					IBindingService service = PlatformUI.getWorkbench().getAdapter(IBindingService.class);
					if (service != null) {
						TriggerSequence sequence = service.getBestActiveBindingFor(toolbarCommandId);
						if (sequence != null) {
							text += " ("+sequence.format()+")";
						}
					}
					item.setToolTipText(text);
				}
			});
		}
	}
	
	protected String getToolTipText() {
		ILaunchConfiguration lastConfig = DebugUITools.getLastLaunch(launchGroupId);
		if (lastConfig != null) {
			return toolTipPrefix+" "+lastConfig.getName();
		} else {
			return toolTipPrefix+" platform code";
		}
	}
}
