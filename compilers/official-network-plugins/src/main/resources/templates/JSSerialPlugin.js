var SerialPort = require('serialport2').SerialPort;
var Format = require('.//*$FORMAT$*/');

function /*$NAME$*/(name, root, debug, port, baudrate) {
    this.name = name;
    this.root = root;
    this.debug = debug;
    var _this;
    this.setThis = function(__this) {
        _this = __this;
    };

    this.ready = false;

    //message queue
    const queue = [];
    this.getQueue = function() {
    return queue;
    };

    /*$PORTS$*/

    var port = new SerialPort();
    var formatter = new /*$FORMAT$*/();
    var START = 0x12;
    var STOP = 0x13;
    var ESCAPE = 0x7D;

    port.on('data', function(data) {
        console.log("data: " + data);
        /*$PARSER$*/
    });

    port.on('error', function(err) {
        console.log("Error during communication: " + err);
    });

    port.open(port, {
        baudRate: baudrate,
        dataBits: 8,
        parity: 'none',
        stopBits: 1},
        function(err) {
            if (err) {
                console.log("Error while opening serial port " + port + " at baudrate " + baudrate);
                port.close();
            } else {//Serialize and send already waiting messages on the serial port
                var msg = this.getQueue().shift();
                while(msg !== undefined) {
                    /*$SERIALIZER$*/
                }
                ready = true;
            }
        }
    );

    this.write = function(payload) {

    };

    /*$FORWARD$*/

    /*$NAME$*/.prototype._receive = function() {
        this.getQueue.push(arguments);
        if (this.ready) {
            var msg = this.getQueue().shift();
            while(msg !== undefined) {
                /*$SERIALIZER$*/
            }
        }
    }
};

modules.export = /*$NAME$*/;
