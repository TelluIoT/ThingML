#include "Timer.h"
#include <time.h>
#include <unistd.h>
#include <pthread.h>

uint32_t Timer_timeouts[NB_SOFT_TIMERS];
uint8_t  Timer_wraps[NB_SOFT_TIMERS];
uint32_t Timer_prev;

pthread_mutex_t Timer_mut;

uint32_t Timer_millis()
{
  struct timespec current;
  int res;
  uint32_t millis;

  res = clock_gettime(CLOCK_REALTIME, &current);
  if (res != 0) {
    perror("[ERROR]: Could not get current time : ");
    return 0;

  } else {
    millis  =  current.tv_sec * 1000;
    millis += current.tv_nsec / 1000000;
    return millis;
  }
}

void Timer_setup(struct Timer_Instance *_instance)
{
    pthread_mutex_init(&Timer_mut, NULL);
    uint8_t i;
    for (i = 0; i < NB_SOFT_TIMERS; i++) {
        Timer_timeouts[i] = 0;
        Timer_wraps[i] = 0;
    }
    Timer_prev = Timer_millis();
}

void Timer_enqueue_timeout(uint8_t id, uint16_t listener_id)
{
    /*ENQUEUERS*/
}

void Timer_loop(struct Timer_Instance *_instance)
{
    while (1) {
        pthread_mutex_lock(&Timer_mut);
        uint32_t current = Timer_millis();
        uint8_t i;
        for (i = 0; i < NB_SOFT_TIMERS; i++) {
            if (Timer_timeouts[i] > 0) {
                if (current >= Timer_prev) {
                    // Normal time progression
                    if (current > Timer_timeouts[i] && Timer_wraps[i] == 0) {
                        Timer_enqueue_timeout(i, _instance->listener_id);
                        Timer_timeouts[i] = 0;
                        Timer_wraps[i] = 0;
                    }
                } else {
                    // A wraparound has occurred
                    if (Timer_wraps[i] == 0) {
                        Timer_enqueue_timeout(i, _instance->listener_id);
                        Timer_timeouts[i] = 0;
                    } else {
                        Timer_wraps[i] = 0;
                        if (current > Timer_timeouts[i]) {
                            Timer_enqueue_timeout(i, _instance->listener_id);
                            Timer_timeouts[i] = 0;
                        }
                    }
                }
            }
        }
        Timer_prev = current;
        pthread_mutex_unlock(&Timer_mut);

        // Wait about a millisecond for the next check
        usleep(1000);
    }
}

void Timer_timer_start(uint8_t id, uint32_t delay)
{
    if (id < NB_SOFT_TIMERS) {
        uint32_t current = Timer_millis();
        uint32_t timeout = current + delay;
        if (timeout == 0) timeout = 1;

        pthread_mutex_lock(&Timer_mut);
        Timer_timeouts[id] = timeout;
        Timer_wraps[id] = (timeout > current) ? 0 : 1;
        pthread_mutex_unlock(&Timer_mut);
    }
}

void Timer_timer_cancel(uint8_t id)
{
    if (id < NB_SOFT_TIMERS) {
        pthread_mutex_lock(&Timer_mut);
        Timer_timeouts[id] = 0;
        Timer_wraps[id] = 0;
        pthread_mutex_unlock(&Timer_mut);
    }
}

// Message forwarders
/*FORWARDERS*/
