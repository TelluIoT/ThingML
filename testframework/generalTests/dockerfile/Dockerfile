FROM maven:3.3.3-jdk-8
MAINTAINER Anatoly Vasilevskiy <Anatoly.Vasilevskiy@sintef.no>

RUN mkdir workingdir \
	&& apt-get -qq update \
	&& apt-get install --no-install-recommends -qqy \
	sshpass \
    curl \
    ca-certificates \
	nodejs \
	npm \
	openssh-server \
	gcc \
	make

COPY dojob.py workingdir/dojob.py
RUN chmod a+x workingdir/dojob.py
WORKDIR workingdir