#
# debian-squeeze
#
DESCRIPTION = "Heimdal"
SECTION = "net"
PR = "r0"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3d5aedd8ac19c7dcdfd994a75948e57e"

PARALLEL_MAKE = ""
inherit autotools native
inherit debian-squeeze
SRC_URI += "file://fix-code.patch \
        file://fix-make.patch"
 
DEPENDS = " "
EXTRA_OECONF = "--with-sqlite3=${STAGING_DIR_NATIVE}/usr \
		--with-sqlite3-lib=${STAGING_LIBDIR_NATIVE} \
		--with-sqlite3-include=${STAGING_INCDIR_NATIVE} "

PARALLEL_MAKE = ""

do_install_append() {
	install -m 0755 ./lib/asn1/.libs/asn1_compile ${D}${STAGING_BINDIR_NATIVE}
	install -m 0755 ./lib/sl/.libs/slc ${D}${STAGING_BINDIR_NATIVE}
}
