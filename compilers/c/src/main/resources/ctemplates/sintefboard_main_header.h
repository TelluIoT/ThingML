/*****************************************************
 *      THIS IS A GENERATED FILE. DO NOT EDIT.  SINTEFBOARD_MAIN_HEADER.H
 *      Implementation for Application /*NAME*/
 *  Generated from ThingML (http://www.thingml.org)
 *****************************************************/

typedef unsigned char byte; 
/*TYPEDEFS*/

/*C_HEADERS*/

/*RUNTIME_CLASS*/

class /*NAME*/ : public ThingMlRuntime_class {
private:
port_class *Ports_ptr;
/*HEADER_CLASS*/

/*HEADER_CONFIGURATION*/

public:
void setup(port_class *ports_ptr);
void loop(void);
#ifdef RCDPORT_IN_USE
void rcd_port_receive_forward(msgc_t *msg_in_ptr, int16_t from_port);
#endif

#ifdef RCDTIMER_IN_USE
uint32_t rcd_timer_next(void);
#endif

};




