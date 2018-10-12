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

StateJS.internalTransitionsTriggerCompletion = true;


/*
 * Definition for type : PingServer
 */

function PingServer(name, root) {
	this.name = name;
	this.root = (root === null)? this : root;
	this.ready = false;
	this.bus = (root === null)? new EventEmitter() : this.root.bus;
	
	this.build(name);
}

PingServer.prototype.build = function(session) {
	/*State machine (states and regions)*/
	/*Building root component*/
	this._statemachine = new StateJS.StateMachine('default');
	let _initial_PingServer = new StateJS.PseudoState('_initial', this._statemachine, StateJS.PseudoStateKind.Initial);
	let PingServer_null_Active = new StateJS.State('Active', this._statemachine);
	_initial_PingServer.to(PingServer_null_Active);
	PingServer_null_Active.to(PingServer_null_Active).when((ping) => {
		return ping._port === 'ping_service' && ping._msg === 'ping';
	}).effect((ping) => {
		setTimeout(() => this.bus.emit('ping_service?pong', ping.seq), 0);
	});
}
PingServer.prototype._stop = function() {
	this.root = null;
	this.ready = false;
}

PingServer.prototype._delete = function() {
	this._statemachine = null;
	this._null_instance = null;
	this.bus.removeAllListeners();
}

PingServer.prototype._init = function() {
	this._null_instance = new StateJS.StateMachineInstance("null_instance");
	StateJS.initialise(this._statemachine, this._null_instance);
	this.ready = true;
}

PingServer.prototype._receive = function(msg) {
	/*msg = {_port:myPort, _msg:myMessage, paramN=paramN, ...}*/
	if (this.ready) {
		StateJS.evaluate(this._statemachine, this._null_instance, msg);
	} else {
		setTimeout(()=>this._receive(msg),0);
	}
}

PingServer.prototype.receivepingOnping_service = function(seq) {
	this._receive({_port:"ping_service", _msg:"ping", seq:seq});
}

PingServer.prototype.toString = function() {
	let result = 'instance ' + this.name + ':' + this.constructor.name + '\n';
	result += '';
	return result;
}

