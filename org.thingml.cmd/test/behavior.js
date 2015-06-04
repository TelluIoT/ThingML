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
var state_js = require('./lib/state.js');
function buildStateMachine(name) {
return new state_js.StateMachine(name);
}

function buildRegion(name, container){
return new state_js.Region(name, container);
}

function buildInitialState(name, container){
return new state_js.PseudoState(name, state_js.PseudoStateKind.Initial, container);
}

function buildFinalState(name, container){
return new state_js.PseudoState(name, state_js.PseudoStateKind.Final, container);
}

function buildHistoryState(name, container){
return new state_js.PseudoState(name, state_js.PseudoStateKind.ShallowHistory, container);
}

function buildSimpleState(name, container){
return new state_js.SimpleState(name, container);
}

function buildCompositeState(name, container){
return new state_js.CompositeState(name, container);
}

function buildOrthogonalState(name, container){
return new state_js.OrthogonalState(name, container);
}

function buildEmptyTransition(source, target){
return new state_js.Transition(source, target);
}

function buildTransition(source, target, guard){
return new state_js.Transition(source, target, guard);
}

function Connector(client, server, clientPort, serverPort) {
this.client = client;
this.server = server;
this.clientPort = clientPort;
this.serverPort = serverPort;
}

Connector.prototype.forward = function(message) {//JSONified messsage, we need to update port before we send to server
var json = JSON.parse(message);
if (json.port === this.clientPort) {
json.port = this.serverPort;
this.server._receive(JSON.stringify(json));
} else {
json.port = this.clientPort;
this.client._receive(JSON.stringify(json));
}
}


/**
 * Definition for type : TestDumpJS
 **/
function TestDumpJS(TestDumpJS_fs__var, TestDumpJS_JavaHarness_transitionsCount__var, TestDumpJS_JavaHarness_time__var) {

this.ready = false;
//Attributes
this.TestDumpJS_fs__var =TestDumpJS_fs__var;
this.TestDumpJS_JavaHarness_transitionsCount__var =TestDumpJS_JavaHarness_transitionsCount__var;
this.TestDumpJS_JavaHarness_time__var =TestDumpJS_JavaHarness_time__var;//bindings
var connectors = [];
this.getConnectors = function() {
return connectors;
}

//message queue
var queue = [];
this.getQueue = function() {
return queue;
}

//callbacks for third-party listeners
//ThingML-defined functions
function write(TestDumpJS_write_string__var) {
TestDumpJS_fs__var.appendFileSync("dump", TestDumpJS_write_string__var);
console.log(TestDumpJS_write_string__var);
}

this.write = function(TestDumpJS_write_string__var) {
write(TestDumpJS_write_string__var);}

//Internal functions
function _send(message) {
var arrayLength = connectors.length;
for (var i = 0; i < arrayLength; i++) {
connectors[i].forward(message);
}
}

//State machine (states and regions)
this.TestDumpJS_JavaHarness = buildRegion("JavaHarness");
this._initial_TestDumpJS_JavaHarness = buildInitialState("_initial", this.TestDumpJS_JavaHarness);
var TestDumpJS_JavaHarness_Testing = buildSimpleState("Testing", this.TestDumpJS_JavaHarness);
TestDumpJS_JavaHarness_Testing.entry = [TestDumpJS_JavaHarness_Testing_entry];
var TestDumpJS_JavaHarness_Failed = buildSimpleState("Failed", this.TestDumpJS_JavaHarness);
TestDumpJS_JavaHarness_Failed.entry = [TestDumpJS_JavaHarness_Failed_entry];
var TestDumpJS_JavaHarness_End = buildSimpleState("End", this.TestDumpJS_JavaHarness);
TestDumpJS_JavaHarness_End.entry = [TestDumpJS_JavaHarness_End_entry];
var t0 = new buildEmptyTransition(this._initial_TestDumpJS_JavaHarness, TestDumpJS_JavaHarness_Testing);
//State machine (transitions)
var t1 = buildTransition(TestDumpJS_JavaHarness_Testing, null, function (s, c) {var json = JSON.parse(c); return json.port === "dumpEnd_s" && json.message === "perfTestSize"});
t1.effect = [t1_effect];
var t2 = buildTransition(TestDumpJS_JavaHarness_Testing, TestDumpJS_JavaHarness_End, function (s, c) {var json = JSON.parse(c); return json.port === "dumpEnd_s" && json.message === "testEnd"});
var t3 = buildTransition(TestDumpJS_JavaHarness_Testing, null, function (s, c) {var json = JSON.parse(c); return json.port === "dump_s" && json.message === "perfTestOut"});
t3.effect = [t3_effect];
var t4 = buildTransition(TestDumpJS_JavaHarness_Testing, null, function (s, c) {var json = JSON.parse(c); return json.port === "dump_s" && json.message === "testOut"});
t4.effect = [t4_effect];
var t5 = buildTransition(TestDumpJS_JavaHarness_Testing, TestDumpJS_JavaHarness_Failed, function (s, c) {var json = JSON.parse(c); return json.port === "dump_s" && json.message === "testFailure"});
//State machine (actions on states and transitions)
function TestDumpJS_JavaHarness_Testing_entry(context, message) {
if(TestDumpJS_fs__var.existsSync('dump')) {
TestDumpJS_fs__var.unlinkSync('dump');}
if(TestDumpJS_fs__var.existsSync('cputime')) {
TestDumpJS_fs__var.unlinkSync('cputime');}
if(TestDumpJS_fs__var.existsSync('transitionsCount')) {
TestDumpJS_fs__var.unlinkSync('transitionsCount');}
}

function TestDumpJS_JavaHarness_Failed_entry(context, message) {
write("*FAILURE*");
process.exit(1);
}

function TestDumpJS_JavaHarness_End_entry(context, message) {
TestDumpJS_fs__var.appendFileSync("cputime", TestDumpJS_JavaHarness_time__var);
console.log(TestDumpJS_JavaHarness_time__var);
TestDumpJS_fs__var.appendFileSync("transitionsCount", TestDumpJS_JavaHarness_transitionsCount__var);
console.log(TestDumpJS_JavaHarness_transitionsCount__var);
process.exit(0);
}

function t1_effect(context, message) {
var json = JSON.parse(message);
TestDumpJS_JavaHarness_time__var = json.time;
TestDumpJS_JavaHarness_transitionsCount__var = TestDumpJS_JavaHarness_transitionsCount__var + 1;
}

function t3_effect(context, message) {
var json = JSON.parse(message);
TestDumpJS_JavaHarness_transitionsCount__var = TestDumpJS_JavaHarness_transitionsCount__var + 1;
}

function t4_effect(context, message) {
var json = JSON.parse(message);
write(json.c);
TestDumpJS_JavaHarness_transitionsCount__var = TestDumpJS_JavaHarness_transitionsCount__var + 1;
}

}
//Public API for lifecycle management
TestDumpJS.prototype._stop = function() {
}

//Public API for third parties
TestDumpJS.prototype._init = function() {
this.TestDumpJS_JavaHarness.initialise( this._initial_TestDumpJS_JavaHarness );
var msg = this.getQueue().shift();
while(msg != null) {
this.TestDumpJS_JavaHarness.process(this._initial_TestDumpJS_JavaHarness, msg);
msg = this.getQueue().shift();
}
this.ready = true;
}

TestDumpJS.prototype._receive = function(message) {//takes a JSONified message
this.getQueue().push(message);
if (this.ready) {
var msg = this.getQueue().shift();
while(msg != null) {
this.TestDumpJS_JavaHarness.process(this._initial_TestDumpJS_JavaHarness, msg);
msg = this.getQueue().shift();
}
}
}
TestDumpJS.prototype.getName = function() {
return "TestDumpJS";
}

var test_t = new TestDumpJS(require('fs'), 0, 0);
test_t._init();
//terminate all things on SIGINT (e.g. CTRL+C)
process.on('SIGINT', function() {
test_t._stop();
});


