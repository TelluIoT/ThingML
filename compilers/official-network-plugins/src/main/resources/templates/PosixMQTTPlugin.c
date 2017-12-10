#include "/*PORT_NAME*/.h"

const char */*PORT_NAME*/_topics[/*NUM_TOPICS*/] = {
    /*TOPICS*/
};
int /*PORT_NAME*/_topics_subscribed[/*NUM_TOPICS*/];

const int /*PORT_NAME*/_qos = /*QOS*/;

struct mosquitto */*PORT_NAME*/_mosq = NULL;

// Callback declarations
void /*PORT_NAME*/_log_callback(struct mosquitto *, void *, int , const char *);
void /*PORT_NAME*/_connect_callback(struct mosquitto *, void *, int);
void /*PORT_NAME*/_subscribe_callback(struct mosquitto *, void *, int , int , const int *);
void /*PORT_NAME*/_message_callback(struct mosquitto *, void *, const struct mosquitto_message *);

// Setup
void /*PORT_NAME*/_setup(struct /*PORT_NAME*/_Instance *_instance)
{
    const char *host = "/*HOST_ADDRESS*/";
    int port = /*HOST_PORT_NUMBER*/;
    const char *client_id = /*CLIENT_ID*/;
    const char *username = NULL;
    const char *password = NULL;
    const char *will_topic = /*WILL_TOPIC*/;
    const char *will_string = /*WILL_STRING*/;

    // Initialise
    /*TRACE_LEVEL_1*/printf("[/*PORT_NAME*/] Initialising MQTT at %s:%i\n", host, port);
    mosquitto_lib_init();
    /*PORT_NAME*/_mosq = mosquitto_new(client_id, true, _instance);
    if (!/*PORT_NAME*/_mosq) {
        perror("[/*PORT_NAME*/] mosquitto_new failed ");
        exit(1); // Exit in case of error
        return;
    }

    // Set username and password
    if (password && !username) fprintf(stderr, "[/*PORT_NAME*/] Warning: Not using password since username is not set.\n");
    if (username && !password) fprintf(stderr, "[/*PORT_NAME*/] Warning: Not using username since password is not set.\n");
    if (username && password && mosquitto_username_pw_set(/*PORT_NAME*/_mosq, username, password)) {
        fprintf(stderr, "[/*PORT_NAME*/] mosquitto_username_pw_set failed\n");
        exit(1); // Exit in case of error
        return;
    }

    // Set callbacks
    mosquitto_log_callback_set(/*PORT_NAME*/_mosq, /*PORT_NAME*/_log_callback);
    mosquitto_connect_callback_set(/*PORT_NAME*/_mosq, /*PORT_NAME*/_connect_callback);
    mosquitto_subscribe_callback_set(/*PORT_NAME*/_mosq, /*PORT_NAME*/_subscribe_callback);
    mosquitto_message_callback_set(/*PORT_NAME*/_mosq, /*PORT_NAME*/_message_callback);

    // Set will
    if (will_string && !will_topic) fprintf(stderr, "[/*PORT_NAME*/] Warning: Not setting will since will_topic is not set.\n");
    if (will_string && will_topic && mosquitto_will_set(/*PORT_NAME*/_mosq, will_topic, strlen(will_string), will_string, /*PORT_NAME*/_qos, false)) {
        fprintf(stderr, "[/*PORT_NAME*/] mosquitto_will_set failed\n");
        exit(1); // Exit in case of error
        return;
    }

    // Connect to broker
    int result = mosquitto_connect(/*PORT_NAME*/_mosq, host, port, 10);
    if (result == MOSQ_ERR_ERRNO) {
        perror("[/*PORT_NAME*/] mosquitto_connect failed ");
        exit(1); // Exit in case of error
    }
    else if (result) {
        fprintf(stderr, "[/*PORT_NAME*/] mosquitto_connect failed : %s\n", mosquitto_strerror(result));
        exit(1); // Exit in case of error    
    }

    if (result) /*PORT_NAME*/_mosq = NULL;
}

// Threaded execution and polling functions
void /*PORT_NAME*/_start_receiver_thread() {
    int result = mosquitto_loop_forever(/*PORT_NAME*/_mosq, -1, 1);

    if (result == MOSQ_ERR_ERRNO) {
        perror("[/*PORT_NAME*/] mosquitto_loop_forever_failed ");
    }
    else if (result) {
        fprintf(stderr, "[/*PORT_NAME*/] mosquitto_connect failed : %s\n", mosquitto_strerror(result));
    }

	mosquitto_destroy(/*PORT_NAME*/_mosq);
	/*PORT_NAME*/_mosq = NULL;
	mosquitto_lib_cleanup();
    exit(1); // Exit in case of error
}

void /*PORT_NAME*/_loop_poll() {
	if (/*PORT_NAME*/_mosq) {
        int result = mosquitto_loop(/*PORT_NAME*/_mosq, 0, 1);

        if (result == MOSQ_ERR_ERRNO) {
            perror("[/*PORT_NAME*/] mosquitto_loop_forever_failed ");
        }
        else if (result) {
            fprintf(stderr, "[/*PORT_NAME*/] mosquitto_connect failed : %s\n", mosquitto_strerror(result));
        }

        if (result) {
            mosquitto_destroy(/*PORT_NAME*/_mosq);
            /*PORT_NAME*/_mosq = NULL;
            mosquitto_lib_cleanup();
            exit(1); // Exit in case of error
        }
    }
}

// Logging
void /*PORT_NAME*/_log_callback(struct mosquitto *mosq, void *_instance, int level, const char *str)
{
    /*TRACE_LEVEL_3*/printf("[/*PORT_NAME*/] %s\n", str);
}

// On connected
void /*PORT_NAME*/_connect_callback(struct mosquitto *mosq, void *_instance, int result)
{
    int ret, i;
    switch (result) {
        case 0:
            for (i = 0; i < /*NUM_TOPICS*/; i++) {
                ret = mosquitto_subscribe(mosq, &/*PORT_NAME*/_topics_subscribed[i], /*PORT_NAME*/_topics[i], /*PORT_NAME*/_qos);
                if (ret) {
                    fprintf(stderr, "[MQTT] mosquitto_subscribe failed for %s : %s\n", /*PORT_NAME*/_topics[i], mosquitto_strerror(result));
                    exit(1); // Exit in case of error
                }
            }
            break;
        case 1:
            /*TRACE_LEVEL_1*/fprintf(stderr, "[/*PORT_NAME*/] Connection error : unacceptable protocol version\n");
            exit(1); // Exit in case of error
            break;
        case 2:
            /*TRACE_LEVEL_1*/fprintf(stderr, "[/*PORT_NAME*/] Connection error : identifier rejected\n");
            exit(1); // Exit in case of error
            break;
        case 3:
            /*TRACE_LEVEL_1*/fprintf(stderr, "[/*PORT_NAME*/] Connection error : broker unavailable\n");
            exit(1); // Exit in case of error
            break;
        default:
            /*TRACE_LEVEL_1*/fprintf(stderr, "[/*PORT_NAME*/] Connection error : unknown reason\n");
            exit(1); // Exit in case of error
            break;
    }
}

// On subscribed
void /*PORT_NAME*/_subscribe_callback(struct mosquitto *mosq, void *_instance, int mid, int qos_count, const int *granted_qos)
{
    int print = false, i;
    /*TRACE_LEVEL_2*/print = true;
    if (print) {
        // Find subscribed topic
        for (i = 0; i < /*NUM_TOPICS*/; i++) {
            if (/*PORT_NAME*/_topics_subscribed[i] == mid) break;
        }
        printf("[/*PORT_NAME*/] Subscribed to topic '%s' - QoS levels ", /*PORT_NAME*/_topics[i]);
        for (i = 0; i < qos_count; i++) printf("%i ",granted_qos[i]);
        printf("\n");
    }
    for (i = 0; i < qos_count; i++)
        if (granted_qos[i] == /*PORT_NAME*/_qos) return;

    for (i = 0; i < /*NUM_TOPICS*/; i++) {
        if (/*PORT_NAME*/_topics_subscribed[i] == mid) break;
    }
    /*TRACE_LEVEL_1*/fprintf(stderr, "[/*PORT_NAME*/] Topic '%s' was not granted the specified QoS level\n", /*PORT_NAME*/_topics[i]);
}

/* ---------- INCOMMING MESSAGES ----------*/
/*PARSER_IMPLEMENTATION*/

void /*PORT_NAME*/_message_callback(struct mosquitto *mosq, void *_instance, const struct mosquitto_message *msg)
{
    /*TRACE_LEVEL_2*/printf("[/*PORT_NAME*/] Received message (%i bytes) on topic %s\n", msg->payloadlen, msg->topic);
    // Find the topic index of the message
    int i;
    for (i = 0; i < /*NUM_TOPICS*/; i++)
        if (strcmp(msg->topic, /*PORT_NAME*/_topics[i]) == 0) break;

    // Only parse and enqueue the message if we are listening for it on this topic
    if (i < /*NUM_TOPICS*/ && (/*TOPIC_INDEX_CHECK*/)) {
        /*PORT_NAME*/_parser(msg->payload, msg->payloadlen, (struct /*PORT_NAME*/_Instance*)_instance);
    }
}


/* ---------- FORWARDERS ----------*/
void /*PORT_NAME*/_send_message(uint8_t *msg, int msglen, int topic)
{
    int ret;
    if (topic < /*NUM_TOPICS*/) {
        /*TRACE_LEVEL_2*/printf("[/*PORT_NAME*/] Sending message (%i bytes) on topic %s\n", msglen, /*PORT_NAME*/_topics[topic]);
        ret = mosquitto_publish(/*PORT_NAME*/_mosq, NULL, /*PORT_NAME*/_topics[topic], msglen, msg, /*PORT_NAME*/_qos, false);
        if (ret) {
            fprintf(stderr, "[MQTT] mosquitto_publish failed for %s : %s\n", /*PORT_NAME*/_topics[topic], mosquitto_strerror(ret));
            exit(1); // Exit in case of error
        }
    }
}

/*FORWARDERS*/
