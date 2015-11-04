#include <stdio.h>
#include <stdlib.h>
#include <nopoll/nopoll.h>


struct /*PORT_NAME*/_instance_type {
    uint16_t listener_id;
    /*INSTANCE_INFORMATION*/
};

extern struct /*PORT_NAME*/_instance_type /*PORT_NAME*/_instance;

bool /*PORT_NAME*/_is_open;

noPollCtx * /*PORT_NAME*/_ctx;
noPollConn * /*PORT_NAME*/_conn;

void /*PORT_NAME*/_set_listener_id(uint16_t id) {
	/*PORT_NAME*/_instance.listener_id = id;
}

void /*PORT_NAME*/_listener_on_message (noPollCtx * ctx, noPollConn * conn, noPollMsg * msg, noPollPtr * user_data) {
        // print the message (for debugging purposes) and reply
        int len = nopoll_msg_get_payload_size(msg);
        // reply to the message
        char * in = nopoll_msg_get_payload(msg);
  
        printf("[/*PORT_NAME*/] l:%i\n", len);
        if (((len % 3) == 1) && (len > 1)) {
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
                printf("[Websocket] Message received\n");

            } else {
                printf("[/*PORT_NAME*/] incorrect message '%s'\n", (char *) in);
            }
        } else {
            printf("[/*PORT_NAME*/] incorrect message '%s'\n", (char *) in);
        }

        return;
}

void /*PORT_NAME*/_setup() {
	/*PORT_NAME*/_is_open = false;

     	/*PORT_NAME*/_ctx = nopoll_ctx_new ();

	if (! /*PORT_NAME*/_ctx) {
		printf("[/*PORT_NAME*/] Context error\n");
	}


	// call to create a connection 
	/*PORT_NAME*/_conn = nopoll_conn_new (/*PORT_NAME*/_ctx, "/*ADDRESS*/", "/*PORT_NUMBER*/", NULL, NULL, "ThingML-protocol", "");
	if (! nopoll_conn_is_ok (/*PORT_NAME*/_conn)) {
		printf("[/*PORT_NAME*/] Connexion error\n");
	}
	
	if (! nopoll_conn_wait_until_connection_ready (/*PORT_NAME*/_conn, 5)) {
    	printf("[/*PORT_NAME*/] Connexion error 2\n");
	} else {
            /*CONNEXION_ESTABLISHED*/
            /*PORT_NAME*/_is_open = true;
	}

	nopoll_ctx_set_on_msg (/*PORT_NAME*/_ctx, /*PORT_NAME*/_listener_on_message, NULL);

    /*TRACE_LEVEL_1*/printf("[/*PORT_NAME*/] Init WS Client at %s:%i\n", "/*ADDRESS*/", /*PORT_NUMBER*/);
}

void /*PORT_NAME*/_start_receiver_process() {
	nopoll_loop_wait(/*PORT_NAME*/_ctx, 0);
    
}

void /*PORT_NAME*/_forwardMessage(char * msg, int length/*PARAM_CLIENT_ID*/) {
	if(/*PORT_NAME*/_is_open) {
		int n, m, i;
		unsigned char buf[(length * 3 + 1)];
		unsigned char *p = &buf[0];	
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
		nopoll_conn_send_text (/*PORT_NAME*/_conn, p, (length * 3 + 1));
	} else {
		/*TRACE_LEVEL_3*/printf("[/*PORT_NAME*/] Error: Attempting to write on a closed socket\n");
	}
}


