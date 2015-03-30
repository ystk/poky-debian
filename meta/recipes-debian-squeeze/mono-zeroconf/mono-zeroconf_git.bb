#
# debian-squeeze
#
DESCRIPTION = "CLI library for multicast DNS service discovery"
SECTION = "libs"
PR = "r0"
DEPENDS = "mono"
LIC_FILES_CHKSUM = "file://COPYING;md5=f1691e09690c85f44a45daa8661bc92d"
LICENSE = "GPLv2"

inherit autotools pkgconfig
inherit debian-squeeze

SRC_URI += "file://fix-make-docs.patch \
	    file://fix-parallel-make.patch"
EXTRA_OECONF = "--disable-docs"

do_configure() {
	sed -i 's:$($PKG_CONFIG --variable=libdir mono):'${STAGING_DIR_HOST}'/lib:' configure
	sed -i 's:$($PKG_CONFIG --variable=prefix mono):'${STAGING_DIR_HOST}'/usr:' configure
	sed -i 's:$(ASSEMBLY).mdb::' src/*/Makefile.*
	oe_runconf
}

