/*
 * dnssd-avahi.h
 *
 *  Created on: Okt 5, 2016
 *      Author: vassik
 */

#ifndef SRC_DNSSD_AVAHI_H_
#define SRC_DNSSD_AVAHI_H_


#ifdef __cplusplus
extern "C" {
#endif

#include <avahi-common/thread-watch.h>
#include <avahi-client/publish.h>


typedef void (*pDNSSDAvahiCallback)(void* _instance, ...);

typedef enum {
	DNSSD_AVAHI_SERVICE_UNPUBLISH,
	DNSSD_AVAHI_SERVICE_PUBLISH,
	DNSSD_AVAHI_SERVICE_NOT_INIT
} DNSSDAvahiServiceState;

typedef enum {
	DNSSD_ERROR_UNEXPECTED = 404,
	DNSSD_ERROR_COLLISION = 405,
	DNSSD_ERROR_UNCOMMITED = 406
} DNSSD_ERROR_CODE;


typedef struct {
	AvahiClient* client;
	AvahiThreadedPoll* threaded_poll;

	pDNSSDAvahiCallback fn_client_failure_callback;
	pDNSSDAvahiCallback fn_client_running_callback;

	void* thing_instance;

} DNSSDAvahiThreadedAhvaiClient;

typedef struct {
	char* name;
	const char* type;
	const char* domain;
	const char* host;
	uint16_t port;
	const char* txt;

	pDNSSDAvahiCallback fn_srv_success_callback;
	pDNSSDAvahiCallback fn_srv_failure_callback;

	void* thing_instance;

	AvahiEntryGroup* group;
	DNSSDAvahiServiceState state;
	DNSSDAvahiThreadedAhvaiClient* avahi_client;

} DNSSDAvahiAvahiService;

void start_avahi_client(DNSSDAvahiThreadedAhvaiClient* client_data);

void stop_avahi_client(DNSSDAvahiThreadedAhvaiClient* client_data);

void add_dnssd_service(DNSSDAvahiAvahiService* service);

void remove_dnssd_service(DNSSDAvahiAvahiService* service);

DNSSDAvahiAvahiService* constructThingMLAvahiService();

void distructThingMLAvahiService(DNSSDAvahiAvahiService** service_data);

DNSSDAvahiThreadedAhvaiClient* constructThingMLThreadedAhvaiClient();

void distructThingMLThreadedAhvaiClient(DNSSDAvahiThreadedAhvaiClient** client_data);


#ifdef __cplusplus
}
#endif


#endif /* SRC_DNSSD_AVAHI_H_ */
