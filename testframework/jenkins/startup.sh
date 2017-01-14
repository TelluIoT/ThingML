#!/bin/bash -e

echo "Starting ssh daemon"
/usr/sbin/sshd -D &

#checking if ssh server is running
SSDPID=$(pidof sshd)
[ $? -eq 0 ] && echo "SUCCESS: ssh daemon" || echo "FAILURE: ssh daemon"

#setting up permissions for docker.sock
if [[ -e "/var/run/docker.sock" ]] && [[ -n "$DOCKER_GID" ]]; then
	echo "Setting permissions for docker.sock"
	groupadd -g "$DOCKER_GID" docker
	usermod -aG docker jenkins
	echo "Added jenkins to the docker group"
fi

#setting up maven START
set -o pipefail

# Copy files from /usr/share/maven/ref into ${MAVEN_CONFIG}
# So the initial ~/.m2 is set with expected content.
# Don't override, as this is just a reference setup
copy_reference_file() {
  local root="${1}"
  local f="${2%/}"
  local logfile="${3}"
  local rel="${f/${root}/}" # path relative to /usr/share/maven/ref/
  echo "$f" >> "$logfile"
  echo " $f -> $rel" >> "$logfile"
  if [[ ! -e ${MAVEN_CONFIG}/${rel} || $f = *.override ]]
  then
    echo "copy $rel to ${MAVEN_CONFIG}" >> "$logfile"
    mkdir -p "${MAVEN_CONFIG}/$(dirname "${rel}")"
    cp -r "${f}" "${MAVEN_CONFIG}/${rel}";
  fi;
}

copy_reference_files() {
  local log="$MAVEN_CONFIG/copy_reference_file.log"
  touch "${log}" || (echo "Can not write to ${log}. Wrong volume permissions?" && exit 1)
  echo "--- Copying files at $(date)" >> "$log"
  find /usr/share/maven/ref/ -type f -exec bash -eu -c 'copy_reference_file /usr/share/maven/ref/ "$1" "$2"' _ {} "$log" \;
}

export -f copy_reference_file
copy_reference_files
#setting up maven END


#running jenkins
echo "Starting jenkins: /usr/local/bin/jenkins.sh $@"
su -c "/usr/local/bin/jenkins.sh $@" jenkins
