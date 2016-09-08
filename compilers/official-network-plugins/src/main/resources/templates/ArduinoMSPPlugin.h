/*****************************************************/
//                     MSP global
/*****************************************************/

#define MSP_MAX_MSG_SIZE 64
// This is manually found from the protocol specification, currently given MSP_SERVO_CONF

#define MSP_LISTENER_STATE_IDLE 0
#define MSP_LISTENER_STATE_PREAMBLE 1
#define MSP_LISTENER_STATE_DIRECTION 2
#define MSP_LISTENER_STATE_SIZE 3
#define MSP_LISTENER_STATE_COMMAND 4
#define MSP_LISTENER_STATE_READING 5
#define MSP_LISTENER_STATE_CRC 6

void externalMessageEnqueue(uint8_t * msg, uint8_t msgSize, uint16_t listener_id);
void MSP_parser(byte* buffer, uint8_t size, uint8_t cmd, uint16_t listener_id);

//--------------------GLOBAL-SPLIT-------------------//

/*****************************************************/
//                 MSP over /*PORT*/
/*****************************************************/

void MSP_/*PORT*/_setup();

void MSP_/*PORT*/_set_listener_id(uint16_t id);

void MSP_/*PORT*/_forwardMessage(byte * msg, uint8_t size);

void MSP_/*PORT*/_read();

/********************* FORWARDERS *********************/
