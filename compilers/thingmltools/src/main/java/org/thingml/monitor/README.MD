# ThingML Tool to log event

This tool takes an annotated ThingML model as input and produces a ThingML with logging instrumentation as output.

```
java -jar thingml.jar -t monitor -o <dir where the instrumented model will be save> -s <myconfig>.thingml
```

> This tool currently **does not work when generating C code.**

> This tool only works with ThingML actions.  
Should you update a ThingML properties in an extern block (without using ThingML assigment), this won't be logged.  
Should you insert a global piece of external code, nothing in this external code will be logged.

## Annotations

Defined on Thing:

- `@monitor "properties"`: to log whenever any property has been updated through a ThingML assigment. 
- `@monitor "functions"`: to log whenever any ThingML function has been called
- `@monitor "events`: to log events.

`@monitor "event"` more precisely logs:

- consumed events *i.e.*, events that actually trigger a transition or internal event
- emitted events *i.e.*, events that are sent by the monitored thing
- discarded events *i.e.*, events that are actually received by the thing but discared as they did not trigger any transition or internal event

Defined on properties, functions or messages:

- `@monitor "not"`: to specify that this particular element should not be included in the logs. 

> Filtering out messages is currently not supported (not a big issue to fix, though).

## Logging framework

This tool will automatically generate ThingML code (platform-indepedent) that interact with a [simple logging framework available in the STL](https://github.com/TelluIoT/ThingML/blob/master/language/thingml/src/org/thingml/stl/log.thingml), which can be imported with: `import "log.thingml" from stl`.

> To avoid side effects, the source ThingML code is significantly modified, but to the best of our knowledge, its semantics should be preserved :-)

### `WithLog` fragment

The following fragment will automatically be included in a thing defining one or more `@monitor` annotation:

```
thing fragment WithLog includes LogMsgs {
	
	readonly property DEBUG_ID : String
	
	required port log @sync_send "true" {
		sends function_called, property_changed, message_lost, message_handled, message_sent
	}
	
}
```

This means that **you will have to give a value to the `DEBUG_ID` property in the configuration**. This property is used as the `inst` parameter in the messages described below.

### Logging properties

```
  /*
  * inst: name of the thing instance as defined in the configuration
  * prop_name: name of the property that has been updated
  * ty: type of the property
  * old: value before update (serialized as string)
  * new: value after update (serialized as string) 
  */
  message property_changed(inst : String, prop_name : String, ty : String, old_value : String, new_value : String)
```

### Logging functions

```
  /*
  * inst: name of the thing instance as defined in the configuration
  * fn_name: name of the invoked function
  * ty: return type of the function ("void" for no return type)
  * returns: value returned by the function ("" for no return)
  * params: list of parameters ("" for no param, "p1=v1, p2=v2, ...pn=vn")
  */
	message function_called(inst : String, fn_name : String, ty : String, returns : String, params : String)
```

### Logging consumed events

```
  message message_handled(inst : String, source : String, target : String, port_name : String, msg_name : String, params : String)
```

### Logging sent events

```
  message message_sent(inst : String, port_name : String, msg_name : String, params : String)
```

### Logging discarded events

```
  message message_lost(inst : String, port_name : String, msg_name : String, params : String)
```

## Logging "back-ends"

By default, the `log.thingml` in the STL comes with a handy console logger, printing logs directly to `stdout`. You can instantiate like this (in the genrated instrumented model):

```
configuration test {
  instance i : MyMonitoredThing
    set i.DEBUG_ID = "i" //remember to set this mandatory property
  
  instance log : ConsoleLogger
    set log.ACTIVATE_ON_STARTUP = true

  connector i.log => log.log
}
```

Other "back-ends" (really, proxies to external log back-ends) will be made available in the STL. A very experimental MQTT "back-end" is already there.
