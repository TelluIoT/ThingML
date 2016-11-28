/*
 *
 *  Created on: Okt 5, 2016
 *      Author: vassik
 */

#ifndef SRC_/*PROTOCOL_NAME*/_AVAHI_H_
#define SRC_/*PROTOCOL_NAME*/_AVAHI_H_


#ifdef __cplusplus
extern "C" {
#endif

#include <avahi-common/thread-watch.h>
#include <avahi-client/publish.h>

typedef void (*pAvahiCallback/*PROTOCOL_NAME*/)(void* _instance, ...);

typedef enum {
	/*PROTOCOL_NAME*/_AVAHI_SERVICE_UNPUBLISH,
	/*PROTOCOL_NAME*/_AVAHI_SERVICE_PUBLISH,
	/*PROTOCOL_NAME*/_AVAHI_SERVICE_NOT_INIT
} /*PROTOCOL_NAME*/AvahiServiceState;

typedef enum {
	/*PROTOCOL_NAME*/_ERROR_UNEXPECTED = 204,
	/*PROTOCOL_NAME*/_ERROR_COLLISION = 205,
	/*PROTOCOL_NAME*/_ERROR_SERVER_POLL = 206,
    /*PROTOCOL_NAME*/_ERROR_SERVER_CLIENT = 207,
	/*PROTOCOL_NAME*/_SRV_ERROR_UNEXPECTED = 104,
	/*PROTOCOL_NAME*/_SRV_ERROR_COLLISION = 105,
	/*PROTOCOL_NAME*/_SRV_ERROR_ALREADY_UNPUBLISHED = 106
} /*PROTOCOL_NAME*/_ERROR_CODE;

typedef struct {
	AvahiClient* client;
	AvahiThreadedPoll* threaded_poll;

	pAvahiCallback/*PROTOCOL_NAME*/ fn_client_failure_callback;
	pAvahiCallback/*PROTOCOL_NAME*/ fn_client_running_callback;

	void* thing_instance;

} /*PROTOCOL_NAME*/ThreadedAhvaiClient;

typedef struct {
	char* name;
	const char* type;
	const char* domain;
	const char* host;
	uint16_t port;
	const char* txt;

	pAvahiCallback/*PROTOCOL_NAME*/ fn_srv_publish_success_callback;
	pAvahiCallback/*PROTOCOL_NAME*/ fn_srv_unpublish_success_callback;
	pAvahiCallback/*PROTOCOL_NAME*/ fn_srv_failure_callback;

	AvahiEntryGroup* group;
	/*PROTOCOL_NAME*/AvahiServiceState state;
	/*PROTOCOL_NAME*/ThreadedAhvaiClient* avahi_client;

} /*PROTOCOL_NAME*/AvahiService;


void /*PROTOCOL_NAME*/_start_avahi_client(/*PROTOCOL_NAME*/ThreadedAhvaiClient* client_data);

void /*PROTOCOL_NAME*/_stop_avahi_client(/*PROTOCOL_NAME*/ThreadedAhvaiClient* client_data);

void /*PROTOCOL_NAME*/_add_dnssd_service(/*PROTOCOL_NAME*/AvahiService* service);

void /*PROTOCOL_NAME*/_remove_dnssd_service(/*PROTOCOL_NAME*/AvahiService* service);

/*PROTOCOL_NAME*/AvahiService* /*PROTOCOL_NAME*/_constructDNSSDAvahiService();

void /*PROTOCOL_NAME*/_distructDNSSDAvahiService(/*PROTOCOL_NAME*/AvahiService** service_data);

/*PROTOCOL_NAME*/ThreadedAhvaiClient* /*PROTOCOL_NAME*/_constructDNSSDThreadedAhvaiClient();

void /*PROTOCOL_NAME*/_distructDNSSDThreadedAhvaiClient(/*PROTOCOL_NAME*/ThreadedAhvaiClient** client_data);


#ifdef __cplusplus
}
#endif


#endif /* SRC_DNSSD_AVAHI_H_ */