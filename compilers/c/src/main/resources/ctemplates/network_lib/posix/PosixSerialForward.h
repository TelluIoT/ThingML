#ifndef PosixSerialForward_h

#define PosixSerialForward_h

#include "PosixSerialForward.c"


void setListenerID(uint16_t id);
int open_serial(char * device, uint32_t baudrate);
void forwardMessageSerial(byte * msg, uint8_t size);
void start_receiver_process();

#endif
