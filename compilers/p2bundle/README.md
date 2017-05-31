This project shows how to create a p2 repository containing the ThingML compilers and all dependencies as OSGi components.

It is currently not used in the building of the ThingML eclipse plugins but may be handy at some point in the future.

To build the p2 repository run:

mvn p2:site

To deploy a local server:

mvn jetty:run

Detailed documentation for how this works is here:

https://github.com/reficio/p2-maven-plugin
