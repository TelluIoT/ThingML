/*****************************************************/
//                    POSIX Timer
/*****************************************************/
#include <stdint.h>

/*INCLUDES*/

#define NB_SOFT_TIMERS /*NB_SOFT_TIMERS*/

struct Timer_Instance {
    uint16_t listener_id;
    /*INSTANCE_INFORMATION*/
};

void Timer_setup(struct Timer_Instance *_instance);
void Timer_loop(struct Timer_Instance *_instance);

void externalMessageEnqueue(uint8_t * msg, uint8_t msgSize, uint16_t listener_id);

/*FORWARDERS*/
