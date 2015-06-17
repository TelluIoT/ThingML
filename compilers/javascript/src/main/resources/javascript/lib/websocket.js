/*
* - thing: the instance we want to wrap into websocket
* - port: the ports used by the websocket
* - sendFilters and receiveFilters are arrays of functions, which takes 2 params
* 	- port name
*   - message name
* and return a boolean telling if the message should go forward (true) or if it should  be ignored (false)
* It basically allows to wrap a subset of the ports/messages of the thing (instance)
*/
function WebSocketWrapper(thing, port, receiveFilters, sendFilters) {

var WebSocketServer = require('websocket').server;
var WebSocketClient = require('websocket').client;
var http = require('http');

//Server
var server = http.createServer(function(request, response) {
    console.log((new Date()) + ' Received request for ' + request.url);
    response.writeHead(404);
    response.end();
});
server.listen(port, function() {
    console.log((new Date()) + ' Server is listening on port ' + port);
});

wsServer = new WebSocketServer({
    httpServer: server,
    // You should not use autoAcceptConnections for production
    // applications, as it defeats all standard cross-origin protection
    // facilities built into the protocol and the browser.  You should
    // *always* verify the connection's origin and decide whether or not
    // to accept it.
    autoAcceptConnections: false
});

function originIsAllowed(origin) {
  // put logic here to detect whether the specified origin is allowed.
  return true;
}

var clients = [];

wsServer.on('request', function(request) {
    if (!originIsAllowed(request.origin)) {
      // Make sure we only accept requests from an allowed origin
      request.reject();
      console.log((new Date()) + ' Connection from origin ' + request.origin + ' rejected.');
      return;
    }

    var connection = request.accept(null, request.origin);
	clients.push(connection);
    console.log((new Date()) + ' Connection accepted.');
    connection.on('message', function(message) {
        if (message.type === 'utf8') {
            var json;
			try {
				json = JSON.parse(message.utf8Data);
				var arrayLength = receiveFilters.length;
				for (var i = 0; i < arrayLength; i++) {
					if (receiveFilters[i][0](json.port.split("_")[0], json.message)) {
						receiveFilters[i][1]();
						console.log("Forwarded: '" + message.utf8Data + "'");
					}
				}
				var arrayLength = clients.length;
				for (var j = 0; j < arrayLength; j++) {
					clients[j].sendUTF(message.utf8Data);
				}
			} catch (e) {
				console.log("JSON: cannot parse " + message.utf8Data);
			}
        }
    });
    connection.on('close', function(reasonCode, description) {
        console.log((new Date()) + ' Peer ' + connection.remoteAddress + ' disconnected.');
		var index = clients.indexOf(connection);
		if (index > -1) {
			clients.splice(index, 1);
		}
    });
});

	//client
	var client = new WebSocketClient();
	var clientConnection = null;

	client.on('connectFailed', function(error) {
		console.log('Connect Error: ' + error.toString());
	});

	client.on('connect', function(connection) {
		clientConnection = connection;
		console.log('WebSocket client connected');
		connection.on('error', function(error) {
			console.log("Connection Error: " + error.toString());
		});
		connection.on('close', function() {
			console.log('thingml-protocol Connection Closed');
			clientConnection = null;
		});
	});

	client.connect('ws://localhost:' + port + '/', null);

	this.onMessage = function(message) {
		var json;
		try {
			var json = JSON.parse(message);
			json.thing = thing.getName();
			json.port = json.port.split("_")[0];
			var arrayLength = sendFilters.length;
			for (var i = 0; i < arrayLength; i++) {
				if (sendFilters[i](json.port, json.message)) {
					clientConnection.sendUTF(JSON.stringify(json));
					console.log("Sent: '" + message + "'");
				} else {
					console.log("Ignored: '" + message + "'");
				}
			}
		} catch (e) {
			// An error has occured, handle it, by e.g. logging it
			console.log("JSON: cannot parse " + message);
		}
	}

	this._stop = function() {
		console.log("Stopping WebSocket...");
		clientConnection.close();
		wsServer.shutDown();
		server.close();
		console.log("Stopping WebSocket... done!");
	}
}