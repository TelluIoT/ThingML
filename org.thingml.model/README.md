# ThingML metamodel

The ThingML metamodel is built by combining well-proven concepts and patterns found in existing modelling languages (such as the UML) and programming languages. The contribution of ThingML is to provide a single complete language with a single coherent semantics. This section presents the different parts of the ThingML metamodel.

## Model and types

<div style="text-align:center"><img src="https://raw.githubusercontent.com/SINTEF-9012/ThingML/master/org.thingml.model/docs/thingml_components.png" alt="ThingML model and types" width="600"></div>

The figure above presents the top level container of the ThingML meta-model. On the top left of the figure, the class `ThingMLModel` is the root of the containment tree and represent a ThingML file. At this level, a ThingML file can import other ThingML files and contain a set of type definitions. There are three kinds of types: primitive types, enumerations and things. The primitive types and enumeration are typical contracts found in most modelling and programming languages. The Thing type of ThingML is similar to some sort of components and/or processes in other approaches. The `Things` are the main ThingML structure and contain all other concepts of the language.

A ThingML Thing can contain a set of properties, a set of functions, a set of messages, a set of ports and a set of state machines. The `properties` are variables or constants which are local to a thing. They can be typed either by primitive types or enumeration and can be either "read only" (value calculated at compile or deployment time) or dynamic. The `functions` are local to a thing. They can be used from anywhere in a thing but are not visible from other things. The only interface of a Thing with its outside are the ports, which can send and receive a set of messages. The `messages` (and their signatures) are defined within a thing but can only be sent and received through ports. Ports can be either `required` or `provided`: this distinction is independent from the direction of messages: both required and provided ports can send and receive messages. When connecting Things together all required ports of a thing have to be connected to "compatible" provided ports. Provided ports can be left unconnected. A provided port is compatible with a required port if there is a match between the messages defined on the two ports, i.e. the provided port should, at least, be able to provide what the required port expects. 

Finally, the behaviour of a Thing is defined by a set of `state machines`. The state machines can react on events occurring on the ports of the Thing.

## State machine

![ThingML State Machines](https://raw.githubusercontent.com/SINTEF-9012/ThingML/master/org.thingml.model/docs/thingml_state_machine.png)

The figure above presents the metamodel for the ThingML `state machines`. The ThingML `state machine` includes typical state machine concepts as found for example in Unified Modeling Language 2 (UML2). The ThingML state machines can include `composite states` and `parallel regions`. The main class of the statemachine metamodel is the `State` class. Each state can contain a set of `properties`, as set of `entry` and `exit actions` and a set of `transitions`. 

The properties correspond to variables which are local to this state (and possible sub-states). The actions can be blocks of arbitrary actions which are executed upon entering and exiting the state. Transitions can be of two kinds: `internal transitions` and "regular" `transitions`. Regular transitions have a `source` and a `target` state (which can be either the same state or a different state). Transitions are contained by their source state. When a regular transition is fired, the exit action of the source state is executed, the transition is executed and the entry code of the target state is executed. In the case of an internal transition the source and target state are the same (i.e. the state containing the internal transition) and the exit and entry actions are not executed. Internal transitions are used to express "event-guard-action" rules which are local to a state but do not produce any change of state.

The triggers for transitions are `events`. In the current version of ThingML events are either incoming messages on a port or the "empty event". In addition to the event, a transition can have a `guard`. The guard corresponds to an arbitrary condition which is required for the transition to fire. In case of a transition with a `ReceiveMessage` event, the guard is evaluated whenever a message of the expected type is received on the specified port. The transition is fired if the guard is true. The guard of an empty transition is constantly evaluated and the transition is fired as soon as the guard is true. The use of empty transitions with guards should be carefully considered because of performance implications.

A `composite state` is a kind of state which can contain some sub-states. A composite state is also a region by itself and can contain a set of parallel `regions`. Each region has its own `initial` and `current` state during the execution. Events arriving to a composite state are dispatched to all its regions. Inside a region an event can only be consumed once. The semantics of the ThingML state machines is aligned with the semantics of UML2 statecharts and is specified and validated through a set of test cases which ensure that different code generators produce equivalent behaviours. 

## Action and Expression language

![Main ThingML Actions](https://raw.githubusercontent.com/SINTEF-9012/ThingML/master/org.thingml.model/docs/thingml_actions.png)

The figure above presents the main classes of the ThingML action language. In ThingML the action language is used to specify the body of functions and all the actions within the state machines (both on state and on transitions). The ThingML action language is a typical "Pascal like" imperative language. It includes basic `control structures`, `blocks` and `local variables`. It also includes `function calls` which allow calling functions defined within the Thing and a `return` action which is used in function bodies in order to return a value. `Print action` and `error action` are primitive actions which can be used to output text to the standard output and error streams. 

The `send action` and `external statement` are more specific to ThingML. The send action is a primitive to allow sending asynchronous messages though the ports of a Thing. At runtime, these messages are received as events by the Things which are connected to the port. The external action offers a mechanism to seamlessly intermix ThingML actions with actions available on the target platform and in the target language. These external statements are used to implement drivers and bridges between ThingML and the specific features and devices available on a given target platform.

The figure below presents the ThingML `expressions`. In ThingML expressions are used to express conditions and calculations within ThingML functions and state machines. The ThingML expressions include typical expressions for literals, basic arithmetic operations and use of variables and functions. There are two types of expression which are specific to ThingML: the `external expression` and the `event reference`. The event reference expression allows referring to the data contained in messages when writing the actions associated to transitions.

![ThingML Expressions](https://raw.githubusercontent.com/SINTEF-9012/ThingML/master/org.thingml.model/docs/thingml_expressions.png)

Up to this point, we have described the part of the ThingML metamodel which allow specifying `things`, describing their interfaces and implementing their behaviours. Figure 12 presents the configuration part of the ThingML metamodel which allows instantiating and connecting together a set of things in order to create an application.

## Configurations

ThingML `configurations` contain a set of `instances` and a set of `connectors`. The `ConfigInclude` class allows defining reusable groups of instances which can be shared between several applications. Instances have a name and are typed by a thing. The `ConfigPropertyAssig` class allows setting the values of the properties of the instantiated things. In case of read-only properties, the values are set only once at the initialization of the application and for regular dynamic properties, the values set in the configuration sets the initial value.

The connectors allow connecting ports of the instances together. Each connector connects a required port with a provided port. In practice, the messages exchanges on the provided and requires ports should be "compatible". At this point, messages are considered "compatible" only if the exact same message is used in both ports (with one port sending the message and the other receiving it). Messages which do not match are simply ignored.

Configurations are the entry point for the ThingML code generation framework. The ThingML compilers traverse the configuration is order to calculate the set of Things for which code should generated. For each Thing a modules is generated and these modules are instantiated and connected together according to the configuration.

![ThingML Configuration](https://raw.githubusercontent.com/SINTEF-9012/ThingML/master/org.thingml.model/docs/thingml_configuration.png)
