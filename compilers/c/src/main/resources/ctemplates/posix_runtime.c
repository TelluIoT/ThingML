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

/******************************************
 * Synchronization for thread safe access
 ******************************************/

pthread_mutex_t fifo_mut;
pthread_cond_t fifo_cond;

void fifo_lock() {
  pthread_mutex_lock (&fifo_mut);
}
void fifo_unlock() {
  pthread_mutex_unlock (&fifo_mut);	  
}
void fifo_wait() {
  pthread_cond_wait (&fifo_cond, &fifo_mut);
}
void fifo_unlock_and_notify() {
  pthread_mutex_unlock (&fifo_mut);
  pthread_cond_signal (&fifo_cond);
}


/******************************************
 * Initialization
 ******************************************/

void init_runtime() {
  pthread_mutex_init (&fifo_mut, NULL);
  pthread_cond_init (&fifo_cond, NULL);
}
