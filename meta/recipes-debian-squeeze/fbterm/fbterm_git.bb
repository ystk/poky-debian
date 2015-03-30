#
# debian-squeeze
#

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d8e20eece214df8ef953ed5857862150"

PR = "r0"

inherit debian-squeeze autotools

DEPENDS = "fontconfig freetype"

RDEPENDS = "libfontconfig"
