var StateJS = require('state.js');

/**
 * Definition for type : Receiver
 **/
function Receiver(Receiver_msgReceived__var) {

var _this;
this.setThis = function(__this) {
_this = __this;
};

this.ready = false;
//Attributes
this.Receiver_msgReceived__var = Receiver_msgReceived__var;
//message queue
const queue = [];
this.getQueue = function() {
return queue;
};

//callbacks for third-party listeners
const redoOnsenderServiceListeners = [];
this.getRedoonsenderServiceListeners = function() {
return redoOnsenderServiceListeners;
};
//CEP dispatch functions
this.cepDispatch = function (message) {
}
//ThingML-defined functions
//Internal functions
function sendRedoOnSenderService() {
//notify listeners
const arrayLength = redoOnsenderServiceListeners.length;
for (var _i = 0; _i < arrayLength; _i++) {
redoOnsenderServiceListeners[_i]();
}
}

//State machine (states and regions)
this.build = function() {
this.Receiver_Receiver = new StateJS.StateMachine("Receiver");
this._initial_Receiver_Receiver = new StateJS.PseudoState("_initial", this.Receiver_Receiver, StateJS.PseudoStateKind.Initial);
var Receiver_Receiver_Init = new StateJS.State("Init", this.Receiver_Receiver);
this._initial_Receiver_Receiver.to(Receiver_Receiver_Init);
Receiver_Receiver_Init.to(null).when(function (message) { v_p1 = message[2]; v_p2 = message[3]; v_p3 = message[4]; v_p4 = message[5]; v_p5 = message[6];return message[0] === "cepService" && message[1] === "cep_forwardWithModif";}).effect(function (message) {
 v_p1 = message[2]; v_p2 = message[3]; v_p3 = message[4]; v_p4 = message[5]; v_p5 = message[6];_this.Receiver_msgReceived__var = _this.Receiver_msgReceived__var + 1;
});
Receiver_Receiver_Init.to(null).when(function (message) {return message[0] === "cepService" && message[1] === "cep_endBench";}).effect(function (message) {
_this.Receiver_msgReceived__var = 0;
process.nextTick(sendRedoOnSenderService.bind(_this));
});
Receiver_Receiver_Init.to(null).when(function (message) {return message[0] === "cepService" && message[1] === "cep_forwardMessage";}).effect(function (message) {
_this.Receiver_msgReceived__var = _this.Receiver_msgReceived__var + 1;
});
Receiver_Receiver_Init.to(null).when(function (message) { v_p1 = message[2]; v_p2 = message[3]; v_p3 = message[4]; v_p4 = message[5]; v_p5 = message[6];return message[0] === "cepService" && message[1] === "cep_forwardMessageWithParams";}).effect(function (message) {
 v_p1 = message[2]; v_p2 = message[3]; v_p3 = message[4]; v_p4 = message[5]; v_p5 = message[6];_this.Receiver_msgReceived__var = _this.Receiver_msgReceived__var + 1;
});
}
}
//Public API for lifecycle management
Receiver.prototype._stop = function() {
this.Receiver_Receiver.beginExit(this._initial_Receiver_Receiver );
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
Receiver.prototype.receivecep_forwardMessageOncepService = function() {
this._receive("cepService", "cep_forwardMessage");
};

Receiver.prototype.receivecep_forwardMessageWithParamsOncepService = function(p1, p2, p3, p4, p5) {
this._receive("cepService", "cep_forwardMessageWithParams", p1, p2, p3, p4, p5);
};

Receiver.prototype.receivecep_forwardWithModifOncepService = function(p1, p2, p3, p4, p5) {
this._receive("cepService", "cep_forwardWithModif", p1, p2, p3, p4, p5);
};

Receiver.prototype.receivecep_endBenchOncepService = function() {
this._receive("cepService", "cep_endBench");
};

Receiver.prototype.getName = function() {
return "Receiver";
};

module.exports = Receiver;
