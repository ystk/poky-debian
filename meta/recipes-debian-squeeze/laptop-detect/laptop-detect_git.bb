#
# debian-squeeze
#
DESCRIPTION = "attempt to detect a laptop"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://README;md5=3429c50b482f258afa39df3eaaf38396"
SECTION = "utils"
PR = "r0"

DEPENDS = "dmidecode "

inherit autotools
inherit debian-squeeze


do_install() {
	mkdir -p ${D}/usr/sbin
	cp ${S}/laptop-detect.in ${D}/usr/sbin/laptop-detect
	mkdir -p ${D}/usr/share/man/man8/
	cp ${S}/laptop-detect.8 ${D}/usr/share/man/man8/
	gzip -N ${D}/usr/share/man/man8/laptop-detect.8
}
	
