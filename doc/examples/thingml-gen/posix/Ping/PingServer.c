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
/*****************************************************
 *      THIS IS A GENERATED FILE. DO NOT EDIT.
 *        Implementation for Thing PingServer
 *  Generated from ThingML (http://www.thingml.org)
 *****************************************************/

#include "PingServer.h"

/*****************************************************************************
 * Implementation for type : PingServer
 *****************************************************************************/

// Declaration of prototypes:
//Prototypes: State Machine
void PingServer_OnExit(int state, struct PingServer_Instance *_instance);
//Prototypes: Message Sending
void PingServer_send_ping_service_pong(struct PingServer_Instance *_instance, uint8_t seq);
//Prototypes: Function
// Declaration of functions:

// Sessions functionss:


// On Entry Actions:
void PingServer_OnEntry(int state, struct PingServer_Instance *_instance) {
switch(state) {
case PINGSERVER_STATE:{
_instance->PingServer_State = PINGSERVER_NULL_ACTIVE_STATE;
PingServer_OnEntry(_instance->PingServer_State, _instance);
break;
}
case PINGSERVER_NULL_ACTIVE_STATE:{
break;
}
default: break;
}
}

// On Exit Actions:
void PingServer_OnExit(int state, struct PingServer_Instance *_instance) {
switch(state) {
case PINGSERVER_STATE:{
PingServer_OnExit(_instance->PingServer_State, _instance);
break;}
case PINGSERVER_NULL_ACTIVE_STATE:{
break;}
default: break;
}
}

// Event Handlers for incoming messages:
void PingServer_handle_ping_service_ping(struct PingServer_Instance *_instance, uint8_t seq) {
if(!(_instance->active)) return;
//Region null
uint8_t PingServer_State_event_consumed = 0;
if (_instance->PingServer_State == PINGSERVER_NULL_ACTIVE_STATE) {
if (PingServer_State_event_consumed == 0 && 1) {
PingServer_OnExit(PINGSERVER_NULL_ACTIVE_STATE, _instance);
_instance->PingServer_State = PINGSERVER_NULL_ACTIVE_STATE;
PingServer_send_ping_service_pong(_instance, seq);
PingServer_OnEntry(PINGSERVER_NULL_ACTIVE_STATE, _instance);
PingServer_State_event_consumed = 1;
}
}
//End Region null
//End dsregion null
//Session list: 
}

// Observers for outgoing messages:
void (*external_PingServer_send_ping_service_pong_listener)(struct PingServer_Instance *, uint8_t)= 0x0;
void (*PingServer_send_ping_service_pong_listener)(struct PingServer_Instance *, uint8_t)= 0x0;
void register_external_PingServer_send_ping_service_pong_listener(void (*_listener)(struct PingServer_Instance *, uint8_t)){
external_PingServer_send_ping_service_pong_listener = _listener;
}
void register_PingServer_send_ping_service_pong_listener(void (*_listener)(struct PingServer_Instance *, uint8_t)){
PingServer_send_ping_service_pong_listener = _listener;
}
void PingServer_send_ping_service_pong(struct PingServer_Instance *_instance, uint8_t seq){
if (PingServer_send_ping_service_pong_listener != 0x0) PingServer_send_ping_service_pong_listener(_instance, seq);
if (external_PingServer_send_ping_service_pong_listener != 0x0) external_PingServer_send_ping_service_pong_listener(_instance, seq);
;
}



