#!/bin/sh
# Set up hostname
HOSTNAME=localhost
a=$(hostname | grep none)
test -z $a || hostname $HOSTNAME 

POSTFIX_HOME=/var/spool/postfix
POSTFIX_VAR=/var/lib/postfix
POSTUSER=postfix
POSTDROP=postdrop
POSTMAP=/usr/sbin/postmap
[ -d $POSTFIX_HOME ] || mkdir -p $POSTFIX_HOME
/bin/chown -R $POSTUSER: $POSTFIX_VAR
/bin/chgrp $POSTDROP /usr/sbin/postqueue
/bin/chgrp $POSTDROP /usr/sbin/postdrop

/bin/chmod g+s /usr/sbin/postqueue
/bin/chmod g+s /usr/sbin/postdrop

# Create alias database
$POSTMAP /etc/postfix/virtual
$POSTMAP /etc/aliases

# Relink sendmail
ln -sf /usr/sbin/sendmail.postfix /usr/sbin/sendmail
