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
package org.thingml.compilers.cep.linker;

import org.sintef.thingml.*;
import org.sintef.thingml.impl.InternalTransitionImpl;
import org.sintef.thingml.impl.PrimitiveTypeImpl;
import org.thingml.compilers.Context;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author ludovic
 */
//todo add abstract class
public class JSCepLinker extends CepLinker {
    private JSCepLinker(){}
    public static JSCepLinker instance = new JSCepLinker();

    @Override
    protected InternalTransitionImpl createInternalTransition(Context ctx, Handler handler,
                                                            Property eventProperty, List<Integer> values, int index) {
        ExternStatement action = ThingmlFactory.eINSTANCE.createExternStatement();
        action.setStatement("_this." + ctx.getVariableName(eventProperty) + ".emit('" + eventProperty.getName() + "',message)");

        InternalTransitionImpl newTransition = (InternalTransitionImpl) ThingmlFactory.eINSTANCE.createInternalTransition();
        newTransition.setAction(action);

        int i = index;
        for(int idEvt : values) {
            Event e = handler.getEvent().get(idEvt - 1 - i);
            newTransition.getEvent().add(e);
            i++;
        }

        newTransition.setGuard(handler.getGuard());

        return newTransition;
    }

    @Override
    protected Property createEventProperty(Thing thing, String eventPropertyName) {
        PrimitiveType eventEmitterType = ThingmlFactory.eINSTANCE.createPrimitiveType();
        eventEmitterType.setName("EventEmitter");

        PlatformAnnotation annotation = ThingmlFactory.eINSTANCE.createPlatformAnnotation();
        annotation.setName("js_type");
        annotation.setValue("EventEmitter");

        List<PlatformAnnotation> annotations = new ArrayList<>();
        annotations.add(annotation);
        ((PrimitiveTypeImpl)eventEmitterType).eSet(ThingmlPackage.ANNOTATED_ELEMENT__ANNOTATIONS, annotations);
        thing.findContainingModel().getTypes().add(eventEmitterType);


        Property eventProperty = ThingmlFactory.eINSTANCE.createProperty();
        eventProperty.setName(eventPropertyName);
        eventProperty.setType(eventEmitterType);
        ExternExpression initEventProperty = ThingmlFactory.eINSTANCE.createExternExpression();
        initEventProperty.setExpression("new EventEmitter()");
        eventProperty.setInit(initEventProperty);
        thing.getProperties().add(eventProperty);

        return eventProperty;
    }


}
