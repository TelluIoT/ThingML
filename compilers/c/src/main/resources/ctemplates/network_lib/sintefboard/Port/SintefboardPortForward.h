// SintefboardPortForward.h *** Dummy to make first step in reverse engineering .... sdalgard
//#ifndef SintefboardSerialForward_h
//
//#define SintefboardSerialForward_h
//
// Place prototypes for /*PORT_NAME*/ here
void /*PORT_NAME*/_setup(void);
void /*PORT_NAME*/_set_listener_id(uint16_t id);
void /*PORT_NAME*/_forwardMessage(byte * msg, uint8_t size);
void /*PORT_NAME*/_read(void);


struct /*PORT_NAME*/_instance_type {
    uint16_t listener_id;
    /*INSTANCE_INFORMATION*/
} /*PORT_NAME*/_instance;

//#endif
