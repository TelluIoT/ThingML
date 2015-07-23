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
var WebSocketClient = require('websocket').client;

var IP_SERVER = '192.168.11.36';
var PORT_SERVER = '1337';
var DELAY = 1; //ms
var NB_TICK = 1000000000;
var countTicks = 0;
var client = new WebSocketClient();

client.on('connectFailed', function(error) {
    console.log('Connect Error: ' + error.toString());
});

client.on('connect', function(connection) {
    console.log('WebSocket Client Connected');

    connection.on('error', function(error) {
        console.log("Connection Error: " + error.toString());
    });

    connection.on('close', function() {
        console.log('echo-protocol Connection Closed');
    });

    function sendNumber() {
        if (connection.connected) {
            connection.send("message");
            countTicks = countTicks + 1;
        }
        
        if(countTicks == NB_TICK) {
			connection.close();
			console.log("END - All ticks sent");
		} else {
			setTimeout(sendNumber, DELAY);
		}
    }
    sendNumber();
    
});

client.connect('ws://' + IP_SERVER + ':' + PORT_SERVER + '/', 'echo-protocol');
