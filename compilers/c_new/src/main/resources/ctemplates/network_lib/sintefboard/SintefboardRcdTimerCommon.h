// SintefboardRcdTimerCommon.h *** Connect RCD timer to many Things .... sdalgard
//
#define RCDTIMER_IN_USE

// Common stuff for all RcdTimer instances
uint32_t rcd_timer_tick_arr[/*RCD_TIMER_INSTANCES*/];
uint32_t rcd_timer_next_tick;

// Place common prototypes for RcdTimer here
void rcd_timer_setup(void);
void rcd_timer_start(unsigned int id, unsigned int delay_ms);
void rcd_timer_cancel(unsigned int id);
void rcd_timer_check(void);
//void rcd_send_timeout(unsigned int id); This is generated in SintefboardRcdTimer.java
//uint32_t rcd_timer_next(void); This is located in sintefboard_main_header.h

