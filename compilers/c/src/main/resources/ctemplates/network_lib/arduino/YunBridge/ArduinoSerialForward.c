
#define LISTENER_STATE_IDLE 0
#define LISTENER_STATE_READING 1
#define LISTENER_STATE_ESCAPE 2
#define LISTENER_STATE_ERROR 3
#define START_BYTE 18
#define STOP_BYTE 19
#define ESCAPE_BYTE 125
#define DEBUGG_SERIAL
#define SERIAL_BUFFER_SIZE 64

#ifdef DEBUGG_SERIAL
SoftwareSerial mySerial(10, 11);
#define TRACE(s) (mySerial.print(s))
#define TRACE_BIN(s) (mySerial.print(s, BIN))
#define INIT_SWS (mySerial.begin(9600))
#endif

#ifndef DEBUGG_SERIAL

#define TRACE(s) 
#define TRACE_BIN(s)
#define INIT_SWS

#endif

/* ---- Software Serial Transfer --- */
//SoftwareSerial mySerial(5, 6);
/* ---- Software Serial Transfer --- */

//byte serialBufferToSend[16];
uint8_t serialMsgSizeToSend = 0;
byte serialBuffer[SERIAL_BUFFER_SIZE];
uint8_t serialMsgSize = 0;
byte incoming = 0;
uint8_t serialListenerState = LISTENER_STATE_IDLE;


int fifo_byte_available();
int _fifo_enqueue(byte b);

void setupArduinoSerialForward(long bps) {
	Serial1.begin(bps);
	INIT_SWS;
	TRACE("[SerialLib] Init Serial at ");
	TRACE(bps);
	TRACE("\n");
}

void SWS_trace(char * s) {
	TRACE(s);
}

void SWS_trace(int s) {
	TRACE(s);
}

void SWS_trace(uint8_t s) {
	TRACE(s);
}

void SWS_trace(uint16_t s) {
	TRACE(s);
}

void SWS_trace_BIN(uint8_t s) {
	TRACE_BIN(s);
}

void forwardByteSerial(byte b) {
	//TRACE("[forward] ");
	//TRACE(b);
	//TRACE("\n");
	
	if((b == START_BYTE) || (b == STOP_BYTE) || (b == ESCAPE_BYTE)) {
		Serial1.write(ESCAPE_BYTE);
	}
	Serial1.write(b);
}

void forwardByteSerial_Start() {
	//TRACE("[forward] ");
	//TRACE("START_BYTE");
	//TRACE("\n");
	Serial1.write(START_BYTE);
}

void forwardByteSerial_Stop() {
	//TRACE("[forward] ");
	//TRACE("STOP_BYTE");
	//TRACE("\n");
	Serial1.write(STOP_BYTE);
}

void forwardMessageSerial(byte * msg, uint8_t size) {
  
  Serial1.write(START_BYTE);
  for(uint8_t i = 0; i < size; i++) {
    if((msg[i] == START_BYTE) && (msg[i] == STOP_BYTE) && (msg[i] == ESCAPE_BYTE)) {
      Serial1.write(ESCAPE_BYTE);
    }
    Serial1.write(msg[i]);
   TRACE("[sent] ");
   TRACE(msg[i]);
   TRACE("\n");
  }
  Serial1.write(STOP_BYTE);
}

void printMsg(byte * msg, uint8_t msgSize) {
  TRACE("[received] msg: ");
  for (uint8_t i = 0; i < msgSize; i++) {
    TRACE(msg[i]);
  }
  TRACE("\n");
}

void enqueueMsg(byte * msg, uint8_t msgSize) {
	if ( fifo_byte_available() > msgSize ) {

	  for (uint8_t i = 0; i < msgSize; i++) {
	    _fifo_enqueue(msg[i]);
	  }
	} else {
		TRACE("ERROR FIFO OVERFLOW (space < ");
		TRACE(msgSize);
		TRACE(")\n");
	}
}

void readSerial() {
  //if (Serial.available()) {
  byte limit = 0;
  while ((Serial1.available()) && (limit < 256)) {
   limit++;
   //TRACE("[received] ");
    incoming = Serial1.read();
   //TRACE(incoming);
   //TRACE("\n");
    
    switch(serialListenerState) {
      case LISTENER_STATE_IDLE:
        if(incoming == START_BYTE) {
          serialListenerState = LISTENER_STATE_READING;
          serialMsgSize = 0;
        }
      break;
      
      case LISTENER_STATE_READING:
        if (serialMsgSize >= 16) {
          serialListenerState = LISTENER_STATE_ERROR;
        } else {
          if(incoming == STOP_BYTE) {
            serialListenerState = LISTENER_STATE_IDLE;
            
            printMsg(serialBuffer, serialMsgSize);
            enqueueMsg(serialBuffer, serialMsgSize);
            
          } else if (incoming == ESCAPE_BYTE) {
            serialListenerState = LISTENER_STATE_ESCAPE;
          } else {
            serialBuffer[serialMsgSize] = incoming;
            serialMsgSize++;
          }
        }
      break;
      
      case LISTENER_STATE_ESCAPE:
        if (serialMsgSize >= SERIAL_BUFFER_SIZE) {
          serialListenerState = LISTENER_STATE_ERROR;
        } else {
          serialBuffer[serialMsgSize] = incoming;
          serialMsgSize++;
          serialListenerState = LISTENER_STATE_READING;
        }
      break;
      
      case LISTENER_STATE_ERROR:
       //TRACE("[Error] Buffer overflow");
        serialListenerState = LISTENER_STATE_IDLE;
        serialMsgSize = 0;
      break;
    }
  }
  
}
