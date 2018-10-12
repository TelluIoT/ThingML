/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 */
'use strict';

var RunThingMLConfiguration = function() {
	/*$REQUIRE_PLUGINS$*/
	
	const client = new PingClient('client', null);
	client.initPingClient_PingClientMachine_counter_var(0);
	const server = new PingServer('server', null);
	
	/*Connecting internal ports...*/
	/*Connecting ports...*/
	server.bus.on('ping_service?pong', (seq) => client.receivepongOnping_service(seq));
	client.bus.on('ping_service?ping', (seq) => server.receivepingOnping_service(seq));
	
	server._init();
	client._init();
	
	/*$PLUGINS_END$*/
}

window.addEventListener('DOMContentLoaded', function(){
	RunThingMLConfiguration();
});

