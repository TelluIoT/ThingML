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
 * Definition for type : PingClient
 */

function PingClient(name, root) {
	this.name = name;
	this.root = (root === null)? this : root;
	this.ready = false;
	this.bus = (root === null)? new EventEmitter() : this.root.bus;
	
	this.build(name);
}

PingClient.prototype.build = function(session) {
	/*State machine (states and regions)*/
	/*Building root component*/
	this._statemachine = new StateJS.StateMachine('PingClientMachine');
	let _initial_PingClient_PingClientMachine = new StateJS.PseudoState('_initial', this._statemachine, StateJS.PseudoStateKind.Initial);
	let PingClient_PingClientMachine_Ping = new StateJS.State('Ping', this._statemachine).entry(() => {
		console.log(''+'Send Ping '+this.PingClient_PingClientMachine_counter_var+' ... ');
		setTimeout(() => this.bus.emit('ping_service?ping', this.PingClient_PingClientMachine_counter_var), 0);
	}).exit(() => {
		this.PingClient_PingClientMachine_counter_var = this.PingClient_PingClientMachine_counter_var + 1;
	});
	let PingClient_PingClientMachine_OK = new StateJS.State('OK', this._statemachine);
	let PingClient_PingClientMachine_Stop = new StateJS.FinalState('Stop', this._statemachine).entry(() => {
		console.log(''+'Bye.');
		setImmediate(()=>this._stop());
	});
	_initial_PingClient_PingClientMachine.to(PingClient_PingClientMachine_Ping);
	PingClient_PingClientMachine_OK.to(PingClient_PingClientMachine_Stop).when(() => {
		return (this.PingClient_PingClientMachine_counter_var > 5);
	});
	PingClient_PingClientMachine_OK.to(PingClient_PingClientMachine_Ping).when(() => {
		return (this.PingClient_PingClientMachine_counter_var <= 5);
	});
	PingClient_PingClientMachine_Ping.to(PingClient_PingClientMachine_OK).when((pong) => {
		return pong._port === 'ping_service' && pong._msg === 'pong' && (pong.seq === this.PingClient_PingClientMachine_counter_var);
	}).effect((pong) => {
		console.log(''+'[OK]');
	});
	PingClient_PingClientMachine_Ping.to(PingClient_PingClientMachine_Stop).when((pong) => {
		return pong._port === 'ping_service' && pong._msg === 'pong' && (pong.seq != this.PingClient_PingClientMachine_counter_var);
	}).effect((pong) => {
		console.log(''+'[Error]');
	});
}
PingClient.prototype._stop = function() {
	this.root = null;
	this.ready = false;
}

PingClient.prototype._delete = function() {
	this._statemachine = null;
	this._PingClientMachine_instance = null;
	this.bus.removeAllListeners();
}

PingClient.prototype._init = function() {
	this._PingClientMachine_instance = new StateJS.StateMachineInstance("PingClientMachine_instance");
	StateJS.initialise(this._statemachine, this._PingClientMachine_instance);
	this.ready = true;
}

PingClient.prototype._receive = function(msg) {
	/*msg = {_port:myPort, _msg:myMessage, paramN=paramN, ...}*/
	if (this.ready) {
		StateJS.evaluate(this._statemachine, this._PingClientMachine_instance, msg);
	} else {
		setTimeout(()=>this._receive(msg),0);
	}
}

PingClient.prototype.receivepongOnping_service = function(seq) {
	this._receive({_port:"ping_service", _msg:"pong", seq:seq});
}

PingClient.prototype.initPingClient_PingClientMachine_counter_var = function(PingClient_PingClientMachine_counter_var) {
	this.PingClient_PingClientMachine_counter_var = PingClient_PingClientMachine_counter_var;
}

PingClient.prototype.toString = function() {
	let result = 'instance ' + this.name + ':' + this.constructor.name + '\n';
	result += '\n\tcounter = ' + this.PingClient_PingClientMachine_counter_var;
	result += '';
	return result;
}

