var StateJS = require('state.js');
var Rx = require('rx'),
	EventEmitter = require('events').EventEmitter;

/**
 * Definition for type : StreamWithCep
 **/
function StreamWithCep() {

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
const cep_forwardMessageOncepServiceListeners = [];
this.getCep_forwardMessageoncepServiceListeners = function() {
return cep_forwardMessageOncepServiceListeners;
};
const cep_forwardMessageWithParamsOncepServiceListeners = [];
this.getCep_forwardMessageWithParamsoncepServiceListeners = function() {
return cep_forwardMessageWithParamsOncepServiceListeners;
};
const cep_forwardWithModifOncepServiceListeners = [];
this.getCep_forwardWithModifoncepServiceListeners = function() {
return cep_forwardWithModifOncepServiceListeners;
};
const cep_endBenchOncepServiceListeners = [];
this.getCep_endBenchoncepServiceListeners = function() {
return cep_endBenchOncepServiceListeners;
};
//CEP dispatch functions
this.cepDispatch = function (message) {
if(message[0] === "senderService" && message[1] === "forwardMessage") {
	this.eventEmitterForStream.emit('StreamWithCep_forwardStream_senderService_forwardMessage',message);
}
if(message[0] === "senderService" && message[1] === "forwardMessageWithParams") {
	this.eventEmitterForStream.emit('StreamWithCep_forwardStreamWithParams_senderService_forwardMessageWithParams',message);
}
if(message[0] === "senderService" && message[1] === "forwardWithModif") {
	this.eventEmitterForStream.emit('StreamWithCep_forwardStreamWithModif_senderService_forwardWithModif',message);
}
if(message[0] === "senderService" && message[1] === "endBench") {
	this.eventEmitterForStream.emit('StreamWithCep_forwardEnd_senderService_endBench',message);
}
}
//ThingML-defined functions
function complexFunction(StreamWithCep_complexFunction_param__var) {
return StreamWithCep_complexFunction_param__var % 3;
}

this.complexFunction = function(StreamWithCep_complexFunction_param__var) {
complexFunction(StreamWithCep_complexFunction_param__var);};

//Internal functions
function sendCep_forwardMessageOnCepService() {
//notify listeners
const arrayLength = cep_forwardMessageOncepServiceListeners.length;
for (var _i = 0; _i < arrayLength; _i++) {
cep_forwardMessageOncepServiceListeners[_i]();
}
}

function sendCep_forwardMessageWithParamsOnCepService(p1, p2, p3, p4, p5) {
//notify listeners
const arrayLength = cep_forwardMessageWithParamsOncepServiceListeners.length;
for (var _i = 0; _i < arrayLength; _i++) {
cep_forwardMessageWithParamsOncepServiceListeners[_i](p1, p2, p3, p4, p5);
}
}

function sendCep_forwardWithModifOnCepService(p1, p2, p3, p4, p5) {
//notify listeners
const arrayLength = cep_forwardWithModifOncepServiceListeners.length;
for (var _i = 0; _i < arrayLength; _i++) {
cep_forwardWithModifOncepServiceListeners[_i](p1, p2, p3, p4, p5);
}
}

function sendCep_endBenchOnCepService() {
//notify listeners
const arrayLength = cep_endBenchOncepServiceListeners.length;
for (var _i = 0; _i < arrayLength; _i++) {
cep_endBenchOncepServiceListeners[_i]();
}
}

//State machine (states and regions)
this.build = function() {
this.StreamWithCep_Stream = new StateJS.StateMachine("Stream");
this._initial_StreamWithCep_Stream = new StateJS.PseudoState("_initial", this.StreamWithCep_Stream, StateJS.PseudoStateKind.Initial);
var StreamWithCep_Stream_Init = new StateJS.State("Init", this.StreamWithCep_Stream);
this._initial_StreamWithCep_Stream.to(StreamWithCep_Stream_Init);
}
var StreamWithCep_forwardStream = Rx.Observable.fromEvent(this.eventEmitterForStream, 'StreamWithCep_forwardStream_senderService_forwardMessage').subscribe(
	function(forwardMessage) {
;
			process.nextTick(sendCep_forwardMessageOnCepService.bind(_this));
	});
var StreamWithCep_forwardStreamWithParams = Rx.Observable.fromEvent(this.eventEmitterForStream, 'StreamWithCep_forwardStreamWithParams_senderService_forwardMessageWithParams').subscribe(
	function(forwardMessageWithParams) {
		var p1 = forwardMessageWithParams[2];
		var p2 = forwardMessageWithParams[3];
		var p3 = forwardMessageWithParams[4];
		var p4 = forwardMessageWithParams[5];
		var p5 = forwardMessageWithParams[6];
;
			process.nextTick(sendCep_forwardMessageWithParamsOnCepService.bind(_this, p1, p2, p3, p4, p5));
	});
var StreamWithCep_forwardStreamWithModif = Rx.Observable.fromEvent(this.eventEmitterForStream, 'StreamWithCep_forwardStreamWithModif_senderService_forwardWithModif').subscribe(
	function(forwardWithModif) {
		var p1 = complexFunction(forwardWithModif[2])
;
		var p2 = complexFunction(forwardWithModif[3])
;
		var p3 = complexFunction(forwardWithModif[4])
;
		var p4 = complexFunction(forwardWithModif[5])
;
		var p5 = complexFunction(forwardWithModif[6])
;
;
			process.nextTick(sendCep_forwardWithModifOnCepService.bind(_this, p1, p2, p3, p4, p5));
	});
var StreamWithCep_forwardEnd = Rx.Observable.fromEvent(this.eventEmitterForStream, 'StreamWithCep_forwardEnd_senderService_endBench').subscribe(
	function(endBench) {
;
			process.nextTick(sendCep_endBenchOnCepService.bind(_this));
	});
}
//Public API for lifecycle management
StreamWithCep.prototype._stop = function() {
this.StreamWithCep_Stream.beginExit(this._initial_StreamWithCep_Stream );
};

//Public API for third parties
StreamWithCep.prototype._init = function() {
this.Stream_instance = new StateJS.StateMachineInstance("Stream_instance");
StateJS.initialise( this.StreamWithCep_Stream, this.Stream_instance );
var msg = this.getQueue().shift();
while(msg !== undefined) {
StateJS.evaluate(this.StreamWithCep_Stream, this.Stream_instance, msg);
msg = this.getQueue().shift();
}
this.ready = true;
};

StreamWithCep.prototype._receive = function() {
this.getQueue().push(arguments);
this.cepDispatch(arguments);if (this.ready) {
var msg = this.getQueue().shift();
while(msg !== undefined) {
StateJS.evaluate(this.StreamWithCep_Stream, this.Stream_instance, msg);
msg = this.getQueue().shift();
}
}
};
StreamWithCep.prototype.receiveforwardMessageOnsenderService = function() {
this._receive("senderService", "forwardMessage");
};

StreamWithCep.prototype.receiveforwardMessageWithParamsOnsenderService = function(p1, p2, p3, p4, p5) {
this._receive("senderService", "forwardMessageWithParams", p1, p2, p3, p4, p5);
};

StreamWithCep.prototype.receiveforwardWithModifOnsenderService = function(p1, p2, p3, p4, p5) {
this._receive("senderService", "forwardWithModif", p1, p2, p3, p4, p5);
};

StreamWithCep.prototype.receiveendBenchOnsenderService = function() {
this._receive("senderService", "endBench");
};

StreamWithCep.prototype.getName = function() {
return "StreamWithCep";
};

module.exports = StreamWithCep;
