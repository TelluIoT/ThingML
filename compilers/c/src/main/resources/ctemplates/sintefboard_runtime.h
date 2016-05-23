#define MAX_INSTANCES 32
#define FIFO_SIZE 256

class /*NAME*/_ThingMlRuntime_class {
private:


/******************************************
 * Simple byte FIFO implementation  
 ******************************************/

byte fifo[FIFO_SIZE];
int fifo_head;
int fifo_tail;


/*********************************
 * Instance IDs and lookup  
 *********************************/

void * instances[MAX_INSTANCES];
uint16_t instances_count;

public:

/*NAME*/_ThingMlRuntime_class(); 

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

/* Adds and instance to the runtime and returns its id */
uint16_t add_instance(void * instance_struct);
/* Returns the instance with id */
void * instance_by_id(uint16_t id);

};

