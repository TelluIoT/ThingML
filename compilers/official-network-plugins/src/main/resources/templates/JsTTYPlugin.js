var Format = require('.//*$FORMAT$*/');

function /*$NAME$*/(name, debug, port, baudrate, instance) {
    this.name = name;
    this.debug = debug;
    var _this;
    this.setThis = function(__this) {
        _this = __this;
    };

    this.ready = false;

    const stdin = process.stdin;
    const stdout = process.stdout;
    const formatter = new Format();

    stdin.on('data', function(received) {
        const msg = formatter.parse(received);
        /*$DISPATCH$*/
    });

    stdin.on('error', function(err) {
        console.log("Error during communication: " + err);
    });

    /*$RECEIVERS$*/

    /*$NAME$*/.prototype._stop = function() {
        this.ready = false;
        
	};
};

module.exports = /*$NAME$*/;
