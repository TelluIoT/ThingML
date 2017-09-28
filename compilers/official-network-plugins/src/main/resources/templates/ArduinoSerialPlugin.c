/*****************************************************/
//                    /*PROTOCOL*/
/*****************************************************/

#define /*PROTOCOL*/_BAUDRATE /*BAUDRATE*/
#define /*PROTOCOL*/_MAX_LOOP /*MAX_LOOP*/
#define /*PROTOCOL*/_MAX_MSG_SIZE /*MAX_MSG_SIZE*/
/*OTHER_VARS*/


#define /*PROTOCOL*/_LISTENER_STATE_IDLE 0
#define /*PROTOCOL*/_LISTENER_STATE_READING 1
#define /*PROTOCOL*/_LISTENER_STATE_ESCAPE 2
#define /*PROTOCOL*/_LISTENER_STATE_ERROR 3


#define /*PROTOCOL*/_START_BYTE /*START_BYTE*/
#define /*PROTOCOL*/_STOP_BYTE /*STOP_BYTE*/
#define /*PROTOCOL*/_ESCAPE_BYTE /*ESCAPE_BYTE*/

struct /*PROTOCOL*/_instance_type /*PROTOCOL*/_instance;

void externalMessageEnqueue(uint8_t * msg, uint8_t msgSize, uint16_t listener_id);

void /*PROTOCOL*/_setup() {
  /*PROTOCOL*/.begin(/*PROTOCOL*/_BAUDRATE);
}

void /*PROTOCOL*/_set_listener_id(uint16_t id) {
  /*PROTOCOL*/_instance.listener_id = id;
}

void /*PROTOCOL*/_forwardMessage(byte * msg, uint8_t size) {
  /*PROTOCOL*/.write(/*PROTOCOL*/_START_BYTE);
  for(uint8_t i = 0; i < size; i++) {
	if(msg[i] == /*PROTOCOL*/_ESCAPE_BYTE || msg[i] == /*PROTOCOL*/_START_BYTE || msg[i] == /*PROTOCOL*/_STOP_BYTE) {
    	/*PROTOCOL*/.write(/*PROTOCOL*/_ESCAPE_BYTE);
	}
    /*PROTOCOL*/.write(msg[i]);
  }
  /*PROTOCOL*/.write(/*PROTOCOL*/_STOP_BYTE);
}

/*PARSER_IMPLEMENTATION*/

uint8_t /*PROTOCOL*/_serialListenerState = 0;
uint8_t /*PROTOCOL*/_msg_buf[/*PROTOCOL*/_MAX_MSG_SIZE];
uint16_t /*PROTOCOL*/_msg_index = 0;
uint8_t /*PROTOCOL*/_incoming = 0;

void /*PROTOCOL*/_read() {
  byte limit = 0;
  while ((/*PROTOCOL*/.available()) && (limit < /*PROTOCOL*/_MAX_LOOP)) {
   limit++;
    /*PROTOCOL*/_incoming = /*PROTOCOL*/.read();
    
    switch(/*PROTOCOL*/_serialListenerState) {
      case /*PROTOCOL*/_LISTENER_STATE_IDLE:
        if(/*PROTOCOL*/_incoming == /*PROTOCOL*/_START_BYTE) {
          /*PROTOCOL*/_serialListenerState = /*PROTOCOL*/_LISTENER_STATE_READING;
          /*PROTOCOL*/_msg_index = 0;
        }
      break;
      
      case /*PROTOCOL*/_LISTENER_STATE_READING:
        if (/*PROTOCOL*/_msg_index > /*PROTOCOL*/_MAX_MSG_SIZE) {
          /*PROTOCOL*/_serialListenerState = /*PROTOCOL*/_LISTENER_STATE_ERROR;
        } else {
          if(/*PROTOCOL*/_incoming == /*PROTOCOL*/_STOP_BYTE) {
            /*PROTOCOL*/_serialListenerState = /*PROTOCOL*/_LISTENER_STATE_IDLE;
            
            
            /*PARSER_CALL*/
            
          } else if (/*PROTOCOL*/_incoming == /*PROTOCOL*/_ESCAPE_BYTE) {
            /*PROTOCOL*/_serialListenerState = /*PROTOCOL*/_LISTENER_STATE_ESCAPE;
          } else {
            /*PROTOCOL*/_msg_buf[/*PROTOCOL*/_msg_index] = /*PROTOCOL*/_incoming;
            /*PROTOCOL*/_msg_index++;
          }
        }
      break;
      
      case /*PROTOCOL*/_LISTENER_STATE_ESCAPE:
        if (/*PROTOCOL*/_msg_index >= /*PROTOCOL*/_MAX_MSG_SIZE) {
          /*PROTOCOL*/_serialListenerState = /*PROTOCOL*/_LISTENER_STATE_ERROR;
        } else {
          /*PROTOCOL*/_msg_buf[/*PROTOCOL*/_msg_index] = /*PROTOCOL*/_incoming;
          /*PROTOCOL*/_msg_index++;
          /*PROTOCOL*/_serialListenerState = /*PROTOCOL*/_LISTENER_STATE_READING;
        }
      break;
      
      case /*PROTOCOL*/_LISTENER_STATE_ERROR:
        /*PROTOCOL*/_serialListenerState = /*PROTOCOL*/_LISTENER_STATE_IDLE;
        /*PROTOCOL*/_msg_index = 0;
      break;
    }
  }
  
}

/*FORWARDERS*/