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
var Server = require('./Server');
var Receiver = require('./Receiver');
var serverCfg_c = new Server();
serverCfg_c.setThis(serverCfg_c);
serverCfg_c.build();
var serverCfg_r = new Receiver();
serverCfg_r.setThis(serverCfg_r);
serverCfg_r.build();
serverCfg_c.getM1onserviceListeners().push(serverCfg_r.receivem1Onservice.bind(serverCfg_r));
serverCfg_c._init();
serverCfg_r._init();
//terminate all things on SIGINT (e.g. CTRL+C)
process.on('SIGINT', function() {
console.log("Stopping components... CTRL+D to force shutdown");
serverCfg_r._stop();
serverCfg_c._stop();
});

/** SERVEUR **/
var WebSocketServer = require('websocket').server;
var http = require('http');

var util = require('util');

var server = http.createServer(function(request, response) {
    console.log((new Date()) + ' Received request for ' + request.url);
    response.writeHead(404);
    response.end();
});

server.listen(1337, function() {
    console.log((new Date()) + ' Server is listening on port 1337');
});

wsServer = new WebSocketServer({
    httpServer: server
});

wsServer.on('request', function(request) {
    var connection = request.accept('echo-protocol', request.origin);
    console.log((new Date()) + ' Connection accepted.');

    connection.on('message', function(message) {
            //console.log('Received Message: ' + message.utf8Data);
            /** call here the Thing API to send a message **/
            serverCfg_r.receivecommandOnservice();
            serverCfg_r.receivecommand2Onservice();
    });

    connection.on('close', function(reasonCode, description) {
        console.log((new Date()) + ' Peer ' + connection.remoteAddress + ' disconnected.');
    });
});
