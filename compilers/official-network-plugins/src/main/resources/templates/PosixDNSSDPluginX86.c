/*
 *
 *  Created on: Okt 5, 2016
 *      Author: vassik
 */

#ifdef HAVE_CONFIG_H
#include <config.h>
#endif

#include <stdio.h>
#include <stdlib.h>
#include <stdarg.h>
#include <stdint.h>


#include <avahi-client/client.h>
#include <avahi-client/publish.h>

#include <avahi-common/alternative.h>
#include <avahi-common/malloc.h>
#include <avahi-common/error.h>
#include <avahi-common/timeval.h>
#include <avahi-common/thread-watch.h>


#include "/*PATH_TO_H*/"


void /*PROTOCOL_NAME*/_entry_group_callback(AvahiEntryGroup *g, AvahiEntryGroupState state, AVAHI_GCC_UNUSED void *userdata) {
	assert(userdata);

	/*PROTOCOL_NAME*/AvahiService* context = (/*PROTOCOL_NAME*/AvahiService*) userdata;

    assert(g == context->group || context->group == NULL);
    context->group = g;

    /* Called whenever the entry group state changes */

    switch (state) {
        case AVAHI_ENTRY_GROUP_ESTABLISHED : {
            /* The entry group has been established successfully */
            /*TRACE_LEVEL_2*/fprintf(stdout, "Service '%s' successfully established.\n", context->name);
            context->state = /*PROTOCOL_NAME*/_AVAHI_SERVICE_PUBLISH;

            if(context->fn_srv_publish_success_callback != NULL)
            	context->fn_srv_publish_success_callback(context->avahi_client->thing_instance);

        }; break;

        case AVAHI_ENTRY_GROUP_COLLISION : {
            /* A service name collision with a remote service
             * happened. */
            /*TRACE_LEVEL_1*/fprintf(stderr, "Service name collision, renaming service to '%s'\n", context->name);
            if(context->fn_srv_failure_callback != NULL)
            	context->fn_srv_failure_callback(context->avahi_client->thing_instance, /*PROTOCOL_NAME*/_ERROR_COLLISION);

        }; break;

        case AVAHI_ENTRY_GROUP_FAILURE : {

            /*TRACE_LEVEL_1*/fprintf(stderr, "Entry group failure: %s\n", avahi_strerror(avahi_client_errno(avahi_entry_group_get_client(g))));

            if(context->fn_srv_failure_callback != NULL)
            	context->fn_srv_failure_callback(context->avahi_client->thing_instance, /*PROTOCOL_NAME*/_ERROR_UNEXPECTED);

        }; break;

        case AVAHI_ENTRY_GROUP_UNCOMMITED: {
        	if(context->state == /*PROTOCOL_NAME*/_AVAHI_SERVICE_PUBLISH) {
        	    /*TRACE_LEVEL_2*/fprintf(stdout, "Services successfully unestablished.\n");
        		context->state = /*PROTOCOL_NAME*/_AVAHI_SERVICE_UNPUBLISH;
        		if(context->fn_srv_unpublish_success_callback != NULL)
        			context->fn_srv_unpublish_success_callback(context->avahi_client->thing_instance);
        	}
        }; break;

        case AVAHI_ENTRY_GROUP_REGISTERING:
            ;break;
    }
}

void /*PROTOCOL_NAME*/_client_callback(AvahiClient *c, AvahiClientState state, AVAHI_GCC_UNUSED void * userdata) {
    assert(c);
    assert(userdata);

    /*PROTOCOL_NAME*/ThreadedAhvaiClient* client_data = (/*PROTOCOL_NAME*/ThreadedAhvaiClient*) userdata;

    client_data->client = c;

    /* Called whenever the client or server state changes */

    switch (state) {
        case AVAHI_CLIENT_S_RUNNING: {

            /* The server has startup successfully and registered its host
             * name on the network, so it's time to create our services */
        	if(client_data->fn_client_running_callback)
        		client_data->fn_client_running_callback(client_data->thing_instance);

        }; break;

        case AVAHI_CLIENT_FAILURE: {

            /*TRACE_LEVEL_1*/fprintf(stderr, "Client failure: %s\n", avahi_strerror(avahi_client_errno(c)));

            if(client_data->fn_client_failure_callback)
            	client_data->fn_client_failure_callback(client_data->thing_instance, /*PROTOCOL_NAME*/_SRV_ERROR_UNEXPECTED);

        }; break;

        case AVAHI_CLIENT_S_COLLISION: {

            /* Let's drop our registered services. When the server is back
             * in AVAHI_SERVER_RUNNING state we will register them
             * again with the new host name. */

        	/*TRACE_LEVEL_1*/fprintf(stderr, "Client failure due to name collision: %s\n", avahi_strerror(avahi_client_errno(c)));

        	if(client_data->fn_client_failure_callback)
        		client_data->fn_client_failure_callback(client_data->thing_instance, /*PROTOCOL_NAME*/_SRV_ERROR_COLLISION);

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

void /*PROTOCOL_NAME*/_start_avahi_client(/*PROTOCOL_NAME*/ThreadedAhvaiClient* client_data) {
	if(client_data == NULL)
		return;

	int error;

    /* Allocate main loop object */
    if (!(client_data->threaded_poll = avahi_threaded_poll_new())) {
        /*TRACE_LEVEL_1*/fprintf(stderr, "Failed to create simple poll object.\n");
        if(client_data->fn_client_failure_callback != NULL)
            client_data->fn_client_failure_callback(client_data->thing_instance, /*PROTOCOL_NAME*/_ERROR_SERVER_POLL);
        return;
    }

    const AvahiPoll* avahi_poll = avahi_threaded_poll_get(client_data->threaded_poll);

    client_data->client = avahi_client_new(avahi_poll, 0, /*PROTOCOL_NAME*/_client_callback, client_data, &error);

    /* Check weather creating the client object succeeded */
    if (!client_data->client) {
        /*TRACE_LEVEL_1*/fprintf(stderr, "Failed to create client: %s\n", avahi_strerror(error));
        if(client_data->fn_client_failure_callback != NULL)
            client_data->fn_client_failure_callback(client_data->thing_instance, /*PROTOCOL_NAME*/_ERROR_SERVER_CLIENT);
        return;
    }

    avahi_threaded_poll_start(client_data->threaded_poll);
}

void /*PROTOCOL_NAME*/_stop_avahi_client(/*PROTOCOL_NAME*/ThreadedAhvaiClient* client_data) {
	if(client_data == NULL)
		return;

	if(client_data->threaded_poll)
		avahi_threaded_poll_stop(client_data->threaded_poll);

    if (client_data->client)
        avahi_client_free(client_data->client);

    if (client_data->threaded_poll)
        avahi_threaded_poll_free(client_data->threaded_poll);
}

void /*PROTOCOL_NAME*/_add_dnssd_service(/*PROTOCOL_NAME*/AvahiService *service) {
    int ret;

    if(!service->avahi_client->client) {
    	/*TRACE_LEVEL_1*/fprintf(stderr, "add_dnssd_service() cannot add service, avahi-client is not created\n");
    	return;
    }

    if(service->state == /*PROTOCOL_NAME*/_AVAHI_SERVICE_PUBLISH) {
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
    if ((ret = avahi_entry_group_add_service(service->group, AVAHI_IF_UNSPEC, AVAHI_PROTO_UNSPEC, 0, service->name, service->type, service->domain, service->host, service->port, service->txt, NULL)) < 0) {

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

void /*PROTOCOL_NAME*/_remove_dnssd_service(/*PROTOCOL_NAME*/AvahiService *service) {
	if(service == NULL)
	    return;

	if(!service->avahi_client->client) {
	     /*TRACE_LEVEL_1*/fprintf(stderr, "remove_dnssd_service() nothing to remove, avahi-client is not created\n");
	    return;
	}

	if(service->state == /*PROTOCOL_NAME*/_AVAHI_SERVICE_NOT_INIT) {
    	 /*TRACE_LEVEL_1*/fprintf(stderr, "remove_dnssd_service() not yet initialized to remove a service\n");
    	return;
    }

	if(service->state == /*PROTOCOL_NAME*/_AVAHI_SERVICE_UNPUBLISH) {
		if(service->fn_srv_failure_callback != NULL)
			service->fn_srv_failure_callback(service->avahi_client->thing_instance, /*PROTOCOL_NAME*/_SRV_ERROR_ALREADY_UNPUBLISHED);
		return;
	}

	// resetting the group which should remove the service
	avahi_entry_group_reset(service->group);
	avahi_free(service->name);
}

/*PROTOCOL_NAME*/AvahiService* /*PROTOCOL_NAME*/_constructDNSSDAvahiService() {
	/*PROTOCOL_NAME*/AvahiService* service = malloc(sizeof(/*PROTOCOL_NAME*/AvahiService));

	service->domain = NULL;
	service->fn_srv_publish_success_callback = NULL;
	service->fn_srv_failure_callback = NULL;
	service->fn_srv_unpublish_success_callback = NULL;
	service->group = NULL;
	service->host = NULL;
	service->name = NULL;
	service->port = 0;
	service->state = /*PROTOCOL_NAME*/_AVAHI_SERVICE_NOT_INIT;
	service->txt = NULL;
	service->type = NULL;
	service->avahi_client = NULL;
	return service;
}

void /*PROTOCOL_NAME*/_distructDNSSDAvahiService(/*PROTOCOL_NAME*/AvahiService** service_data) {
	if(*service_data == NULL)
		return;

	free(*service_data);
	*service_data = NULL;
}

/*PROTOCOL_NAME*/ThreadedAhvaiClient* /*PROTOCOL_NAME*/_constructDNSSDThreadedAhvaiClient() {
	/*PROTOCOL_NAME*/ThreadedAhvaiClient* client = malloc(sizeof(/*PROTOCOL_NAME*/ThreadedAhvaiClient));
	client->client = NULL;
	client->threaded_poll = NULL;
	client->thing_instance = NULL;
	client->fn_client_failure_callback = NULL;
	client->fn_client_running_callback = NULL;
	return client;
}

void /*PROTOCOL_NAME*/_distructDNSSDThreadedAhvaiClient(/*PROTOCOL_NAME*/ThreadedAhvaiClient** client_data) {
	if(*client_data == NULL)
		return;

	free(*client_data);
	*client_data = NULL;
}