#!/bin/bash

# Deploy the update site on http://dist.thingml.org/update2
# (Only I can do it since it needs my ssh key :-))
# It also does not cleanup previous versions of the plugins...

scp -r target/repository/* franck@thingml.org:/var/www/dist/update2

