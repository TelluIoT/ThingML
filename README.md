ThingML
=======

ThingML is a modelling language for embedded and distributed systems (Internet of Things).
It is developed by the Networked Systems and Services department of SINTEF in Oslo, Norway.
It is distributed under the LGPL licence.

Visit http://www.thingml.org to find out more about ThingML!

## [ThingML Metamodel](https://github.com/SINTEF-9012/ThingML/blob/master/org.thingml.model/README.md)

## [ThingML Code Generation Framework](https://github.com/SINTEF-9012/ThingML/blob/master/compilers/README.md)

Visit http://www.thingml.org to find out more about ThingML !

# ThingML and Complex Event Processing
This repository contains my contribution on ThingML. During my 6 months internship, I will study Complex Event Processing (CEP) and the concepts that we can add to ThingML. I choose [ReactiveX](http://reactivex.io/) to implement CEP.

# What is it possible now? (on June 2nd)

## JS Compiler
A complete sample with all the notion is available in the `Example` folder : `cepSamples.thingml`

To create a stream, I have introduced 3 new keywords : `select`, `from` and `action`.
- The `select` keyword allows to choose which event values, or a new value derived from event(s) value(s), can be sent by the streams.
- The `from` keyword allows to define the stream sources, i.e an event : a message and a port.
- The `action` keyword allows to define the output event (port + message) with optinal parameters. These parameters must be declare in the `select` part. 

Currently, we can define 3 kind of streams : 
- **Simple Stream** : it is a stream based on one event. When the event is received, the output event is sent.
- **Merged streams** : it is a stream based on at least 2 event. The streams send a message every time an event (whatever its source) is received. The different sources are separated by a `|`. The event parameters are automatically map to the send message. So, all the messages of events sources and the message of the output event must have the same footprint. For exemple, if there is a message *m1* declared like this : `message m1(v1 : Integer, v2 : Char)` wich is merged with a message *m2* (source) to produce a message *cep1* (output message), then *m2* and *cep1* must have a first parameter with type `Integer` and a second parameter with type `Char`. Currently, when we declare a merged stream it is not possible to include `select` option.
- **Joined streams** : it is a stream based on exactly 2 sources. The different sources are separated by a `&`. The output event is send if a message of each source is received in a time windows (hard coded, currently is 50ms, but it is possible to modify it in the generated code)

# Next features
- [ ] Manage `select` option for merged streams
- [ ] Manage joined streams with more than 2 sources
- [ ] Manage the time to wait during joined streams

