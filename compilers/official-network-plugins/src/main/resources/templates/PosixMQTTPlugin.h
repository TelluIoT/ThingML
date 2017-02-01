#ifndef /*PORT_NAME*/_PosixMQTT_Client_h
#define  /*PORT_NAME*/_PosixMQTT_Client_h

#include <errno.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <ctype.h>
#include <stdint.h>
#include <math.h>
#include <time.h>
#include <mosquitto.h>

/*INCLUDES*/

struct /*PORT_NAME*/_Instance {
    uint16_t listener_id;
    /*INSTANCE_INFORMATION*/
};

void /*PORT_NAME*/_setup(struct /*PORT_NAME*/_Instance *_instance);

void /*PORT_NAME*/_start_receiver_thread();
void /*PORT_NAME*/_loop_poll();

/*FORWARDERS*/

#endif
