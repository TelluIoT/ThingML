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

/**
 * Definition for type : Receiver
 **/
function Receiver() {

var _this;
this.setThis = function(__this) {
_this = __this;
};

this.ready = false;
//Attributes
//message queue
const queue = [];
this.getQueue = function() {
return queue;
};

//callbacks for third-party listeners
//CEP dispatch functions
this.cepDispatch = function (message) {
}
//ThingML-defined functions
//Internal functions
//State machine (states and regions)
this.build = function() {
this.Receiver_Receiver = new StateJS.StateMachine("Receiver");
this._initial_Receiver_Receiver = new StateJS.PseudoState("_initial", this.Receiver_Receiver, StateJS.PseudoStateKind.Initial);
var Receiver_Receiver_Idle = new StateJS.State("Idle", this.Receiver_Receiver);
this._initial_Receiver_Receiver.to(Receiver_Receiver_Idle);
Receiver_Receiver_Idle.to(null).when(function (message) {return message[0] === "service" && message[1] === "m1";}).effect(function (message) {
console.log("m1 received");
});
}
}
//Public API for lifecycle management
Receiver.prototype._stop = function() {
this.ready = false;
};

//Public API for third parties
Receiver.prototype._init = function() {
this.Receiver_instance = new StateJS.StateMachineInstance("Receiver_instance");
StateJS.initialise( this.Receiver_Receiver, this.Receiver_instance );
var msg = this.getQueue().shift();
while(msg !== undefined) {
StateJS.evaluate(this.Receiver_Receiver, this.Receiver_instance, msg);
msg = this.getQueue().shift();
}
this.ready = true;
};

Receiver.prototype._receive = function() {
this.getQueue().push(arguments);
this.cepDispatch(arguments);if (this.ready) {
var msg = this.getQueue().shift();
while(msg !== undefined) {
StateJS.evaluate(this.Receiver_Receiver, this.Receiver_instance, msg);
msg = this.getQueue().shift();
}
}
};
Receiver.prototype.receivem1Onservice = function() {
this._receive("service", "m1");
};

Receiver.prototype.getName = function() {
return "Receiver";
};

module.exports = Receiver;
