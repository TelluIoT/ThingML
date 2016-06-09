var mqtt = require('mqtt');
var Format = require('.//*$FORMAT$*/');

function /*$NAME$*/(name, debug, serverURL, topic, instance) {
    this.name = name;
    this.debug = debug;
    var _this;
    this.setThis = function(__this) {
        _this = __this;
    };

    this.ready = false;

    const formatter = new Format();
    const client = mqtt.connect(serverURL);

    client.on('connect', function open() {
        client.subscribe(topic);
    });

   	client.on('message', function(topic, message) {
   	    console.log("topic: " + topic + ", message: " + message);
        const msg = formatter.parse(message);
        /*$DISPATCH$*/
   	});

    /*$RECEIVERS$*/

   	/*$NAME$*/.prototype._stop = function() {
   		client.end();
   	};
};

module.exports = /*$NAME$*/;
