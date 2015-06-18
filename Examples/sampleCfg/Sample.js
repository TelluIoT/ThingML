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
 * Definition for type : Sample
 **/
function Sample() {

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
const m1OnsendPortListeners = [];
this.getM1onsendPortListeners = function() {
return m1OnsendPortListeners;
};
const m2OnsendPortListeners = [];
this.getM2onsendPortListeners = function() {
return m2OnsendPortListeners;
};
const m3OnsendPortListeners = [];
this.getM3onsendPortListeners = function() {
return m3OnsendPortListeners;
};
const m4OnsendPortListeners = [];
this.getM4onsendPortListeners = function() {
return m4OnsendPortListeners;
};
const m5OnsendPortListeners = [];
this.getM5onsendPortListeners = function() {
return m5OnsendPortListeners;
};
const m6OnsendPortListeners = [];
this.getM6onsendPortListeners = function() {
return m6OnsendPortListeners;
};
const m7OnsendPortListeners = [];
this.getM7onsendPortListeners = function() {
return m7OnsendPortListeners;
};
const m8OnsendPortListeners = [];
this.getM8onsendPortListeners = function() {
return m8OnsendPortListeners;
};
const cep1OnsendPortListeners = [];
this.getCep1onsendPortListeners = function() {
return cep1OnsendPortListeners;
};
const cep2OnsendPortListeners = [];
this.getCep2onsendPortListeners = function() {
return cep2OnsendPortListeners;
};
const cep3OnsendPortListeners = [];
this.getCep3onsendPortListeners = function() {
return cep3OnsendPortListeners;
};
const cep4OnsendPortListeners = [];
this.getCep4onsendPortListeners = function() {
return cep4OnsendPortListeners;
};
const cep5OnsendPortListeners = [];
this.getCep5onsendPortListeners = function() {
return cep5OnsendPortListeners;
};
//CEP dispatch functions
this.cepDispatch = function (message) {
if(message[0] === "rcvPort" && message[1] === "m1") {
	this.eventEmitterForStream.emit('Sample_forwardSimpleMessage_rcvPort_m1',message);
}
if(message[0] === "rcvPort" && message[1] === "m2") {
	this.eventEmitterForStream.emit('Sample_forwardMessageWithParams_rcvPort_m2',message);
}
if(message[0] === "rcvPort" && message[1] === "m2") {
	this.eventEmitterForStream.emit('Sample_transformingMessageValue_rcvPort_m2',message);
}
if(message[0] === "rcvPort" && message[1] === "m3") {
	this.eventEmitterForStream.emit('Sample_mergedSimpleMessages',message);
}
if(message[0] === "rcvPort" && message[1] === "m4") {
	this.eventEmitterForStream.emit('Sample_mergedSimpleMessages',message);
}
if(message[0] === "rcvPort" && message[1] === "m5") {
	this.eventEmitterForStream.emit('Sample_mergeMessagesWithValues',message);
}
if(message[0] === "rcvPort" && message[1] === "m6") {
	this.eventEmitterForStream.emit('Sample_mergeMessagesWithValues',message);
}
if(message[0] === "rcvPort" && message[1] === "m7") {
	this.eventEmitterForStream.emit('Sample_joinMessages_rcvPort_m7',message);
}
if(message[0] === "rcvPort" && message[1] === "m8") {
	this.eventEmitterForStream.emit('Sample_joinMessages_rcvPort_m8',message);
}
}
//ThingML-defined functions
function complexTransforming(Sample_complexTransforming_value__var) {
return Sample_complexTransforming_value__var + 1;
}

this.complexTransforming = function(Sample_complexTransforming_value__var) {
complexTransforming(Sample_complexTransforming_value__var);};

//Internal functions
function sendM1OnSendPort() {
//notify listeners
const arrayLength = m1OnsendPortListeners.length;
for (var _i = 0; _i < arrayLength; _i++) {
m1OnsendPortListeners[_i]();
}
}

function sendM2OnSendPort(v1, v2) {
//notify listeners
const arrayLength = m2OnsendPortListeners.length;
for (var _i = 0; _i < arrayLength; _i++) {
m2OnsendPortListeners[_i](v1, v2);
}
}

function sendM3OnSendPort() {
//notify listeners
const arrayLength = m3OnsendPortListeners.length;
for (var _i = 0; _i < arrayLength; _i++) {
m3OnsendPortListeners[_i]();
}
}

function sendM4OnSendPort() {
//notify listeners
const arrayLength = m4OnsendPortListeners.length;
for (var _i = 0; _i < arrayLength; _i++) {
m4OnsendPortListeners[_i]();
}
}

function sendM5OnSendPort(x, y, z) {
//notify listeners
const arrayLength = m5OnsendPortListeners.length;
for (var _i = 0; _i < arrayLength; _i++) {
m5OnsendPortListeners[_i](x, y, z);
}
}

function sendM6OnSendPort(value1, value2, value3) {
//notify listeners
const arrayLength = m6OnsendPortListeners.length;
for (var _i = 0; _i < arrayLength; _i++) {
m6OnsendPortListeners[_i](value1, value2, value3);
}
}

function sendM7OnSendPort(a, b, c) {
//notify listeners
const arrayLength = m7OnsendPortListeners.length;
for (var _i = 0; _i < arrayLength; _i++) {
m7OnsendPortListeners[_i](a, b, c);
}
}

function sendM8OnSendPort(v1, v2, v3) {
//notify listeners
const arrayLength = m8OnsendPortListeners.length;
for (var _i = 0; _i < arrayLength; _i++) {
m8OnsendPortListeners[_i](v1, v2, v3);
}
}

function sendCep1OnSendPort() {
//notify listeners
const arrayLength = cep1OnsendPortListeners.length;
for (var _i = 0; _i < arrayLength; _i++) {
cep1OnsendPortListeners[_i]();
}
}

function sendCep2OnSendPort(val1, val2) {
//notify listeners
const arrayLength = cep2OnsendPortListeners.length;
for (var _i = 0; _i < arrayLength; _i++) {
cep2OnsendPortListeners[_i](val1, val2);
}
}

function sendCep3OnSendPort() {
//notify listeners
const arrayLength = cep3OnsendPortListeners.length;
for (var _i = 0; _i < arrayLength; _i++) {
cep3OnsendPortListeners[_i]();
}
}

function sendCep4OnSendPort(a, b, c) {
//notify listeners
const arrayLength = cep4OnsendPortListeners.length;
for (var _i = 0; _i < arrayLength; _i++) {
cep4OnsendPortListeners[_i](a, b, c);
}
}

function sendCep5OnSendPort(a, b, c) {
//notify listeners
const arrayLength = cep5OnsendPortListeners.length;
for (var _i = 0; _i < arrayLength; _i++) {
cep5OnsendPortListeners[_i](a, b, c);
}
}

//State machine (states and regions)
this.build = function() {
this.Sample_Sample = new StateJS.StateMachine("Sample");
this._initial_Sample_Sample = new StateJS.PseudoState("_initial", this.Sample_Sample, StateJS.PseudoStateKind.Initial);
var Sample_Sample_Init = new StateJS.State("Init", this.Sample_Sample).entry(function () {
console.log("Init");
process.nextTick(sendM1OnSendPort.bind(_this));
process.nextTick(sendM2OnSendPort.bind(_this, 8, 24));
process.nextTick(sendM3OnSendPort.bind(_this));
process.nextTick(sendM4OnSendPort.bind(_this));
process.nextTick(sendM5OnSendPort.bind(_this, 1, 2, 3));
process.nextTick(sendM6OnSendPort.bind(_this, 4, 5, 6));
process.nextTick(sendM7OnSendPort.bind(_this, 1, 2, 3));
process.nextTick(sendM8OnSendPort.bind(_this, 10, 20, 30));
})

;
this._initial_Sample_Sample.to(Sample_Sample_Init);
Sample_Sample_Init.to(null).when(function (message) { v_val1 = message[2]; v_val2 = message[3];return message[0] === "rcvPort" && message[1] === "cep2";}).effect(function (message) {
 v_val1 = message[2]; v_val2 = message[3];console.log("cep2 message receive with values (" + v_val1 + ";" + v_val2 + ").");
});
Sample_Sample_Init.to(null).when(function (message) { v_a = message[2]; v_b = message[3]; v_c = message[4];return message[0] === "rcvPort" && message[1] === "cep4";}).effect(function (message) {
 v_a = message[2]; v_b = message[3]; v_c = message[4];console.log("cep4 message receive with values (" + v_a + ";" + v_b + ";" + v_c + ").");
});
Sample_Sample_Init.to(null).when(function (message) { v_a = message[2]; v_b = message[3]; v_c = message[4];return message[0] === "rcvPort" && message[1] === "cep5";}).effect(function (message) {
 v_a = message[2]; v_b = message[3]; v_c = message[4];console.log("cep5 message receive with values (" + v_a + ";" + v_b + ";" + v_c + ").");
});
Sample_Sample_Init.to(null).when(function (message) {return message[0] === "rcvPort" && message[1] === "cep1";}).effect(function (message) {
console.log("cep message receive.");
});
Sample_Sample_Init.to(null).when(function (message) {return message[0] === "rcvPort" && message[1] === "cep3";}).effect(function (message) {
console.log("cep3 message receive.");
});
}
var Sample_forwardSimpleMessage = Rx.Observable.fromEvent(this.eventEmitterForStream, 'Sample_forwardSimpleMessage_rcvPort_m1').subscribe(
	function(m1) {
;
			process.nextTick(sendCep1OnSendPort.bind(_this));
	});
var Sample_forwardMessageWithParams = Rx.Observable.fromEvent(this.eventEmitterForStream, 'Sample_forwardMessageWithParams_rcvPort_m2').subscribe(
	function(m2) {
		var a = m2[2];
		var b = m2[3];
;
			process.nextTick(sendCep2OnSendPort.bind(_this, a, b));
	});
var Sample_transformingMessageValue = Rx.Observable.fromEvent(this.eventEmitterForStream, 'Sample_transformingMessageValue_rcvPort_m2').subscribe(
	function(m2) {
		var a = (m2[2] * 2) + 5;
		var b = complexTransforming(m2[3])
;
;
			process.nextTick(sendCep2OnSendPort.bind(_this, a, b));
	});
var Sample_mergedSimpleMessages = Rx.Observable.fromEvent(this.eventEmitterForStream, 'Sample_mergedSimpleMessages').subscribe(
	function(x) {
process.nextTick(sendCep3OnSendPort.bind(_this));
	});
var Sample_mergeMessagesWithValues = Rx.Observable.fromEvent(this.eventEmitterForStream, 'Sample_mergeMessagesWithValues').subscribe(
	function(x) {
process.nextTick(sendCep4OnSendPort.bind(_this, x[2], x[3], x[4]));
	});

function wait1() { return Rx.Observable.timer(50); }
var Sample_joinMessages_m7 = Rx.Observable.fromEvent(this.eventEmitterForStream, 'Sample_joinMessages_rcvPort_m7');
var Sample_joinMessages_m8 = Rx.Observable.fromEvent(this.eventEmitterForStream, 'Sample_joinMessages_rcvPort_m8');
var Sample_joinMessages = Sample_joinMessages_m8.join(Sample_joinMessages_m7,wait1,wait1,
	function(m8,m7) {
		var a = m7[2] * m8[2];
		var b = m7[3] * m8[3];
		var c = m7[4] * m8[4];
		return { '0' : a, '1' : b, '2' : c };
	}).subscribe(
			function(x) {
			process.nextTick(sendCep5OnSendPort.bind(_this, x[0], x[1], x[2]));
	});
}
//Public API for lifecycle management
Sample.prototype._stop = function() {
this.Sample_Sample.beginExit(this._initial_Sample_Sample );
};

//Public API for third parties
Sample.prototype._init = function() {
this.Sample_instance = new StateJS.StateMachineInstance("Sample_instance");
StateJS.initialise( this.Sample_Sample, this.Sample_instance );
var msg = this.getQueue().shift();
while(msg !== undefined) {
StateJS.evaluate(this.Sample_Sample, this.Sample_instance, msg);
msg = this.getQueue().shift();
}
this.ready = true;
};

Sample.prototype._receive = function() {
this.getQueue().push(arguments);
this.cepDispatch(arguments);if (this.ready) {
var msg = this.getQueue().shift();
while(msg !== undefined) {
StateJS.evaluate(this.Sample_Sample, this.Sample_instance, msg);
msg = this.getQueue().shift();
}
}
};
Sample.prototype.receivem1OnrcvPort = function() {
this._receive("rcvPort", "m1");
};

Sample.prototype.receivem2OnrcvPort = function(v1, v2) {
this._receive("rcvPort", "m2", v1, v2);
};

Sample.prototype.receivem3OnrcvPort = function() {
this._receive("rcvPort", "m3");
};

Sample.prototype.receivem4OnrcvPort = function() {
this._receive("rcvPort", "m4");
};

Sample.prototype.receivem5OnrcvPort = function(x, y, z) {
this._receive("rcvPort", "m5", x, y, z);
};

Sample.prototype.receivem6OnrcvPort = function(value1, value2, value3) {
this._receive("rcvPort", "m6", value1, value2, value3);
};

Sample.prototype.receivem7OnrcvPort = function(a, b, c) {
this._receive("rcvPort", "m7", a, b, c);
};

Sample.prototype.receivem8OnrcvPort = function(v1, v2, v3) {
this._receive("rcvPort", "m8", v1, v2, v3);
};

Sample.prototype.receivecep1OnrcvPort = function() {
this._receive("rcvPort", "cep1");
};

Sample.prototype.receivecep2OnrcvPort = function(val1, val2) {
this._receive("rcvPort", "cep2", val1, val2);
};

Sample.prototype.receivecep3OnrcvPort = function() {
this._receive("rcvPort", "cep3");
};

Sample.prototype.receivecep4OnrcvPort = function(a, b, c) {
this._receive("rcvPort", "cep4", a, b, c);
};

Sample.prototype.receivecep5OnrcvPort = function(a, b, c) {
this._receive("rcvPort", "cep5", a, b, c);
};

Sample.prototype.getName = function() {
return "Sample";
};

module.exports = Sample;
