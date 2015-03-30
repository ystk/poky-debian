#
# debian-squeeze
#
DESCRIPTION = "Lightweight and easy-use Mandatory Access Control for Linux"
SECTION = "admin"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING.ccs;md5=751419260aa954499f7abaabaa882bbe"
PR = "r0"

DEPENDS += "ncurses readline"
inherit autotools
inherit debian-squeeze
do_configure_prepend() {
	sed -i 's:CC=gcc::' Makefile
}
SRC_URI += "file://fix_make.patch"

FILES_${PN}-dbg += "${libdir}/ccs/.debug"
FILES_${PN} += "${libdir}/ccs"
