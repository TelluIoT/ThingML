const Format = require('.//*$FORMAT$*/');

function /*$NAME$*/(name, debug, instance, callback) {
    this.name = name;
    this.debug = debug;
    this.ready = false;

    if (process.stdin.isTTY && process.stdout.isTTY) {
        this.stdin = process.stdin;
        this.stdout = process.stdout;
        this.formatter = new Format();

        this.stdin.on('data', function(received) {
            const msg = this.formatter.parse(received);
            /*$DISPATCH$*/
        }.bind(this));

        this.stdin.on('error', function(err) {
            console.log("Error during communication: " + err);
        });
        callback(true);
    } else {
        callback(false);
    }
};

/*$RECEIVERS$*/

/*$NAME$*/.prototype._stop = function() {
	this.stdin.pause();
    this.ready = false;
};

module.exports = /*$NAME$*/;
