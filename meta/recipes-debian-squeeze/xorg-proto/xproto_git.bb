#
# xproto_7.0.22.bb
#

require xorg-proto-common.inc

SUMMARY = "Xlib: C Language X interface headers"

DESCRIPTION = "This package provides the basic headers for the X Window \
System."

LICENSE = "MIT & MIT-style"
LIC_FILES_CHKSUM = "file://COPYING;md5=d31ce6f4daff8f6596865ed15a354cf0"

#PR = "r0"
#PE = "1"

EXTRA_OECONF_append = " --enable-specs=no"
BBCLASSEXTEND = "native nativesdk"

#SRC_URI[md5sum] = "da0b0eb2f432b7cc1d665b05422a0457"
#SRC_URI[sha256sum] = "ad8397dd2a3de7249d2f3fb3a49444fef71483d43681285936c11911663817a8"

#
# debian-squeeze
#

DEBIAN_SQUEEZE_SRCPKG_NAME = "x11proto-core"

PR = "r0"
