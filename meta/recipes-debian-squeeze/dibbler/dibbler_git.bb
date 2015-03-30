#
# debian-squeeze 
#
DESCRIPTION = " portable DHCPv6"
SECTION = "admin" 
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=94d55d512a9ba36caa9b7df079bae19f"
PR = "r0"
DEPENDS = "libselinux readline"

inherit autotools 
inherit debian-squeeze

SRC_URI += "file://fix-makefile.patch"

LIBTOOL = "${HOST_SYS}-libtool"
EXTRA_OEMAKE = "'LIBTOOL=${LIBTOOL}'" 
