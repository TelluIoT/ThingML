/*****************************************************
 *      THIS IS A GENERATED FILE. DO NOT EDIT.
 *      Implementation for Application /*NAME*/
 *  Generated from ThingML (http://www.thingml.org)
 *****************************************************/

#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <ctype.h>
#include <string.h>
#include <math.h>
#include <signal.h>
#include <pthread.h>
#include "thingml_typedefs.h"
/*INCLUDES*/

/*CONFIGURATION*/

int main(int argc, char *argv[]) {
  /*INIT_CODE*/

  while (1) {
    /*POLL_CODE*/
    processMessageQueue();
    usleep(10000);
  }
}