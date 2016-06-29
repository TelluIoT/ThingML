# Complex Event Processing semantics in ThingML targeting C languages

__Disclaimer__:
ThingML compilers for Java and Javascript use the third-party
library [ReactiveX](http://reactivex.io). Even if ThingML vision aims for unifying
CEP this document describes how it has been implemented for C languages (C,
Arduino and C++). See the notes for main differences.


## Join Sources

### With time window

```ruby
stream joinStream @TTL "500"
from join: [t: rcvP?temp & p: rcvP?pressure -> cep()]::during 5000 by 1000
produce sendP!cep()
```

`@TTL` is optional, default value is `250`, it means that `temp` and `pressure` events are joinable
if they are received in a `500`ms time window.

The step of this stream is `1000`ms, meaning that every `1000`ms the output events, `cep` may be created
if the requirements are meant (`temp` and `pressure` events in a `500`ms time interval).

The output messages, `cep` will be saved for `5000`ms.

__Note:__ ReactiveX implementation is a bit different, the equivalent output buffer used is `buffer(5000, 1000, TimeUnit.MILLISECONDS)` you can look at their [API](http://reactivex.io/RxJava/javadoc/rx/Observable.html#buffer%28long,%20long,%20java.util.concurrent.TimeUnit%29).

### With length window

Length window allow to store a particular number of output messages, here `cep`. Once the buffer is full the number of message to remove is specified by the step of the window.

```ruby
stream joinStream @TTL "500"
from join: [t: rcvP?temp & p: rcvP?pressure -> cep()]::buffer 5 by 2
produce sendP!cep()
```

In this exemple the two oldest `cep` messages are removed from the buffer every time it reached the size of `5` messages.

__Note:__ ReactiveX implementation is a bit different, the equivalent output buffer used is `buffer(5, 2)` you can look at their [API](http://reactivex.io/RxJava/javadoc/rx/Observable.html#buffer%28int,%20int%29).

### Without any window

```ruby
stream joinStream
from join: [t: rcvP?temp & p: rcvP?pressure -> cep()]
produce sendP!cep()
```

In that case we do not want to store the output event `cep`. We store only the last message for every input events, here `temp` and `pressure`.

Every time an input message is received we check if it is joinable with the other one, meaning both are in the same time frame defined by the `TTL` in this case the default one: `250`ms.

We garanty this property over the reception time of messages.

![equation](https://raw.githubusercontent.com/AlexandreRio/ThingML/master/org.thingml.model/docs/join_time_property.png)

### Adding guards

Guards can be added to specify constaints over input and output messages in streams. For instance to filter input values you can do as follow:

```ruby
stream joinStream
from join: [t: rcvP?temp::keep if t.value > 10 &
            p: rcvP?pressure
            -> cep()
            ]::keep if true
produce sendP!cep()
```

### Event consumption policy

Considering this stream:

```ruby
stream joinStream @TTL "2000"
from join: [t: rcvP?temp & p: rcvP?pressure -> cep()]::during 1000 by 1000
produce sendP!cep()
```

If we suppose that `temp` messages are produced twice as often as `pressure` messages we may want to join the same `pressure` message with two different `temp` messages (considering their `TTL` is still valid).

You can prevent this behavior by adding an annotation to a message or to a stream, impacting every input messages, as shown in the example bellow:

```ruby
stream joinStream @TTL "2000"
from join: [t: rcvP?temp &
            p @UseOnce "False" : rcvP?pressure
            -> cep()
           ]::during 1000 by 1000
produce sendP!cep()
```

or for all messages:

```ruby
stream joinStream @TTL "2000" @UseOnce "False"
from join: [t: rcvP?temp & p: rcvP?pressure -> cep()]::during 1000 by 1000
produce sendP!cep()
```

### Input buffers

The default behavior is to store only the last message for every input events.

Considering this stream joining temperature and pressure into a new message `simple_joined`
containing both:

```ruby
stream joinStream
from join: [t: rcvP?temp & p: rcvP?pressure -> simple_joined(t.v, p.v)]
produce sendP!simple_joined(t.v, p.v)
```

If we now consider the following message sequence:

```c
t1 (22), p1 (1033), t2( 23), p2 (1024)
```

If every messages is emitted in the same interval smaller than the `TTL` the output will be the following:

```c
simple_joined(23, 1033), simple_joined(23, 1024)
```

To obtain `simple_joined(23, 1033), simple_joined(22, 1024)` you have to use both `@UseOnce` and `@Buffer` annotation, such as:

```ruby
stream joinStream @UseOnce "False" @Buffer "5"
from join: [t: rcvP?temp & p: rcvP?pressure -> simple_joined(t.v, p.v)]
produce sendP!simple_joined(t.v, p.v)
```

Input buffers can be specified at a source granularity, e.g.:

```ruby
stream joinStream
from join: [t @Buffer "20" : rcvP?temp &
            p @Buffer "10" : rcvP?pressure
            -> cep()
           ]::during 1000 by 1000
produce sendP!cep()
```

## Merge Sources

Merge streams work the same way as join streams except they work as a logical `OR` instead of a logical `AND`, meaning only one input event is needed to produce an output event. The syntax is as follow:

```ruby
stream mergeStream
from m: [ e1: sensor1?temp | e2: sensor2?temp | e3: sensor3?temp -> res]
produce sendP!res(m.value)
```

### Window and guards

Like join streams, merge streams allow the same kind of time and length window and guards having the same effects.

## Simple Sources

Simple source streams only wait for one event message, a basic syntax using a `select` statement calling a previously defined funcion is as follow:

```ruby
stream simpleStream
from s: rcvP?temp
select var a: Int = transformValue(s.value)
produce sendP!res(a)
```

### Window and guards

As every other stream simple source stream allow time and length window and also guards.

# Buffer Access

Buffer can be accessed in a stream by adding brackets `[]` after the parameter and using the `.length` operator, consider the following sum:

```ruby
function sum(values: Int16[], size: UInt8) : Int16 do
  var tmp: Int16 = 0
  var index : UInt8 = 0
  while (index < size) do
    tmp = tmp + values[index]
    index = index + 1
  end
  return tmp
end

stream aggregateM
from e : rcvPort?m::buffer 4 by 4
select var sum : Int16 = sum(e.v[], e.length)
produce sendPort!cep(sum)
```

This will store 4 `m` message, once 4 are received the sum of their `v` parameter is computed and send as a parameter of a `cep` event.

# Sharing buffers among streams

Sharing or exposing a stream buffer is not fully supported by the grammar but here is how you can do it.

First, define a stream aggregating your data:

```ruby
  message tpcomp(T : UInt8, P : UInt8)

  stream compBuffer
  from e @UseOnce "False" @Expose "CompBuffer" : dataRcv?tpcomp::buffer 3 by 1
  produce dataSend!void()
```
This will create a buffer for the last 3 ``tpcomp`` message received. Messages will be removed after 250ms, because it's the default TTL, see the Event consumption section if you want to change that.
New messages will “push” the oldest ones as they arrive, making it a sliding window of 3 elements.
Name your message whatever you want using the `@Expose` annotation.

To access it in another stream just use the name you chose followed by the name of the parameter you want. The type will an array of the datatype of the parameter.

So we can get the buffer in anothen stream using this:  `var param : UInt8[] = 'CompBufferP'`. See this full version:

```ruby
  stream join
  from e : dataRcv?tp
  select var comp : UInt8[]  = 'CompBufferP'
         var a : UInt8 = comp[0]
         var b : UInt8 = comp[1]
         var c : UInt8 = comp[2]
         var compP : UInt8 = compensate(e.P, a, b, c)
  produce dataSend!tpcomp(e.T, compP)
```

For performance issue it's better to store the buffer in a local variable before accessing it, i.e avoid the following syntax `'CompBufferP[0]`, it will work but doing it multiple time will considerably slow down your system.
