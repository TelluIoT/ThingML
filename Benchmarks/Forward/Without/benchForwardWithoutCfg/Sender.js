var StateJS = require('state.js');

/**
 * Definition for type : Sender
 **/
function Sender(Sender_initRedo__var, Sender_nbIter__var, Sender_redo__var) {

var _this;
this.setThis = function(__this) {
_this = __this;
};

this.ready = false;
//Attributes
this.Sender_initRedo__var = Sender_initRedo__var;
this.Sender_nbIter__var = Sender_nbIter__var;
this.Sender_redo__var = Sender_redo__var;
//message queue
const queue = [];
this.getQueue = function() {
return queue;
};

//callbacks for third-party listeners
const forwardMessageOnsenderCepServiceListeners = [];
this.getForwardMessageonsenderCepServiceListeners = function() {
return forwardMessageOnsenderCepServiceListeners;
};
const forwardMessageWithParamsOnsenderCepServiceListeners = [];
this.getForwardMessageWithParamsonsenderCepServiceListeners = function() {
return forwardMessageWithParamsOnsenderCepServiceListeners;
};
const forwardWithModifOnsenderCepServiceListeners = [];
this.getForwardWithModifonsenderCepServiceListeners = function() {
return forwardWithModifOnsenderCepServiceListeners;
};
const endBenchOnsenderCepServiceListeners = [];
this.getEndBenchonsenderCepServiceListeners = function() {
return endBenchOnsenderCepServiceListeners;
};
const forwardMessageOnsenderServiceListeners = [];
this.getForwardMessageonsenderServiceListeners = function() {
return forwardMessageOnsenderServiceListeners;
};
const forwardMessageWithParamsOnsenderServiceListeners = [];
this.getForwardMessageWithParamsonsenderServiceListeners = function() {
return forwardMessageWithParamsOnsenderServiceListeners;
};
const forwardWithModifOnsenderServiceListeners = [];
this.getForwardWithModifonsenderServiceListeners = function() {
return forwardWithModifOnsenderServiceListeners;
};
const endBenchOnsenderServiceListeners = [];
this.getEndBenchonsenderServiceListeners = function() {
return endBenchOnsenderServiceListeners;
};
//CEP dispatch functions
this.cepDispatch = function (message) {
}
//ThingML-defined functions
//Internal functions
function sendForwardMessageOnSenderCepService() {
//notify listeners
const arrayLength = forwardMessageOnsenderCepServiceListeners.length;
for (var _i = 0; _i < arrayLength; _i++) {
forwardMessageOnsenderCepServiceListeners[_i]();
}
}

function sendForwardMessageWithParamsOnSenderCepService(p1, p2, p3, p4, p5) {
//notify listeners
const arrayLength = forwardMessageWithParamsOnsenderCepServiceListeners.length;
for (var _i = 0; _i < arrayLength; _i++) {
forwardMessageWithParamsOnsenderCepServiceListeners[_i](p1, p2, p3, p4, p5);
}
}

function sendForwardWithModifOnSenderCepService(p1, p2, p3, p4, p5) {
//notify listeners
const arrayLength = forwardWithModifOnsenderCepServiceListeners.length;
for (var _i = 0; _i < arrayLength; _i++) {
forwardWithModifOnsenderCepServiceListeners[_i](p1, p2, p3, p4, p5);
}
}

function sendEndBenchOnSenderCepService() {
//notify listeners
const arrayLength = endBenchOnsenderCepServiceListeners.length;
for (var _i = 0; _i < arrayLength; _i++) {
endBenchOnsenderCepServiceListeners[_i]();
}
}

function sendForwardMessageOnSenderService() {
//notify listeners
const arrayLength = forwardMessageOnsenderServiceListeners.length;
for (var _i = 0; _i < arrayLength; _i++) {
forwardMessageOnsenderServiceListeners[_i]();
}
}

function sendForwardMessageWithParamsOnSenderService(p1, p2, p3, p4, p5) {
//notify listeners
const arrayLength = forwardMessageWithParamsOnsenderServiceListeners.length;
for (var _i = 0; _i < arrayLength; _i++) {
forwardMessageWithParamsOnsenderServiceListeners[_i](p1, p2, p3, p4, p5);
}
}

function sendForwardWithModifOnSenderService(p1, p2, p3, p4, p5) {
//notify listeners
const arrayLength = forwardWithModifOnsenderServiceListeners.length;
for (var _i = 0; _i < arrayLength; _i++) {
forwardWithModifOnsenderServiceListeners[_i](p1, p2, p3, p4, p5);
}
}

function sendEndBenchOnSenderService() {
//notify listeners
const arrayLength = endBenchOnsenderServiceListeners.length;
for (var _i = 0; _i < arrayLength; _i++) {
endBenchOnsenderServiceListeners[_i]();
}
}

//State machine (states and regions)
this.build = function() {
this.Sender_Sender = new StateJS.StateMachine("Sender");
this._initial_Sender_Sender = new StateJS.PseudoState("_initial", this.Sender_Sender, StateJS.PseudoStateKind.Initial);
var Sender_Sender_Init = new StateJS.State("Init", this.Sender_Sender).entry(function () {
var i__var = 0;

while(i__var < _this.Sender_nbIter__var) {
process.nextTick(sendForwardMessageOnSenderService.bind(_this));
process.nextTick(sendForwardMessageWithParamsOnSenderService.bind(_this, 1, 2, 3, 4, 5));
process.nextTick(sendForwardWithModifOnSenderService.bind(_this, 1, 2, 3, 4, 5));
i__var = i__var + 1;

}
process.nextTick(sendEndBenchOnSenderService.bind(_this));
_this.Sender_redo__var = _this.Sender_redo__var - 1;
console.log("Redo = " + _this.Sender_redo__var);
})

;
var Sender_Sender_Idle = new StateJS.State("Idle", this.Sender_Sender);
var Sender_Sender_End = new StateJS.State("End", this.Sender_Sender).entry(function () {
console.log("Bye");
})

;
this._initial_Sender_Sender.to(Sender_Sender_Init);
Sender_Sender_Init.to(Sender_Sender_Idle);
Sender_Sender_Idle.to(Sender_Sender_Init).when(function (message) {return message[0] === "senderService" && message[1] === "redo" && _this.Sender_redo__var > 0;});
Sender_Sender_Idle.to(Sender_Sender_End).when(function (message) {return message[0] === "senderService" && message[1] === "redo" && _this.Sender_redo__var === 0;});
}
}
//Public API for lifecycle management
Sender.prototype._stop = function() {
this.Sender_Sender.beginExit(this._initial_Sender_Sender );
};

//Public API for third parties
Sender.prototype._init = function() {
this.Sender_instance = new StateJS.StateMachineInstance("Sender_instance");
StateJS.initialise( this.Sender_Sender, this.Sender_instance );
var msg = this.getQueue().shift();
while(msg !== undefined) {
StateJS.evaluate(this.Sender_Sender, this.Sender_instance, msg);
msg = this.getQueue().shift();
}
this.ready = true;
};

Sender.prototype._receive = function() {
this.getQueue().push(arguments);
this.cepDispatch(arguments);if (this.ready) {
var msg = this.getQueue().shift();
while(msg !== undefined) {
StateJS.evaluate(this.Sender_Sender, this.Sender_instance, msg);
msg = this.getQueue().shift();
}
}
};
Sender.prototype.receiveredoOnsenderService = function() {
this._receive("senderService", "redo");
};

Sender.prototype.getName = function() {
return "Sender";
};

module.exports = Sender;
