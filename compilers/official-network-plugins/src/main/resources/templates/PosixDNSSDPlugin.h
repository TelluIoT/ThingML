/*
 * dnssd-avahi.h
 *
 *  Created on: Okt 5, 2016
 *      Author: vassik
 */

#ifndef SRC_/*PROTOCOL_NAME*/_DNSSD_AVAHI_H_
#define SRC_/*PROTOCOL_NAME*/_DNSSD_AVAHI_H_


#ifdef __cplusplus
extern "C" {
#endif

#include <avahi-common/thread-watch.h>
#include <avahi-client/publish.h>

typedef void (*p/*PROTOCOL_NAME*/_DNSSDAvahiCallback)(void* _instance, ...);

typedef enum {
	DNSSD_AVAHI_SERVICE_UNPUBLISH,
	DNSSD_AVAHI_SERVICE_PUBLISH,
	DNSSD_AVAHI_SERVICE_NOT_INIT
} /*PROTOCOL_NAME*/_DNSSDAvahiServiceState;

typedef enum {
	DNSSD_ERROR_UNEXPECTED = 204,
	DNSSD_ERROR_COLLISION = 205,
	DNSSD_SRV_ERROR_UNEXPECTED = 104,
	DNSSD_SRV_ERROR_COLLISION = 105
} /*PROTOCOL_NAME*/_DNSSD_ERROR_CODE;

typedef struct {
	AvahiClient* client;
	AvahiThreadedPoll* threaded_poll;

	p/*PROTOCOL_NAME*/_DNSSDAvahiCallback fn_client_failure_callback;
	p/*PROTOCOL_NAME*/_DNSSDAvahiCallback fn_client_running_callback;

	void* thing_instance;

} /*PROTOCOL_NAME*/_DNSSDThreadedAhvaiClient;

typedef struct {
	char* name;
	const char* type;
	const char* domain;
	const char* host;
	uint16_t port;
	const char* txt;

	p/*PROTOCOL_NAME*/_DNSSDAvahiCallback fn_srv_success_callback;
	p/*PROTOCOL_NAME*/_DNSSDAvahiCallback fn_srv_failure_callback;

	AvahiEntryGroup* group;
	/*PROTOCOL_NAME*/_DNSSDAvahiServiceState state;
	/*PROTOCOL_NAME*/_DNSSDThreadedAhvaiClient* avahi_client;

} /*PROTOCOL_NAME*/_DNSSDAvahiService;

struct DNSSD_instance_type {
    uint16_t listener_id;
    /*INSTANCE_INFORMATION*/
    /*PROTOCOL_NAME*/_DNSSDAvahiService* service_data;
};

/*EXTERN_INSTANCE_DECLARATIONS*/

void /*PROTOCOL_NAME*/_start_avahi_client(/*PROTOCOL_NAME*/_DNSSDThreadedAhvaiClient* client_data);

void /*PROTOCOL_NAME*/_stop_avahi_client(/*PROTOCOL_NAME*/_DNSSDThreadedAhvaiClient* client_data);

void /*PROTOCOL_NAME*/_add_dnssd_service(/*PROTOCOL_NAME*/_DNSSDAvahiService* service);

void /*PROTOCOL_NAME*/_remove_dnssd_service(/*PROTOCOL_NAME*/_DNSSDAvahiService* service);

/*PROTOCOL_NAME*/_DNSSDAvahiService* /*PROTOCOL_NAME*/_constructDNSSDAvahiService();

void /*PROTOCOL_NAME*/_distructThingMLAvahiService(/*PROTOCOL_NAME*/_DNSSDAvahiService** service_data);

/*PROTOCOL_NAME*/_DNSSDThreadedAhvaiClient* /*PROTOCOL_NAME*/_constructDNSSDThreadedAhvaiClient();

void /*PROTOCOL_NAME*/_distructThingMLThreadedAhvaiClient(/*PROTOCOL_NAME*/_DNSSDThreadedAhvaiClient** client_data);


void /*PROTOCOL_NAME*/_setup();

void /*PROTOCOL_NAME*/_start_publish_process();

//in fact this should not be here, things which want to send to an external thing a message should go through the process queue
/*INCLUDES_SENDERS_H*/
/*FORWARDS_FUNCTION_PROTOTYPES*/

#ifdef __cplusplus
}
#endif

#endif /* SRC_/*PROTOCOL_NAME*/_DNSSD_AVAHI_H_ */
