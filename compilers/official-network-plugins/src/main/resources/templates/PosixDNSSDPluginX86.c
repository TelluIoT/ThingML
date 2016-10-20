/*
 * dnssd-avahi.c
 *
 *  Created on: Okt 5, 2016
 *      Author: vassik
 */

#ifdef HAVE_CONFIG_H
#include <config.h>
#endif

#include <stdio.h>
#include <stdlib.h>
/*DNSSD_SERVICE_FAILURE_CALLBACK_COMMENT*/#include <stdarg.h>
/*DNSSD_SERVICE_FAILURE_CALLBACK_COMMENT*/#include <stdint.h>


#include <avahi-client/client.h>
#include <avahi-client/publish.h>

#include <avahi-common/alternative.h>
#include <avahi-common/malloc.h>
#include <avahi-common/error.h>
#include <avahi-common/timeval.h>
#include <avahi-common/thread-watch.h>


#include "/*PATH_TO_H*/"


/*INSTANCE_DECLARATIONS*/

void /*PROTOCOL_NAME*/_entry_group_callback(AvahiEntryGroup *g, AvahiEntryGroupState state, AVAHI_GCC_UNUSED void *userdata) {
	assert(userdata);

	/*PROTOCOL_NAME*/_DNSSDAvahiService* context = (/*PROTOCOL_NAME*/_DNSSDAvahiService*) userdata;

    assert(g == context->group || context->group == NULL);
    context->group = g;

    /* Called whenever the entry group state changes */

    switch (state) {
        case AVAHI_ENTRY_GROUP_ESTABLISHED : {
            /* The entry group has been established successfully */
        	/*TRACE_LEVEL_2*/fprintf(stdout, "Service '%s' successfully established.\n", context->name);
            context->state = DNSSD_AVAHI_SERVICE_PUBLISH;

            if(context->fn_srv_success_callback != NULL)
            	context->fn_srv_success_callback(context->avahi_client->thing_instance);

        }; break;

        case AVAHI_ENTRY_GROUP_COLLISION : {
            /* A service name collision with a remote service
             * happened. */
        	/*TRACE_LEVEL_1*/fprintf(stderr, "Service name collision, renaming service to '%s'\n", context->name);
            if(context->fn_srv_failure_callback != NULL)
            	context->fn_srv_failure_callback(context->avahi_client->thing_instance, DNSSD_ERROR_COLLISION);

        }; break;

        case AVAHI_ENTRY_GROUP_FAILURE : {

        	/*TRACE_LEVEL_1*/fprintf(stderr, "Entry group failure: %s\n", avahi_strerror(avahi_client_errno(avahi_entry_group_get_client(g))));

            if(context->fn_srv_failure_callback != NULL)
            	context->fn_srv_failure_callback(context->avahi_client->thing_instance, DNSSD_ERROR_UNEXPECTED);

        }; break;

        case AVAHI_ENTRY_GROUP_UNCOMMITED:
        	;break;

        case AVAHI_ENTRY_GROUP_REGISTERING:
            ;break;
    }
}

void /*PROTOCOL_NAME*/_client_callback(AvahiClient *c, AvahiClientState state, AVAHI_GCC_UNUSED void * userdata) {
    assert(c);
    assert(userdata);

    /*PROTOCOL_NAME*/_DNSSDThreadedAhvaiClient* client_data = (/*PROTOCOL_NAME*/_DNSSDThreadedAhvaiClient*) userdata;

    client_data->client = c;

    /* Called whenever the client or server state changes */

    switch (state) {
        case AVAHI_CLIENT_S_RUNNING: {

            /* The server has startup successfully and registered its host
             * name on the network, so it's time to create our services */
        	/*TRACE_LEVEL_2*/fprintf(stdout, "Client is running, ready to publish services\n");
        	if(client_data->fn_client_running_callback)
        		client_data->fn_client_running_callback(client_data->thing_instance);

        }; break;

        case AVAHI_CLIENT_FAILURE: {

        	/*TRACE_LEVEL_1*/fprintf(stderr, "Client failure: %s\n", avahi_strerror(avahi_client_errno(c)));

            if(client_data->fn_client_failure_callback)
            	client_data->fn_client_failure_callback(client_data->thing_instance, DNSSD_SRV_ERROR_UNEXPECTED);

        }; break;

        case AVAHI_CLIENT_S_COLLISION: {

            /* Let's drop our registered services. When the server is back
             * in AVAHI_SERVER_RUNNING state we will register them
             * again with the new host name. */

        	/*TRACE_LEVEL_1*/fprintf(stderr, "Client failure due to name collision: %s\n", avahi_strerror(avahi_client_errno(c)));

        	if(client_data->fn_client_failure_callback)
        		client_data->fn_client_failure_callback(client_data->thing_instance, DNSSD_SRV_ERROR_COLLISION);

        }; break;

        case AVAHI_CLIENT_S_REGISTERING:

            /* The server records are now being established. This
             * might be caused by a host name change. We need to wait
             * for our own records to register until the host name is
             * properly esatblished. */
            break;

        case AVAHI_CLIENT_CONNECTING:
            ;
    }
}

void /*PROTOCOL_NAME*/_start_avahi_client(/*PROTOCOL_NAME*/_DNSSDThreadedAhvaiClient* client_data) {
	if(client_data == NULL)
		return;

	int error;

    /* Allocate main loop object */
    if (!(client_data->threaded_poll = avahi_threaded_poll_new())) {
    	/*TRACE_LEVEL_1*/fprintf(stderr, "Failed to create simple poll object.\n");
        return;
    }

    const AvahiPoll* avahi_poll = avahi_threaded_poll_get(client_data->threaded_poll);

    client_data->client = avahi_client_new(avahi_poll, 0, /*PROTOCOL_NAME*/_client_callback, client_data, &error);

    /* Check weather creating the client object succeeded */
    if (!client_data->client) {
    	/*TRACE_LEVEL_1*/fprintf(stderr, "Failed to create client: %s\n", avahi_strerror(error));
        return;
    }

    avahi_threaded_poll_start(client_data->threaded_poll);
}

void /*PROTOCOL_NAME*/_stop_avahi_client(/*PROTOCOL_NAME*/_DNSSDThreadedAhvaiClient* client_data) {
	if(client_data == NULL)
		return;

	if(client_data->threaded_poll)
		avahi_threaded_poll_stop(client_data->threaded_poll);

    if (client_data->client)
        avahi_client_free(client_data->client);

    if (client_data->threaded_poll)
        avahi_threaded_poll_free(client_data->threaded_poll);
}

void /*PROTOCOL_NAME*/_add_dnssd_service(/*PROTOCOL_NAME*/_DNSSDAvahiService *service) {
	char r[128];
    int ret;

    if(service->state == DNSSD_AVAHI_SERVICE_PUBLISH) {
    	/*TRACE_LEVEL_1*/fprintf(stderr, "add_dnssd_service() service is already published\n");
    	return;
    }

    char* name = avahi_strdup(service->name);

    avahi_threaded_poll_lock(service->avahi_client->threaded_poll);

    int state = avahi_client_get_state(service->avahi_client->client);

    if(state != AVAHI_CLIENT_S_RUNNING) {
    	/*TRACE_LEVEL_1*/fprintf(stderr, "add_dnssd_service() failed due to client is in wrong state: %s\n", avahi_strerror(avahi_client_errno(service->avahi_client->client)));
    	avahi_threaded_poll_unlock(service->avahi_client->threaded_poll);
    	avahi_free(name);
    	return;
    }

    service->name = name;

    if (!service->group) {
        if (!(service->group = avahi_entry_group_new(service->avahi_client->client, /*PROTOCOL_NAME*/_entry_group_callback, service))) {
        	/*TRACE_LEVEL_1*/fprintf(stderr, "avahi_entry_group_new() failed: %s\n", avahi_strerror(avahi_client_errno(service->avahi_client->client)));
        }
    }

    avahi_threaded_poll_unlock(service->avahi_client->threaded_poll);

    /* Add the service */
    if ((ret = avahi_entry_group_add_service(service->group, AVAHI_IF_UNSPEC, AVAHI_PROTO_UNSPEC, 0, service->name, service->type, service->domain, service->host, service->port, service->txt, r, NULL)) < 0) {

        if (ret == AVAHI_ERR_COLLISION) {
        	/*TRACE_LEVEL_1*/fprintf(stderr, "Failed to add  service AVAHI_ERR_COLLISION: %s\n", avahi_strerror(ret));
        }

        /*TRACE_LEVEL_1*/fprintf(stderr, "Failed to add  service: %s\n", avahi_strerror(ret));
    }

    /* Tell the server to register the service */
    if ((ret = avahi_entry_group_commit(service->group)) < 0) {
    	/*TRACE_LEVEL_1*/fprintf(stderr, "Failed to commit entry group: %s\n", avahi_strerror(ret));
    }
}

void /*PROTOCOL_NAME*/_remove_dnssd_service(/*PROTOCOL_NAME*/_DNSSDAvahiService *service) {
	if(service == NULL)
		return;

	if(service->state == DNSSD_AVAHI_SERVICE_PUBLISH) {
		avahi_entry_group_reset(service->group);
		avahi_free(service->name);
		service->state = DNSSD_AVAHI_SERVICE_UNPUBLISH;
	}
}

/*PROTOCOL_NAME*/_DNSSDAvahiService* /*PROTOCOL_NAME*/_constructDNSSDAvahiService() {
	/*PROTOCOL_NAME*/_DNSSDAvahiService* service = malloc(sizeof(/*PROTOCOL_NAME*/_DNSSDAvahiService));

	service->domain = NULL;
	service->fn_srv_success_callback = NULL;
	service->fn_srv_failure_callback = NULL;
	service->group = NULL;
	service->host = NULL;
	service->name = NULL;
	service->port = 0;
	service->state = DNSSD_AVAHI_SERVICE_NOT_INIT;
	service->txt = NULL;
	service->type = NULL;
	service->avahi_client = NULL;
	return service;
}

void /*PROTOCOL_NAME*/_distructThingMLAvahiService(/*PROTOCOL_NAME*/_DNSSDAvahiService** service_data) {
	if(*service_data == NULL)
		return;

	free(*service_data);
	*service_data = NULL;
}

/*PROTOCOL_NAME*/_DNSSDThreadedAhvaiClient* /*PROTOCOL_NAME*/_constructDNSSDThreadedAhvaiClient() {
	/*PROTOCOL_NAME*/_DNSSDThreadedAhvaiClient* client = malloc(sizeof(/*PROTOCOL_NAME*/_DNSSDThreadedAhvaiClient));
	client->client = NULL;
	client->threaded_poll = NULL;
	client->thing_instance = NULL;
	client->fn_client_failure_callback = NULL;
	client->fn_client_running_callback = NULL;
	return client;
}

void /*PROTOCOL_NAME*/_distructThingMLThreadedAhvaiClient(/*PROTOCOL_NAME*/_DNSSDThreadedAhvaiClient** client_data) {
	if(*client_data == NULL)
		return;

	free(*client_data);
	*client_data = NULL;
}

void /*PROTOCOL_NAME*/_fn_srv_publish_success_callback(void * _instance, ...) {
	/*TRACE_LEVEL_2*/fprintf(stdout, "fn_srv_publish_success_callback is /*DNSSD_SERVICE_SUCCESS_CALLBACK_NOT*/ handled\n");
	/*DNSSD_SERVICE_SUCCESS_CALLBACK_COMMENT*/struct /*PROTOCOL_NAME*/_instance_type* dnssd_instance = (struct /*PROTOCOL_NAME*/_instance_type*) _instance;

	/*DNSSD_SERVICE_SUCCESS_CALLBACK_COMMENT*/int msg_buf_size = 2;
	/*DNSSD_SERVICE_SUCCESS_CALLBACK_COMMENT*/uint8_t true_buf[msg_buf_size];
	/*DNSSD_SERVICE_SUCCESS_CALLBACK_COMMENT*/true_buf[0] = (/*DNSSD_SERVICE_SUCCESS_MESSAGE_ID*/ >> 8);
	/*DNSSD_SERVICE_SUCCESS_CALLBACK_COMMENT*/true_buf[1] = (/*DNSSD_SERVICE_SUCCESS_MESSAGE_ID*/ & 0xFF);
	/*DNSSD_SERVICE_SUCCESS_CALLBACK_COMMENT*/externalMessageEnqueue(true_buf, msg_buf_size, dnssd_instance->listener_id);
}

void /*PROTOCOL_NAME*/_fn_srv_publish_failure_callback(void* _instance, ...) {
	/*TRACE_LEVEL_2*/fprintf(stdout, "fn_srv_publish_failure_callback is /*DNSSD_SERVICE_FAILURE_CALLBACK_NOT*/ handled\n");
	/*DNSSD_SERVICE_FAILURE_CALLBACK_COMMENT*/struct /*PROTOCOL_NAME*/_instance_type* dnssd_instance = (struct /*PROTOCOL_NAME*/_instance_type*) _instance;

	/*DNSSD_SERVICE_FAILURE_CALLBACK_COMMENT*/int error_code;
	/*DNSSD_SERVICE_FAILURE_CALLBACK_COMMENT*/va_list valist;
	/*DNSSD_SERVICE_FAILURE_CALLBACK_COMMENT*/va_start(valist, _instance);
	/*DNSSD_SERVICE_FAILURE_CALLBACK_COMMENT*/error_code = va_arg(valist, int);
	/*DNSSD_SERVICE_FAILURE_CALLBACK_COMMENT*/va_end(valist);

	/*DNSSD_SERVICE_FAILURE_CALLBACK_COMMENT*/int msg_buf_size = 3;
	/*DNSSD_SERVICE_FAILURE_CALLBACK_COMMENT*/uint8_t true_buf[msg_buf_size];
	/*DNSSD_SERVICE_FAILURE_CALLBACK_COMMENT*/true_buf[0] = (/*DNSSD_SERVICE_FAILURE_MESSAGE_ID*/ >> 8);
	/*DNSSD_SERVICE_FAILURE_CALLBACK_COMMENT*/true_buf[1] = (/*DNSSD_SERVICE_FAILURE_MESSAGE_ID*/ & 0xFF);
	/*DNSSD_SERVICE_FAILURE_CALLBACK_COMMENT*/true_buf[2] = error_code;
	/*DNSSD_SERVICE_FAILURE_CALLBACK_COMMENT*/externalMessageEnqueue(true_buf, msg_buf_size, dnssd_instance->listener_id);
}

void /*PROTOCOL_NAME*/_fn_client_running_callback(void * _instance, ...) {
	/*TRACE_LEVEL_2*/fprintf(stdout, "fn_client_running_callback success is called\n");
	struct /*PROTOCOL_NAME*/_instance_type* dnssd_instance = (struct /*PROTOCOL_NAME*/_instance_type*) _instance;
	/*PROTOCOL_NAME*/_add_dnssd_service(dnssd_instance->service_data);
}

void /*PROTOCOL_NAME*/_fn_client_failure_callback(void* _instance, ...) {
	/*TRACE_LEVEL_2*/fprintf(stdout, "fn_client_failure_callback is /*DNSSD_SERVICE_FAILURE_CALLBACK_NOT*/ handled\n");
	/*DNSSD_SERVICE_FAILURE_CALLBACK_COMMENT*/struct /*PROTOCOL_NAME*/_instance_type* dnssd_instance = (struct /*PROTOCOL_NAME*/_instance_type*) _instance;

	/*DNSSD_SERVICE_FAILURE_CALLBACK_COMMENT*/int error_code;
	/*DNSSD_SERVICE_FAILURE_CALLBACK_COMMENT*/va_list valist;
	/*DNSSD_SERVICE_FAILURE_CALLBACK_COMMENT*/va_start(valist, _instance);
	/*DNSSD_SERVICE_FAILURE_CALLBACK_COMMENT*/error_code = va_arg(valist, int);
	/*DNSSD_SERVICE_FAILURE_CALLBACK_COMMENT*/va_end(valist);

	/*DNSSD_SERVICE_FAILURE_CALLBACK_COMMENT*/int msg_buf_size = 3;
	/*DNSSD_SERVICE_FAILURE_CALLBACK_COMMENT*/uint8_t true_buf[msg_buf_size];
	/*DNSSD_SERVICE_FAILURE_CALLBACK_COMMENT*/true_buf[0] = (/*DNSSD_SERVICE_FAILURE_MESSAGE_ID*/ >> 8);
	/*DNSSD_SERVICE_FAILURE_CALLBACK_COMMENT*/true_buf[1] = (/*DNSSD_SERVICE_FAILURE_MESSAGE_ID*/ & 0xFF);
	/*DNSSD_SERVICE_FAILURE_CALLBACK_COMMENT*/true_buf[2] = error_code;
	/*DNSSD_SERVICE_FAILURE_CALLBACK_COMMENT*/externalMessageEnqueue(true_buf, msg_buf_size, dnssd_instance->listener_id);
}

void /*PROTOCOL_NAME*/_setup() {
	/*INSTANCE_INITIALIZATION*/
}

void /*PROTOCOL_NAME*/_start_process() {
	/*INSTANCE_START*/
}

void /*PROTOCOL_NAME*/_stop_process() {
	/*INSTANCE_STOP*/
}

/*FORWARDS_FUNCTION_PROTOTYPES_IMPL*/