const WebSocketServer = require('ws').Server,
	wss = new WebSocketServer({ port: /*$PORT$*/ /*$PROTOCOL$*/});

wss.on('connection', (ws) => {
  ws.on('message', (message) => {
	wss.clients.forEach((client) => {
		if (client !== ws)
			client.send(message);
	});
  });
});
