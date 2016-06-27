var WebSocketServer = require('ws').Server,
	wss = new WebSocketServer({ port: /*$PORT$*/ });

wss.on('connection', function connection(ws) {
  ws.on('message', function incoming(message) {
	wss.clients.forEach(function each(client) {
		if (client !== ws)
			client.send(message);
	});
  });
});
