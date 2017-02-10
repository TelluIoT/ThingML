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
package org.thingml.compilers.checker;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.validation.EObjectDiagnosticImpl;
import org.thingml.compilers.ThingMLCompiler;

import java.util.Collections;

/**
 * Created by bmori on 28.01.2016.
 */
public class EMFWrapper implements ErrorWrapper {
    @Override
    public void addError(final String msg, final EObject el) {
    	/*
        IThingmlProblem problem = new IThingmlProblem() {
            public String getMessage() {
                return msg;
            }

            public org.sintef.thingml.resource.thingml.ThingmlEProblemSeverity getSeverity() {
                return ThingmlEProblemSeverity.ERROR;
            }

            public org.sintef.thingml.resource.thingml.ThingmlEProblemType getType() {
                return ThingmlEProblemType.UNKNOWN;
            }

            public java.util.Collection<org.sintef.thingml.resource.thingml.IThingmlQuickFix> getQuickFixes() {
                return Collections.EMPTY_LIST;
            }
        };
        */
        ThingMLCompiler.resource.getErrors().add(null);
    }

    @Override
    public void addWarning(final String msg, final EObject el) {
        /*IThingmlProblem problem = new IThingmlProblem() {
            public String getMessage() {
                return msg;
            }

            public org.sintef.thingml.resource.thingml.ThingmlEProblemSeverity getSeverity() {
                return ThingmlEProblemSeverity.WARNING;
            }

            public org.sintef.thingml.resource.thingml.ThingmlEProblemType getType() {
                return ThingmlEProblemType.UNKNOWN;
            }

            public java.util.Collection<org.sintef.thingml.resource.thingml.IThingmlQuickFix> getQuickFixes() {
                return Collections.EMPTY_LIST;
            }
        };
        */
        ThingMLCompiler.resource.getErrors().add(null);
    }
}
