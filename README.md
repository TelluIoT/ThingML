ThingML
=======

ThingML is a modelling language for embedded and distributed systems (Internet of Things).
It is developed by the Networked Systems and Services department of SINTEF in Oslo, Norway.
It is distributed under the LGPL licence.

## Documentation

### [ThingML Metamodel](https://github.com/SINTEF-9012/ThingML/blob/master/org.thingml.model/README.md)

### [ThingML Code Generation Framework](https://github.com/SINTEF-9012/ThingML/blob/master/compilers/README.md)

## CEP

### Some constraints
Currently, the language allow to do a few of features that are not supported by the compiler. You should respect all the constraintes below. We cannot guarantee the result (probably an exception will be thrown by the ThingML compiler) of a code that does not respect them.

Some of them could be managed by the compiler as a new feature in a future version.

**SourceComposition:** when we declare a source composition, all of them should be a receive message (a simple source). Moreover, all the messages must have the same footprint, __i.e__ same number of parameters with the same type. Finally, you cannot mix a merge with a join. Thus, the three streams in the following example declaration are not correct:
```
stream incorrect do
  from mge : [ [recvPort?m1 | recvPort?m2 -> m2m3(#0,#1,#2)] | recvPort?m3 -> m2m3(#0,#1,#2)]
  action sendPort!m2m3()      
end 

message m5();
message m6(val : Integer);
stream incorrect2 do
  from mge : [ recvPort?m5 | recvPort?m6 -> m2m3(#0)]
  action sendPort!m2m3()
end

stream incorrect3 do
  from mge : [ [recvPort?m2 | recvPort?m3 -> m2m3(#0,#1,#2)] & recvPort?m2 -> m2m3()]
  action sendPort!m2m3()
end
```
The result message (the message wich is defined after the "->") and the output message must be the same. Thus, the following code is not correct:
```
stream incorrect do
  from mge : [ recvPort?m1 | recvPort?m2 -> m3()]
  action sendPort!m4() 
end
```

**StreamParamRef:** A stream parameter reference (#i, where i is the index of a message paramter) should be ONLY in a **select** clause or in the specification of source composition result message creation. Therefore, the following code is not correct:
```
function foo(param: Integer) do
  var a : Integer = #1
  [...]
end
```

**Window operator:** a window operator must be the last operators of a source declaration. So, the following code is not correct:
```
stream incorrect do
  from mge : [ recvPort?m1 | recvPort?m2 -> m3()]::lengthWindow(5)::filter(filterFunction(mge))
  action sendPort!m4() 
end
```

**ArrayParamRef:** An array parameter reference (eventRef.param[]) should be in a stream declaration, with a window operator. Thus, the following code is not correct:
```
message message1(param1 : Integer[]);

operator fooOp(msg : message1) : Boolean do
  print msg.param1[]
  [...]
end

stream incorrect do
  from e : [port?message1]
  select a : e.param1[]
  action sendPort!m2(a)
end
```

> Visit [thingml.org](http://www.thingml.org) to find out more about ThingML !
