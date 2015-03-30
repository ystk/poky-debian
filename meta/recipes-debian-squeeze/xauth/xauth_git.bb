#
# debian-squeeze
#
DESCRIPTION = "X authentication utility"
LICENSE = "GPL"
LIC_FILES_CHKSUM = "file://COPYING;md5=5ec74dd7ea4d10c4715a7c44f159a40b"
SECTION = "x11"
PR = "r0"

inherit debian-squeeze autotools

DEPENDS += "libx11 libxext libxau libxmu"

