#
# debian-squeeze 
#

DESCRIPTION = "Library of callable ftp routines"
HOMEPAGE = "http://nbpfaus.net/~pfau/ftplib/"
SECTION = "libs"
LICENSE = "GPLv2 & LGPLv2"
LIC_FILES_CHKSUM = "file://${DEBIAN_SQUEEZE_UNPACKDIR}/README.ftplib;md5=15d7cbed4fe9ccbd77c512b0840dc61a"

inherit debian-squeeze
inherit autotools

S = ${DEBIAN_SQUEEZE_UNPACKDIR}/linux

SRC_URI += "file://fix-compile.patch"

PREFIX = "/usr"
EXTRA_OEMAKE += "PREFIX=${PREFIX}"

# destdir is hard coded as /usr/local/*
do_unpack_srcpkg_append() {
	sed -i 's@/usr/local@$(DESTDIR)$(PREFIX)@g' ${S}/Makefile
}

do_install_prepend() {
	install -d ${D}/${PREFIX}/bin ${D}/${PREFIX}/lib ${D}/${PREFIX}/include
}
