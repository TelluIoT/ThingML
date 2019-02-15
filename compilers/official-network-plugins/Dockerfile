#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
# See the NOTICE file distributed with this work for additional
# information regarding copyright ownership.
#

#Get the latest version of ThingML (from master) and compile it
FROM maven:3-jdk-8-slim

COPY ./src/main/resources/settings.xml settings.xml

RUN apt-get update && apt-get install -y git-core && rm -rf /var/lib/apt/lists/*

RUN git clone --depth 1 https://github.com/TelluIoT/ThingML.git

RUN cd ThingML && mvn -s ../settings.xml -DskipTests clean install

FROM openjdk:8-jre-slim

COPY --from=0 /ThingML/compilers/official-network-plugins/target/*-jar-with-dependencies.jar thingml.jar

RUN chmod +x thingml.jar


ENTRYPOINT ["java", "-jar", "thingml.jar"]
CMD ["-h"]
