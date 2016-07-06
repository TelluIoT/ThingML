var WebSocket = require('ws');
var Format = require('.//*$FORMAT$*/');

function /*$NAME$*/(name, debug, serverURL, instance, callback) {
    this.name = name;
    this.debug = debug;
    var _this;
    this.setThis = function(__this) {
        _this = __this;
    };

    this.ready = false;

    const formatter = new Format();
    const ws = new WebSocket(serverURL);

    ws.on('open', function open() {
        callback(true);
    });

   	ws.on('message', function(data, flags) {
        const msg = formatter.parse(data);
        /*$DISPATCH$*/
   	});

    /*$RECEIVERS$*/

   	/*$NAME$*/.prototype._stop = function() {
   		ws.close();
   	};
};

module.exports = /*$NAME$*/;
