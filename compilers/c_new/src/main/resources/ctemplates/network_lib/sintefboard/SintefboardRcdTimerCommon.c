// SintefboardRcdTimerCommon.c *** Connect RCD timer to many Things .... sdalgard
//
// Common methods for all RcdTimer instances

void /*CFG_CPPNAME_SCOPE*/rcd_timer_setup(void){
    unsigned int i;

    for ( i = 0; i < /*RCD_TIMER_INSTANCES*/; i++) {
        rcd_timer_tick_arr[i] = HOST_TICK_NONE;
    }
    rcd_timer_next_tick = HOST_TICK_NONE;
}

void /*CFG_CPPNAME_SCOPE*/rcd_timer_start(unsigned int id, unsigned int delay_ms){
    uint32_t delay_tick = HOST_MS2TICK(delay_ms);
    if (delay_tick == 0)
        delay_tick = 1;
    if (id < /*RCD_TIMER_INSTANCES*/) {
        uint32_t wakeup_tick = HOST_TICK_NOW() + delay_tick;
        rcd_timer_tick_arr[id] = wakeup_tick;

        if (rcd_timer_next_tick == HOST_TICK_NONE) {
            rcd_timer_next_tick = wakeup_tick;
        } else {
            if (rcd_timer_next_tick > wakeup_tick)
                rcd_timer_next_tick = wakeup_tick;
        }
    }
}

void /*CFG_CPPNAME_SCOPE*/rcd_timer_cancel(unsigned int id){
    unsigned int i;
    uint32_t wakeup_tick;
    if (id < /*RCD_TIMER_INSTANCES*/) {
        wakeup_tick = rcd_timer_tick_arr[id];
        rcd_timer_tick_arr[id] = HOST_TICK_NONE;
        if (rcd_timer_next_tick == wakeup_tick) {
            rcd_timer_next_tick = HOST_TICK_NONE;
            for ( i = 0; i < /*RCD_TIMER_INSTANCES*/; i++) {
                if (rcd_timer_next_tick == HOST_TICK_NONE) {
                    rcd_timer_next_tick = rcd_timer_tick_arr[i];
                } else {
                    if (rcd_timer_next_tick > rcd_timer_tick_arr[i])
                        rcd_timer_next_tick = rcd_timer_tick_arr[i];
                }
            }
        }
    }
}

void /*CFG_CPPNAME_SCOPE*/rcd_timer_check(void){
    unsigned int i;
    uint32_t tick_now = HOST_TICK_NOW();
    if (rcd_timer_next_tick == HOST_TICK_NONE)
        return;
    if (rcd_timer_next_tick > tick_now)
        return;

    rcd_timer_next_tick = HOST_TICK_NONE;
    for ( i = 0; i < /*RCD_TIMER_INSTANCES*/; i++) {
        if (rcd_timer_tick_arr[i] <= tick_now) {
            rcd_timer_tick_arr[i] = HOST_TICK_NONE;
            rcd_send_timeout(i);
        }

        if (rcd_timer_next_tick == HOST_TICK_NONE) {
            rcd_timer_next_tick = rcd_timer_tick_arr[i];
        } else {
            if (rcd_timer_next_tick > rcd_timer_tick_arr[i])
                rcd_timer_next_tick = rcd_timer_tick_arr[i];
        }
    }
}

uint32_t /*CFG_CPPNAME_SCOPE*/rcd_timer_next(void){
    return rcd_timer_next_tick;
}
