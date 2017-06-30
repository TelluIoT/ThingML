/*********************************
 * Methods - ThingMlRuntime_class
 *********************************/

/*NAME*/_ThingMlRuntime_class::/*NAME*/_ThingMlRuntime_class() { 

	fifo_head = 0;
	fifo_tail = 0;
	
	instances_count = 0;
}

/*********************************
 * Implementation - Instance IDs and lookup 
 *********************************/


void * /*NAME*/_ThingMlRuntime_class::instance_by_id(uint16_t id) {
  return instances[id];
}

uint16_t /*NAME*/_ThingMlRuntime_class::add_instance(void * instance_struct) {
  instances[instances_count] = instance_struct;
  return instances_count++;
}


/******************************************
 * Implementation - Simple byte FIFO
 ******************************************/

// Returns the number of byte currently in the fifo
int /*NAME*/_ThingMlRuntime_class::fifo_byte_length() {
  if (fifo_tail >= fifo_head)
    return fifo_tail - fifo_head;
  return fifo_tail + FIFO_SIZE - fifo_head;
}

// Returns the number of bytes currently available in the fifo
int /*NAME*/_ThingMlRuntime_class::fifo_byte_available() {
  return FIFO_SIZE - 1 - fifo_byte_length();
}

// Returns true if the fifo is empty
int /*NAME*/_ThingMlRuntime_class::fifo_empty() {
  return fifo_head == fifo_tail;
}

// Return true if the fifo is full
int /*NAME*/_ThingMlRuntime_class::fifo_full() {
  return fifo_head == ((fifo_tail + 1) % FIFO_SIZE);
}

// Enqueue 1 byte in the fifo if there is space
// returns 1 for sucess and 0 if the fifo was full
int /*NAME*/_ThingMlRuntime_class::fifo_enqueue(byte b) {
  int new_tail = (fifo_tail + 1) % FIFO_SIZE;
  if (new_tail == fifo_head) return 0; // the fifo is full
  fifo[fifo_tail] = b;
  fifo_tail = new_tail;
  return 1;
}

// Enqueue 1 byte in the fifo without checking for available space
// The caller should have checked that there is enough space
int /*NAME*/_ThingMlRuntime_class::_fifo_enqueue(byte b) {
  fifo[fifo_tail] = b;
  fifo_tail = (fifo_tail + 1) % FIFO_SIZE;
  return 0; // Dummy added by steffend
}

// Dequeue 1 byte in the fifo.
// The caller should check that the fifo is not empty
byte /*NAME*/_ThingMlRuntime_class::fifo_dequeue() {
  if (!fifo_empty()) {
    byte result = fifo[fifo_head];
    fifo_head = (fifo_head + 1) % FIFO_SIZE;
    return result;
  }
  return 0;
}

