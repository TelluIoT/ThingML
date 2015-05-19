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
import org.thingml.compilers.cep.linker.utils.ConnectNewPorts;
import org.thingml.compilers.cep.linker.utils.CreateMessage;

import java.security.Guard;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * @author ludovic
 */
public class CepLinker {
    /*public void modifyThing(Context ctx, Configuration conf, Thing thing, RootStream streams) {
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

            //create and connect two ports
            // the ports are used to send messages between CEP and statemachine
            String requiredPortName = thing.getName() + "_send_port_transition_" + indexTransition;
            String providedPortName = thing.getName() + "_receive_port_transition_" + indexTransition;
            ConnectNewPorts connector = new ConnectNewPorts(requiredPortName,providedPortName, thing);
            connector.connect(conf);
            RequiredPort portSend = connector.getRequiredPort();
            ProvidedPort portReceive = connector.getProvidedPort();

            ParseResult parseResult = CEPParser.instance.parse(annotationValue);

            parseIdEvents(ctx, thing, streams, handler, eventPropertyName, streamMessageName, portSend, portReceive, parseResult);
            parseMergedEvents(ctx, thing, streams, handler, eventPropertyName, streamMessageName, portSend, portReceive, parseResult);

            if(parseResult.getJoinResult().isPresent()) {
                JoinResult joinResult = parseResult.getJoinResult().get();
                JoinStream joinStream = new JoinStream();
                joinStream.setPortSend(portSend);

                ParseResult tmp = new ParseResult();
                tmp.addEventToStream(Integer.valueOf(joinResult.getIdEvt1()));
                tmp.addEventToStream(Integer.valueOf(joinResult.getIdEvt2()));

                parseIdEvents(ctx, thing, streams, handler, eventPropertyName, streamMessageName, portSend, portReceive, tmp);

                joinStream.setWaitStream(new TimeStream(Integer.valueOf(joinResult.getTimeMS())));

                List<Stream> streamsJoin = new ArrayList<>();
                streamsJoin.add(streams.getStreams().get(streams.getStreams().size() - 2));
                streamsJoin.add(streams.getStreams().get(streams.getStreams().size() - 1));
                joinStream.setStreams(streamsJoin);

                streams.getStreams().get(streams.getStreams().size() - 2).setWithSubscribe(false);
                streams.getStreams().get(streams.getStreams().size() - 1).setWithSubscribe(false);
                joinStream.setWithSubscribe(true);

                for(Function f : thing.getFunctions()) {//fixme method/abstract/... ?
                    if(f.getName().equals(joinResult.getFuncValueName())) {
                        joinStream.setJoinFunction(f);

                        ActionBlock block = (ActionBlock) f.getBody();
                        String paramMessageName = joinStream.getStreams().get(0).getStreamMessage().getParameters().get(0).getName(); //fixme

                        for (Parameter p : f.getParameters()) {
                            String statement = "";

                            //add thingmlcode :
                            // 'var p1J = JSON.parse('& p1 &' );'
                            ExternStatement externStatement = ThingmlFactory.eINSTANCE.createExternStatement();
                            statement += "var " + p.getName() + "J = JSON.parse(";
                            externStatement.setStatement(statement);

                            PropertyReference propRef = ThingmlFactory.eINSTANCE.createPropertyReference();
                            propRef.setProperty(p);
                            externStatement.getSegments().add(propRef);

                            ExternExpression externExpression = ThingmlFactory.eINSTANCE.createExternExpression();
                            externExpression.setExpression(");");
                            externStatement.getSegments().add(externExpression);

                            //add thingml code :
                            // p1 = p1J.val
                            VariableAssignment variableAssignment = ThingmlFactory.eINSTANCE.createVariableAssignment();
                            variableAssignment.setProperty(p);
                            ExternExpression expressionAssigment = ThingmlFactory.eINSTANCE.createExternExpression();
                            expressionAssigment.setExpression(p.getName() + "J." + paramMessageName);
                            variableAssignment.setExpression(expressionAssigment);

                            block.getActions().add(0, externStatement);
                            block.getActions().add(1,variableAssignment);
                        }
                        //transorm return
                        // return p1 + p2 => return 'JSON.stringify({val: '& p1 + p2 &'})'
                        ReturnAction oldReturnAction = (ReturnAction) block.getActions().get(block.getActions().size()-1);
                        ExternExpression returnExpression = ThingmlFactory.eINSTANCE.createExternExpression();
                        returnExpression.setExpression("JSON.stringify({" + paramMessageName + ":");
                        returnExpression.getSegments().add(oldReturnAction.getExp());
                        ExternExpression endReturnExpression = ThingmlFactory.eINSTANCE.createExternExpression();
                        endReturnExpression.setExpression("})");
                        returnExpression.getSegments().add(endReturnExpression);
                        oldReturnAction.setExp(returnExpression);
                        break;
                    }
                }

                joinStream.setName("stream" + streams.getStreams().size());
                streams.getStreams().add(joinStream);
            }

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

    /**
     * Transform each events, present in the annotation, to streams
     * @param ctx
     * @param thing
     * @param streams
     * @param handler
     * @param eventPropertyName
     * @param streamMessageName
     * @param portSend
     * @param portReceive
     * @param parseResult
     */
    /*private void parseIdEvents(Context ctx, Thing thing, RootStream streams, Handler handler, String eventPropertyName,
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
        SimpleStream stream = new SimpleStream();
        stream.setPortSend(portSend);
        stream.setWithSubscribe(true);
        modifyStateMachine(ctx, thing, handler, eventPropertyName, streamMessageName,
                portReceive, portSend, values, index, stream);

        stream.setName("stream" + streams.getStreams().size());
        streams.getStreams().add(stream);
    }

    protected void modifyStateMachine(Context ctx, Thing thing, Handler handler, String eventPropertyName, String streamMessageName,
                                      ProvidedPort portReceive, RequiredPort portSend, List<Integer> values, int index, SimpleStream stream) {

        Property eventProperty = createEventProperty(thing, eventPropertyName);
        stream.setEventProperty(eventProperty);

        InternalTransitionImpl newTransition = createInternalTransition(ctx, handler, eventProperty, values, index);

        checkParamsEvent(newTransition);
        //the checkParamsEvent method assure us that all the parameters are the same
        // so we take the parameters of the first message
        Message message = CreateMessage.createMessage(thing, ((ReceiveMessage) newTransition.getEvent().get(0)).getMessage().getParameters(),
                streamMessageName, portReceive, portSend);
        stream.setStreamMessage(message);

        ReceiveMessage receiveMessage = CreateMessage.createReceiveMessage(handler, portReceive, message);
        stream.setMessage(receiveMessage);

        handler.findContainingState().getInternal().add(newTransition);
    }*/

    /*protected InternalTransitionImpl createInternalTransition(Context ctx, Handler handler, Property eventProperty, List<Integer> values, int index) {
        throw(new UnsupportedOperationException("The operation CepLinker.createInternalTransition is platform-specific and should be refined!"));
    }*/

    protected Property createEventProperty(Thing thing, String eventPropertyName) {
        throw(new UnsupportedOperationException("The operation CepLinker.createEventProperty is platform-specific and should be refined!"));
    }

    /*
    /**
     * Check if the params of the messages have the same type
     * @param transition
     */
    /*private void checkParamsEvent(InternalTransition transition) {
        Iterator<Event> itEvt = transition.getEvent().iterator();
        List<Parameter> firstParams = ((ReceiveMessage)itEvt.next()).getMessage().getParameters();

        while(itEvt.hasNext()) {
            Iterator<Parameter> itfirstParams = firstParams.iterator();
            Iterator<Parameter> itParams = ((ReceiveMessage)itEvt.next()).getMessage().getParameters().iterator();

            while(itfirstParams.hasNext() && itParams.hasNext()) {
                if(!itfirstParams.next().getType().getName().equals(itParams.next().getType().getName())) {
                    throw(new UnsupportedOperationException("The params of a merged streams must have the same type"));
                }
            }

            if(itfirstParams.hasNext() || itParams.hasNext()) {
                throw(new UnsupportedOperationException("The params of a merged streams must have the same type"));
            }

        }
    }*/

    public void modifyThing(Context ctx, Configuration conf, Thing thing) {
       for(StateMachine stateMachine : thing.allStateMachines()) {
            for(State state : stateMachine.allStates()) {
                //fixme optim : two ports by things (?)
                //create and connect two ports
                // the ports are used to send messages between CEP and statemachine
                String requiredPortName = thing.getName() + "_send_port_state_" + state.getName();
                String providedPortName = thing.getName() + "_receive_port_state_" + state.getName();
                ConnectNewPorts connector = new ConnectNewPorts(requiredPortName,providedPortName, thing);
                connector.connect(conf);
                RequiredPort portSend = connector.getRequiredPort();
                ProvidedPort portReceive = connector.getProvidedPort();

                List<Handler> handlersWithStream = new ArrayList<>();
                for(Handler h : state.getInternal()) {
                   if(!h.allStreams().isEmpty()) handlersWithStream.add(h);
                }
                for(Handler h : state.getOutgoing()) {
                    if(!h.allStreams().isEmpty()) handlersWithStream.add(h);
                }


                int indexHandler = 0;
                for(Handler handler : handlersWithStream) {
                    for (Stream stream : handler.allStreams()) {
                        if (indexHandler == 0) ctx.addProperty("hasStream", "true");

                        SimpleStream simpleStream = (SimpleStream) stream;
                        String eventPropertyName = thing.getName() + "_event_transition_" + indexHandler;
                        Property eventProperty = createEventProperty(thing, eventPropertyName);

                        String streamMessageName = thing.getName() + "_stream_message_" + indexHandler;
                        Message streamMessage = CreateMessage.createMessage(thing, simpleStream.getSource().getMessage().getParameters(),
                                streamMessageName, portReceive, portSend);
                        ReceiveMessage receiveMessage = CreateMessage.createReceiveMessage(handler, portReceive, streamMessage);

                        Optional<InternalTransition> opt = getInternalIfExists(state, simpleStream.getSource());
                        InternalTransition newTransition;
                        if (opt.isPresent()) {
                            newTransition = opt.get();
                        } else {
                            newTransition = ThingmlFactory.eINSTANCE.createInternalTransition();
                            ActionBlock actionBlock = ThingmlFactory.eINSTANCE.createActionBlock();
                            newTransition.setAction(actionBlock);
                            newTransition.getEvent().add(simpleStream.getSource());
                            state.getInternal().add(newTransition);
                        }

                        ExternStatement action = ThingmlFactory.eINSTANCE.createExternStatement();
                        action.setStatement("_this." + ctx.getVariableName(eventProperty) + ".emit('" + eventProperty.getName() + "',message)");
                        ((ActionBlock) newTransition.getAction()).getActions().add(action);

                        simpleStream.setSource(receiveMessage);
                        simpleStream.setEventProperty(eventProperty);
                        simpleStream.setPortSend(portSend);
                        simpleStream.setStreamMessage(streamMessage);

                        indexHandler++;
                    }
                }



            }
        }

    }

    private Optional<InternalTransition> getInternalIfExists(State s, ReceiveMessage looking) {
        for(InternalTransition it : s.getInternal()) {
            if(it.getEvent().get(0) instanceof ReceiveMessage) { //les intern qu on cree ont forcement que des receivemessage
                ReceiveMessage rm = (ReceiveMessage) it.getEvent().get(0); //pas plus de 1 event sur l'internal cree
                if (rm.getMessage().getName().equals(looking.getMessage().getName()) &&
                        rm.getPort().getName().equals(looking.getPort().getName())) {
                    return Optional.of(it);
                }
            }
        }

        return Optional.empty();
    }

}
