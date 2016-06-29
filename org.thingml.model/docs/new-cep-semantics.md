# New ThingML semantics for Complex Event Processing

**Disclaimer**: This document describes features that are not and probably will never be implemented into the ThingML framework, for the actual Complex Event Processing features usage please refer to the according [documentation](cep-semantics.md).

---

## Stream as buffers

The current approach is to work on 1 or many sources of data and apply an operation, merge or join, on all of them.
It can be then reduced with the `select` operator and then produce 1 output message. This production can be delayed by “one output every X input” (Length Window) or “possibly one output every X ms” (Time Window).

New proposed approach is to make `select` and `produce` optional

```ruby
stream aggregate
from e : rcvPort?msg::buffer 10 by 1
```

This will make a sliding window of 10 elements but the step of the window (here 1) does not make sense since we do not want to produce any output the step part should also be optional. The final syntax would then be:

```ruby
stream aggregate
from e : rcvPort?msg::buffer 10
```

## Exposing buffers

Current approach to access the buffer of another stream is very _hacky_. It works with C macro, you have to add an annotation to an input message such as `@Expose 'aggMsg'` and then use it as a target embedded code such as `var array : UInt8[] = 'aggMsgParam'` (notice the parameter name added after the name we exposed).

Proposed approach is to handle that natively in ThingML and not through annotation. All buffers should be accessed using the following syntax `stream_name.input_message.parameter_name`. Accessing the buffer of the `aggregate` stream described above would look like:

```ruby
stream comp
from m : rcvPort?stuff
select var array : UInt8[] = aggregate.e.Param
produce sendPort!msg(avg(array))
```

## More flexibility in operations

As stated before, ThingML can only perform 3 operations, that is fine but the constraints are too strong, only one operation can be perform at a time on a stream.
The operations are the following:

* Simple source, not really an operation, it concerns streams with only 1 source
* Merge source, works on from 2 to any sources, perform a logical OR over all the inputs, _i.e_ a message of every input sources have to be received before being able to produce,
* Join source, same as merge source but with a logical AND.

New grammar would allow mixing operators:

```ruby
stream op
from j : [j1 : rcvData?m1 & j2 : rcvData?m2], s : rcvData?m3
...
```

This would make a stream joining 2 events: `m1` and `m2` and aggretating messages from a third types: `m3`.

This new approach raise a new problem, how do we know what triggers the output?

### Using annotation

```ruby
stream op
from j @Trigger : [j1 : rcvData?m1 & j2 : rcvData?m2], s : rcvData?m3
...
```

Putting a `@Trigger` on one and only one of the input sources (of any type) could specify which one is used to trigger the output.

### Using native grammar

...

# More generally, documenting

A formal definition of the semantics of each streams should be defined. Using a general algebra for example, this would allow to precisely define the scope of the CEP domain in ThingML.
This would allow to “easily” compare the CEP features provided by ThingML to those of traditional CEP engines and query languages.

_Demers, A., Gehrke, J., Hong, M., Riedewald, M., & White, W. (2005). A General Algebra and Implementation for Monitoring Event Streams._
