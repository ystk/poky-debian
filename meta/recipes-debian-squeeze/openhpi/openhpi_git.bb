#
# debian-squeeze
#

DESCRIPTION = "OpenHPI libraries (runtime and support files)"
LICENSE = "BSD & GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=e3c772a32386888ccb5ae1c0ba95f1a4"
SECTION = "admin"
PR = "r0"

inherit autotools
inherit debian-squeeze

DEPENDS += "libtool libxml2 ncurses openssl net-snmp glib-2.0 openipmi sysfsutils"

do_configure_prepend() {
	sed -i 's:@XML2_INCLUDE@:-I'${STAGING_INCDIR}'/libxml2:' $(find -name "Makefile.am")
}
SRC_URI += "file://fix-src.patch"
