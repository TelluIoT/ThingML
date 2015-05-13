/**
 * Copyright (C) 2014 SINTEF <franck.fleurey@sintef.no>
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3, 29 June 2007;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
