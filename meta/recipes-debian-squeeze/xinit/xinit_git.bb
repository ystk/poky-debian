#
# debian-squeeze
#
DESCRIPTION = "X server initialisation tool"
LICENSE = "GPL"
LIC_FILES_CHKSUM = "file://COPYING;md5=0d4b5eef75f1584ccbdc5e4a34314407"
SECTION = "x11"
PR = "r0"

inherit debian-squeeze autotools

DEPENDS += "libx11 coreutils xauth"
