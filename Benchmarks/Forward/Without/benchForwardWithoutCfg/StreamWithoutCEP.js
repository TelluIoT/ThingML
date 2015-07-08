var StateJS = require('state.js');

/**
 * Definition for type : StreamWithoutCEP
 **/
function StreamWithoutCEP() {

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
const c_forwardMessageOncServiceListeners = [];
this.getC_forwardMessageoncServiceListeners = function() {
return c_forwardMessageOncServiceListeners;
};
const c_forwardMessageWithParamsOncServiceListeners = [];
this.getC_forwardMessageWithParamsoncServiceListeners = function() {
return c_forwardMessageWithParamsOncServiceListeners;
};
const c_forwardWithModifOncServiceListeners = [];
this.getC_forwardWithModifoncServiceListeners = function() {
return c_forwardWithModifOncServiceListeners;
};
const c_endBenchOncServiceListeners = [];
this.getC_endBenchoncServiceListeners = function() {
return c_endBenchOncServiceListeners;
};
//CEP dispatch functions
this.cepDispatch = function (message) {
}
//ThingML-defined functions
function complexFunction(StreamWithoutCEP_complexFunction_param__var) {
return StreamWithoutCEP_complexFunction_param__var % 3;
}

this.complexFunction = function(StreamWithoutCEP_complexFunction_param__var) {
complexFunction(StreamWithoutCEP_complexFunction_param__var);};

//Internal functions
function sendC_forwardMessageOnCService() {
//notify listeners
const arrayLength = c_forwardMessageOncServiceListeners.length;
for (var _i = 0; _i < arrayLength; _i++) {
c_forwardMessageOncServiceListeners[_i]();
}
}

function sendC_forwardMessageWithParamsOnCService(p1, p2, p3, p4, p5) {
//notify listeners
const arrayLength = c_forwardMessageWithParamsOncServiceListeners.length;
for (var _i = 0; _i < arrayLength; _i++) {
c_forwardMessageWithParamsOncServiceListeners[_i](p1, p2, p3, p4, p5);
}
}

function sendC_forwardWithModifOnCService(p1, p2, p3, p4, p5) {
//notify listeners
const arrayLength = c_forwardWithModifOncServiceListeners.length;
for (var _i = 0; _i < arrayLength; _i++) {
c_forwardWithModifOncServiceListeners[_i](p1, p2, p3, p4, p5);
}
}

function sendC_endBenchOnCService() {
//notify listeners
const arrayLength = c_endBenchOncServiceListeners.length;
for (var _i = 0; _i < arrayLength; _i++) {
c_endBenchOncServiceListeners[_i]();
}
}

//State machine (states and regions)
this.build = function() {
this.StreamWithoutCEP_StreamWithoutCEP = new StateJS.StateMachine("StreamWithoutCEP");
this._initial_StreamWithoutCEP_StreamWithoutCEP = new StateJS.PseudoState("_initial", this.StreamWithoutCEP_StreamWithoutCEP, StateJS.PseudoStateKind.Initial);
var StreamWithoutCEP_StreamWithoutCEP_Init = new StateJS.State("Init", this.StreamWithoutCEP_StreamWithoutCEP);
this._initial_StreamWithoutCEP_StreamWithoutCEP.to(StreamWithoutCEP_StreamWithoutCEP_Init);
StreamWithoutCEP_StreamWithoutCEP_Init.to(null).when(function (message) { v_p1 = message[2]; v_p2 = message[3]; v_p3 = message[4]; v_p4 = message[5]; v_p5 = message[6];return message[0] === "senderService" && message[1] === "forwardWithModif";}).effect(function (message) {
 v_p1 = message[2]; v_p2 = message[3]; v_p3 = message[4]; v_p4 = message[5]; v_p5 = message[6];process.nextTick(sendC_forwardMessageWithParamsOnCService.bind(_this, v_p1 % 3, v_p2 % 3, v_p3 % 3, v_p4 % 3, v_p5 % 3));
});
StreamWithoutCEP_StreamWithoutCEP_Init.to(null).when(function (message) {return message[0] === "senderService" && message[1] === "forwardMessage";}).effect(function (message) {
process.nextTick(sendC_forwardMessageOnCService.bind(_this));
});
StreamWithoutCEP_StreamWithoutCEP_Init.to(null).when(function (message) { v_p1 = message[2]; v_p2 = message[3]; v_p3 = message[4]; v_p4 = message[5]; v_p5 = message[6];return message[0] === "senderService" && message[1] === "forwardMessageWithParams";}).effect(function (message) {
 v_p1 = message[2]; v_p2 = message[3]; v_p3 = message[4]; v_p4 = message[5]; v_p5 = message[6];process.nextTick(sendC_forwardMessageWithParamsOnCService.bind(_this, v_p1, v_p2, v_p3, v_p4, v_p5));
});
StreamWithoutCEP_StreamWithoutCEP_Init.to(null).when(function (message) {return message[0] === "senderService" && message[1] === "endBench";}).effect(function (message) {
process.nextTick(sendC_endBenchOnCService.bind(_this));
});
}
}
//Public API for lifecycle management
StreamWithoutCEP.prototype._stop = function() {
this.StreamWithoutCEP_StreamWithoutCEP.beginExit(this._initial_StreamWithoutCEP_StreamWithoutCEP );
};

//Public API for third parties
StreamWithoutCEP.prototype._init = function() {
this.StreamWithoutCEP_instance = new StateJS.StateMachineInstance("StreamWithoutCEP_instance");
StateJS.initialise( this.StreamWithoutCEP_StreamWithoutCEP, this.StreamWithoutCEP_instance );
var msg = this.getQueue().shift();
while(msg !== undefined) {
StateJS.evaluate(this.StreamWithoutCEP_StreamWithoutCEP, this.StreamWithoutCEP_instance, msg);
msg = this.getQueue().shift();
}
this.ready = true;
};

StreamWithoutCEP.prototype._receive = function() {
this.getQueue().push(arguments);
this.cepDispatch(arguments);if (this.ready) {
var msg = this.getQueue().shift();
while(msg !== undefined) {
StateJS.evaluate(this.StreamWithoutCEP_StreamWithoutCEP, this.StreamWithoutCEP_instance, msg);
msg = this.getQueue().shift();
}
}
};
StreamWithoutCEP.prototype.receiveforwardMessageOnsenderService = function() {
this._receive("senderService", "forwardMessage");
};

StreamWithoutCEP.prototype.receiveforwardMessageWithParamsOnsenderService = function(p1, p2, p3, p4, p5) {
this._receive("senderService", "forwardMessageWithParams", p1, p2, p3, p4, p5);
};

StreamWithoutCEP.prototype.receiveforwardWithModifOnsenderService = function(p1, p2, p3, p4, p5) {
this._receive("senderService", "forwardWithModif", p1, p2, p3, p4, p5);
};

StreamWithoutCEP.prototype.receiveendBenchOnsenderService = function() {
this._receive("senderService", "endBench");
};

StreamWithoutCEP.prototype.getName = function() {
return "StreamWithoutCEP";
};

module.exports = StreamWithoutCEP;
