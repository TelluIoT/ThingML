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
 *           Header for Thing PingServer
 *  Generated from ThingML (http://www.thingml.org)
 *****************************************************/

#ifndef PingServer_H_
#define PingServer_H_

#ifdef __cplusplus
extern "C" {
#endif

#include "thingml_typedefs.h"

/*****************************************************************************
 * Headers for type : PingServer
 *****************************************************************************/

// Definition of the instance struct:
struct PingServer_Instance {

// Instances of different sessions
bool active;
// Variables for the ID of the ports of the instance
uint16_t id_ping_service;
// Variables for the current instance state
int PingServer_State;
// Variables for the properties of the instance

};
// Declaration of prototypes outgoing messages :
void PingServer_OnEntry(int state, struct PingServer_Instance *_instance);
void PingServer_handle_ping_service_ping(struct PingServer_Instance *_instance, uint8_t seq);
// Declaration of callbacks for incoming messages:
void register_PingServer_send_ping_service_pong_listener(void (*_listener)(struct PingServer_Instance *, uint8_t));
void register_external_PingServer_send_ping_service_pong_listener(void (*_listener)(struct PingServer_Instance *, uint8_t));

// Definition of the states:
#define PINGSERVER_STATE 0
#define PINGSERVER_NULL_ACTIVE_STATE 1



#ifdef __cplusplus
}
#endif

#endif //PingServer_H_
