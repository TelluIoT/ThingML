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


/*********************************
 * Instance IDs and lookup
 *********************************/
void * instance_by_id(uint16_t id);

uint16_t add_instance(void * instance_struct);



/*********************************
 *              FIFO             *
 *********************************/

struct instance_fifo {
    pthread_mutex_t fifo_mut;
    pthread_cond_t fifo_cond;
    uint16_t fifo_size;
    byte * fifo;
    uint16_t fifo_head;
    uint16_t fifo_tail;
};


/* Queuing of pointers (size is different depending of the platform)*/

#define PTR_MAX_SIZE 8  // Code generator should adjust this

typedef union {
   uint8_t buffer[PTR_MAX_SIZE];
   void* pointer;
} ptr_union_t;

/*
void _fifo_enqueue_ptr(void * ptr);
void * _fifo_dequeue_ptr();
static_assert(sizeof(void*) <= PTR_MAX_SIZE*sizeof(uint8_t));
*/

/* Returns the number of byte currently in the fifo */
int fifo_byte_length(struct instance_fifo *fifo);
/* Returns the number of bytes currently available in the fifo */
int fifo_byte_available(struct instance_fifo *fifo);
/* Returns true if the fifo is empty */
int fifo_empty(struct instance_fifo *fifo);
/* Return true if the fifo is full */
int fifo_full(struct instance_fifo *fifo);
/* Enqueue 1 byte in the fifo if there is space
   returns 1 for sucess and 0 if the fifo was full */
int fifo_enqueue(struct instance_fifo *fifo, byte b);
/* Enqueue 1 byte in the fifo without checking for available space
   The caller should have checked that there is enough space */
int _fifo_enqueue(struct instance_fifo *fifo, byte b);
/* Dequeue 1 byte in the fifo.
   The caller should check that the fifo is not empty */
byte fifo_dequeue(struct instance_fifo *fifo);

// Synchronization for thread safe FIFO access
void fifo_lock(struct instance_fifo *fifo);
void fifo_unlock(struct instance_fifo *fifo);
void fifo_wait(struct instance_fifo *fifo);
void fifo_unlock_and_notify(struct instance_fifo *fifo);

void init_runtime(struct instance_fifo *fifo);

#ifdef __cplusplus
}
#endif

#endif /*RUNTIME_H_*/
