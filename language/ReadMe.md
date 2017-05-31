# ThingML Model

 * **ThingMLModel**: Root node of the model.

 ---

 * **Type**: (Abstract) Types are used to defines everything that can be instantiated, primitive datatypes as variable, objects, and components (also called Thing).

 * **Protocol**: Protocol are used to named external components, that will not lead to code generation by compilers. They may however be used by plugins. Typically used for network transmissions. Additional information must be provided with annotation in most cases.

 * **Configuration**: Describes the liste of component instances and their inter connections.

 ---

 * **PlatformAnnotation**: Additional information passed to the compiler. (@key "value)
 * **NamedElement**: (Abstract) Element that can be named
 * **AnnotatedElement**: (Abstract) Element that can be annotated.
 * **Variable**: (Abstract) Regroups all local variables, parameters, properties

 ---

### Types

 * **TypeRef**: Declares the type of a variable.
 * **PrimitiveType**: Container for plateform types. (Allows to link types from differents plateforms).
 * **ObjectType**: Container for complex types such as classes, pointers, structs...
 * **Enumeration**: Type that consist of a set of named values.

 ---

### Things

 * **Thing**: The thing is a central concept in ThingML. It models a component that can be instanciated in a configuration. It may include a statemachine, ports, messages, properties, functions. It can be a whole thing or a fragment. A fragment is a thing that can not be instanciated but rather be included in another thing.
 * **PropertyAssign**: Assign a value to a property.
 * **Function**: Declare a function for the thing.
 * **AbstractFunction**: Declare a function that will be implemented in another thing that includes the current one.
 * **Property**: Declare a variable for the whole instance of a thing.
 * **Message**: Declare the model of a message with its eventual parameters. It can then be sent or received over a port. Things needs to include ever messages that they use.
 * **Parameter**: Variable for function and messages.
 * **Port**: (Abstract) Describes potential communication of a thing. List messages that can be sent and/or received.
 * **RequiredPort**: (If possible thing that depends on other are instantiated after their dependancies.)
 * **ProvidedPort**: (If possible thing that depends on other are instantiated after their dependancies.)
 * **InternalPort**: Describes communications from a thing to itself (Can be used to send messages from a session to another).

 ---

### State Machines

 * **State**: Describes a state, action that will be triggered at the entry and exit of the state, and the set of transitions (which doesn't need to be exhaustive. Event not mentionned will simply not be taken into account). 
 * **Handler**: (Abstract) 
 * **Transition**: Describe a transition from one state to another (can be the same). Can be triggered automatically or by a given event.
 * **Internal Transition**: Special form of transition from one state to itself that do not trigger entry actions, nor exit actions.
 * **CompositeState**: Sub state machine contained in a state.
 * **StateMachine**: Finite state machine.
 * **Session**: Fork of a sub state machine.
 * **Region**: Describes a parallel group of state. The state machine is simultaneously in a state inside the region, and in another outside.
 * **FinalState**: State from which it can't be any outgoing transition.
 * **StateContainer**: (Abstract)

 ---

 * **Event**: Event allowiing to trigger a transition.
 * **ReceiveMessage**: Message reception event.

 ---

 * **Action**: (Abstract)
 * **ActionBlock**: Defines a group of action (executed sequentially).
 * **ExternStatement**: Statement expressed in plateform code (ie**: c, java, javascript...). ex: `'printf("Hello World);'`
 * **LocalVariable**: Variable defined for an Action block.
 * **SendAction**: Sending of a message on a port with given parameters.
 * **VariableAssignment**: Assignement of a value/instance to a variable.
 * **Increment**: Add 1 to the value of a variable.
 * **Decrement**: Substract 1 to the value of a variable.
 * **LoopAction**: While loop.
 * **ConditionalAction**: If (/else) statement
 * **ReturnAction**: return Expression
 * **PrintAction**: Send an expression (String) to the standard output.
 * **ErrorAction**: Send an expression (String) to the error output.
 * **StartSession**: Instantiate a given session.
 * **FunctionCallStatement**: Call a function.

 ---

 * **Expression**: (Abstract)
 * **CastExpression**: (TypeRef) Expression
 * **OrExpression**: Expression or Expression
 * **AndExpression**: Expression and Expression
 * **Equality**: Expression == Expression
 * **Comparaison**: Expression (>,<,>=,<=) Expression
 * **Addition**: Expression (+,-) Expression
 * **Multiplication**: Expression (*,/) Expression
 * **Modulo**: Expression % Expression
 * **Primary**: (not,-)Expression
 * **ArrayIndexPostfix**: Expression[Expression]
 * **AtomicExpression**: (Abstract)
 * **ExternExpression**: Expression expressed in plateform language. ex: `'1 < 2'`
 * **EnumLiteralRef**
 * **IntegerLiteral** ex: `42`
 * **BooleanLiteral**: (True, False)
 * **StringLiteral**: ex: `"my string"`
 * **DoubleLiteral** ex: `0.3E-2`, `3.14`, `10e5`
 * **EnumerationLiteral**
 * **PropertyReference**
 * **EventReference**
 * **FunctionCallExpression**

 ---

### Configurations

 * **Instance**: Instance of a given thing.
 * **ConfigPropertyAssign**: Set the value of the property of a given instance.
 * **AbstractConnector**: (Abstract)
 * **Connector**: Connection between two port of two instances. (Provided => Required)
 * **ExternalConnector**: Connection between a port of an instance and a protocol.
