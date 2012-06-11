Book hotel sample show how the mediator works.

API1 defines deifines messages of the booking hotel process between client and orginal server.

API2 defines deifines messages of the booking hotel process between client and substitute server.

bookhotel_client defines the client and fake client (statemachines and messages send and receives).

bookhotel_server defines the original server.

bookhotel_substitute_server defines a substitute server that receive and send same data with different message styles.

bookhotel_mediator defines the mediator that resolve the message mismatches between client and substitute server and 
let the communication work.

bookhotel_substitute_server2 defines another substitute server.

bookhotel_mediator2 defines the mediator that resolve the message mismatches between client and substitute server2 and 
let the communication work.

bookhotel_merge_mediator defines the mediator that merge the functions of  mediator and mediator2 and let the client 
communicate with substitute server as well as substitute server2.





