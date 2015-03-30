#
# debian-squeeze
#

DESCRIPTION = "generic Apache request library"
SECTION = "perl"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57 \
file://README;md5=2014960921bdb192899933547cde8459 \
"

PR = "r0"

inherit debian-squeeze
inherit autotools

DEPENDS = "apr apr-util db apache2-native apache2"
LEAD_SONAME = "libapr-1.so.0"

SRC_URI += " \
file://fix-config.patch \
file://fix-code.patch \
"

EXTRA_OECONF = " \
--with-apu-config=${STAGING_BINDIR_CROSS}/apu-1-config \
--with-apr-config=${STAGING_BINDIR_CROSS}/apr-1-config \
--with-expat=${STAGING_LIBDIR}/.. \
"

LIBTOOL = ${HOST_SYS}-libtool
EXTRA_OEMAKE = "'LIBTOOL=${HOST_SYS}-libtool'"
do_configure() {
	oe_runconf
}
