====
    Copyright (C) 2014 SINTEF <franck.fleurey@sintef.no>

    Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3, 29 June 2007;
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    	http://www.gnu.org/licenses/lgpl-3.0.txt

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
====

Kevoree
	to genearte kevoree component, please click Compile to -> Java/Kevoree.
Logger 
	to genrate logger for a configuration, please click "Compile to " -> "Logger", and find it in the same folder as this configuration.

SimpleCase 
	simple book hotel process between one client one server.

SimpleCase&Logger
	simple book hotel process between one client one server with generated logger.
	
SimpleCase&Kevoree
	simple book hotel process between one client one server with kevoree wrapper.
	
Mediator&Kevoree
	a simple client, a new server and mediator for them to communicate with each other. can be generated to kevoree.
	
Mediator&Logger
	a simple client, a new server and mediator for them to communicate with each other. with a logger generated.
	
LoggerSample
	to test if loggergenerator works with two sets of client-server that communicate with each other.
	
NewServer&Logger
	a simple client, a new server and generated logger, we can see because message mismatches, can't be logged.