/*****************************************************/
//                     MSP global
/*****************************************************/

void MSP_parser(byte* buffer, uint8_t size, uint8_t cmd, uint16_t listener_id) {
    uint8_t msg[size+2];
    uint16_t msgId;
    uint8_t i = 2;
    
    switch (cmd) {
/*PARSER_IMPLEMENTATION*/
    }
    
    msg[0] = msgId >> 8;
    msg[1] = msgId & 0xFF;
    externalMessageEnqueue(msg, size+2, listener_id);
}

//--------------------GLOBAL-SPLIT-------------------//

/*****************************************************/
//                 MSP over /*PORT*/
/*****************************************************/

#define MSP_/*PORT*/_LISTENER_STATE_IDLE 0
#define MSP_/*PORT*/_LISTENER_STATE_READING 2
#define MSP_/*PORT*/_LISTENER_STATE_ERROR 3

struct MSP_/*PORT*/_instance_type {
    uint16_t listener_id;
    /*INSTANCE_INFORMATION*/
} MSP_/*PORT*/_instance;

void MSP_/*PORT*/_setup() {
    /*PORT*/.begin(/*BAUDRATE*/);
}

void MSP_/*PORT*/_set_listener_id(uint16_t id) {
    MSP_/*PORT*/_instance.listener_id = id;
}

void MSP_/*PORT*/_forwardMessage(byte * msg, uint8_t size, uint8_t cmd) {
    /*PORT*/.write("$M<");
    /*PORT*/.write(size);
    /*PORT*/.write(cmd);
    
    uint8_t crc = size ^ cmd;
    for(uint8_t i = 0; i < size; i++) {
        /*PORT*/.write(msg[i]);
        crc ^= msg[i];
    }
    
    /*PORT*/.write(crc);
}

uint8_t MSP_/*PORT*/_serialListenerState = 0;
uint8_t MSP_/*PORT*/_msg_buf[MSP_MAX_MSG_SIZE];
uint8_t MSP_/*PORT*/_msg_index = 0;
uint8_t MSP_/*PORT*/_msg_size = 0;
uint8_t MSP_/*PORT*/_msg_cmd = 0;
uint8_t MSP_/*PORT*/_msg_recv_crc = 0;
uint8_t MSP_/*PORT*/_msg_calc_crc = 0;
uint8_t MSP_/*PORT*/_incoming = 0;

void MSP_/*PORT*/_read() {
    byte limit = 0;
    while ((/*PORT*/.available()) && (limit < MSP_MAX_MSG_SIZE)) {
        limit++;
        MSP_/*PORT*/_incoming = /*PORT*/.read();
        
        switch(MSP_/*PORT*/_serialListenerState) {
            case MSP_LISTENER_STATE_IDLE:
                if (MSP_/*PORT*/_incoming == '$') {
                    MSP_/*PORT*/_serialListenerState = MSP_LISTENER_STATE_PREAMBLE;
                    MSP_/*PORT*/_msg_index = 0;
                }
                break;
            case MSP_LISTENER_STATE_PREAMBLE:
                if (MSP_/*PORT*/_incoming == 'M')
                    MSP_/*PORT*/_serialListenerState = MSP_LISTENER_STATE_DIRECTION;
                else
                    MSP_/*PORT*/_serialListenerState = MSP_LISTENER_STATE_IDLE;
                break;
            case MSP_LISTENER_STATE_DIRECTION:
                // We should only be able to se messages from the FCU
                if (MSP_/*PORT*/_incoming == '>')
                    MSP_/*PORT*/_serialListenerState = MSP_LISTENER_STATE_SIZE;
                else
                    MSP_/*PORT*/_serialListenerState = MSP_LISTENER_STATE_IDLE;
                break;
            case MSP_LISTENER_STATE_SIZE:
                if (MSP_/*PORT*/_incoming > MSP_MAX_MSG_SIZE)
                    MSP_/*PORT*/_serialListenerState = MSP_LISTENER_STATE_IDLE;
                else {
                    MSP_/*PORT*/_msg_size = MSP_/*PORT*/_incoming;
                    MSP_/*PORT*/_serialListenerState = MSP_LISTENER_STATE_COMMAND;
                }
                break;
            case MSP_LISTENER_STATE_COMMAND:
                MSP_/*PORT*/_msg_cmd = MSP_/*PORT*/_incoming;
                if (MSP_/*PORT*/_msg_size > 0)
                    MSP_/*PORT*/_serialListenerState = MSP_LISTENER_STATE_READING;
                else
                    MSP_/*PORT*/_serialListenerState = MSP_LISTENER_STATE_CRC;
                break;
            case MSP_LISTENER_STATE_READING:
                MSP_/*PORT*/_msg_buf[MSP_/*PORT*/_msg_index] = MSP_/*PORT*/_incoming;
                if (++MSP_/*PORT*/_msg_index == MSP_/*PORT*/_msg_size)
                    MSP_/*PORT*/_serialListenerState = MSP_LISTENER_STATE_CRC;
                break;
            case MSP_LISTENER_STATE_CRC:
                MSP_/*PORT*/_msg_recv_crc = MSP_/*PORT*/_incoming;
                MSP_/*PORT*/_msg_calc_crc = MSP_/*PORT*/_msg_size ^ MSP_/*PORT*/_msg_cmd;
                for (uint8_t i = 0; i < MSP_/*PORT*/_msg_size; i++)
                    MSP_/*PORT*/_msg_calc_crc ^= MSP_/*PORT*/_msg_buf[i];
                if (MSP_/*PORT*/_msg_recv_crc == MSP_/*PORT*/_msg_calc_crc)
                    MSP_parser(MSP_/*PORT*/_msg_buf, MSP_/*PORT*/_msg_size, MSP_/*PORT*/_msg_cmd, MSP_/*PORT*/_instance.listener_id);
                MSP_/*PORT*/_serialListenerState = MSP_LISTENER_STATE_IDLE;
                break;
        }
    }
}

/********************* FORWARDERS *********************/
