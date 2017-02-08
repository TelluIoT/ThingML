const dgram = require('dgram');
const Format = require('.//*$FORMAT$*/');

function /*$NAME$*/(name, debug, port, address, instance, callback) {
    this.name = name;
    this.debug = debug;
    this.ready = false;

    this.port = port;
    this.address = address;

    this.formatter = new Format();
    this.server = dgram.createSocket('udp4');


    this.server.on('error', (err) => {
        console.log(`server error:\n${err.stack}`);
        this.server.close();
    });

    this.server.on('message', (msg, rinfo) => {
        console.log(`server got: ${msg} from ${rinfo.address}:${rinfo.port}`);
    });

    this.server.on('listening', () => {
        var address = this.server.address();
        console.log(`server listening ${address.address}:${address.port}`);
    });

    this.server.bind(port);

    callback(true);
}

/*$RECEIVERS$*/

/*$NAME$*/.prototype._stop = function() {
	this.server.close();
};

module.exports = /*$NAME$*/;
