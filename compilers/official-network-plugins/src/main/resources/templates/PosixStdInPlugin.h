#ifndef PosixStdIn_h

#define PosixStdIn_h

#include "/*PATH_TO_C*/"

struct /*PORT_NAME*/_instance_type /*PORT_NAME*/_instance;

void /*PORT_NAME*/_set_listener_id(uint16_t id);
int /*PORT_NAME*/_setup();
void /*PORT_NAME*/_forwardMessage(byte * msg, uint8_t size);
void /*PORT_NAME*/_start_receiver_process();

#endif
