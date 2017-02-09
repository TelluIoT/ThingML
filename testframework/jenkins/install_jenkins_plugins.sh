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


if ! [[ -e "$1" ]]; then
	echo "Cannot find $1, no jenkins plugins to install"
	exit 0 
fi

while read PLUGIN || [[ -n "$PLUGIN" ]]; do
	if [[ -z "${PLUGIN_LIST}" ]]; then
		PLUGIN_LIST="${PLUGIN}"
	else
		PLUGIN_LIST=$(echo "${PLUGIN_LIST} ${PLUGIN}")
	fi
done < $1

echo "Plugins to install: '$PLUGIN_LIST'"
/usr/local/bin/install-plugins.sh ${PLUGIN_LIST}