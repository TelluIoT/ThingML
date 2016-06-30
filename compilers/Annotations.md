#ThingML Annotation
##Common
 * code
 * docker
 * enum_val
 * generate_debugGUI
 * nlg
 * serializerkevoree
 * kevscript
 * kevscript_import

##Compilers
###Arduino
 * arduino_stdout
 * arduino_stdout_baudrate

####CEP
 * Buffer
 * Expose
 * TTL
 * TTL1
 * TTL2
 * UseOnce

###C
 * c_byte_buffer
 * c_compiler
 * c_dyn_connectors
 * c_dyn_connectors_lib
 * c_external_send
 * c_global
 * c_header
 * c_instance_var_name
 * c_prototype
 * c_type
 * platform
 * sync_send

###Java
 * java_interface
 * java_type

##Plugins
###MQTT
###Serial
 * serial_baudrate
 * serial_escape_byte
 * serial_limit_byte_per_loop
 * serial_msg_buffer_size
 * serial_path_to_device
 * serial_start_byte
 * serial_stop_byte

###TTY
 * stdin_escape_byte
 * stdin_msg_buffer_size
 * stdin_start_char
 * stdin_stop_byte

###Timer
 * hardware_timer
 * timeout
 * timer_cancel
 * timer_start
 * xms_tic

###UDP
 * udp_address
 * udp_local_port
 * udp_remote_port

###Websocket
 * websocket_client
 * websocket_client_disconnected
 * websocket_connector_ready
 * websocket_enable_unicast
 * websocket_nb_client_max
 * websocket_new_client
 * websocket_port_number
 * websocket_server_address
 * websocket_server_ready

##Tests
 * test_duration
 * test_timer_instance

#Notes

```
#In order to get the list of used annotation:
cd $THINGML_DIR
find . -name "*.java" | xargs sed -n '/hasAnnotation/p' | sed -n 's/.*hasAnnotation([^,]*, "//p' | sed -n 's/".*//p' | sort | uniq
```

