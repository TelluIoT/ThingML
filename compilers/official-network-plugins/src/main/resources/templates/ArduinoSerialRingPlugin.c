
/*SOFTWARE_SERIAL*/

#define /*PORT_NAME*/_LISTENER_STATE_IDLE 0
#define /*PORT_NAME*/_LISTENER_STATE_READING 1
#define /*PORT_NAME*/_LISTENER_STATE_ESCAPE 2
#define /*PORT_NAME*/_LISTENER_STATE_ERROR 3


#define /*PORT_NAME*/_START_BYTE /*START_BYTE*/
#define /*PORT_NAME*/_STOP_BYTE /*STOP_BYTE*/
#define /*PORT_NAME*/_ESCAPE_BYTE /*ESCAPE_BYTE*/

#define /*PORT_NAME*/_LIMIT_BYTE_PER_LOOP /*LIMIT_BYTE_PER_LOOP*/
#define /*PORT_NAME*/_MAX_MSG_SIZE /*MAX_MSG_SIZE*/
#define /*PORT_NAME*/_MSG_BUFFER_SIZE /*MSG_BUFFER_SIZE*/

uint8_t /*PORT_NAME*/_max_ttl = /*TTL_MAX*/;
byte /*PORT_NAME*/_serialBuffer[/*PORT_NAME*/_MSG_BUFFER_SIZE];
uint8_t /*PORT_NAME*/_serialMsgSize = 0;
byte /*PORT_NAME*/_incoming = 0;
uint8_t /*PORT_NAME*/_serialListenerState = /*PORT_NAME*/_LISTENER_STATE_IDLE;


struct /*PORT_NAME*/_instance_type {
    uint16_t listener_id;
    /*INSTANCE_INFORMATION*/
} /*PORT_NAME*/_instance;

int fifo_byte_available();
int _fifo_enqueue(byte b);

void set_max_ttl(uint8_t ttl) {
    /*PORT_NAME*/_max_ttl = ttl;
}

void /*PORT_NAME*/_setup() {
	/*PORT_NAME*/.begin(/*BAUDRATE*/);
}

void /*PORT_NAME*/_set_listener_id(uint16_t id) {
	/*PORT_NAME*/_instance.listener_id = id;
}
void /*PORT_NAME*/_forwardMessage_with_TTL(byte * msg, uint8_t size, uint8_t ttl) {
  /*PORT_NAME*/.write(/*PORT_NAME*/_START_BYTE);


    if((ttl == /*PORT_NAME*/_START_BYTE) 
		|| (ttl == /*PORT_NAME*/_STOP_BYTE) 
		|| (ttl == /*PORT_NAME*/_ESCAPE_BYTE)) {
      /*PORT_NAME*/.write(/*PORT_NAME*/_ESCAPE_BYTE);
    }
    /*PORT_NAME*/.write(ttl);

  for(uint8_t i = 0; i < size; i++) {
    if((msg[i] == /*PORT_NAME*/_START_BYTE) 
		|| (msg[i] == /*PORT_NAME*/_STOP_BYTE) 
		|| (msg[i] == /*PORT_NAME*/_ESCAPE_BYTE)) {
      /*PORT_NAME*/.write(/*PORT_NAME*/_ESCAPE_BYTE);
    }
    /*PORT_NAME*/.write(msg[i]);
  }
  /*PORT_NAME*/.write(/*PORT_NAME*/_STOP_BYTE);
}

void /*PORT_NAME*/_forwardMessage(byte * msg, uint8_t size) {
    /*PORT_NAME*/_forwardMessage_with_TTL(msg, size, /*PORT_NAME*/_max_ttl);
}

void /*PORT_NAME*/_read() {
  byte limit = 0;
  while ((/*PORT_NAME*/.available()) && (limit < /*PORT_NAME*/_LIMIT_BYTE_PER_LOOP)) {
   limit++;
    /*PORT_NAME*/_incoming = /*PORT_NAME*/.read();
    
    switch(/*PORT_NAME*/_serialListenerState) {
      case /*PORT_NAME*/_LISTENER_STATE_IDLE:
        if(/*PORT_NAME*/_incoming == /*PORT_NAME*/_START_BYTE) {
          /*PORT_NAME*/_serialListenerState = /*PORT_NAME*/_LISTENER_STATE_READING;
          /*PORT_NAME*/_serialMsgSize = 0;
        }
      break;
      
      case /*PORT_NAME*/_LISTENER_STATE_READING:
        if (/*PORT_NAME*/_serialMsgSize > /*PORT_NAME*/_MAX_MSG_SIZE) {
          /*PORT_NAME*/_serialListenerState = /*PORT_NAME*/_LISTENER_STATE_ERROR;
        } else {
          if(/*PORT_NAME*/_incoming == /*PORT_NAME*/_STOP_BYTE) {
            /*PORT_NAME*/_serialListenerState = /*PORT_NAME*/_LISTENER_STATE_IDLE;
            if(/*PORT_NAME*/_serialBuffer[0] > 0) {
                /*PORT_NAME*/_serialBuffer[0]--;
            }
            if(/*PORT_NAME*/_serialBuffer[0] > 0) {
                /*PORT_NAME*/_forwardMessage_with_TTL(/*PORT_NAME*/_serialBuffer+1, /*PORT_NAME*/_serialMsgSize-1, /*PORT_NAME*/_serialBuffer[0]);
            }
            
            externalMessageEnqueue(/*PORT_NAME*/_serialBuffer+1, /*PORT_NAME*/_serialMsgSize-1, /*PORT_NAME*/_instance.listener_id);
            
          } else if (/*PORT_NAME*/_incoming == /*PORT_NAME*/_ESCAPE_BYTE) {
            /*PORT_NAME*/_serialListenerState = /*PORT_NAME*/_LISTENER_STATE_ESCAPE;
          } else {
            /*PORT_NAME*/_serialBuffer[/*PORT_NAME*/_serialMsgSize] = /*PORT_NAME*/_incoming;
            /*PORT_NAME*/_serialMsgSize++;
          }
        }
      break;
      
      case /*PORT_NAME*/_LISTENER_STATE_ESCAPE:
        if (/*PORT_NAME*/_serialMsgSize >= /*PORT_NAME*/_MAX_MSG_SIZE) {
          /*PORT_NAME*/_serialListenerState = /*PORT_NAME*/_LISTENER_STATE_ERROR;
        } else {
          /*PORT_NAME*/_serialBuffer[/*PORT_NAME*/_serialMsgSize] = /*PORT_NAME*/_incoming;
          /*PORT_NAME*/_serialMsgSize++;
          /*PORT_NAME*/_serialListenerState = /*PORT_NAME*/_LISTENER_STATE_READING;
        }
      break;
      
      case /*PORT_NAME*/_LISTENER_STATE_ERROR:
        /*PORT_NAME*/_serialListenerState = /*PORT_NAME*/_LISTENER_STATE_IDLE;
        /*PORT_NAME*/_serialMsgSize = 0;
      break;
    }
  }
  
}
