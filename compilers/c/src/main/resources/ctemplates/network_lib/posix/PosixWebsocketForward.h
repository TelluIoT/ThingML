#ifndef PosixXebsocketForward_h
#define  PosixXebsocketForward_h



#include <lws_config.h>
#include <stdio.h>
#include <stdlib.h>
#include <libwebsockets.h>




void setWSListenerID(uint16_t id);
void init_WS_server(int port);
void run_WS_server() ;
void broadcast_WS_message(char * msg, int length);

#endif
