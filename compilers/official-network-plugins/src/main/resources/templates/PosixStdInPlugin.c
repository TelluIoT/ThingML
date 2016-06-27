#include <string.h> // string function definitions
#include <fcntl.h> // File control definitions
#include <errno.h> // Error number definitions
#include <termios.h> // POSIX terminal control definitions
#include <time.h> // time calls

#define /*PORT_NAME*/_INPUT_BUFFER_SIZE /*MSG_BUFFER_SIZE*/ // for possible future optimizations
#define /*PORT_NAME*/_MAX_MSG_LENGTH /*MSG_BUFFER_SIZE*/
#define /*PORT_NAME*/_START_CHAR /*START_CHAR*/
#define /*PORT_NAME*/_STOP_CHAR /*STOP_CHAR*/
#define /*PORT_NAME*/_ESCAPE_CHAR /*ESCAPE_CHAR*/

#define /*PORT_NAME*/_LISTENER_STATE_IDLE 0
#define /*PORT_NAME*/_LISTENER_STATE_READING 1
#define /*PORT_NAME*/_LISTENER_STATE_ESCAPE 2
#define /*PORT_NAME*/_LISTENER_STATE_ERROR 3
int /*PORT_NAME*/_device_id;


struct /*PORT_NAME*/_instance_type {
    uint16_t listener_id;
    /*INSTANCE_INFORMATION*/
};

extern struct /*PORT_NAME*/_instance_type /*PORT_NAME*/_instance;

void /*PORT_NAME*/_set_listener_id(uint16_t id) {
	/*PORT_NAME*/_instance.listener_id = id;
}

/*PARSER_IMPLEMENTATION*/

int /*PORT_NAME*/_setup() {
}

void /*PORT_NAME*/_forwardMessage(byte * msg, uint8_t size) {
    printf("%c", /*PORT_NAME*/_START_CHAR);
    uint8_t i;
    for(i = 0; i < size; i++) {
        if((msg[i] == /*PORT_NAME*/_START_CHAR) || (msg[i] == /*PORT_NAME*/_STOP_CHAR) || (msg[i] == /*PORT_NAME*/_ESCAPE_CHAR)) {
            printf("%c", /*PORT_NAME*/_ESCAPE_CHAR);
        }
        printf("%c", msg[i]);
    }
    printf("%c", /*PORT_NAME*/_STOP_CHAR);
}
	
void /*PORT_NAME*/_start_receiver_process()
{				
    int stdinListenerState = /*PORT_NAME*/_LISTENER_STATE_IDLE;
    char stdinBuffer[/*PORT_NAME*/_MAX_MSG_LENGTH];
    int stdinMsgSize = 0;
    char ch;
    while (1) {
        while(read(STDIN_FILENO, &ch, 1) > 0) {
            switch(stdinListenerState) {
                case /*PORT_NAME*/_LISTENER_STATE_IDLE:
                    if(ch == /*PORT_NAME*/_START_CHAR) {
                        stdinListenerState = /*PORT_NAME*/_LISTENER_STATE_READING;
                        stdinMsgSize = 0;
                    }
                break;

                case /*PORT_NAME*/_LISTENER_STATE_READING:
                    if (stdinMsgSize > /*PORT_NAME*/_MAX_MSG_LENGTH) {
                        stdinListenerState = /*PORT_NAME*/_LISTENER_STATE_ERROR;
                    } else {
                        if(ch == /*PORT_NAME*/_STOP_CHAR) {
                            stdinListenerState = /*PORT_NAME*/_LISTENER_STATE_IDLE;

                            /*PARSER_CALL*/

                        } else if (ch == /*PORT_NAME*/_ESCAPE_CHAR) {
                            stdinListenerState = /*PORT_NAME*/_LISTENER_STATE_ESCAPE;
                        } else {
                            stdinBuffer[stdinMsgSize] = ch;
                            stdinMsgSize++;
                        }
                    }
                break;

                case /*PORT_NAME*/_LISTENER_STATE_ESCAPE:
                    if (stdinMsgSize > /*PORT_NAME*/_MAX_MSG_LENGTH) {
                        stdinListenerState = /*PORT_NAME*/_LISTENER_STATE_ERROR;
                    } else {
                        stdinBuffer[stdinMsgSize] = ch;
                        stdinMsgSize++;
                        stdinListenerState = /*PORT_NAME*/_LISTENER_STATE_READING;
                    }
                break;

                case /*PORT_NAME*/_LISTENER_STATE_ERROR:
                    stdinListenerState = /*PORT_NAME*/_LISTENER_STATE_IDLE;
                    stdinMsgSize = 0;
                break;
            }
        }
    }
}

