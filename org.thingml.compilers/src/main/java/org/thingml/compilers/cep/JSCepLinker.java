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
package org.thingml.compilers.cep;

import org.eclipse.emf.common.util.EList;
import org.sintef.thingml.*;
import org.sintef.thingml.impl.InternalTransitionImpl;
import org.sintef.thingml.impl.PrimitiveTypeImpl;
import org.thingml.compilers.Context;
import org.thingml.compilers.cepLibrairy.parser.CEPParser;
import org.thingml.compilers.cepLibrairy.parser.ParseResult;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * @author ludovic
 */
public class JSCepLinker {
    private JSCepLinker(){}
    public static JSCepLinker instance = new JSCepLinker();

    public void modification(Context ctx, Configuration conf, Thing thing) {
        List<Handler> transitions = getAllAnnotedTransition(thing);

        if(!transitions.isEmpty()) {
            ctx.addProperty("hasStream","true");
        }

        int i=0;
        for(Handler handler : transitions) {
            String eventPropertyName = thing.getName() + "_event_transition_" + i;
            String streamMessageName = thing.getName() + "_stream_message_" + i;
            String annotationValue = handler.annotation("stream").toString().replace("[","").replace("]","");

            if(annotationValue.equals("") && !handler.getEvent().isEmpty()) {
                annotationValue = "1";
            }

            RequiredPort portSend = ThingmlFactory.eINSTANCE.createRequiredPort();
            portSend.setName(thing.getName() + "_send_port_transition_" + i);
            portSend.setOwner(thing);

            ProvidedPort portReceive = ThingmlFactory.eINSTANCE.createProvidedPort();
            portReceive.setName(thing.getName() + "_receive_port_transition_" + i);
            portReceive.setOwner(thing);

            connectPort(portSend, portReceive, conf, thing);

            ParseResult parseResult = CEPParser.instance.parse(annotationValue);

            int index = 0;
            for(int iDEvent : parseResult.getIdEventToStream()) {
                List<Integer> values = new ArrayList<>();
                values.add(iDEvent);
                modifyStateMachine(ctx, thing, handler, eventPropertyName + "_" + iDEvent, streamMessageName + "_" + iDEvent,
                        portReceive, portSend,values,index);
                index++;
            }

            index = 0;
            Map<Integer,List<Integer>> idsMergedEvents = parseResult.getIdMergedEvent();
            for(int key : idsMergedEvents.keySet()) {
               String listEvString = "";
               for(int value : idsMergedEvents.get(key)) {
                   listEvString += value;
               }

                String tmpString = "_" + key + "_" + listEvString;
                modifyStateMachine(ctx,thing, handler,eventPropertyName + tmpString, streamMessageName + tmpString,
                        portReceive,portSend, idsMergedEvents.get(key),index);

                index++;
            }

            handler.getAnnotations().removeIf(new Predicate<PlatformAnnotation>() {
                @Override
                public boolean test(PlatformAnnotation platformAnnotation) {
                    return platformAnnotation.getName().equals("stream");
                }
            });

            i++;
        }
    }

    private void modifyStateMachine(Context ctx, Thing thing, Handler handler, String eventPropertyName, String streamMessageName,
                                    ProvidedPort portReceive, RequiredPort portSend, List<Integer> values, int index) {


        Property eventProperty = createEventProperty(thing, eventPropertyName);

        InternalTransitionImpl newTransition = createInternalTransition(ctx,handler,eventPropertyName,streamMessageName,eventProperty,portSend,values,index);

        changeTransitionEvents(thing,handler,newTransition,streamMessageName,portReceive,portSend);

        handler.findContainingState().getInternal().add(newTransition);
    }

    private void changeTransitionEvents(Thing thing, Handler handler, InternalTransitionImpl newTransition, String streamMessageName,
                                        ProvidedPort portReceive, RequiredPort portSend) {

        Message message = ThingmlFactory.eINSTANCE.createMessage();
        message.setName(streamMessageName);
        thing.getMessages().add(message);
        portSend.getSends().add(message);
        portReceive.getReceives().add(message);

        List<Parameter> listParams = new ArrayList<>();
        for(Parameter param : ((ReceiveMessage)newTransition.getEvent().get(0)).getMessage().getParameters()) {
            Parameter p = ThingmlFactory.eINSTANCE.createParameter();
            p.setName(param.getName());
            p.setType(param.getType());
            p.setCardinality(param.getCardinality());
            listParams.add(p);
        }
        message.getParameters().addAll(listParams);

        ReceiveMessage receiveMessage = ThingmlFactory.eINSTANCE.createReceiveMessage();
        receiveMessage.setMessage(message);
        receiveMessage.setPort(portReceive);

        List<Event> lmsgs = new ArrayList<>();
        lmsgs.add(receiveMessage);

        handler.getEvent().add(receiveMessage);
    }

    private InternalTransitionImpl createInternalTransition(Context ctx, Handler handler, String eventPropertyName, String streamMessageName,
                                                            Property eventProperty, RequiredPort portSend, List<Integer> values, int index) {
        ExternStatement action = ThingmlFactory.eINSTANCE.createExternStatement();
        action.setStatement("_this." + ctx.getVariableName(eventProperty) + ".emit('" + eventProperty.getName() + "',message)");

        InternalTransitionImpl newTransition = (InternalTransitionImpl) ThingmlFactory.eINSTANCE.createInternalTransition();
        newTransition.setAction(action);

        PlatformAnnotation streamAnnotation = ThingmlFactory.eINSTANCE.createPlatformAnnotation();
        streamAnnotation.setName("stream");
        streamAnnotation.setValue(eventProperty.getName() + "." + ctx.getVariableName(eventProperty) + "." + "send" + ctx.firstToUpper(streamMessageName) +
                "On" + ctx.firstToUpper(portSend.getName()));
        newTransition.getAnnotations().add(streamAnnotation);

        int i = index;
        for(int idEvt : values) {
            newTransition.getEvent().add(handler.getEvent().get(idEvt - 1 - i));
            i++;
        }

        newTransition.setGuard(handler.getGuard());

        return newTransition;
    }

    private Property createEventProperty(Thing thing, String eventPropertyName) {
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

    private void connectPort(RequiredPort portSend, ProvidedPort portReceive, Configuration conf, Thing thing) {
        InstanceRef instanceRefSend = ThingmlFactory.eINSTANCE.createInstanceRef();
        InstanceRef instanceRefRcve = ThingmlFactory.eINSTANCE.createInstanceRef();
        for(Instance instance : conf.getInstances()) {
            if(instance.getType().getName().equals(thing.getName())) {
                instanceRefSend.setInstance(instance);
                instanceRefRcve.setInstance(instance);
                break;
            }
        }

        Connector connector = ThingmlFactory.eINSTANCE.createConnector();
        connector.setProvided(portReceive);
        connector.setRequired(portSend);
        connector.setCli(instanceRefSend);
        connector.setSrv(instanceRefRcve);
        conf.getConnectors().add(connector);
    }


    private void addHandlers(EList<? extends Handler> handlers, List<Handler> result) {
        for(Handler h : handlers) {
            if(h.hasAnnotation("stream")) {
                result.add(h);
            }
        }
    }

    private List<Handler> getAllAnnotedTransition(Thing thing) {
        List<Handler> result = new ArrayList<>();

        for(StateMachine sm : thing.getBehaviour()) {
            for(State state : sm.allStates()) {
                addHandlers(state.getOutgoing(),result);
                addHandlers(state.getInternal(),result);
            }
        }

        return result;
    }
}
