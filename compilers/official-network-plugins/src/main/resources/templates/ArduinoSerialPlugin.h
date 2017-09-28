/*****************************************************/
//                    /*PROTOCOL*/
/*****************************************************/

struct /*PROTOCOL*/_instance_type {
    uint16_t listener_id;
    /*INSTANCE_INFORMATION*/
};

extern struct /*PROTOCOL*/_instance_type /*PROTOCOL*/_instance;

void /*PROTOCOL*/_setup();

void /*PROTOCOL*/_set_listener_id(uint16_t id);

void /*PROTOCOL*/_forwardMessage(byte * msg, uint8_t size);

void /*PROTOCOL*/_read();

/********************* FORWARDERS *********************/

/*FORWARDERS*/