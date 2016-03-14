/*****************************************************/
//                    /*PROTOCOL*/
/*****************************************************/

#define /*PROTOCOL*/_BAUDRATE /*BAUDRATE*/
#define /*PROTOCOL*/_MAX_LOOP /*MAX_LOOP*/
#define /*PROTOCOL*/_MAX_MSG_SIZE /*MAX_MSG_SIZE*/
/*OTHER_VARS*/

#define /*PROTOCOL*/_LISTENER_STATE_IDLE 0
#define /*PROTOCOL*/_LISTENER_STATE_ERROR 1
#define /*PROTOCOL*/_LISTENER_STATE_READ_BYTE 2
/*OTHER_CASES*/

struct /*PROTOCOL*/_instance_type {
    uint16_t listener_id;
    /*INSTANCE_INFORMATION*/
} /*PROTOCOL*/_instance;

int fifo_byte_available();
int _fifo_enqueue(byte b);

void /*PROTOCOL*/_setup() {
  /*PROTOCOL*/.begin(/*PROTOCOL*/_BAUDRATE);
}

void /*PROTOCOL*/_set_listener_id(uint16_t id) {
  /*PROTOCOL*/_instance.listener_id = id;
}

void /*PROTOCOL*/_forwardMessage(byte * msg, uint8_t size) {
  /*WRITE_HEADER*/
  for(uint8_t i = 0; i < size; i++) {
    /*WRITE_ESCAPE*/
    /*PROTOCOL*/.write(msg[i]);
  }
  /*WRITE_FOOTER*/
}

/*PARSER_IMPLEMENTATION*/

uint8_t /*PROTOCOL*/_serialListenerState = 0;
uint8_t /*PROTOCOL*/_msg_buf[/*PROTOCOL*/_MAX_MSG_SIZE];
uint16_t /*PROTOCOL*/_msg_index = 0;

void /*PROTOCOL*/_read() {
	int loop_count = 0;
	while ((/*PROTOCOL*/.available() > 0) && (loop_count < /*PROTOCOL*/_MAX_LOOP)) {
		switch(/*PROTOCOL*/_serialListenerState) {
			case /*PROTOCOL*/_LISTENER_STATE_IDLE:
				/*PROTOCOL*/_msg_index = 0;
				//TRANSITION TO READ_HEADER_0
				//TRANSITION TO ERROR
			break;
			/*OTHER_CASES*/
			case /*PROTOCOL*/_LISTENER_STATE_READ_BYTE:
				//ESCAPE
				//END soit un compteur, soit un char, parse call
				//ERROR

				/*PROTOCOL*/_msg_buf[/*PROTOCOL*/_msg_index] = /*PROTOCOL*/.read();
				/*PROTOCOL*/_msg_index++;
			break;
			case /*PROTOCOL*/_LISTENER_STATE_ERROR:
				//TRANSITION TO IDLE
			break;
		}
		loop_count++;
	}
}

/*FORWARDERS*/