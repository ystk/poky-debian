#
# debian-squeeze
#

DESCRIPTION = "TCP/IP swiss army knife -- transitional package"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://README;md5=f1557018bf57b2ca74c68d44c03ddd91"
SECTION = "net"
PR = "r0"

inherit autotools
inherit debian-squeeze

do_install() {
	make netcat
	mkdir -p ${D}/bin
	cp netcat ${D}/bin/
}

SRC_URI += "file://fix_netcat.c.patch"
