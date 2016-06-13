var SerialPort = require('serialport').SerialPort;
var ByteBuffer = require("bytebuffer");
var Format = require('.//*$FORMAT$*/');

function /*$NAME$*/(name, debug, port, baudrate, instance) {
    this.name = name;
    this.debug = debug;
    var _this;
    this.setThis = function(__this) {
        _this = __this;
    };

    this.ready = false;

    const serial = new SerialPort(port, {baudrate: baudrate});
    const formatter = new Format();
    const START_BYTE = 0x12;
    const STOP_BYTE = 0x13;
    const ESCAPE_BYTE = 0x7D;
    var bb = new ByteBuffer(capacity=256, littleEndian=false);
    var buffer_idx = 0;

    const RCV_WAIT = 0;
    const RCV_MSG = 1;
    const RCV_ESC = 2;
  	var state = RCV_WAIT;


    serial.on('data', function(received) {
        received.forEach(function(data) {
            if (state == RCV_WAIT) { // it should be a start byte or we just ignore it
                if (data == START_BYTE) {
                    state = RCV_MSG;
                    buffer_idx = 0;
                    bb = new ByteBuffer(capacity=256, littleEndian=false);
                }
            } else if (state == RCV_MSG) {
                if (data == ESCAPE_BYTE) {
                    state = RCV_ESC;
                } else if (data == STOP_BYTE) {
					const trimBB = new ByteBuffer(capacity=buffer_idx+1, littleEndian=false);
					bb.flip();
					var i = 0;
					while(i < buffer_idx) {
						trimBB.writeByte(bb.readByte());
						i = i + 1
					}
					trimBB.flip();
                    const msg = formatter.parse(trimBB);
                    if (msg !== null && msg !== undefined) {
                        /*$DISPATCH$*/
                    }
                    state = RCV_WAIT;
                } else if (data == START_BYTE) {
                    // Should not happen but we reset just in case
                    state = RCV_MSG;
                    buffer_idx = 0;
                    bb = new ByteBuffer(capacity=256, littleEndian=false);
                } else { // it is just a byte to store
                    bb.writeByte(data);
                    buffer_idx++;
                }
            } else if (state == RCV_ESC) {
                // Store the byte without looking at it
                bb.writeByte(data);
                buffer_idx++;
                state = RCV_MSG;
            }
        });
    });

    serial.on('error', function(err) {
        console.log("Error during communication: " + err);
    });

    /*$RECEIVERS$*/

    /*$NAME$*/.prototype._stop = function() {
        this.ready = false;
        serial.flush();
        serial.drain();
		serial.close(function(error){
			console.log("Something went wrong when closing serial port " + port + " at " + baudrate + ":\n\t" + error);
		});
	};
};

module.exports = /*$NAME$*/;
