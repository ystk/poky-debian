#
# debian-squeeze
#
DESCRIPTION = "Commandline tool to perform operations on tagged collections"
LICENSE = "GPLv2 & LGPLv2.1"
SECTION = "lib/devel"
LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"
PR = "r0"
inherit debian-squeeze autotools native
DEPENDS += "libwibble-native"
