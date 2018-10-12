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
 *      Implementation for Application Ping
 *  Generated from ThingML (http://www.thingml.org)
 *****************************************************/

#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <ctype.h>
#include <string.h>
#include <math.h>
#include <signal.h>
#include <pthread.h>
#include "thingml_typedefs.h"
#include "runtime.h"
#include "PingServer.h"
#include "PingClient.h"





/*****************************************************************************
 * Definitions for configuration : Ping
 *****************************************************************************/

//Declaration of instance variables
//Instance server
// Variables for the properties of the instance
struct PingServer_Instance server_var;
// Variables for the sessions of the instance
//Instance client
// Variables for the properties of the instance
struct PingClient_Instance client_var;
// Variables for the sessions of the instance


// Enqueue of messages PingServer::ping_service::pong
void enqueue_PingServer_send_ping_service_pong(struct PingServer_Instance *_instance, uint8_t seq){
fifo_lock();
if ( fifo_byte_available() > 5 ) {

_fifo_enqueue( (1 >> 8) & 0xFF );
_fifo_enqueue( 1 & 0xFF );

// ID of the source port of the instance
_fifo_enqueue( (_instance->id_ping_service >> 8) & 0xFF );
_fifo_enqueue( _instance->id_ping_service & 0xFF );

// parameter seq
union u_seq_t {
uint8_t p;
byte bytebuffer[1];
} u_seq;
u_seq.p = seq;
_fifo_enqueue(u_seq.bytebuffer[0] & 0xFF );
}
fifo_unlock_and_notify();
}
// Enqueue of messages PingClient::ping_service::ping
void enqueue_PingClient_send_ping_service_ping(struct PingClient_Instance *_instance, uint8_t seq){
fifo_lock();
if ( fifo_byte_available() > 5 ) {

_fifo_enqueue( (2 >> 8) & 0xFF );
_fifo_enqueue( 2 & 0xFF );

// ID of the source port of the instance
_fifo_enqueue( (_instance->id_ping_service >> 8) & 0xFF );
_fifo_enqueue( _instance->id_ping_service & 0xFF );

// parameter seq
union u_seq_t {
uint8_t p;
byte bytebuffer[1];
} u_seq;
u_seq.p = seq;
_fifo_enqueue(u_seq.bytebuffer[0] & 0xFF );
}
fifo_unlock_and_notify();
}


//New dispatcher for messages
void dispatch_pong(uint16_t sender, uint8_t param_seq) {
if (sender == server_var.id_ping_service) {
PingClient_handle_ping_service_pong(&client_var, param_seq);

}

}


//New dispatcher for messages
void dispatch_ping(uint16_t sender, uint8_t param_seq) {
if (sender == client_var.id_ping_service) {
PingServer_handle_ping_service_ping(&server_var, param_seq);

}

}


int processMessageQueue() {
fifo_lock();
while (fifo_empty()) fifo_wait();
uint8_t mbufi = 0;

// Read the code of the next port/message in the queue
uint16_t code = fifo_dequeue() << 8;

code += fifo_dequeue();

// Switch to call the appropriate handler
switch(code) {
case 1:{
byte mbuf[5 - 2];
while (mbufi < (5 - 2)) mbuf[mbufi++] = fifo_dequeue();
fifo_unlock();
uint8_t mbufi_pong = 2;
union u_pong_seq_t {
uint8_t p;
byte bytebuffer[1];
} u_pong_seq;
u_pong_seq.bytebuffer[0] = mbuf[mbufi_pong + 0];
mbufi_pong += 1;
dispatch_pong((mbuf[0] << 8) + mbuf[1] /* instance port*/,
 u_pong_seq.p /* seq */ );
break;
}
case 2:{
byte mbuf[5 - 2];
while (mbufi < (5 - 2)) mbuf[mbufi++] = fifo_dequeue();
fifo_unlock();
uint8_t mbufi_ping = 2;
union u_ping_seq_t {
uint8_t p;
byte bytebuffer[1];
} u_ping_seq;
u_ping_seq.bytebuffer[0] = mbuf[mbufi_ping + 0];
mbufi_ping += 1;
dispatch_ping((mbuf[0] << 8) + mbuf[1] /* instance port*/,
 u_ping_seq.p /* seq */ );
break;
}
}
return 1;
}


//external Message enqueue

void initialize_configuration_Ping() {
// Initialize connectors
register_PingServer_send_ping_service_pong_listener(&enqueue_PingServer_send_ping_service_pong);
register_PingClient_send_ping_service_ping_listener(&enqueue_PingClient_send_ping_service_ping);


// Network Initialization
// End Network Initialization

// Init the ID, state variables and properties for instance server
server_var.active = true;
server_var.id_ping_service = add_instance( (void*) &server_var);
server_var.PingServer_State = PINGSERVER_NULL_ACTIVE_STATE;

PingServer_OnEntry(PINGSERVER_STATE, &server_var);
// Init the ID, state variables and properties for instance client
client_var.active = true;
client_var.id_ping_service = add_instance( (void*) &client_var);
client_var.PingClient_PingClientMachine_State = PINGCLIENT_PINGCLIENTMACHINE_PING_STATE;
client_var.PingClient_PingClientMachine_counter_var = 0;

PingClient_PingClientMachine_OnEntry(PINGCLIENT_PINGCLIENTMACHINE_STATE, &client_var);
}




void term(int signum)
{
    

    fflush(stdout);
    fflush(stderr);
    exit(signum);
}


int main(int argc, char *argv[]) {
    struct sigaction action;
    memset(&action, 0, sizeof(struct sigaction));
    action.sa_handler = term;
    sigaction(SIGINT, &action, NULL);
    sigaction(SIGTERM, &action, NULL);

    init_runtime();
    
    initialize_configuration_Ping();

    while (1) {
        
// Network Listener// End Network Listener

int emptyEventConsumed = 1;
while (emptyEventConsumed != 0) {
emptyEventConsumed = 0;
emptyEventConsumed += PingClient_handle_empty_event(&client_var);
}

        processMessageQueue();
  }
}