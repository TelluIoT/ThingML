var mqtt = require('mqtt');
var Format = require('.//*$FORMAT$*/');

function /*$NAME$*/(name, debug, serverURL, subtopic, pubtopic, instance, callback) {
    this.name = name;
    this.debug = debug;
    this.ready = false;

    this.formatter = new Format();
    this.client = mqtt.connect(serverURL);

    this.client.on('connect', function open() {
        this.client.subscribe(subtopic);
        callback(true);
    }.bind(this));

   	this.client.on('message', function(topic, message) {
        const msg = this.formatter.parse(message);
        /*$DISPATCH$*/
   	}.bind(this));
};

/*$RECEIVERS$*/

/*$NAME$*/.prototype._stop = function() {
	this.client.end();
};

module.exports = /*$NAME$*/;
