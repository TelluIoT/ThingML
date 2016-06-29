// SintefboardRcdTimerInstance.c *** Connect RCD timer to many Things .... sdalgard
//

void /*CFG_CPPNAME_SCOPE*//*PORT_NAME*/_setup(void) {
    rcd_timer_setup();
}

void /*CFG_CPPNAME_SCOPE*//*PORT_NAME*/_set_listener_id(uint16_t id) {
	/*PORT_NAME*/_instance.listener_id = id;
}

