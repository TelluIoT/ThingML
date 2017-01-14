FROM jenkins:2.32.1
MAINTAINER Anatoly Vasilevskiy <Anatoly.Vasilevskiy@sintef.no>

USER root

RUN apt-get update && apt-get install -y openssl \
						sshpass \
						openssh-server \
						git \
						vim

RUN echo 'jenkins:jenkins' | chpasswd \
	&& mkdir /var/run/sshd	

#starup scritp, runs sshd and than jenkins.sh from jenkins:latest
COPY startup.sh /usr/local/bin/startup.sh
RUN chmod a+x /usr/local/bin/startup.sh

#installing docker START
ENV DOCKER_BUCKET get.docker.com
#ENV DOCKER_VERSION 1.12.3
#ENV DOCKER_SHA256 626601deb41d9706ac98da23f673af6c0d4631c4d194a677a9a1a07d7219fa0f
ENV DOCKER_VERSION 1.12.6
ENV DOCKER_SHA256 cadc6025c841e034506703a06cf54204e51d0cadfae4bae62628ac648d82efdd

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
ARG USER_HOME_DIR="$JENKINS_HOME"

RUN mkdir -p /usr/share/maven /usr/share/maven/ref \
  && curl -fsSL http://apache.osuosl.org/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz \
    | tar -xzC /usr/share/maven --strip-components=1 \
  && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn

ENV MAVEN_HOME /usr/share/maven
ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"
ENV PATH "$PATH:/usr/share/maven/bin"

#COPY mvn-entrypoint.sh /usr/local/bin/mvn-entrypoint.sh
COPY settings-docker.xml /usr/share/maven/ref/

#VOLUME "$USER_HOME_DIR/.m2"
#installing mavne END

CMD ["/usr/local/bin/startup.sh"]