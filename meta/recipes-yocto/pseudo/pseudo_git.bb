#
# pseudo_1.1.1.bb
#

require pseudo.inc

#PR = "r2"

#SRC_URI = "http://www.yoctoproject.org/downloads/${BPN}/${BPN}-${PV}.tar.bz2 \
#           file://oe-config.patch \
#           file://realpath_fix.patch \
#           file://static_sqlite.patch"

#SRC_URI[md5sum] = "dd59766c17e199fe6144fce8a2c67802"
#SRC_URI[sha256sum] = "c697f643577d661c3ce826504b9dcd11fa98e78a5d10e3c83931da8942f6bfad"

#
# debian-squeeze
#

inherit debian-squeeze-yocto

PR = "r0"

# git fetch: Sometimes "yacc" or "bison -y" of bison-native is required
# to compile *.c from *.y. It should be built as early as possible.
DEPENDS += "bison-native"

SRC_URI = " \
file://oe-config.patch \
file://realpath_fix.patch \
file://static_sqlite.patch \
"

PKG_SRC_VERSION = "1.0"
