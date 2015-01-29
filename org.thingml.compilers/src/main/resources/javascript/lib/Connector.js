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
};

module.exports = Connector;
