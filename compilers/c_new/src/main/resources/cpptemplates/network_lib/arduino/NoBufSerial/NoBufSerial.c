
/*SOFTWARE_SERIAL*/

#define /*PORT_NAME*/_LISTENER_STATE_IDLE 0
#define /*PORT_NAME*/_LISTENER_STATE_ERROR 1
/*LISTENER_STATE*/

uint8_t /*PORT_NAME*/_serialListenerState = /*PORT_NAME*/_LISTENER_STATE_IDLE;


struct /*PORT_NAME*/_instance_type {
    uint16_t listener_id;
    /*INSTANCE_INFORMATION*/
} /*PORT_NAME*/_instance;

int fifo_byte_available();
int _fifo_enqueue(byte b);

void /*PORT_NAME*/_setup() {
	/*PORT_NAME*/.begin(/*BAUDRATE*/);
}

void /*PORT_NAME*/_set_listener_id(uint16_t id) {
	/*PORT_NAME*/_instance.listener_id = id;
}


void /*PORT_NAME*/_forwardMessage(byte * msg, uint8_t size) {
  /*WRITE_HEADER*/
  for(uint8_t i = 0; i < size; i++) {
    /*WRITE_ESCAPE*/
    /*PORT_NAME*/.write(msg[i]);
  }
  /*WRITE_FOOTER*/
}

/*PARSER_IMPLEMENTATION*/

/*READER_IMPLEMENTATION*/

void /*PORT_NAME*/_read() {
  if (/*PORT_NAME*/.available() > 0) {
    
    switch(/*PORT_NAME*/_serialListenerState) {
      case /*PORT_NAME*/_LISTENER_STATE_IDLE:
        if(/*PORT_NAME*/_read_header()) {
              if(!/*PORT_NAME*/_parse()) {
                /*PORT_NAME*/_serialListenerState = /*PORT_NAME*/_LISTENER_STATE_ERROR;
              }

              /*TRANSITION_TO_READ_FOOTER*/
            } else {
              /*PORT_NAME*/_serialListenerState = /*PORT_NAME*/_LISTENER_STATE_ERROR;
            }
      break;
      
      case /*PORT_NAME*/_LISTENER_STATE_ERROR:
        /*PORT_NAME*/_serialListenerState = /*PORT_NAME*/_LISTENER_STATE_IDLE;
      break;
      
      /*OTHER_CASES*/
    }
  }
  
}
