#define /*PORT_NAME*/_NB_SOFT_TIMER /*NB_SOFT_TIMER*/
uint32_t /*PORT_NAME*/_timer[/*PORT_NAME*/_NB_SOFT_TIMER];
uint32_t  /*PORT_NAME*/_prev_1sec = 0;

/*FLAGS*/

void externalMessageEnqueue(uint8_t * msg, uint8_t msgSize, uint16_t listener_id);

/*INTERRUPT_COUNTER*/
/*INTERRUPT_VECTOR*/

//struct /*PORT_NAME*/_instance_type {
//    uint16_t listener_id;
//    /*INSTANCE_INFORMATION*/
//} /*PORT_NAME*/_instance;

struct /*PORT_NAME*/_instance_type /*PORT_NAME*/_instance;


void /*PORT_NAME*/_setup() {
	/*INITIALIZATION*/

	/*PORT_NAME*/_prev_1sec = millis() + 1000;
}

void /*PORT_NAME*/_set_listener_id(uint16_t id) {
	/*PORT_NAME*/_instance.listener_id = id;
}

/*INSTRUCTIONS*/



void /*PORT_NAME*/_read() {
    uint32_t tms = millis();
    /*POLL*/
    if (/*PORT_NAME*/_prev_1sec < tms) {
        /*PORT_NAME*/_prev_1sec += 1000;
    }
    /*FLAGS_HANDLING*/
}
