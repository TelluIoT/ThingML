====
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

    See the NOTICE file distributed with this work for additional
    information regarding copyright ownership.
====

This creates an OSGi bundle with the ThingML compilers.

It includes all the code of the compilers and its dependencies. 

But it EXCLUDES the ThingML models, the XTEXT parser and everything else which is built in the /language folder.

The point of this bundle is to be used as a dependency in the ThingML UI Eclipse plug-in. 

The magic line in the POM to decide what is included and what is not is this one:

`<Embed-Dependency>*;scope=compile|runtime;artifactId=!org.eclipse.*|thingml</Embed-Dependency>`

And the associated documentation is here: 
http://felix.apache.org/documentation/subprojects/apache-felix-maven-bundle-plugin-bnd.html


*IMPORTNANT Note regarding the versioning of the bundle*

By default, the bundle is created with the same version as the maven version and "SNAPSHOT" is kept as "SNAPSHOT" and not replaced
with a timestamp. When creating the update site in the "thingml.repository" project, the version of the SNAPSHOT is not replaced either.
this causes the update site to contain the compiler with a version called "SNAPSHOT" and not incremented when rebuilding. This leads
to eclipse not updating the compilers and everything is broken...

The solution is to set the version of the bundle with the fully qualified timestamp:

<Bundle-Version>2.0.0.${timestamp}</Bundle-Version>

This doesn not change anything in terms of eclipse but puts the timestamp version in the MANIFEST of the bundle. When the p2 repository is
created, it uses this version number.

The down side is that we have to edit this version manually when upgrading maven version.
