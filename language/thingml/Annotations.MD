# List of officially-supported annotations

## @arduino_stdout

- *description*: Specifies the stdout device e.g. Serial
- *applies to*: Function, 

## @arduino_stdout_baudrate

- *description*: Specifies the baudrate for the (Serial) stdout device e.g. 9600
- *applies to*: Function, 

## @arduino_type

- *description*: Specifies the concrete Arduino type (if different from the c_type) of a ThingML-defined type.
- *applies to*: Type, 

## @c_compiler

- *description*: Specifies an alternative compiler to be used to compile the generated C code e.g. clang
- *applies to*: Configuration, 

## @c_global

- *description*: Adds a standalone fragment of code into the generated code for this Thing.
- *applies to*: Thing, 

## @c_header

- *description*: Adds a header (.h) to the generated code for this Thing.
- *applies to*: Thing, 

## @c_type

- *description*: Specifies the concrete C type of a ThingML-defined type.
- *applies to*: Type, 

## @checker_should_fail

- *description*: Used in the Checker tests. Specifies whether or not the checker should fail.
- *applies to*: Thing, 

## @code

- *description*: Sets a code to a given message. Used by serializers to identify messages.
- *applies to*: Message, 

## @compiler

- *description*: Specifies the compiler to be used by the auto compiler
- *applies to*: Configuration, 

## @docker

- *description*: Generates a Dockerfile for this configuration
- *applies to*: Configuration, 

## @docker_jre

- *description*: Alternative JRE to be used in the docker image
- *applies to*: Configuration, 

## @docker_js

- *description*: Alternative JS engine to be used in the docker image e.g. chakra
- *applies to*: Configuration, 

## @fork_linux_thread

- *description*: Makes this function to run in a separate thread.
- *applies to*: Function, 

## @fork_thread

- *description*: Makes this function to run in a separate thread.
- *applies to*: Function, 

## @go_compiler

- *description*: Specifies an alternative compiler to be used to compile the generated Go code e.g. gccgo
- *applies to*: Configuration, 

## @go_import

- *description*: Imports a Go package into the generated code for this Thing.
- *applies to*: Thing, 

## @go_type

- *description*: Specifies the concrete Go type of a ThingML-defined type.
- *applies to*: Type, 

## @html_body

- *description*: Content to be appended to HTML <body>, when using Browser compiler
- *applies to*: Configuration, 

## @html_head

- *description*: Content to be appended to HTML <head>, when using Browser compiler
- *applies to*: Configuration, 

## @ignore

- *description*: Ignores specific warnings raised by the checker
- *applies to*: AnnotatedElement, 

## @java_features

- *description*: Adds a standalone fragment of code into the generated code for this Thing.
- *applies to*: Thing, 

## @java_import

- *description*: Adds an import to the generated code for this Thing.
- *applies to*: Thing, 

## @java_interface

- *description*: Makes this Thing to implement that interface.
- *applies to*: Thing, 

## @java_type

- *description*: Specifies the concrete Java type of a ThingML-defined type.
- *applies to*: Type, 

## @java_visibility

- *description*: Changes the visibility of this function.
- *applies to*: Function, 

## @js_dep

- *description*: Adds an NPM dependecy to the generated package.json file.
- *applies to*: Type, 

## @js_include

- *description*: Includes a JS resource e.g. from a CDN or from a local file.
- *applies to*: Type, 

## @js_type

- *description*: Specifies the concrete JavaScript type of a ThingML-defined type.
- *applies to*: Type, 

## @maven_dep

- *description*: Adds a Maven dependency to the generated pom.xml file.
- *applies to*: Type, 

## @mock

- *description*: Generates a graphical mock-up for this thing.
- *applies to*: Thing, 

## @monitor

- *description*: Generates monitoring instrumentation in a PIM way
- *applies to*: AnnotatedElement, 

## @override

- *description*: Marks this function as @Override.
- *applies to*: Function, 

## @stdout_sync

- *description*: Syncs prints to stdout
- *applies to*: Configuration, Thing, 

## @sync_send

- *description*: Makes a port syncrhonous
- *applies to*: Port, 

## @type_checker

- *description*: Specifies the abstract type of a ThingML-defined primitive type. Used by the type checker.
- *applies to*: Type, 


