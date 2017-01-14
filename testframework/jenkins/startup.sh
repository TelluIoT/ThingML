#!/bin/bash

echo "Starting ssh daemon"
#sudo /usr/sbin/sshd -D &
/usr/sbin/sshd -D &

#checking if ssh server is running
#SSDPID=$(sudo /bin/pidof sshd)
SSDPID=$(pidof sshd)
[ $? -eq 0 ] && echo "SUCCESS: ssh daemon" || echo "FAILURE: ssh daemon"

#setting up permissions for docker.sock
if [[ -e "/var/run/docker.sock" ]] && [[ -n "$DOCKER_GID" ]]; then
	echo "Setting permissions for docker.sock"
	#sudo groupadd -g "$DOCKER_GID" docker
	#sudo usermod -aG docker jenkins
	groupadd -g "$DOCKER_GID" docker
	usermod -aG docker jenkins
	echo "Added jenkins to the docker group"
fi

#this for testing START
#sudo chown -R jenkins:jenkins /var/jenkins_home/master
#this for testing END

#su - jenkins

#running jenkins
echo "Starting jenkins: /usr/local/bin/jenkins.sh $@"
#sudo -u jenkins COPY_REFERENCE_FILE_LOG="${COPY_REFERENCE_FILE_LOG}" \
#	JENKINS_HOME="${JENKINS_HOME}" JAVA_OPTS="${JAVA_OPTS}" JENKINS_OPTS="${JENKINS_OPTS}" \
#	MASTER_SLAVE_USER="${MASTER_SLAVE_USER}" MASTER_SLAVE_PWD="${MASTER_SLAVE_PWD}" /usr/local/bin/jenkins.sh "$@"
#source /usr/local/bin/jenkins.sh "$@"
su -c "/usr/local/bin/jenkins.sh $@" jenkins
