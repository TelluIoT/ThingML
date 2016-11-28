/*PROTOCOL_INSTANCE_NAME*/_instance.service_data = /*PROTOCOL_NAME*/_constructDNSSDAvahiService();
/*PROTOCOL_INSTANCE_NAME*/_instance.service_data->fn_srv_success_callback = /*PROTOCOL_NAME*/_fn_srv_publish_success_callback;
/*PROTOCOL_INSTANCE_NAME*/_instance.service_data->fn_srv_failure_callback = /*PROTOCOL_NAME*/_fn_srv_publish_failure_callback;

/*PROTOCOL_INSTANCE_NAME*/_instance.service_data->avahi_client = /*PROTOCOL_NAME*/_constructDNSSDThreadedAhvaiClient();
/*PROTOCOL_INSTANCE_NAME*/_instance.service_data->avahi_client->thing_instance = &/*PROTOCOL_INSTANCE_NAME*/_instance;
/*PROTOCOL_INSTANCE_NAME*/_instance.service_data->avahi_client->fn_client_running_callback = /*PROTOCOL_NAME*/_fn_client_running_callback;
/*PROTOCOL_INSTANCE_NAME*/_instance.service_data->avahi_client->fn_client_failure_callback = /*PROTOCOL_NAME*/_fn_client_failure_callback;

/*PROTOCOL_INSTANCE_NAME*/_instance.service_data->name = "/*DNSSD_SERVICE_NAME*/";
/*PROTOCOL_INSTANCE_NAME*/_instance.service_data->type = "/*DNSSD_SERVICE_TYPE*/";
/*PROTOCOL_INSTANCE_NAME*/_instance.service_data->port = /*DNSSD_SERVICE_PORT*/;
/*IS_DNSSD_SERVICE_TXT*/ /*PROTOCOL_INSTANCE_NAME*/_instance.service_data->txt = "/*DNSSD_SERVICE_TXT*/";
/*IS_DNSSD_SERVICE_HOST*/ /*PROTOCOL_INSTANCE_NAME*/_instance.service_data->host = "/*DNSSD_SERVICE_HOST*/";
/*IS_DNSSD_SERVICE_DOMAIN*/ /*PROTOCOL_INSTANCE_NAME*/_instance.service_data->domain = "/*DNSSD_SERVICE_DOMAIN*/";