const WebSocket = require('ws');
const Format = require('.//*$FORMAT$*/');

function /*$NAME$*/(name, debug, serverURL, instance) {
    this.name = name;
    this.debug = debug;
    this.ready = false;

    this.formatter = new Format();
    this.ws = new WebSocket(serverURL/*$PROTOCOL$*/);

    this.ws.on('open', () => {
        /*$CALLBACK$*/
    });

   	this.ws.on('message', (data, flags) => {
        const msg = this.formatter.parse(data);
        /*$DISPATCH$*/
   	});
}

/*$RECEIVERS$*/

/*$NAME$*/.prototype._stop = function() {
	this.ws.close();
};

module.exports = /*$NAME$*/;
