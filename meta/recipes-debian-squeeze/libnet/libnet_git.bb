#
# debian-squeeze
#
DESCRIPTION = "library for the construction and handling of network packets"
HOMEPAGE = "http://libnet-dev.sourceforge.net"
SECTION = "net"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://README;md5=c00d4bcb3c17025a2b826e26f0e62c64"
PR = "r0"

inherit autotools
inherit debian-squeeze

SRC_URI += "file://fix-acinclude.patch"
