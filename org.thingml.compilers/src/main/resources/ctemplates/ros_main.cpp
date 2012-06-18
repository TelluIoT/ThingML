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
#include "ros/ros.h"
/*INCLUDES*/

/*ROS_HEADERS*/

/*CONFIGURATION*/

/*ROS_HANDLERS*/

void initialize_ROS_connectors() {
/*ROS_CONNECTORS*/
}

int main(int argc, char *argv[]) {
  init_runtime();
  initialize_ROS_connectors();
  /*INIT_CODE*/
  /*ROS_INIT*/

  while (ros::ok()) {
    /*POLL_CODE*/
    processMessageQueue();
    ros::spinOnce();
  }
  exit(0);
}