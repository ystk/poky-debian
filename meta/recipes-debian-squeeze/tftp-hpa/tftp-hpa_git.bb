#
# debian-squeeze 
#

DESCRIPTION = "HPA's tftp client"
SECTION = "net"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://README;md5=b7954b490c6cc1228656351d47416153"
PR = "r0"

inherit autotools 
inherit debian-squeeze

do_configure() {
	aclocal
	oe_runconf
}
do_install() {
	oe_runmake 'DESTDIR=${D}' 'INSTALLROOT=${D}' install	
}
