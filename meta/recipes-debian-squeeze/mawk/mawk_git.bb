#
# debian-squeeze
#
DESCRIPTION = "a pattern scanning and text processing language"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=8ca43cbc842c2336e835926c2166c28b"
SECTION = "utils"
PR = "r0"

inherit autotools
inherit debian-squeeze

do_install() {
	cd ${S}
	mkdir -p ${D}/usr/local/bin
	mkdir -p ${D}/usr/local/man/man1/mawk.1
	oe_runmake install DESTDIR=${D}
}

SRC_URI += "file://fix_Makefile.patch"

FILES_${PN} += "/usr/local/*"
