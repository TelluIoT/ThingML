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
 *        Implementation for Thing PingClient
 *  Generated from ThingML (http://www.thingml.org)
 *****************************************************/

#include "PingClient.h"

/*****************************************************************************
 * Implementation for type : PingClient
 *****************************************************************************/

// Declaration of prototypes:
//Prototypes: State Machine
void PingClient_PingClientMachine_OnExit(int state, struct PingClient_Instance *_instance);
//Prototypes: Message Sending
void PingClient_send_ping_service_ping(struct PingClient_Instance *_instance, uint8_t seq);
//Prototypes: Function
// Declaration of functions:

// Sessions functionss:


// On Entry Actions:
void PingClient_PingClientMachine_OnEntry(int state, struct PingClient_Instance *_instance) {
switch(state) {
case PINGCLIENT_PINGCLIENTMACHINE_STATE:{
_instance->PingClient_PingClientMachine_State = PINGCLIENT_PINGCLIENTMACHINE_PING_STATE;
PingClient_PingClientMachine_OnEntry(_instance->PingClient_PingClientMachine_State, _instance);
break;
}
case PINGCLIENT_PINGCLIENTMACHINE_PING_STATE:{
fprintf(stdout, "Send Ping ");
fprintf(stdout, "%i",_instance->PingClient_PingClientMachine_counter_var);
fprintf(stdout, " ... ");
PingClient_send_ping_service_ping(_instance, _instance->PingClient_PingClientMachine_counter_var);
break;
}
case PINGCLIENT_PINGCLIENTMACHINE_STOP_STATE:{
fprintf(stdout, "Bye.");
fprintf(stdout, "\n");
_instance->active = false;
break;
}
case PINGCLIENT_PINGCLIENTMACHINE_OK_STATE:{
break;
}
default: break;
}
}

// On Exit Actions:
void PingClient_PingClientMachine_OnExit(int state, struct PingClient_Instance *_instance) {
switch(state) {
case PINGCLIENT_PINGCLIENTMACHINE_STATE:{
PingClient_PingClientMachine_OnExit(_instance->PingClient_PingClientMachine_State, _instance);
break;}
case PINGCLIENT_PINGCLIENTMACHINE_PING_STATE:{
_instance->PingClient_PingClientMachine_counter_var = _instance->PingClient_PingClientMachine_counter_var + 1;
break;}
case PINGCLIENT_PINGCLIENTMACHINE_STOP_STATE:{
break;}
case PINGCLIENT_PINGCLIENTMACHINE_OK_STATE:{
break;}
default: break;
}
}

// Event Handlers for incoming messages:
void PingClient_handle_ping_service_pong(struct PingClient_Instance *_instance, uint8_t seq) {
if(!(_instance->active)) return;
//Region PingClientMachine
uint8_t PingClient_PingClientMachine_State_event_consumed = 0;
if (_instance->PingClient_PingClientMachine_State == PINGCLIENT_PINGCLIENTMACHINE_PING_STATE) {
if (PingClient_PingClientMachine_State_event_consumed == 0 && seq == _instance->PingClient_PingClientMachine_counter_var) {
PingClient_PingClientMachine_OnExit(PINGCLIENT_PINGCLIENTMACHINE_PING_STATE, _instance);
_instance->PingClient_PingClientMachine_State = PINGCLIENT_PINGCLIENTMACHINE_OK_STATE;
fprintf(stdout, "[OK]");
fprintf(stdout, "\n");
PingClient_PingClientMachine_OnEntry(PINGCLIENT_PINGCLIENTMACHINE_OK_STATE, _instance);
PingClient_PingClientMachine_State_event_consumed = 1;
}
else if (PingClient_PingClientMachine_State_event_consumed == 0 && seq != _instance->PingClient_PingClientMachine_counter_var) {
PingClient_PingClientMachine_OnExit(PINGCLIENT_PINGCLIENTMACHINE_PING_STATE, _instance);
_instance->PingClient_PingClientMachine_State = PINGCLIENT_PINGCLIENTMACHINE_STOP_STATE;
fprintf(stdout, "[Error]");
fprintf(stdout, "\n");
PingClient_PingClientMachine_OnEntry(PINGCLIENT_PINGCLIENTMACHINE_STOP_STATE, _instance);
PingClient_PingClientMachine_State_event_consumed = 1;
}
}
//End Region PingClientMachine
//End dsregion PingClientMachine
//Session list: 
}
int PingClient_handle_empty_event(struct PingClient_Instance *_instance) {
 uint8_t empty_event_consumed = 0;
if(!(_instance->active)) return 0;
//Region PingClientMachine
if (_instance->PingClient_PingClientMachine_State == PINGCLIENT_PINGCLIENTMACHINE_OK_STATE) {
if (_instance->PingClient_PingClientMachine_counter_var > 5) {
PingClient_PingClientMachine_OnExit(PINGCLIENT_PINGCLIENTMACHINE_OK_STATE, _instance);
_instance->PingClient_PingClientMachine_State = PINGCLIENT_PINGCLIENTMACHINE_STOP_STATE;
PingClient_PingClientMachine_OnEntry(PINGCLIENT_PINGCLIENTMACHINE_STOP_STATE, _instance);
return 1;
}
else if (_instance->PingClient_PingClientMachine_counter_var <= 5) {
PingClient_PingClientMachine_OnExit(PINGCLIENT_PINGCLIENTMACHINE_OK_STATE, _instance);
_instance->PingClient_PingClientMachine_State = PINGCLIENT_PINGCLIENTMACHINE_PING_STATE;
PingClient_PingClientMachine_OnEntry(PINGCLIENT_PINGCLIENTMACHINE_PING_STATE, _instance);
return 1;
}
}
//begin dispatchEmptyToSession
//end dispatchEmptyToSession
return empty_event_consumed;
}

// Observers for outgoing messages:
void (*external_PingClient_send_ping_service_ping_listener)(struct PingClient_Instance *, uint8_t)= 0x0;
void (*PingClient_send_ping_service_ping_listener)(struct PingClient_Instance *, uint8_t)= 0x0;
void register_external_PingClient_send_ping_service_ping_listener(void (*_listener)(struct PingClient_Instance *, uint8_t)){
external_PingClient_send_ping_service_ping_listener = _listener;
}
void register_PingClient_send_ping_service_ping_listener(void (*_listener)(struct PingClient_Instance *, uint8_t)){
PingClient_send_ping_service_ping_listener = _listener;
}
void PingClient_send_ping_service_ping(struct PingClient_Instance *_instance, uint8_t seq){
if (PingClient_send_ping_service_ping_listener != 0x0) PingClient_send_ping_service_ping_listener(_instance, seq);
if (external_PingClient_send_ping_service_ping_listener != 0x0) external_PingClient_send_ping_service_ping_listener(_instance, seq);
;
}



