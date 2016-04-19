#ifndef /*PORT_NAME*/_PosixWebsocketForwardClient_h
#define  /*PORT_NAME*/_PosixWebsocketForwardClient_h



#include "lws_config.h"
#include <stdio.h>
#include <stdlib.h>
#include <libwebsockets.h>

#include "/*PATH_TO_C*/" /*PORT_NAME*/_instance;

struct /*PORT_NAME*/_instance_type /*PORT_NAME*/_instance;


void /*PORT_NAME*/_set_listener_id(uint16_t id);
void /*PORT_NAME*/_setup();
void /*PORT_NAME*/_start_receiver_process() ;
void /*PORT_NAME*/_forwardMessage(char * msg, int length);

#endif
