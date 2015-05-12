/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.sintef.thingml.resource.thingml.debug;

public enum EThingmlDebugMessageTypes {
	
	// An enumeration of all commands that can be sent to the debug server from debug
	// clients (i.e., interpreters or other processes).
	STARTED,
	RESUMED,
	TERMINATED,
	SUSPENDED,
	
	// An enumeration of all commands that can be sent by the debug server to debug
	// clients (i.e., interpreters or other processes).
	GET_FRAME_VARIABLES,
	GET_VARIABLES,
	GET_STACK,
	STEP_RETURN,
	STEP_INTO,
	STEP_OVER,
	RESUME,
	EXIT,
	
	ADD_LINE_BREAKPOINT,
	REMOVE_LINE_BREAKPOINT,
	
	GET_STACK_RESPONSE,
	GET_FRAME_VARIABLES_RESPONSE,
	GET_VARIABLES_RESPONSE,
}
