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
 *           Header for Thing PingClient
 *  Generated from ThingML (http://www.thingml.org)
 *****************************************************/

#ifndef PingClient_H_
#define PingClient_H_

#ifdef __cplusplus
extern "C" {
#endif

#include "thingml_typedefs.h"

/*****************************************************************************
 * Headers for type : PingClient
 *****************************************************************************/

// Definition of the instance struct:
struct PingClient_Instance {

// Instances of different sessions
bool active;
// Variables for the ID of the ports of the instance
uint16_t id_ping_service;
// Variables for the current instance state
int PingClient_PingClientMachine_State;
// Variables for the properties of the instance
uint8_t PingClient_PingClientMachine_counter_var;

};
// Declaration of prototypes outgoing messages :
void PingClient_PingClientMachine_OnEntry(int state, struct PingClient_Instance *_instance);
void PingClient_handle_ping_service_pong(struct PingClient_Instance *_instance, uint8_t seq);
// Declaration of callbacks for incoming messages:
void register_PingClient_send_ping_service_ping_listener(void (*_listener)(struct PingClient_Instance *, uint8_t));
void register_external_PingClient_send_ping_service_ping_listener(void (*_listener)(struct PingClient_Instance *, uint8_t));

// Definition of the states:
#define PINGCLIENT_PINGCLIENTMACHINE_PING_STATE 0
#define PINGCLIENT_PINGCLIENTMACHINE_STOP_STATE 1
#define PINGCLIENT_PINGCLIENTMACHINE_OK_STATE 2
#define PINGCLIENT_PINGCLIENTMACHINE_STATE 3



#ifdef __cplusplus
}
#endif

#endif //PingClient_H_
