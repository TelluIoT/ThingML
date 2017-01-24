#!/bin/bash

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