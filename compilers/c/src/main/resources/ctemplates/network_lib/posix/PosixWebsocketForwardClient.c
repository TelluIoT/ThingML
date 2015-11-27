#include <lws_config.h>
#include <stdio.h>
#include <stdlib.h>
#include <libwebsockets.h>

#ifndef BOOL
#define BOOL
typedef enum { false, true } bool;
#endif
//externalMessageEnqueue(uint8_t * msg, uint8_t msgSize, uint16_t listener_id);



struct /*PORT_NAME*/_instance_type {
    uint16_t listener_id;
    /*INSTANCE_INFORMATION*/
};

extern struct /*PORT_NAME*/_instance_type /*PORT_NAME*/_instance;


struct libwebsocket * /*PORT_NAME*/_socket;


struct lws_context_creation_info /*PORT_NAME*/_info;
struct libwebsocket_context */*PORT_NAME*/_context;

bool /*PORT_NAME*/_is_open;



static int /*PORT_NAME*/_callback_ThingML_protocol(struct libwebsocket_context * this,
                                   struct libwebsocket *wsi,
                                   enum libwebsocket_callback_reasons reason,
                                   void *user, void *in, size_t len)
{
   
    switch (reason) {
        
	case LWS_CALLBACK_CLIENT_ESTABLISHED:
		/*TRACE_LEVEL_3*/printf("[/*PORT_NAME*/] LWS_CALLBACK_CLIENT_ESTABLISHED\n");
		/*PORT_NAME*/_is_open = true;
		/*CONNEXION_ESTABLISHED*/
		break;

	case LWS_CALLBACK_CLOSED:
		/*TRACE_LEVEL_3*/printf("[/*PORT_NAME*/] LWS_CALLBACK_CLOSED\n");
		/*PORT_NAME*/_is_open = false;
		/*PORT_NAME*/_socket = NULL;
		break;

	case LWS_CALLBACK_CLIENT_RECEIVE:{
            int len = strlen((char *) in);
            /*TRACE_LEVEL_2*/printf("[/*PORT_NAME*/] l:%i\n", len);
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
                            externalMessageEnqueue(msg, (len / 3), /*PORT_NAME*/_instance.listener_id);
                            /*TRACE_LEVEL_2*/printf("[/*PORT_NAME*/] Message received\n");

                    } else {
                            /*TRACE_LEVEL_1*/printf("[/*PORT_NAME*/] incorrect message '%s'\n", (char *) in);
                    }
            } else {
                /*TRACE_LEVEL_1*/printf("[/*PORT_NAME*/] incorrect message '%s'\n", (char *) in);
            }
		break;}

	case LWS_CALLBACK_CLIENT_WRITEABLE:{
		/*TRACE_LEVEL_3*/printf("[/*PORT_NAME*/] LWS_CALLBACK_CLIENT_WRITEABLE\n");
		/*PORT_NAME*/_is_open = true;
		break;}

	default:
		/*TRACE_LEVEL_3*/printf("[/*PORT_NAME*/] LWS_CALLBACK default\n");
		break;
    }
   
   
    return 0;
}

static struct libwebsocket_protocols /*PORT_NAME*/_protocols[] = {
    {
        "ThingML-protocol", // protocol name - very important!
        /*PORT_NAME*/_callback_ThingML_protocol,   // callback
        0                          // we don't use any per session data
    },
    {
        NULL, NULL, 0   /* End of list */
    }
};

void /*PORT_NAME*/_set_listener_id(uint16_t id) {
	/*PORT_NAME*/_instance.listener_id = id;
}

void /*PORT_NAME*/_setup() {
	/*PORT_NAME*/_is_open = false;

        lws_set_log_level(2,NULL);
    
    
	/*PORT_NAME*/_info.port = CONTEXT_PORT_NO_LISTEN;
	/*PORT_NAME*/_info.protocols = /*PORT_NAME*/_protocols;
	/*PORT_NAME*/_info.extensions = libwebsocket_get_internal_extensions();
	/*PORT_NAME*/_info.gid = -1;
	/*PORT_NAME*/_info.uid = -1;


	// create libwebsocket /*PORT_NAME*/_context representing this server
    /*PORT_NAME*/_context = libwebsocket_create_context(&/*PORT_NAME*/_info);
   
    if (/*PORT_NAME*/_context == NULL) {
        /*TRACE_LEVEL_1*/fprintf(stderr, "[/*PORT_NAME*/] libwebsocket init failed\n");
        return -1;
    }

    /*TRACE_LEVEL_1*/printf("[/*PORT_NAME*/] Init WS Client on port:%i\n", /*PORT_NUMBER*/);
}

void /*PORT_NAME*/_start_receiver_process() {
	
    /*TRACE_LEVEL_1*/printf("[/*PORT_NAME*/] Start running WS Client\n");
	
	int n = 0;
	int ret = 0;
	int port = /*PORT_NUMBER*/;
	int use_ssl = 0;
	int ietf_version = -1; /* latest */

	/*PORT_NAME*/_socket = libwebsocket_client_connect(/*PORT_NAME*/_context, "/*ADDRESS*/", port, use_ssl,
			"/", "/*ADDRESS*/", "/*ADDRESS*/",
			 /*PORT_NAME*/_protocols[0].name, ietf_version);

	if (/*PORT_NAME*/_socket == NULL) {
		fprintf(stderr, "libwebsocket connect failed\n");
		ret = 1;
	}

    /*TRACE_LEVEL_1*/printf("[/*PORT_NAME*/] Starting Listener...\n");
	
    // infinite loop, to end this server send SIGTERM. (CTRL+C)
    while (1) {
        libwebsocket_service(/*PORT_NAME*/_context, 50);
    }
	
    libwebsocket_context_destroy(/*PORT_NAME*/_context);
}

void /*PORT_NAME*/_forwardMessage(char * msg, int length/*PARAM_CLIENT_ID*/) {
	if(/*PORT_NAME*/_is_open) {
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
		/*TRACE_LEVEL_3*/printf("[/*PORT_NAME*/] Trying to send:\n%s \n", p);

		m = libwebsocket_write(/*PORT_NAME*/_socket, p, (length * 3 + 1), LWS_WRITE_TEXT);
	} else {
		/*TRACE_LEVEL_3*/printf("[/*PORT_NAME*/] Error: Attempting to write on a closed socket\n");
	}
}


