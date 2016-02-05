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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.EMFEventType;
import org.eclipse.emf.validation.IValidationContext;
import org.sintef.thingml.Configuration;
import org.thingml.compilers.ThingMLCompiler;
import org.thingml.compilers.checker.Checker;

public class EclipseChecker extends AbstractModelConstraint {

	Checker checker = new Checker("Generic") {
		@Override
		public void do_check(Configuration cfg) {
			do_generic_check(cfg);
		}
	};

	private IStatus check(IValidationContext ctx, Configuration cfg) {
		checker.do_check(cfg);
		if (checker.containsErrors())
			return ctx.createFailureStatus(new Object[] { cfg.getName() });
		return ctx.createSuccessStatus();	
	}

	@Override
	public IStatus validate(IValidationContext ctx) {			
		EObject eObj = ctx.getTarget();
		if (eObj instanceof Configuration) {
			EMFEventType eType = ctx.getEventType();
			if (eType == EMFEventType.NULL) {//BATCH mode
				System.out.println("BATCH model");
				check(ctx, (Configuration)eObj);			
			} else {//LIVE mode
				System.out.println("LIVE model");
				check(ctx, (Configuration)eObj);		
			}
		}
		return ctx.createSuccessStatus();		
	}

}