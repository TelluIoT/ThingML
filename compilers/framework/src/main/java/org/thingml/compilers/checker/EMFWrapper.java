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
package org.thingml.compilers.checker;

import org.eclipse.emf.ecore.EObject;
import org.thingml.compilers.ThingMLCompiler;
import org.sintef.thingml.resource.thingml.*;//FIXME: import just what is needed

import java.util.Collections;

/**
 * Created by bmori on 28.01.2016.
 */
public class EMFWrapper implements ErrorWrapper {
    @Override
    public void addError(final String msg, final EObject el) {
        IThingmlProblem problem = new IThingmlProblem(){
            public String getMessage(){return msg;}
            public org.sintef.thingml.resource.thingml.ThingmlEProblemSeverity getSeverity(){return ThingmlEProblemSeverity.ERROR;}
            public org.sintef.thingml.resource.thingml.ThingmlEProblemType getType(){return ThingmlEProblemType.UNKNOWN;}
            public java.util.Collection<org.sintef.thingml.resource.thingml.IThingmlQuickFix> getQuickFixes(){return Collections.EMPTY_LIST;}
        };
        ThingMLCompiler.resource.addProblem(problem, el);
    }

    @Override
    public void addWarning(final String msg, final EObject el) {
        IThingmlProblem problem = new IThingmlProblem(){
            public String getMessage(){return msg;}
            public org.sintef.thingml.resource.thingml.ThingmlEProblemSeverity getSeverity(){return ThingmlEProblemSeverity.WARNING;}
            public org.sintef.thingml.resource.thingml.ThingmlEProblemType getType(){return ThingmlEProblemType.UNKNOWN;}
            public java.util.Collection<org.sintef.thingml.resource.thingml.IThingmlQuickFix> getQuickFixes(){return Collections.EMPTY_LIST;}
        };
        ThingMLCompiler.resource.addProblem(problem, el);
    }
}
