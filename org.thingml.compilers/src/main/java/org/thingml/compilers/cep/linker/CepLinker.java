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
import org.thingml.compilers.Context;
import org.thingml.compilers.cep.architecture.RootStream;
import org.thingml.compilers.cep.architecture.SimpleStream;
import org.thingml.compilers.cep.architecture.Stream;
import org.thingml.compilers.cep.linker.utils.ConnectNewPorts;
import org.thingml.compilers.cep.linker.utils.CreateMessage;
import org.thingml.compilers.cep.linker.utils.GetAnnotedTransitions;
import org.thingml.compilers.cep.parser.CEPParser;
import org.thingml.compilers.cep.parser.ParseResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * @author ludovic
 */
public class CepLinker {
    public void modifyThing(Context ctx, Configuration conf, Thing thing, RootStream streams) {
        List<Handler> transitions = GetAnnotedTransitions.getAllAnnotedTransitions(thing);

        if(!transitions.isEmpty()) {
            ctx.addProperty("hasStream","true");
        }

        int indexTransition=0;
        for(Handler handler : transitions) {
            String eventPropertyName = thing.getName() + "_event_transition_" + indexTransition;
            String streamMessageName = thing.getName() + "_stream_message_" + indexTransition;
            String annotationValue = handler.annotation("stream").toString().replace("[","").replace("]","");


            if(annotationValue.equals("") && !handler.getEvent().isEmpty()) {
                annotationValue = "1";
            }

            String requiredPortName = thing.getName() + "_send_port_transition_" + indexTransition;
            String providedPortName = thing.getName() + "_receive_port_transition_" + indexTransition;
            ConnectNewPorts connector = new ConnectNewPorts(requiredPortName,providedPortName, thing);
            connector.connect(conf);
            RequiredPort portSend = connector.getRequiredPort();
            ProvidedPort portReceive = connector.getProvidedPort();

            ParseResult parseResult = CEPParser.instance.parse(annotationValue);

            parseIdEvents(ctx, thing, streams, handler, eventPropertyName, streamMessageName, portSend, portReceive, parseResult);
            parseMergedEvents(ctx, thing, streams, handler, eventPropertyName, streamMessageName, portSend, portReceive, parseResult);

            handler.getAnnotations().removeIf(new Predicate<PlatformAnnotation>() {
                @Override
                public boolean test(PlatformAnnotation platformAnnotation) {
                    return platformAnnotation.getName().equals("stream");
                }
            });


            indexTransition++;
        }
    }

    private void parseMergedEvents(Context ctx, Thing thing, RootStream streams, Handler handler, String eventPropertyName,
                                   String streamMessageName, RequiredPort portSend, ProvidedPort portReceive, ParseResult parseResult) {
        int indexEvent;
        indexEvent = 0;
        Map<Integer,List<Integer>> idsMergedEvents = parseResult.getIdMergedEvent();
        for(int key : idsMergedEvents.keySet()) {
            String listEvString = "";
            for(int value : idsMergedEvents.get(key)) {
                listEvString += value;
            }

            String tmpString = "_" + key + "_" + listEvString;
            modifyModel(portSend, portReceive, streams, ctx, thing, handler, eventPropertyName + tmpString,
                    streamMessageName + tmpString, indexEvent, idsMergedEvents.get(key));

            indexEvent++;
        }
    }

    private void parseIdEvents(Context ctx, Thing thing, RootStream streams, Handler handler, String eventPropertyName,
                               String streamMessageName, RequiredPort portSend, ProvidedPort portReceive, ParseResult parseResult) {
        int indexEvent = 0;
        for(int iDEvent : parseResult.getIdEventToStream()) {
            List<Integer> values = new ArrayList<>();
            values.add(iDEvent);
            modifyModel(portSend, portReceive, streams, ctx, thing, handler, eventPropertyName + "_" + iDEvent,
                    streamMessageName + "_" + iDEvent, indexEvent, values);

            indexEvent++;
        }
    }

    private void modifyModel(RequiredPort portSend, ProvidedPort portReceive, RootStream streams, Context ctx, Thing thing, Handler handler,
                             String eventPropertyName, String streamMessageName, int index, List<Integer> values) {
        Stream stream = new SimpleStream();
        stream.setPortSend(portSend);

        modifyStateMachine(ctx, thing, handler, eventPropertyName, streamMessageName,
                portReceive, portSend, values, index, stream);

        streams.getStreams().add(stream);
    }

    protected void modifyStateMachine(Context ctx, Thing thing, Handler handler, String eventPropertyName, String streamMessageName,
                                      ProvidedPort portReceive, RequiredPort portSend, List<Integer> values, int index, Stream stream) {

        Property eventProperty = createEventProperty(thing, eventPropertyName);
        stream.setEventProperty(eventProperty);

        InternalTransitionImpl newTransition = createInternalTransition(ctx, handler, eventProperty, values, index);

        //fixme
        Message message = CreateMessage.createMessage(thing, ((ReceiveMessage) newTransition.getEvent().get(0)).getMessage().getParameters(),
                streamMessageName, portReceive, portSend);
        stream.setStreamMessage(message);

        ReceiveMessage receiveMessage = CreateMessage.createReceiveMessage(handler, portReceive, message);
        stream.setMessage(receiveMessage);

        handler.findContainingState().getInternal().add(newTransition);
    }

    protected InternalTransitionImpl createInternalTransition(Context ctx, Handler handler, Property eventProperty, List<Integer> values, int index) {
        throw(new UnsupportedOperationException("The operation CepLinker.createInternalTransition is platform-specific and should be refined!"));
    }

    protected Property createEventProperty(Thing thing, String eventPropertyName) {
        throw(new UnsupportedOperationException("The operation CepLinker.createEventProperty is platform-specific and should be refined!"));
    }
}
