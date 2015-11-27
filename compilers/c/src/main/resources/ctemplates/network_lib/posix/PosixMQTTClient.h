#ifndef /*PORT_NAME*/_PosixMQTT_Client_h
#define  /*PORT_NAME*/_PosixMQTT_Client_h



#include "/*PATH_TO_C*/"/*PORT_NAME*/_instance;

struct /*PORT_NAME*/_instance_type /*PORT_NAME*/_instance;


void /*PORT_NAME*/_set_listener_id(uint16_t id);


void /*PORT_NAME*/_setup();

void /*PORT_NAME*/_start_receiver_process();

void /*PORT_NAME*/_forwardMessage(uint8_t * msg, int size/*PUBLISH_MULTI_OR_MONO_DECLARATION*/);

#endif
