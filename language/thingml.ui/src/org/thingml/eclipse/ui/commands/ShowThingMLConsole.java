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

import java.util.Iterator;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;
import org.thingml.eclipse.ui.ThingMLConsole;
import org.thingml.compilers.registry.*;

public class ShowThingMLConsole implements IHandler {

	@Override
	public void addHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		
		ISelection selection = HandlerUtil.getActiveWorkbenchWindow(event)
		        .getActivePage().getSelection();
		    if (selection != null & selection instanceof IStructuredSelection) {
		      IStructuredSelection strucSelection = (IStructuredSelection) selection;
		      for (Iterator<Object> iterator = strucSelection.iterator(); iterator
		          .hasNext();) {
		        Object element = iterator.next();
		        ThingMLConsole.getInstance().printDebug("Selected : " + element.toString() + "\n");
		        
		        
		        for (String cid : ThingMLCompilerRegistry.getInstance().getCompilerIds()) {
		        	ThingMLConsole.getInstance().printDebug("Found ThingML Compiler :" + cid + "\n");
				}
		        
		      }
		    }
		    return null;
		  }

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean isHandled() {
		return true;
	}

	@Override
	public void removeHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub

	}

}
