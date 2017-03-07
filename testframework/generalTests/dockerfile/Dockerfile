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

FROM maven:3.3.3-jdk-8
MAINTAINER Anatoly Vasilevskiy <Anatoly.Vasilevskiy@sintef.no>

RUN mkdir workingdir \
	&& apt-get -qq update \
	&& apt-get install --no-install-recommends -qqy \
	sshpass \
	curl \
	ca-certificates \
	openssh-server \
	gcc \
	make

RUN curl -sL https://deb.nodesource.com/setup_7.x | bash - \
	&& apt-get install --no-install-recommends -qqy nodejs \
	build-essential

COPY dojob.py workingdir/dojob.py
RUN chmod a+x workingdir/dojob.py
WORKDIR workingdir
