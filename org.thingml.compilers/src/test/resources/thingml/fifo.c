/**
 * Copyright (C) 2011 SINTEF <franck.fleurey@sintef.no>
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3, 29 June 2007;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

#define FIFO_SIZE 256

byte fifo[FIFO_SIZE];
int fifo_head = 0;
int fifo_tail = 0;

// Returns the number of byte currently in the fifo
int fifo_byte_length() {
  if (fifo_tail >= fifo_head) 
    return fifo_tail - fifo_head;
  return fifo_tail + FIFO_SIZE - fifo_head;
}

// Returns the number of bytes currently available in the fifo
int fifo_byte_available() {
  return FIFO_SIZE - 1 - fifo_length;
}

// Returns true if the fifo is empty
int fifo_empty() {
  return fifo_head == fifo_tail;
}

// Return true if the fifo is full
int fifo_full() {
  return fifo_head == ((fifo_tail + 1) % FIFO_SIZE);
}

// Enqueue 1 byte in the fifo if there is space
// returns 1 for sucess and 0 if the fifo was full
int fifo_enqueue(byte b) {
  int new_tail = (fifo_tail + 1) % FIFO_SIZE;
  if (new_tail == fifo_head) return 0; // the fifo is full
  fifo[fifo_tail] = b;
  fifo_tail = new_tail;
  return 1;
} 

// Enqueue 1 byte in the fifo without checking for available space
// The caller should have checked that there is enough space
int _fifo_enqueue(byte b) {
  fifo[fifo_tail] = b;
  fifo_tail = (fifo_tail + 1) % FIFO_SIZE;
} 

// Dequeue 1 byte in the fifo.
// The caller should check that the fifo is not empty
byte fifo_dequeue() {
  if (!fifo_empty()) {
    byte result = fifo[fifo_head];
    fifo_head = (fifo_head + 1) % FIFO_SIZE;
    return result;
  }
  return 0;
}

#define PORT_CODE 0x35
#define MSG_CODE 0x65
#define MSG_SIZE 8

void enqueueMSG(void * src, byte p1, long p2) {
  if ( fifo_byte_available > MSG_SIZE ) {
    
    // message code (16 bits)
    _fifo_enqueue( (MSG_CODE >> 8) & 0xFF );
    _fifo_enqueue( MSG_CODE & 0xFF );

    // pointer for the source port
    _fifo_enqueue( PORT_CODE  );

    // pointer to the source instance
    _fifo_enqueue( (src >> 8) & 0xFF );
    _fifo_enqueue( src & 0xFF );

    // parameter p1
    _fifo_enqueue( p1 );
    // parameter p2
    _fifo_enqueue( (p2 >> 24) & 0xFF );
    _fifo_enqueue( (p2 >> 16) & 0xFF );
    _fifo_enqueue( (p2 >> 8) & 0xFF );
    _fifo_enqueue( p2 & 0xFF );
  }
}

void dequeueMSG() {
  
  if (!fifo_empty) {
    int code = (fifo_dequeue << 8) + fifo_dequeue;
    int port = fifo_dequeue;
    switch(code) {
    case MSG_CODE: 
      handleMSG((fifo_dequeue << 8) + fifo_dequeue /* target instance */,
		fifo_dequeue /* p1 */, 
		(fifo_dequeue << 24) +(fifo_dequeue << 16) + (fifo_dequeue << 8) + fifo_dequeue /* p2 */);
      break;
    }
  }

}
