#List of officially-supported annotations

##@c_global

- *description*: Adds a standalone fragment of code into the generated code for this Thing.
- *applies to*: Thing, 

##@c_header

- *description*: Adds a header (.h) to the generated code for this Thing.
- *applies to*: Thing, 

##@c_type

- *description*: Specifies the concrete C type of a ThingML-defined type.
- *applies to*: Type, 

##@code

- *description*: Sets a code to a given message. Used by serializers to identify messages.
- *applies to*: Message, 

##@docker

- *description*: Generates a Dockerfile for this configuration
- *applies to*: Configuration, 

##@fork_linux_thread

- *description*: Makes this function to run in a separate thread.
- *applies to*: Function, 

##@fork_thread

- *description*: Makes this function to run in a separate thread.
- *applies to*: Function, 

##@go_type

- *description*: Specifies the concrete Go type of a ThingML-defined type.
- *applies to*: Type, 

##@java_features

- *description*: Adds a standalone fragment of code into the generated code for this Thing.
- *applies to*: Thing, 

##@java_import

- *description*: Adds an import to the generated code for this Thing.
- *applies to*: Thing, 

##@java_interface

- *description*: Makes this Thing to implement that interface.
- *applies to*: Thing, 

##@java_type

- *description*: Specifies the concrete Java type of a ThingML-defined type.
- *applies to*: Type, 

##@java_visibility

- *description*: Changes the visibility of this function.
- *applies to*: Function, 

##@js_dep

- *description*: Adds an NPM dependecy to the generated package.json file.
- *applies to*: Type, 

##@js_include

- *description*: Includes a JS resource e.g. from a CDN or from a local file.
- *applies to*: Type, 

##@js_type

- *description*: Specifies the concrete JavaScript type of a ThingML-defined type.
- *applies to*: Type, 

##@maven_dep

- *description*: Adds a Maven dependency to the generated pom.xml file.
- *applies to*: Type, 

##@override

- *description*: Marks this function as @Override.
- *applies to*: Function, 

##@sync_send

- *description*: Makes a port syncrhonous
- *applies to*: Port, 

##@type_checker

- *description*: Specifies the abstract type of a ThingML-defined primitive type. Used by the type checker.
- *applies to*: PrimitiveType, 
