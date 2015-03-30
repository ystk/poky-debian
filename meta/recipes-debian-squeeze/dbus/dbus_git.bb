#
# dbus_1.4.12.bb
#

include dbus.inc
#SRC_URI[md5sum] = "104f2ea94c10a896dfb1edecb5714cb1"
#SRC_URI[sha256sum] = "da3c97fd546610558d588799e27c4fa81101e754acbcd34747a42c131f30dbe7"

#
# debian-squeeze
#

inherit debian-squeeze

PR = "r0"

SRC_URI = " \
file://dbus-1.init \
"

EXTRA_OEMAKE += 'CFLAGS="-Os"'
