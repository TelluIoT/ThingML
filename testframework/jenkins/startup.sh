#!/bin/bash -e
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


echo "Starting ssh daemon"
/usr/sbin/sshd -D &

#checking if ssh server is running
SSDPID=$(pidof sshd)
[ $? -eq 0 ] && echo "SUCCESS: ssh daemon" || echo "FAILURE: ssh daemon"

#setting up permissions for docker.sock
if ! grep -q docker /etc/group; then
  if [[ -e "/var/run/docker.sock" ]] && [[ -n "$DOCKER_GID" ]] ; then
  	echo "Setting permissions for docker.sock"
  	groupadd -g "$DOCKER_GID" docker
  	usermod -aG docker jenkins
  	echo "Added jenkins to the docker group"
  fi
else
  echo "docker group already exists"
fi

#setting up maven START
echo "Setting up MAVEN"
if ! [[ -d "${JENKINS_HOME}/.m2" ]]; then
  echo "Creating .m2 directory in ${JENKINS_HOME}"
  mkdir "${JENKINS_HOME}/.m2"
  chown -R jenkins:jenkins "${JENKINS_HOME}/.m2"
fi

if ! [[ -e "${JENKINS_HOME}/.m2/settings.xml" ]]; then
  echo "Copying ${M2_REPO_ROOT}/settings.xml to ${JENKINS_HOME}/.m2"
  cp "${M2_REPO_ROOT}/settings.xml" "${JENKINS_HOME}/.m2/settings.xml"
  chown jenkins:jenkins "${JENKINS_HOME}/.m2/settings.xml"
fi

chown -R jenkins:jenkins "$M2_REPO_ROOT"
#setting up maven END

#running jenkins
echo "Starting jenkins: /usr/local/bin/jenkins.sh $@"
chown -R jenkins:jenkins "$JENKINS_HOME"
su -c "/usr/local/bin/jenkins.sh $@" jenkins
