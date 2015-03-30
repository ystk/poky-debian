#
# debian-squeeze
#
DESCRIPTION = "X11 Athena Widget library
LICENSE = "GPL"
LIC_FILES_CHKSUM = "file://COPYING;md5=5be9d425d663e0bc8bf4055ca9e64970"
SECTION = "x11"
PR ="r0"

inherit debian-squeeze autotools
DEPENDS += "libx11 libxext libxmu libxt libxpm"
