#Network libs

##Trace
4 different trace levels are proposed:
* 0: No traces at all
* 1: Initializations and errors are noticed
* 2: Messages are notified
* 3: Detailed byte per byte notifying

##Annotation 
* @port_name default value: Protocol (Must be unique)

###Posix
* @trace_level default value: 1

####Serial
#####Compulsory
* @serial_path_to_device

#####Optional
* @serial_start_byte default value: 18
* @serial_stop_byte default value: 19
* @serial_escape_byte default value: 125
* @serial_msg_buffer_size default value: 10 * Max message size
* @serial_baudrate default value: 115200
* @serial_ring default value: false
* @serial_ring_max_ttl default value: 1

####Websocket
* @websocket_port_number default value: 9000
* @websocket_nb_client_max default: 16
* @websocket_enable_unicast default: false
* @websocket_receiver_id
* @websocket_new_client
* @websocket_client_disconnected

* @websocket_client default: false
* @websocket_serveur_address default: 127.0.0.0
* @websocket_connector_ready

####MQTT
* @mqtt_broker_address default value: localhost
* @mqtt_port_number default value: 1883
* @mqtt_topic default value: ThingML
* @mqtt_multi_topic_publish_selection deafault value: false
* @mqtt_topic_id

###Arduino

####Serial
* @serial_start_byte default value: 18
* @serial_stop_byte default value: 19
* @serial_escape_byte default value: 125
* @serial_limit_byte_per_loop default value: 2 * Max message size
* @serial_msg_buffer_size default value: 2 * Max message size
* @serial_baudrate default value: 115200
