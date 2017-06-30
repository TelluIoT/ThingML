/*****************************************************
 *      THIS IS A GENERATED FILE. DO NOT EDIT.
 *
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

#include "runtime.h"

/*FIFO*/

/*********************************
 * Instance IDs and lookup
 *********************************/

void * instances[MAX_INSTANCES];
uint16_t instances_count = 0;

void * instance_by_id(uint16_t id) {
  return instances[id];
}

uint16_t add_instance(void * instance_struct) {
  instances[instances_count] = instance_struct;
  return instances_count++;
}

/******************************************
 * Simple byte FIFO implementation
 ******************************************/


// Returns the number of byte currently in the fifo
int fifo_byte_length(struct instance_fifo *fifo) {
  if (fifo->fifo_tail >= fifo->fifo_head)
    return fifo->fifo_tail - fifo->fifo_head;
  return fifo->fifo_tail + fifo->fifo_size - fifo->fifo_head;
}

// Returns the number of bytes currently available in the *fifo
int fifo_byte_available(struct instance_fifo *fifo) {
  return fifo->fifo_size - 1 - fifo_byte_length(fifo);
}

// Returns true if the fifo is empty
int fifo_empty(struct instance_fifo *fifo) {
  return fifo->fifo_head == fifo->fifo_tail;
}

// Return true if the fifo is full
int fifo_full(struct instance_fifo *fifo) {
  return fifo->fifo_head == ((fifo->fifo_tail + 1) % fifo->fifo_size);
}

// Enqueue 1 byte in the fifo if there is space
// returns 1 for sucess and 0 if the fifo was full
int fifo_enqueue(struct instance_fifo *fifo, byte b) {
  int new_tail = (fifo->fifo_tail + 1) % fifo->fifo_size;
  if (new_tail == fifo->fifo_head) return 0; // the fifo is full
  fifo->fifo[fifo->fifo_tail] = b;
  fifo->fifo_tail = new_tail;
  return 1;
}

// Enqueue 1 byte in the fifo without checking for available space
// The caller should have checked that there is enough space
int _fifo_enqueue(struct instance_fifo *fifo, byte b) {
  fifo->fifo[fifo->fifo_tail] = b;
  fifo->fifo_tail = (fifo->fifo_tail + 1) % fifo->fifo_size;
}

// Dequeue 1 byte in the fifo
// The caller should check that the fifo is not empty
byte fifo_dequeue(struct instance_fifo *fifo) {
  if (!fifo_empty(fifo)) {
    byte result = fifo->fifo[fifo->fifo_head];
    fifo->fifo_head = (fifo->fifo_head + 1) % fifo->fifo_size;
    return result;
  }
  return 0;
}

/******************************************
 * Synchronization for thread safe access
 ******************************************/

void fifo_lock(struct instance_fifo *fifo) {
  pthread_mutex_lock (&(fifo->fifo_mut));
}
void fifo_unlock(struct instance_fifo *fifo) {
  pthread_mutex_unlock (&(fifo->fifo_mut));	  
}
void fifo_wait(struct instance_fifo *fifo) {
  pthread_cond_wait (&(fifo->fifo_cond), &(fifo->fifo_mut));
}
void fifo_unlock_and_notify(struct instance_fifo *fifo) {
  pthread_cond_signal (&(fifo->fifo_cond));
  pthread_mutex_unlock (&(fifo->fifo_mut));
}


/******************************************
 * Initialization
 ******************************************/

void init_runtime(struct instance_fifo *fifo) {
  pthread_mutex_init (&(*fifo).fifo_mut, NULL);
  pthread_cond_init (&(*fifo).fifo_cond, NULL);
}
