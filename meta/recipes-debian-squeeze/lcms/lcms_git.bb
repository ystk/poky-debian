#
# debian-squeeze 
#
DESCRIPTION = "Little cms is a small-footprint, speed optimized color management engine."
SECTION = "libs"
LICENSE = "MIT & GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=156745cad721a8783cb847e82b79f586"
PR = "r0"

inherit autotools
inherit debian-squeeze

AUTOTOOLS_STAGE_PKGCONFIG = "1"
LIBTOOL = ${HOST_SYS}-libtool

EXTRA_OEMAKE = "'LIBTOOL=${LIBTOOL}'"
EXTRA_OECONF = "--without-zlib \
		--with-libtool-sysroot=${STAGING_LIBDIR}"

do_configure() {
	oe_runconf
}
