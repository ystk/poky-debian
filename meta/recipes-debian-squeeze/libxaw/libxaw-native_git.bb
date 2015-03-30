#
# debian-squeeze
#
DESCRIPTION = "X11 Athena Widget library
LICENSE = "GPL"
LIC_FILES_CHKSUM = "file://COPYING;md5=5be9d425d663e0bc8bf4055ca9e64970"
SECTION = "x11"
PR ="r0"

inherit debian-squeeze autotools native
DEPENDS += "libx11-native libxext-native libxmu-native libxt-native libxpm-native"
