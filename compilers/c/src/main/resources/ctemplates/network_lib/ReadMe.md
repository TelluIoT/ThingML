#Network libs

##Trace
4 different trace levels are proposed:
* 0: No traces at all
* 1: Initializations and errors are noticed
* 2: Messages are notified
* 3: Detailed byte per byte notifying

##Annotation
* @port_name

###Posix

####Serial
* @serial_start_byte
* @serial_stop_byte
* @serial_escape_byte
* @serial_msg_buffer_size

###Arduino

####Serial
* @serial_start_byte
* @serial_stop_byte
* @serial_escape_byte
* @serial_limit_byte_per_loop
* @serial_msg_buffer_size
