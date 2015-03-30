#
#  debian-squeeze
#
DESCRIPTION = "utilities to use resources from NetWare servers" 
SECTION = "net"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=8ca43cbc842c2336e835926c2166c28b"
PR = "r0"

DEPENDS = "libpam"

inherit autotools gettext 
inherit debian-squeeze

do_configure() {
	cd ${S}
	libtoolize --force
	aclocal
	autoheader
	oe_runconf
}
