ThingML
=======

This is a fork of [ThingML repository](http://github.thingml.org/)
I modify compilers to manage CEP.

# Current features

The default stream behavior is to print an "Hack" message and forward the event.  
You can transform some event reception to stream with an annotation on transition (or internal).
You have some examples in the ```Examples``` folder.

- Basic stream in JS
- Merged streams in JS

# Try it yourself!

- clone the repository
- ```mvn clean install```
- ```cd org.thingml.editor.standalone```
- ```mvn exec:java -Dexec.mainClass=org.sintef.thingml.ThingMLApp```

The result of compilation is in ```[TEMP Directory]/ThingML_temp```. (For example, on Linux : ```/tmp/ThingML_temp```).
