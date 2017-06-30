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
#include "runtime.h"
/*INCLUDES*/

/*C_HEADERS*/

/*CONFIGURATION*/

/*C_GLOBALS*/

void term(int signum)
{
    /*CLEAN_UP_ON_TERMINATE*/

    fflush(stdout);
    fflush(stderr);
    exit(signum);
}


int main(int argc, char *argv[]) {
    struct sigaction action;
    memset(&action, 0, sizeof(struct sigaction));
    action.sa_handler = term;
    sigaction(SIGINT, &action, NULL);
    sigaction(SIGTERM, &action, NULL);

    init_runtime();
    /*C_MAIN*/
    /*INIT_CODE*/
    while (1) {
        /*POLL_CODE*/
        processMessageQueue();
  }
}