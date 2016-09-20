/*****************************************************/
//                    /*PORT_NAME*/
/*****************************************************/
struct /*PORT_NAME*/_instance_type {
    uint16_t listener_id;
} /*PORT_NAME*/_instance;

void /*PORT_NAME*/_setup();

void /*PORT_NAME*/_set_listener_id(uint16_t id);

void /*PORT_NAME*/_forwardMessage(byte * msg, uint8_t size);

void /*PORT_NAME*/_read();

/********************* FORWARDERS *********************/

/*FORWARDERS*/