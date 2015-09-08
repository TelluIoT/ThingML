#define DEBUGG true


#include <lws_config.h>
#include <stdio.h>
#include <stdlib.h>
#include <libwebsockets.h>

typedef enum { false, true } bool;

//int fifo_byte_available();
//int _fifo_enqueue(uint8_t b);
//void fifo_lock();
//void fifo_unlock_and_notify();
externalMessageEnqueue(uint8_t * msg, uint8_t msgSize, uint16_t listener_id);

uint16_t WSlistener_id;
/*void _fifo_enqueue(uint8_t b) {
	printf("[PosixWSForward] enqueue %i\n", b);
}*/

/*void enqueueMsgWS(uint8_t * msg, uint8_t msgSize) {
	fifo_lock();	
	if ( fifo_byte_available() > (msgSize + 2) ) {
		uint8_t i;
		for (i = 0; i < 2; i++) {
			_fifo_enqueue(msg[i]);
		}
		_fifo_enqueue((WSlistener_id >> 8) & 0xFF);
		_fifo_enqueue(WSlistener_id & 0xFF);
		if(DEBUGG) {printf("[PosixWSForward] enqueue with id:%i\n", WSlistener_id);}
		for (i = 2; i < msgSize; i++) {
			_fifo_enqueue(msg[i]);
		}
	} else {
		//TRACE("ERROR FIFO OVERFLOW\n");
	}
	fifo_unlock_and_notify();
}*/


#define NB_MAX_CLIENT 16
struct libwebsocket * clients[NB_MAX_CLIENT];
int nb_client;

void add_client(struct libwebsocket *wsi) {
	int i = 0;
	bool done = false;
	while ((!done) && (i < NB_MAX_CLIENT)) {
		if(clients[i] == NULL) {
			clients[i] = wsi;
			done = true;
		}
		i++;
	}
	if (!done) {
		if(DEBUGG) {printf("[PosixWSForward] Client list overflow\n");}
	} else {
		nb_client++;
	}
}

void remove_client(struct libwebsocket *wsi) {
	int i = 0;
	bool done = false;
	while ((!done) && (i < NB_MAX_CLIENT)) {
		if(clients[i] == wsi) {
			clients[i] = NULL;
			done = true;
		}
		i++;
	}
	if (!done) {
		if(DEBUGG) {printf("[PosixWSForward] Client not found\n");}
	} else {
		nb_client--;
	}
}

struct lws_context_creation_info info;
struct libwebsocket_context *context;

bool broadcast = false;


static int callback_http(struct libwebsocket_context * this,
                         struct libwebsocket *wsi,
                         enum libwebsocket_callback_reasons reason, void *user,
                         void *in, size_t len)
{
    return 0;
}

static int callback_ThingML_protocol(struct libwebsocket_context * this,
                                   struct libwebsocket *wsi,
                                   enum libwebsocket_callback_reasons reason,
                                   void *user, void *in, size_t len)
{
   
    switch (reason) {
        case LWS_CALLBACK_ESTABLISHED: // just log message that someone is connecting
            if(DEBUGG) {printf("[PosixWSForward] Connection established\n");}
			add_client(wsi);
			
            break;

        case LWS_CALLBACK_RECEIVE: { // the funny part
            // create a buffer to hold our response
            // it has to have some pre and post padding. You don't need to care
            // what comes there, libwebsockets will do everything for you. For more info see
            // http://git.warmcat.com/cgi-bin/cgit/libwebsockets/tree/lib/libwebsockets.h#n597
/*			unsigned char *buf = (unsigned char*) malloc(LWS_SEND_BUFFER_PRE_PADDING + len +
                                                         LWS_SEND_BUFFER_POST_PADDING);
           
            int i;
           
            // pointer to `void *in` holds the incomming request
            // we're just going to put it in reverse order and put it in `buf` with
            // correct offset. `len` holds length of the request.
            for (i=0; i < len; i++) {
                buf[LWS_SEND_BUFFER_PRE_PADDING + (len - 1) - i ] = ((char *) in)[i];
            }
           
            // log what we recieved and what we're going to send as a response.
            // that disco syntax `%.*s` is used to print just a part of our buffer
            // http://stackoverflow.com/questions/5189071/print-part-of-char-array
            if(DEBUGG) {printf("[PosixWSForward] received data: %s, replying: %.*s\n", (char *) in, (int) len,
                 buf + LWS_SEND_BUFFER_PRE_PADDING);}

*/

			int len = strlen((char *) in);
			if(DEBUGG) {printf("[PosixWSForward] l:%i\n", len);}
			if ((len % 3) == 0) {
				unsigned char msg[len % 3];
				unsigned char * p = in;
				
				int buf = 0;
				int index = 0;
				bool everythingisfine = true;
				while ((index < len) && everythingisfine) {
					if((*p - 48) < 10) {
						buf = (*p - 48) + 10 * buf;
					} else {
						everythingisfine = false;
					}
					if ((index % 3) == 2) {
						if(buf < 256) {
							msg[(index-2) / 3] =  (uint8_t) buf;
						} else {
							everythingisfine = false;
						}
						buf = 0;
					}
					index++;
					p++;
				}
				if(everythingisfine) {
					int j;
					externalMessageEnqueue(msg, (len / 3), WSlistener_id);
					/*for(j = 0; j < (len / 3); j++) {
						_fifo_enqueue(msg[j]);
						if(DEBUGG) {printf("[PosixWSForward] enqueue %i\n", msg[j]);}
						if(j == 1) {
							_fifo_enqueue((uint8_t) ((listener_id >> 8) & 0xFF));
							_fifo_enqueue((uint8_t) (listener_id  & 0xFF));
							if(DEBUGG) {printf("[PosixWSForward] enqueue %i\n", (uint8_t) ((listener_id << 8) & 0xFF));}
							if(DEBUGG) {printf("[PosixWSForward] enqueue %i\n", (uint8_t) (listener_id  & 0xFF));}
						}
					}*/
				} else {
					if(DEBUGG) {printf("[PosixWSForward] incorrect message '%s'\n", (char *) in);}
				}
			} else {if(DEBUGG) {printf("[PosixWSForward] incorrect message '%s'\n", (char *) in);}}
			
			
			
/*			if(strcmp((const char *)in, "999") == 0) {
				uint8_t tab[56];
				uint8_t j;
				for(j = 0; j < 56;j++){
					tab[j] = j+199;
					//fprintf(stderr, "[PosixWSForward] write '%03i'\n", (unsigned char) tab[j]);
				}
				broadcast_WS_message((unsigned char *) tab, 56);
			}
*/           
            // send response
            // just notice that we have to tell where exactly our response starts. That's
            // why there's `buf[LWS_SEND_BUFFER_PRE_PADDING]` and how long it is.
            // we know that our response has the same length as request because
            // it's the same message in reverse order.
            //libwebsocket_write(wsi, &buf[LWS_SEND_BUFFER_PRE_PADDING], len, LWS_WRITE_TEXT);
           
            // release memory back into the wild
            //free(buf);
            break;
        }
		
		case LWS_CALLBACK_WSI_DESTROY: {
			remove_client(wsi);
			if(DEBUGG) {printf("[PosixWSForward] Wsi destroyed\n");}
		}

		case LWS_CALLBACK_CLOSED: {
			remove_client(wsi);
			if(DEBUGG) {printf("[PosixWSForward] Connexion closed\n");}
		}

        default:
            break;
    }
   
   
    return 0;
}

static struct libwebsocket_protocols protocols[] = {
    /* first protocol must always be HTTP handler */
    {
        "http-only",   // name
        callback_http, // callback
        0              // per_session_data_size
    },
    {
        "ThingML-protocol", // protocol name - very important!
        callback_ThingML_protocol,   // callback
        0                          // we don't use any per session data
    },
    {
        NULL, NULL, 0   /* End of list */
    }
};

void setWSListenerID(uint16_t id) {
	WSlistener_id = id;
	if(DEBUGG) {printf("[PosixWSForward] Register listener ID: %i\n", WSlistener_id);}
}

void init_WS_server(int port) {
	memset(&info, 0, sizeof info);

	//int port = 9000;
    const char *interface = NULL;
    // we're not using ssl
    const char *cert_path = NULL;
    const char *key_path = NULL;
    // no special options
    int opts = 0;
   
	info.port = port;
	info.iface = interface;
	info.protocols = protocols;
	info.extensions = libwebsocket_get_internal_extensions();
	info.ssl_cert_filepath = NULL;
	info.ssl_private_key_filepath = NULL;
	
	info.gid = -1;
	info.uid = -1;
	info.options = opts;
}

void run_WS_server() {


    // create libwebsocket context representing this server
    context = libwebsocket_create_context(&info);
   
    if (context == NULL) {
        fprintf(stderr, "[PosixWSForward] libwebsocket init failed\n");
        return -1;
    }
	
    if(DEBUGG) {printf("[PosixWSForward] Starting server...\n");}
	
    // infinite loop, to end this server send SIGTERM. (CTRL+C)
    while (1) {
        libwebsocket_service(context, 50);
	}
	
	libwebsocket_context_destroy(context);
}

void broadcast_WS_message(char * msg, int length) {
	int n, m, i;
	unsigned char buf[LWS_SEND_BUFFER_PRE_PADDING + (length * 3 + 1) +
						  LWS_SEND_BUFFER_POST_PADDING];
	unsigned char *p = &buf[LWS_SEND_BUFFER_PRE_PADDING];	
	unsigned char *q = p;
	n = 0;
	for(i = 0; i < length; i++) {
		//printf("%03i -> ", (unsigned char) msg[i]);
		n += sprintf((unsigned char *)q, "%03i", (unsigned char) msg[i]);
		//printf("%s\n", q);
		n--;
		q += 3;
	}
	*q = '\0';
	n++;
	//printf("[PosixWSForward] Trying to send:\n%s \n", p);
	for(i = 0; i < nb_client; i++) {
		m = libwebsocket_write(clients[i], p, (length * 3 + 1), LWS_WRITE_TEXT);
	}
}
/*
int main(void) {
	init_WS_server(9000);
	run_WS_server();
	return 0;
}*/
