

#include <errno.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

#include <mosquitto.h>

/*TOPIC_VAR*/
static int /*PORT_NAME*/_topic_qos = 0;
static char */*PORT_NAME*/_username = NULL;
static char */*PORT_NAME*/_password = NULL;
static int /*PORT_NAME*/_qos = 0;
static int /*PORT_NAME*/_retain = 0;
static uint16_t /*PORT_NAME*/_mid_sent = 0;
struct mosquitto */*PORT_NAME*/_mosq = NULL;
static int /*PORT_NAME*/_connected = 0;


struct /*PORT_NAME*/_instance_type {
    uint16_t listener_id;
    /*INSTANCE_INFORMATION*/
};

extern struct /*PORT_NAME*/_instance_type /*PORT_NAME*/_instance;

void /*PORT_NAME*/_set_listener_id(uint16_t id) {
	/*PORT_NAME*/_instance.listener_id = id;
}

/*PARSE_IMPLEMENTATION*/


void /*PORT_NAME*/_publish_callback(void *obj, uint16_t mid)
{
	struct mosquitto *mosq = obj;

	
}

void /*PORT_NAME*/_message_callback(void *obj, const struct mosquitto_message *message)
{
    printf("%s %s\n", message->topic, message->payload);
    int len = strlen(message->payload);
    /*TRACE_LEVEL_2*/printf("[/*PORT_NAME*/] receveid l:%i\n", len);
    /*PORT_NAME*/_parser(message->payload, len, /*PORT_NAME*/_instance.listener_id);
}

void /*PORT_NAME*/_connect_callback(void *obj, int result)
{
    struct mosquitto *mosq = obj;

    int i;
    if(!result){
        /*SUBSCRUBE_MULTI_OR_MONO*/
    }else{
        switch(result){
            case 1:
                /*TRACE_LEVEL_1*/fprintf(stderr, "[/*PORT_NAME*/] Connection Refused: unacceptable protocol version\n");
                break;
            case 2:
                /*TRACE_LEVEL_1*/fprintf(stderr, "[/*PORT_NAME*/] Connection Refused: identifier rejected\n");
                break;
            case 3:
                /*TRACE_LEVEL_1*/fprintf(stderr, "[/*PORT_NAME*/] Connection Refused: broker unavailable\n");
                break;
            case 4:
                /*TRACE_LEVEL_1*/fprintf(stderr, "[/*PORT_NAME*/] Connection Refused: bad user name or password\n");
                break;
            case 5:
                /*TRACE_LEVEL_1*/fprintf(stderr, "[/*PORT_NAME*/] Connection Refused: not authorised\n");
                break;
            default:
                /*TRACE_LEVEL_1*/fprintf(stderr, "[/*PORT_NAME*/] Connection Refused: unknown reason\n");
                break;
        }
    }
}

void /*PORT_NAME*/_subscribe_callback(void *obj, uint16_t mid, int qos_count, const uint8_t *granted_qos)
{
	int i;

	/*TRACE_LEVEL_1*/printf("[/*PORT_NAME*/] Subscribed (mid: %d): %d", mid, granted_qos[0]);
	for(i=1; i<qos_count; i++){
		/*TRACE_LEVEL_1*/printf(", %d", granted_qos[i]);
	}
	/*TRACE_LEVEL_1*/printf("\n");
}

void /*PORT_NAME*/_setup() {
	char *id = NULL;
	char *id_prefix = NULL;
	int i;
	char *host = "/*HOST_ADDRESS*/";
	int port = /*PORT_NUMBER*/;
	int keepalive = 60;
	bool clean_session = true;
	bool debug = false;
	int rc, rc2;
	char hostname[21];
	char err[1024];
	
	uint8_t *will_payload = NULL;
	long will_payloadlen = 0;
	int will_qos = 0;
	bool will_retain = false;
	char *will_topic = NULL;

        /*MULTI_TOPIC_INIT*/

        /*TRACE_LEVEL_1*/printf("[/*PORT_NAME*/] Initialization MQTT at %s:%i\n", host, port);
	
	if(clean_session == false && (id_prefix || !id)){
		/*TRACE_LEVEL_1*/fprintf(stderr, "[/*PORT_NAME*/] Error: You must provide a client id if you are using the -c option.\n");
		return 1;
	}
	if(id_prefix){
		id = malloc(strlen(id_prefix)+10);
		if(!id){
			/*TRACE_LEVEL_1*/fprintf(stderr, "[/*PORT_NAME*/] Error: Out of memory.\n");
			return 1;
		}
		snprintf(id, strlen(id_prefix)+10, "%s%d", id_prefix, getpid());
	}else if(!id){
		id = malloc(30);
		if(!id){
			/*TRACE_LEVEL_1*/fprintf(stderr, "[/*PORT_NAME*/] Error: Out of memory.\n");
			return 1;
		}
		memset(hostname, 0, 21);
		gethostname(hostname, 20);
		snprintf(id, 23, "mosq_sub_%d_%s", getpid(), hostname);
	}

	if(will_payload && !will_topic){
		/*TRACE_LEVEL_1*/fprintf(stderr, "[/*PORT_NAME*/] Error: Will payload given, but no will topic given.\n");
		return 1;
	}
	if(will_retain && !will_topic){
		/*TRACE_LEVEL_1*/fprintf(stderr, "[/*PORT_NAME*/] Error: Will retain given, but no will topic given.\n");
		return 1;
	}
	if(/*PORT_NAME*/_password && !/*PORT_NAME*/_username){
		/*TRACE_LEVEL_1*/fprintf(stderr, "[/*PORT_NAME*/] Warning: Not using password since username not set.\n");
	}
	mosquitto_lib_init();
	/*PORT_NAME*/_mosq = mosquitto_new(id, NULL);
	if(!/*PORT_NAME*/_mosq){
		/*TRACE_LEVEL_1*/fprintf(stderr, "[/*PORT_NAME*/] Error: Out of memory.\n");
		return 1;
	}
	if(will_topic && mosquitto_will_set(/*PORT_NAME*/_mosq, true, will_topic, will_payloadlen, will_payload, will_qos, will_retain)){
		/*TRACE_LEVEL_1*/fprintf(stderr, "[/*PORT_NAME*/] Error: Problem setting will.\n");
		return 1;
	}
	if(/*PORT_NAME*/_username && mosquitto_username_pw_set(/*PORT_NAME*/_mosq, /*PORT_NAME*/_username, /*PORT_NAME*/_password)){
		/*TRACE_LEVEL_1*/fprintf(stderr, "[/*PORT_NAME*/] Error: Problem setting username and password.\n");
		return 1;
	}
	mosquitto_connect_callback_set(/*PORT_NAME*/_mosq, /*PORT_NAME*/_connect_callback);
	mosquitto_message_callback_set(/*PORT_NAME*/_mosq, /*PORT_NAME*/_message_callback);
	
	/*TRACE_LEVEL_1*/mosquitto_subscribe_callback_set(/*PORT_NAME*/_mosq, /*PORT_NAME*/_subscribe_callback);

	rc = mosquitto_connect(/*PORT_NAME*/_mosq, host, port, keepalive, clean_session);
	if(rc){
		/*TRACE_LEVEL_1*/if(rc == MOSQ_ERR_ERRNO){
			/*TRACE_LEVEL_1*/strerror_r(errno, err, 1024);
			/*TRACE_LEVEL_1*/fprintf(stderr, "[/*PORT_NAME*/] Error: %s\n", err);
		/*TRACE_LEVEL_1*/}else{
			/*TRACE_LEVEL_1*/fprintf(stderr, "[/*PORT_NAME*/] Unable to connect (%d).\n", rc);
		/*TRACE_LEVEL_1*/}
		//return rc;
	}

}

void /*PORT_NAME*/_start_receiver_process() {
        int rc;	

	do{
		rc = mosquitto_loop(/*PORT_NAME*/_mosq, -1);
	}while(rc == MOSQ_ERR_SUCCESS);

        /*TRACE_LEVEL_1*/printf("[/*PORT_NAME*/] Error :%i\n", rc);
	mosquitto_destroy(/*PORT_NAME*/_mosq);
	mosquitto_lib_cleanup();

}

void /*PORT_NAME*/_forwardMessage(uint8_t * msg, int size/*PUBLISH_MULTI_OR_MONO_DECLARATION*/) {
    int n, m, i;
    int length = size;
    unsigned char buf[length];
    unsigned char *p = &buf[0];	
    unsigned char *q = p;
    n = 0;
    for(i = 0; i < length; i++) {
        *q = msg[i];
        q++;
        n++;
    }
    
    /*PUBLISH_MULTI_OR_MONO_CORE*/
    
}

