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


#include <avahi-client/client.h>
#include <avahi-client/publish.h>

#include <avahi-common/alternative.h>
#include <avahi-common/malloc.h>
#include <avahi-common/error.h>
#include <avahi-common/timeval.h>
#include <avahi-common/thread-watch.h>


#include "/*PATH_TO_DNSSD_H*/"


void entry_group_callback(AvahiEntryGroup *g, AvahiEntryGroupState state, AVAHI_GCC_UNUSED void *userdata) {
	assert(userdata);

	DNSSDAvahiAvahiService* context = (DNSSDAvahiAvahiService*) userdata;

    assert(g == context->group || context->group == NULL);
    context->group = g;

    /* Called whenever the entry group state changes */

    switch (state) {
        case AVAHI_ENTRY_GROUP_ESTABLISHED : {
            /* The entry group has been established successfully */
            fprintf(stderr, "Service '%s' successfully established.\n", context->name);
            context->state = DNSSD_AVAHI_SERVICE_PUBLISH;

            if(context->fn_srv_success_callback != NULL)
            	context->fn_srv_success_callback(context->thing_instance, context);

        }; break;

        case AVAHI_ENTRY_GROUP_COLLISION : {
            /* A service name collision with a remote service
             * happened. */
            fprintf(stderr, "Service name collision, renaming service to '%s'\n", context->name);
            if(context->fn_srv_failure_callback != NULL)
            	context->fn_srv_failure_callback(context->thing_instance, context, DNSSD_ERROR_COLLISION);

        }; break;

        case AVAHI_ENTRY_GROUP_FAILURE : {

            fprintf(stderr, "Entry group failure: %s\n", avahi_strerror(avahi_client_errno(avahi_entry_group_get_client(g))));

            if(context->fn_srv_failure_callback != NULL)
            	context->fn_srv_failure_callback(context->thing_instance, context, DNSSD_ERROR_UNEXPECTED);

        }; break;

        case AVAHI_ENTRY_GROUP_UNCOMMITED: {
        	 fprintf(stderr, "Entry group is not committed: %s\n", avahi_strerror(avahi_client_errno(avahi_entry_group_get_client(g))));

        	 if(context->fn_srv_failure_callback != NULL)
        		 context->fn_srv_failure_callback(context->thing_instance, context, DNSSD_ERROR_UNCOMMITED);

        }; break;

        case AVAHI_ENTRY_GROUP_REGISTERING:
            ;
    }
}

void client_callback(AvahiClient *c, AvahiClientState state, AVAHI_GCC_UNUSED void * userdata) {
    assert(c);
    assert(userdata);

    DNSSDAvahiThreadedAhvaiClient* client_data = (DNSSDAvahiThreadedAhvaiClient*) userdata;

    client_data->client = c;

    /* Called whenever the client or server state changes */

    switch (state) {
        case AVAHI_CLIENT_S_RUNNING: {

            /* The server has startup successfully and registered its host
             * name on the network, so it's time to create our services */
        	if(client_data->fn_client_running_callback)
        		client_data->fn_client_running_callback(client_data->thing_instance, client_data);

        }; break;

        case AVAHI_CLIENT_FAILURE: {

            fprintf(stderr, "Client failure: %s\n", avahi_strerror(avahi_client_errno(c)));

            if(client_data->fn_client_failure_callback)
            	client_data->fn_client_failure_callback(client_data->thing_instance, client_data, DNSSD_SRV_ERROR_UNEXPECTED);

        }; break;

        case AVAHI_CLIENT_S_COLLISION: {

            /* Let's drop our registered services. When the server is back
             * in AVAHI_SERVER_RUNNING state we will register them
             * again with the new host name. */

        	fprintf(stderr, "Client failure due to name collision: %s\n", avahi_strerror(avahi_client_errno(c)));

        	if(client_data->fn_client_failure_callback)
        		client_data->fn_client_failure_callback(client_data->thing_instance, client_data, DNSSD_SRV_ERROR_COLLISION);

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

void start_avahi_client(DNSSDAvahiThreadedAhvaiClient* client_data) {
	if(client_data == NULL)
		return;

	int error;

    /* Allocate main loop object */
    if (!(client_data->threaded_poll = avahi_threaded_poll_new())) {
        fprintf(stderr, "Failed to create simple poll object.\n");
        return;
    }

    const AvahiPoll* avahi_poll = avahi_threaded_poll_get(client_data->threaded_poll);

    client_data->client = avahi_client_new(avahi_poll, 0, client_callback, client_data, &error);

    /* Check weather creating the client object succeeded */
    if (!client_data->client) {
        fprintf(stderr, "Failed to create client: %s\n", avahi_strerror(error));
        return;
    }

    avahi_threaded_poll_start(client_data->threaded_poll);
}

void stop_avahi_client(DNSSDAvahiThreadedAhvaiClient* client_data) {
	if(client_data == NULL)
		return;

	if(client_data->threaded_poll)
		avahi_threaded_poll_stop(client_data->threaded_poll);

    if (client_data->client)
        avahi_client_free(client_data->client);

    if (client_data->threaded_poll)
        avahi_threaded_poll_free(client_data->threaded_poll);
}

void add_dnssd_service(DNSSDAvahiAvahiService *service) {
	char r[128];
    int ret;

    if(service->state == DNSSD_AVAHI_SERVICE_PUBLISH) {
    	fprintf(stderr, "add_dnssd_service() service is already published\n");
    	return;
    }

    char* name = avahi_strdup(service->name);

    avahi_threaded_poll_lock(service->avahi_client->threaded_poll);

    int state = avahi_client_get_state(service->avahi_client->client);

    if(state != AVAHI_CLIENT_S_RUNNING) {
    	fprintf(stderr, "add_dnssd_service() failed due to client is in wrong state: %s\n", avahi_strerror(avahi_client_errno(service->avahi_client->client)));
    	avahi_threaded_poll_unlock(service->avahi_client->threaded_poll);
    	avahi_free(name);
    	return;
    }

    service->name = name;

    if (!service->group) {
        if (!(service->group = avahi_entry_group_new(service->avahi_client->client, entry_group_callback, service)))
            fprintf(stderr, "avahi_entry_group_new() failed: %s\n", avahi_strerror(avahi_client_errno(service->avahi_client->client)));
    }

    avahi_threaded_poll_unlock(service->avahi_client->threaded_poll);

    /* Add the service */
    if ((ret = avahi_entry_group_add_service(service->group, AVAHI_IF_UNSPEC, AVAHI_PROTO_UNSPEC, 0, service->name, service->type, service->domain, service->host, service->port, service->txt, r, NULL)) < 0) {

        if (ret == AVAHI_ERR_COLLISION)
        	fprintf(stderr, "Failed to add  service AVAHI_ERR_COLLISION: %s\n", avahi_strerror(ret));

        fprintf(stderr, "Failed to add  service: %s\n", avahi_strerror(ret));
    }

    /* Tell the server to register the service */
    if ((ret = avahi_entry_group_commit(service->group)) < 0)
        fprintf(stderr, "Failed to commit entry group: %s\n", avahi_strerror(ret));
}

void remove_dnssd_service(DNSSDAvahiAvahiService *service) {
	if(service == NULL)
		return;

	if(service->state == DNSSD_AVAHI_SERVICE_PUBLISH) {
		avahi_entry_group_reset(service->group);
		avahi_free(service->name);
		service->state = DNSSD_AVAHI_SERVICE_UNPUBLISH;
	}
}

DNSSDAvahiAvahiService* constructThingMLAvahiService() {
	DNSSDAvahiAvahiService* service = malloc(sizeof(DNSSDAvahiAvahiService));

	service->domain = NULL;
	service->fn_srv_success_callback = NULL;
	service->fn_srv_failure_callback = NULL;
	service->group = NULL;
	service->host = NULL;
	service->name = NULL;
	service->port = 0;
	service->state = DNSSD_AVAHI_SERVICE_NOT_INIT;
	service->thing_instance = NULL;
	service->txt = NULL;
	service->type = NULL;
	service->avahi_client = NULL;
	return service;
}

void distructThingMLAvahiService(DNSSDAvahiAvahiService** service_data) {
	if(*service_data == NULL)
		return;

	free(*service_data);
	*service_data = NULL;
}

DNSSDAvahiThreadedAhvaiClient* constructThingMLThreadedAhvaiClient() {
	DNSSDAvahiThreadedAhvaiClient* client = malloc(sizeof(DNSSDAvahiThreadedAhvaiClient));
	client->client = NULL;
	client->threaded_poll = NULL;
	client->thing_instance = NULL;
	client->fn_client_failure_callback = NULL;
	client->fn_client_running_callback = NULL;
	return client;
}

void distructThingMLThreadedAhvaiClient(DNSSDAvahiThreadedAhvaiClient** client_data) {
	if(*client_data == NULL)
		return;

	free(*client_data);
	*client_data = NULL;
}
