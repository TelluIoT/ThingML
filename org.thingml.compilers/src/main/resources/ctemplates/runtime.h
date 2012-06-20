/*****************************************************
 *      THIS IS A GENERATED FILE. DO NOT EDIT.
 *
 *  Generated from ThingML (http://www.thingml.org)
 *****************************************************/

#ifndef RUNTIME_H_
#define RUNTIME_H_

#ifdef __cplusplus
extern "C" {
#endif

#include <stdint.h>

typedef unsigned char byte;

#define PTR_MAX_SIZE 8

typedef union {
   uint8_t buffer[PTR_MAX_SIZE];
   void* pointer;
} ptr_union_t;

/*

proxy_t proxy;
proxy.pointer = the_pointer;

// call to the function, cannot be generic here
f(proxy.buffer[0], proxy.buffer[1], proxy.buffer[2], proxy.buffer[3]);

// how to recover the value of the pointer
void f(int b0, int b1, int b2, int b3) {

  proxy_t proxy = { { b0, b1, b2, b3 } };

  the_pointer = proxy.pointer;

}

*/

static_assert(sizeof(void*) <= PTR_MAX_SIZE*sizeof(uint8_t));

/* Adds and instance to the runtime and returns its id */
uint16_t add_instance(void * instance_struct);
/* Returns the instance with id */
void * instance_by_id(uint16_t id);

/* Returns the number of byte currently in the fifo */
int fifo_byte_length();
/* Returns the number of bytes currently available in the fifo */
int fifo_byte_available();
/* Returns true if the fifo is empty */
int fifo_empty();
/* Return true if the fifo is full */
int fifo_full();
/* Enqueue 1 byte in the fifo if there is space
   returns 1 for sucess and 0 if the fifo was full */
int fifo_enqueue(byte b);
/* Enqueue 1 byte in the fifo without checking for available space
   The caller should have checked that there is enough space */
int _fifo_enqueue(byte b);
/* Dequeue 1 byte in the fifo.
   The caller should check that the fifo is not empty */
byte fifo_dequeue();

// Synchronization for thread safe FIFO access
void fifo_lock();
void fifo_unlock();
void fifo_wait();
void fifo_unlock_and_notify();

void init_runtime();

#ifdef __cplusplus
}
#endif

#endif /*RUNTIME_H_*/
