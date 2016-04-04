# Complex Event Processing Semantics in ThingML

## Join Sources

### With time window

```ruby
stream simpleSource @TTL "500"
from join: [t: rcvP?temp & p: rcvP?pressure -> cep()]::during 5000 by 1000
produce sendP!cep()
```

`@TTL` is optional, default value is `250`, it means that `temp` and `pressure` events are joinable
if they are received in a `500`ms time window.

The step of this stream is `1000`ms, meaning that every `1000`ms the output events, `cep` may be created
if the requirements are meant (`temp` and `pressure` events in a `500`ms time interval).

The output messages, `cep` will be saved for `5000`ms.

### Without time window

```ruby
stream simpleSource
from join: [t: rcvP?temp & p: rcvP?pressure -> cep()]
produce sendP!cep()
```

In that case we do not want to store the output event `cep`. We store only the last message for every input events, here `temp` and `pressure`.

Every time an input message is received we check if it is joinable with the other one, meaning both are in the same time frame defined by the `TTL` in this case the default one: `250`ms.

We garanty this property over the reception time of messages.

![equation](https://github.com/AlexandreRio/ThingML/tree/master/org.thingml.model/docs/join_time_property.png)

### Event consumption policy

Considering this stream:

```ruby
stream simpleSource @TTL "2000"
from join: [t: rcvP?temp & p: rcvP?pressure -> cep()]::during 1000 by 1000
produce sendP!cep()
```

If we suppose that `temp` messages are produced twice as often as `pressure` messages we may want to join the same `pressure` message with two different `temp` messages (considering their `TTL` is still valid).

You can prevent this behavior by adding an annotation to a message or to a stream, impacting every input messages, as shown in the example bellow:

```ruby
stream simpleSource @TTL "2000"
from join: [t: rcvP?temp &
            p: rcvP?pressure @UseOnce "True"
            -> cep()
           ]::during 1000 by 1000
produce sendP!cep()
```

or for all messages:

```ruby
stream simpleSource @TTL "2000" @UseOnce "True"
from join: [t: rcvP?temp & p: rcvP?pressure -> cep()]::during 1000 by 1000
produce sendP!cep()
```

### Input buffers

The default behavior is to store only the last message for every input events.

Considering this stream joining temperature and pressure into a new message `simple_joined`
containing both:

```ruby
stream simpleSource
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
stream simpleSource @UseOnce "True" @Buffer "5"
from join: [t: rcvP?temp & p: rcvP?pressure -> simple_joined(t.v, p.v)]
produce sendP!simple_joined(t.v, p.v)
```
