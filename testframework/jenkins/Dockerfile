FROM jenkins:latest
MAINTAINER Anatoly Vasilevskiy <Anatoly.Vasilevskiy@sintef.no>

USER root

RUN apt-get update && apt-get install -y openssl \
						sshpass \
						openssh-server \
						sudo \
						vim

RUN echo 'jenkins:jenkins' | chpasswd \
	#&& echo "jenkins  ALL=(ALL:ALL) NOPASSWD:ALL" >> /etc/sudoers \
	&& mkdir /var/run/sshd	

#starup scritp, runs sshd and than jenkins.sh from jenkins:latest
COPY startup.sh /usr/local/bin/startup.sh
RUN chmod a+x /usr/local/bin/startup.sh

#installing docker
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

#this test code
#RUN mkdir /var/jenkins_home/master
#WORKDIR /var/jenkins_home/master

#COPY compilers ./compilers
#COPY testJar ./testJar
#COPY testframework ./testframework

#RUN chown -R jenkins:jenkins /var/jenkins_home/master
#test code end

#USER jenkins

CMD ["/usr/local/bin/startup.sh"]