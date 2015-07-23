/*
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
var StateJS = require('state.js');
var Rx = require('rx'),
	EventEmitter = require('events').EventEmitter;

/**
 * Definition for type : Server
 **/
function Server() {

var _this;
this.setThis = function(__this) {
_this = __this;
};

this.ready = false;
//Attributes
this.eventEmitterForStream = new EventEmitter();
//message queue
const queue = [];
this.getQueue = function() {
return queue;
};

//callbacks for third-party listeners
const m1OnserviceListeners = [];
this.getM1onserviceListeners = function() {
return m1OnserviceListeners;
};
//CEP dispatch functions
this.cepDispatch = function (message) {
if(message[0] === "service" && message[1] === "command") {
	this.eventEmitterForStream.emit('Server_joinStream_service_command',message);
}
if(message[0] === "service" && message[1] === "command2") {
	this.eventEmitterForStream.emit('Server_joinStream_service_command2',message);
}
}
//ThingML-defined functions
//Internal functions
function sendM1OnService() {
//notify listeners
const arrayLength = m1OnserviceListeners.length;
for (var _i = 0; _i < arrayLength; _i++) {
m1OnserviceListeners[_i]();
}
}

//State machine (states and regions)
this.build = function() {
this.Server_Server = new StateJS.StateMachine("Server");
this._initial_Server_Server = new StateJS.PseudoState("_initial", this.Server_Server, StateJS.PseudoStateKind.Initial);
var Server_Server_Idle = new StateJS.State("Idle", this.Server_Server);
this._initial_Server_Server.to(Server_Server_Idle);
}

function wait1() { return Rx.Observable.timer(50); }
var Server_joinStream_command = Rx.Observable.fromEvent(this.eventEmitterForStream, 'Server_joinStream_service_command');
var Server_joinStream_command2 = Rx.Observable.fromEvent(this.eventEmitterForStream, 'Server_joinStream_service_command2');
var Server_joinStream = Server_joinStream_command2.join(Server_joinStream_command,wait1,wait1,
	function(command2,command) {
		return {  };
	}).subscribe(
			function(x) {
			setImmediate(sendM1OnService);
	});
}
//Public API for lifecycle management
Server.prototype._stop = function() {
this.ready = false;
};

//Public API for third parties
Server.prototype._init = function() {
this.Server_instance = new StateJS.StateMachineInstance("Server_instance");
StateJS.initialise( this.Server_Server, this.Server_instance );
var msg = this.getQueue().shift();
while(msg !== undefined) {
StateJS.evaluate(this.Server_Server, this.Server_instance, msg);
msg = this.getQueue().shift();
}
this.ready = true;
};

Server.prototype._receive = function() {
this.getQueue().push(arguments);
this.cepDispatch(arguments);if (this.ready) {
var msg = this.getQueue().shift();
while(msg !== undefined) {
StateJS.evaluate(this.Server_Server, this.Server_instance, msg);
msg = this.getQueue().shift();
}
}
};
Server.prototype.receivecommandOnservice = function() {
this._receive("service", "command");
};

Server.prototype.receivecommand2Onservice = function() {
this._receive("service", "command2");
};

Server.prototype.getName = function() {
return "Server";
};

module.exports = Server;
