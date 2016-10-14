var WebSocket = require('ws');
var Format = require('.//*$FORMAT$*/');

function /*$NAME$*/(name, debug, serverURL, instance, callback) {
    this.name = name;
    this.debug = debug;
    this.ready = false;

    this.formatter = new Format();
    this.ws = new WebSocket(serverURL);

    this.ws.on('open', function open() {
        callback(true);
    });

   	this.ws.on('message', function(data, flags) {
        const msg = this.formatter.parse(data);
        /*$DISPATCH$*/
   	}.bind(this));
};

/*$RECEIVERS$*/

/*$NAME$*/.prototype._stop = function() {
	this.ws.close();
};

module.exports = /*$NAME$*/;
