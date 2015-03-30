#!/bin/sh -e

PASSWD=$1
[ -n "$PASSWD" ] || { echo "usage: $0 <passwd>"; exit 1; }

SALT=$(openssl rand -base64 6)
openssl passwd -1 -salt $SALT $PASSWD
