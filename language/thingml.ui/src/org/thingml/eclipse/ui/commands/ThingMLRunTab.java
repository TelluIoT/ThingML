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
package org.thingml.eclipse.ui.commands;

import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class ThingMLRunTab extends AbstractLaunchConfigurationTab {

    private Text text, mtext;
	
	@Override
	public void createControl(Composite parent) {
        Composite comp = new Group(parent, SWT.BORDER);
        setControl(comp);

        GridLayoutFactory.swtDefaults().numColumns(2).applyTo(comp);

        Label label = new Label(comp, SWT.NONE);
        label.setText("Compiler:");
        GridDataFactory.swtDefaults().applyTo(label);

        text = new Text(comp, SWT.BORDER);
        text.setMessage("Compiler");
        GridDataFactory.fillDefaults().grab(true, false).applyTo(text);
                
        Button mButton = new Button(comp, SWT.NONE);
		mButton.setText("File...");
        mtext = new Text(comp, SWT.BORDER);
        mtext.setMessage("File"); 
        GridDataFactory.fillDefaults().grab(true, false).applyTo(mtext);
		mButton.addSelectionListener(new SelectionListener() { 
			public void widgetDefaultSelected(SelectionEvent e) {
			}
 
			public void widgetSelected(SelectionEvent e) {
				FileDialog dlg = new FileDialog(mButton.getShell(),  SWT.OPEN  );
				dlg.setText("Open");
				String path = dlg.open();
				if (path == null) return;
				mtext.setText(path);
			}
		});
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		System.out.println("compiler = " + text.getText() + ", source = " + mtext.getText());
		
		configuration.setAttribute("compiler", text.getText());
		configuration.setAttribute("source", mtext.getText());		
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		// TODO Auto-generated method stub

	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		System.out.println("compiler = " + text.getText() + ", source = " + mtext.getText());	
		configuration.setAttribute("compiler", text.getText());
		configuration.setAttribute("source", mtext.getText());				
	}

	@Override
	public String getName() {
		return "ThingML launch tab";
	}

}
