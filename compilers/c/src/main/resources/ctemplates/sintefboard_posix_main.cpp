/*****************************************************
 *      THIS IS A GENERATED FILE. DO NOT EDIT.   SINTEFBOARD_POSIX_MAIN.C
 *      Implementation for Application /*NAME*/
 *  Generated from ThingML (http://www.thingml.org)
 *****************************************************/



#ifdef  __cplusplus
extern "C" {
#endif

#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <ctype.h>
#include <string.h>
#include <math.h>
#include <signal.h>
#include <sys/time.h>

#ifdef  __cplusplus
}
#endif

typedef unsigned char uint8_t;
typedef int uint16_t;

#include "/*NAME*/.hpp"
#include "/*NAME*/.cpp"

int main(int argc, char *argv[]) {

	struct timeval tv;
    /*NAME*/ ThingMl;

    ThingMl.setup();
    
    while (1) {
        ThingMl.loop();
        
        // Nothing i message queue, wait 100us before checking
        tv.tv_sec = 0;
        tv.tv_usec = 100;
        select(0, NULL, NULL, NULL, &tv);
        
    }
}

