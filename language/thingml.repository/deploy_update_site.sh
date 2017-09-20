#!/bin/bash
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


# Deploy the update site on http://dist.thingml.org/update2
# (Only I can do it since it needs my ssh key :-))
# It also does not cleanup previous versions of the plugins...

scp -r target/repository/* franck@thingml.org:/var/www/dist/update2
scp ../../compilers/official-network-plugins/target/official-network-plugins-2.0.0-SNAPSHOT-jar-with-dependencies.jar franck@thingml.org:/var/www/dist/ThingML2CLI.jar

