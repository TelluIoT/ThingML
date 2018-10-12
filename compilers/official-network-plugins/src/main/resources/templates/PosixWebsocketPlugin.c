#include <lws_config.h>
#include <stdio.h>
#include <stdlib.h>
#include <libwebsockets.h>

#ifndef BOOL
#define BOOL
//typedef enum { false, true } bool;
#endif

//externalMessageEnqueue(uint8_t * msg, uint8_t msgSize, uint16_t listener_id);



struct /*PORT_NAME*/_instance_type {
    uint16_t listener_id;
    /*INSTANCE_INFORMATION*/
};

extern struct /*PORT_NAME*/_instance_type /*PORT_NAME*/_instance;


struct libwebsocket * /*PORT_NAME*/_clients[/*NB_MAX_CLIENT*/];
int /*PORT_NAME*/_nb_client;

struct lws_context_creation_info /*PORT_NAME*/_info;
struct libwebsocket_context */*PORT_NAME*/_context;


uint16_t add_client(struct libwebsocket *wsi) {
	uint16_t i = 0;
	bool done = false;
	while ((!done) && (i < /*NB_MAX_CLIENT*/)) {
		if(/*PORT_NAME*/_clients[i] == NULL) {
			/*PORT_NAME*/_clients[i] = wsi;
			done = true;
		}
		i++;
	}
	if (!done) {
            /*TRACE_LEVEL_1*/printf("[/*PORT_NAME*/] Client list overflow\n");
            return -1;
	} else {
            /*PORT_NAME*/_nb_client++;
            i=i-1;
            return i;
	}
}

uint16_t remove_client(struct libwebsocket *wsi) {
	uint16_t i = 0;
	bool done = false;
	while ((!done) && (i < /*NB_MAX_CLIENT*/)) {
            if(/*PORT_NAME*/_clients[i] == wsi) {
                /*PORT_NAME*/_clients[i] = NULL;
                done = true;
            }
            i++;
	}
	if (!done) {
            /*TRACE_LEVEL_1*/printf("[/*PORT_NAME*/] Client not found\n");
            return -1;
	} else {
            /*PORT_NAME*/_nb_client--;
            i=i-1;
            return i;
	}
}


static int /*PORT_NAME*/_callback_http(
#ifndef LWS_LIBRARY_VERSION_NUMBER
                         struct libwebsocket_context * this,
#endif
                         struct libwebsocket *wsi,
#ifdef LWS_LIBRARY_VERSION_NUMBER
                         enum lws_callback_reasons reason,
#else
                         enum libwebsocket_callback_reasons reason,
#endif
                         void *user,
                         void *in, size_t len)
{
    return 0;
}

static int /*PORT_NAME*/_callback_ThingML_protocol(
#ifndef LWS_LIBRARY_VERSION_NUMBER
                                   struct libwebsocket_context * this,
#endif
                                   struct libwebsocket *wsi,
#ifdef LWS_LIBRARY_VERSION_NUMBER
                                   enum lws_callback_reasons reason,
#else
                                   enum libwebsocket_callback_reasons reason,
#endif
                                   void *user, void *in, size_t len)
{
   
    switch (reason) {
        case LWS_CALLBACK_ESTABLISHED:{ // just log message that someone is connecting
            uint16_t clientID = add_client(wsi);
            /*TRACE_LEVEL_2*/printf("[/*PORT_NAME*/] New Client:%i\n", clientID);
		/*NEW_CLIENT*/
            break;}

        case LWS_CALLBACK_RECEIVE: {
                /*PORT_NAME*/_parser(in, len, /*PORT_NAME*/_instance.listener_id);
            break;
        }
		
        //case LWS_CALLBACK_WSI_DESTROY: {
        //        uint16_t clientID = remove_client(wsi);
	//	/*CLIENT_DECO*/
        //        /*TRACE_LEVEL_1*/printf("[/*PORT_NAME*/] Wsi destroyed:%i\n", clientID);
        //}

        case LWS_CALLBACK_CLOSED: {
                uint16_t clientID = remove_client(wsi);
                /*TRACE_LEVEL_2*/printf("[/*PORT_NAME*/] Connexion with client closed:%i\n", clientID);
		/*CLIENT_DECO*/
        }

        default:
            break;
    }
   
   
    return 0;
}

#ifdef LWS_LIBRARY_VERSION_NUMBER
static struct lws_protocols protocols[] = {
#else
static struct libwebsocket_protocols protocols[] = {
#endif
    /* first protocol must always be HTTP handler */
    {
        "http-only",   // name
        /*PORT_NAME*/_callback_http, // callback
        0              // per_session_data_size
    },
    {
        "/*WS_PROTOCOL*/", // protocol name - very important!
        /*PORT_NAME*/_callback_ThingML_protocol,   // callback
        0                          // we don't use any per session data
    },
    {
        NULL, NULL, 0   /* End of list */
    }
};

/*PARSER_IMPLEMENTATION*/

void /*PORT_NAME*/_set_listener_id(uint16_t id) {
	/*PORT_NAME*/_instance.listener_id = id;
}

void /*PORT_NAME*/_setup() {
    memset(&/*PORT_NAME*/_info, 0, sizeof /*PORT_NAME*/_info);
    lws_set_log_level(2,NULL);
    int port = /*PORT_NUMBER*/;
    const char *interface = NULL;
    // we're not using ssl
    const char *cert_path = NULL;
    const char *key_path = NULL;
    // no special options
    int opts = 0;
   
    /*PORT_NAME*/_info.port = port;
    /*PORT_NAME*/_info.iface = interface;
    /*PORT_NAME*/_info.protocols = protocols;
#ifndef LWS_LIBRARY_VERSION_NUMBER
    /*PORT_NAME*/_info.extensions = libwebsocket_get_internal_extensions();
#endif
    /*PORT_NAME*/_info.ssl_cert_filepath = NULL;
    /*PORT_NAME*/_info.ssl_private_key_filepath = NULL;

    /*PORT_NAME*/_info.gid = -1;
    /*PORT_NAME*/_info.uid = -1;
    /*PORT_NAME*/_info.options = opts;
    /*TRACE_LEVEL_1*/printf("[/*PORT_NAME*/] Init WS Server on port:%i\n", port);
}

void /*PORT_NAME*/_start_receiver_process() {

    /*TRACE_LEVEL_1*/printf("[/*PORT_NAME*/] Start running WS Server\n");
    // create libwebsocket /*PORT_NAME*/_context representing this server
#ifdef LWS_LIBRARY_VERSION_NUMBER
    /*PORT_NAME*/_context = lws_create_context(&/*PORT_NAME*/_info);
#else
    /*PORT_NAME*/_context = libwebsocket_create_context(&/*PORT_NAME*/_info);
#endif
   
    if (/*PORT_NAME*/_context == NULL) {
        /*TRACE_LEVEL_1*/fprintf(stderr, "[/*PORT_NAME*/] libwebsocket init failed\n");
        return -1;
    }
	
    /*TRACE_LEVEL_1*/printf("[/*PORT_NAME*/] Starting server...\n");

    /*LISTENER_READY*/
	
    // infinite loop, to end this server send SIGTERM. (CTRL+C)
    while (1) {
#ifdef LWS_LIBRARY_VERSION_NUMBER
        lws_service(/*PORT_NAME*/_context, 50);
#else
        libwebsocket_service(/*PORT_NAME*/_context, 50);
#endif
    }
	
    libwebsocket_context_destroy(/*PORT_NAME*/_context);
}

void /*PORT_NAME*/_forwardMessage(char * msg, int length/*PARAM_CLIENT_ID*/) {
	
	unsigned char buf[LWS_SEND_BUFFER_PRE_PADDING + length + 1 +
						  LWS_SEND_BUFFER_POST_PADDING];
	unsigned char *p = &buf[LWS_SEND_BUFFER_PRE_PADDING];	
	unsigned char *q = p;
	
        
        int i, m;
        for(i = 0; i < length; i++) {
                *q = msg[i];
                q++;
        }
        //*q = '\0';

	/*TRACE_LEVEL_3*/printf("[/*PORT_NAME*/] Trying to send:\n%s \n", p);

        /*SENDING_BROADCAST_OR_NOT*/
}

