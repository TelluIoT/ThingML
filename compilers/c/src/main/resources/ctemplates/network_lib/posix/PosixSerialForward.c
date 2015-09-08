#include <string.h> // string function definitions
#include <fcntl.h> // File control definitions
#include <errno.h> // Error number definitions
#include <termios.h> // POSIX terminal control definitions
#include <time.h> // time calls

//#define /*PORT_NAME*/_TIMEOUT 10 // timeout waiting for messages from the serial device
#define /*PORT_NAME*/_INPUT_BUFFER_SIZE /*MSG_BUFFER_SIZE*/ // for possible future optimizations
#define /*PORT_NAME*/_MAX_MSG_LENGTH /*MAX_MSG_SIZE*/
#define /*PORT_NAME*/_START_BYTE /*START_BYTE*/
#define /*PORT_NAME*/_STOP_BYTE /*STOP_BYTE*/
#define /*PORT_NAME*/_ESCAPE_BYTE /*ESCAPE_BYTE*/

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

int fifo_byte_available();
int _fifo_enqueue(byte b);
void fifo_lock();
void fifo_unlock_and_notify();

void /*PORT_NAME*/_setListenerID(uint16_t id) {
	/*PORT_NAME*/_instance.listener_id = id;
}

int /*PORT_NAME*/_setup(char * device, uint32_t baudrate) {
	int result;
	struct termios port_settings;
	printf("Opening Serial device at %s...\n", device);
	result = open(device, O_RDWR | O_NOCTTY | O_NDELAY);
	if (result < 0) {
		perror("Error opening Serial port");
	}
	else if (tcgetattr(result, &port_settings) < 0) {// try to get current options
		perror("Error opening Serial port: could not get serial port attributes");
	}
	else {
		printf("Configuring port %s...\n", device);
		switch(baudrate) {
			case 115200:
				cfsetispeed(&port_settings, B115200);    // set baud rates to 115200 ---------- Test with 57600
				cfsetospeed(&port_settings, B115200);
			break;
			
			case 57600:
				cfsetispeed(&port_settings, B57600);    // set baud rates to 115200 ---------- Test with 57600
				cfsetospeed(&port_settings, B57600);
			break;
			
			case 38400:
				cfsetispeed(&port_settings, B38400);    // set baud rates to 38400 ---------- Test with 57600
				cfsetospeed(&port_settings, B38400);
			break;
			
			case 19200:
				cfsetispeed(&port_settings, B19200);    // set baud rates to 19200 ---------- Test with 57600
				cfsetospeed(&port_settings, B19200);
			break;
			
			case 9600:
				cfsetispeed(&port_settings, B9600);    // set baud rates to 115200 ---------- Test with 57600
				cfsetospeed(&port_settings, B9600);
			break;
			
			default:
				cfsetispeed(&port_settings, B115200);    // set baud rates to 115200 ---------- Test with 57600
				cfsetospeed(&port_settings, B115200);
			break;
		}
		// 8N1
		port_settings.c_cflag &= ~PARENB;
		port_settings.c_cflag &= ~CSTOPB;
		port_settings.c_cflag &= ~CSIZE;
		port_settings.c_cflag |= CS8;
		// no flow control
		port_settings.c_cflag &= ~CRTSCTS;
		port_settings.c_cflag |= CREAD | CLOCAL; // turn on READ & ignore ctrl lines
		port_settings.c_iflag &= ~(IXON | IXOFF | IXANY); // turn off s/w flow ctrl
		port_settings.c_lflag &= ~(ICANON | ECHO | ECHOE | ISIG); // make raw
		port_settings.c_oflag &= ~OPOST; // make raw
		// see: http://unixwiz.net/techtips/termios-vmin-vtime.html
		port_settings.c_cc[VMIN] = 0;
		port_settings.c_cc[VTIME] = 20;
		if (tcsetattr(result, TCSANOW, &port_settings) < 0 ) {
			perror("Error opening Serial port: could not set serial port attributes");
		}
		sleep(1); // wait a bit
	}

	/*PORT_NAME*/_device_id = result;
	//return result;
}
	
int send_byte(int device, uint8_t byte) {
	int n;
	unsigned char data[1];
	data[0] = byte;
	n = write(device, data, 1);
	
	//fprintf(stdout, "[lib] forwarding %i with result %i\n", data[0], n);
	if (n < 0) {
		perror("Error writing to Serial device");
		return -1;
	}
	return 0;
}

void /*PORT_NAME*/_forwardMessage(byte * msg, uint8_t size) {
	send_byte(/*PORT_NAME*/_device_id, /*PORT_NAME*/_START_BYTE);
	uint8_t i;
	for(i = 0; i < size; i++) {
		if((msg[i] == /*PORT_NAME*/_START_BYTE) && (msg[i] == /*PORT_NAME*/_STOP_BYTE) && (msg[i] == /*PORT_NAME*/_ESCAPE_BYTE)) {
	  		send_byte(/*PORT_NAME*/_device_id, /*PORT_NAME*/_ESCAPE_BYTE);
		}
		send_byte(/*PORT_NAME*/_device_id, msg[i]);
	}
	send_byte(/*PORT_NAME*/_device_id, /*PORT_NAME*/_STOP_BYTE);
}
	
void /*PORT_NAME*/_start_receiver_process()
{
	int device = /*PORT_NAME*/_device_id;
	int serialListenerState = /*PORT_NAME*/_LISTENER_STATE_IDLE;
	char serialBuffer[/*PORT_NAME*/_MAX_MSG_LENGTH];
	int serialMsgSize = 0;
	char buffer[/*PORT_NAME*/_INPUT_BUFFER_SIZE]; // Data read from the ESUSMS device
	int n; // used to store the results of select and read
	int i; // loop index
	while (1) {
		fd_set rdfs; // The file descriptor to wait on
		FD_ZERO( &rdfs );
		FD_SET(device, &rdfs ); // set to the esusms fd
		n = select(device + 1, &rdfs, NULL, NULL, NULL); // NO Timeout here (last parameter)
		if (n < 0) {
			perror("Error waiting for incoming data from Serial device");
			break;
		}
		else if (n == 0) { // timeout
			printf("Timeout waiting for incoming data from Serial device\n");
			break;
		}
		else { // there is something to read
			//printf("[receiver] rx?");
			n = read(device, &buffer, /*PORT_NAME*/_INPUT_BUFFER_SIZE * sizeof(char));
			//printf(" n=<%i>\n", n);
			if (n<0) {
				perror("Error reading from Serial device");
				break;
			}
			else if (n==0) {
				printf("Nothing to read from Serial device\n"); // Should never happen unless there are too many transmission errors with wrong CRCs
				break;
			}
			else { // There are n incoming bytes in buffer
				//printf("[receiver] rx! <%i>\n", n);
				for (i = 0; i<n; i++) {
					
					switch(serialListenerState) {
						case /*PORT_NAME*/_LISTENER_STATE_IDLE:
							if(buffer[i] == /*PORT_NAME*/_START_BYTE) {
							  serialListenerState = /*PORT_NAME*/_LISTENER_STATE_READING;
							  serialMsgSize = 0;
							}
						break;

						case /*PORT_NAME*/_LISTENER_STATE_READING:
							if (serialMsgSize > /*PORT_NAME*/_MAX_MSG_LENGTH) {
							  serialListenerState = /*PORT_NAME*/_LISTENER_STATE_ERROR;
							} else {
							  if(buffer[i] == /*PORT_NAME*/_STOP_BYTE) {
								serialListenerState = /*PORT_NAME*/_LISTENER_STATE_IDLE;

								//printMsg(serialBuffer, serialMsgSize);
								//enqueueMsg(serialBuffer, serialMsgSize);
                                                                externalMessageEnqueue(serialBuffer, serialMsgSize, /*PORT_NAME*/_instance.listener_id);

							  } else if (buffer[i] == /*PORT_NAME*/_ESCAPE_BYTE) {
								serialListenerState = /*PORT_NAME*/_LISTENER_STATE_ESCAPE;
							  } else {
								serialBuffer[serialMsgSize] = buffer[i];
								serialMsgSize++;
							  }
							}
						break;

						case /*PORT_NAME*/_LISTENER_STATE_ESCAPE:
							if (serialMsgSize > /*PORT_NAME*/_MAX_MSG_LENGTH) {
							  serialListenerState = /*PORT_NAME*/_LISTENER_STATE_ERROR;
							} else {
							  serialBuffer[serialMsgSize] = buffer[i];
							  serialMsgSize++;
							  serialListenerState = /*PORT_NAME*/_LISTENER_STATE_READING;
							}
						break;

						case /*PORT_NAME*/_LISTENER_STATE_ERROR:
							serialListenerState = /*PORT_NAME*/_LISTENER_STATE_IDLE;
							serialMsgSize = 0;
						break;
					}
				}
			}
		}
	}
}
