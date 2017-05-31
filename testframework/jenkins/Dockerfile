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

FROM jenkins:2.32.1
MAINTAINER Anatoly Vasilevskiy <Anatoly.Vasilevskiy@sintef.no>

#installing jenkins plugins
#COPY plugins.txt /usr/share/jenkins/plugins.txt
#COPY install_jenkins_plugins.sh /usr/local/bin/install_jenkins_plugins.sh
#RUN /usr/local/bin/install_jenkins_plugins.sh /usr/share/jenkins/plugins.txt

USER root

RUN apt-get update && apt-get install -y openssl \
						sshpass \
						openssh-server \
						git

RUN echo 'jenkins:jenkins' | chpasswd \
	&& mkdir /var/run/sshd	

#starup scritp, runs sshd and than jenkins.sh from jenkins:latest
COPY startup.sh /usr/local/bin/startup.sh
RUN chmod a+x /usr/local/bin/startup.sh

#installing docker START
ENV DOCKER_BUCKET get.docker.com
ENV DOCKER_VERSION 1.13.0
ENV DOCKER_SHA256 fc194bb95640b1396283e5b23b5ff9d1b69a5e418b5b3d774f303a7642162ad6
#ENV DOCKER_VERSION 1.12.6
#ENV DOCKER_SHA256 cadc6025c841e034506703a06cf54204e51d0cadfae4bae62628ac648d82efdd

RUN set -x \
	&& curl -fSL "https://${DOCKER_BUCKET}/builds/Linux/x86_64/docker-${DOCKER_VERSION}.tgz" -o docker.tgz \
	&& echo "${DOCKER_SHA256} *docker.tgz" | sha256sum -c - \
	&& tar -xzvf docker.tgz \
	&& mv docker/* /usr/local/bin/ \
	&& rmdir docker \
	&& rm docker.tgz \
	&& docker -v

#installing docker END

#installing maven START
ARG MAVEN_VERSION=3.3.9
ARG MAVEN_REPO_ROOT="/var/jenkins_mvn"
ARG MAVEN_M2_REPO="$MAVEN_REPO_ROOT/repo"

RUN mkdir -p /usr/share/maven /usr/share/maven/ref \
  && curl -fsSL http://apache.osuosl.org/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz \
    | tar -xzC /usr/share/maven --strip-components=1 \
  && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn

ENV M2_REPO_ROOT "$MAVEN_REPO_ROOT"
ENV MAVEN_HOME "/usr/share/maven"
ENV M2_REPO "$MAVEN_M2_REPO"
ENV PATH "$PATH:/usr/share/maven/bin"

RUN mkdir "$MAVEN_REPO_ROOT"
	#&& chown -R jenkins:jenkins "$MAVEN_REPO_ROOT"

COPY settings-docker.xml "$MAVEN_REPO_ROOT/settings.xml"

VOLUME "$MAVEN_M2_REPO"
#installing mavne END

CMD ["/usr/local/bin/startup.sh"]
