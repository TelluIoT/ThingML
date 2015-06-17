function RESTWrapper(thing, port, receiveFilters, sendFilters, remoteURL, remotePort) {
	var express = require( 'express' ); //Web framework
	var http = require('http');
	var bodyParser = require('body-parser')


	//Create server
	var app = express();
	http.createServer(app).listen(port, function() {
		console.log( 'Express server listening on port %d in %s mode', port, app.settings.env );
	});
	// parse application/x-www-form-urlencoded
	app.use(bodyParser.urlencoded({ extended: false }))

	// parse application/json
	app.use(bodyParser.json())

	//Router
	var arrayLength = receiveFilters.length;
	for (var i = 0; i < arrayLength; i++) {
		var callback = receiveFilters[i][1];
		app.get(receiveFilters[i][0], function( request, response ) {
			callback(/*params*/);
			response.send("OK!");
		});
	}
	///////////////Just for test (when posting on local host)///////////////////////
	arrayLength = sendFilters.length;
	for (var i = 0; i < arrayLength; i++) {
		app.post(sendFilters[i][0], function( request, response ) {
			console.dir(request.body);
			response.send("Received! " + JSON.stringify(request.body, null, 2));
		});
	}
	///////////////END Just for test (when posting on local host)///////////////////////

	this.onMessage = function(message) {
		var json;
		try {
			var json = JSON.parse(message);
			json.thing = thing.getName();
			json.port = json.port.split("_")[0];
			var arrayLength = sendFilters.length;
			for (var i = 0; i < arrayLength; i++) {
				if (sendFilters[i][1](json.port, json.message)) {
					var options = {
						host: remoteURL,
						port: remotePort,
						path: sendFilters[i][0],
						method: 'POST',
						headers: {
							'Content-Type': 'application/json',
							'Content-Length': Buffer.byteLength(message)
						}
					};
					var req = http.request(options, function(res) {
						console.log('STATUS: ' + res.statusCode);
						console.log('HEADERS: ' + JSON.stringify(res.headers));
						res.setEncoding('utf8');
						res.on('data', function (chunk) {
							console.log('BODY: ' + chunk);
						});
					});
					req.write(message);
					req.end();
					console.log("Sent: '" + message + "'");
				} else {
					console.log("Ignored: '" + message + "'");
				}
			}
		} catch (e) {
			console.log(e);
		}
	}
}
