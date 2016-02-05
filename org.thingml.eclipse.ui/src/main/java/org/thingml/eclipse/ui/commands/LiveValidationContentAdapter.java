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
package org.thingml.eclipse.ui.commands;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.validation.model.EvaluationMode;
import org.eclipse.emf.validation.service.ILiveValidator;
import org.eclipse.emf.validation.service.ModelValidationService;

public class LiveValidationContentAdapter extends EContentAdapter {
	private ILiveValidator validator = null;

	public LiveValidationContentAdapter() {
	}

	public void notifyChanged(final Notification notification) {
		super.notifyChanged(notification);
		
		if (validator == null) {
			validator = 
				(ILiveValidator)ModelValidationService.getInstance().newValidator(EvaluationMode.LIVE);
			
		}		
		CheckThingMLFile.running = true;
		
		System.out.println("notification: " + notification);
		IStatus status = validator.validate(notification);
		
		if (!status.isOK()) {
			if (status.isMultiStatus()) {
				status = status.getChildren()[0];
			}			
			System.err.println("The current modification has violated one or more live constraints. " + status);
		} else {
			//System.err.println("The current modification is OK");
		}
		
		CheckThingMLFile.running = false;		
	}
}