#
# debian-squeeze 
#

DESCRIPTION = "A powerful tool for network monitoring and data acquisition" 
SECTION = "net"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1d4b0366557951c84a94fabe3529f867"
PR = "r0"

inherit autotools 
inherit debian-squeeze

SRC_URI += "file://fix-config.patch"

DEPENDS = "libpcap openssl"

# the both of them are required to exclude -I/usr/include from INCRS in Makefile
# PCAP_CONFIG: path to pcap-config
# --with-crypto: path to the directory crypto libraries are installed
EXTRA_OECONF += " \
PCAP_CONFIG=${STAGING_BINDIR_CROSS}/pcap-config \
--with-crypto=${STAGING_DIR_TARGET}/usr \
LIBS=-lnl \
"

CFLAGS += "-lpcap"

# replace hard-coded "/usr/*include/*/pcap.h"
do_unpack_append() {
	bb.build.exec_func('fix_pcap_incdir', d)
}
fix_pcap_incdir() {
	sed -i "s@/usr/.*include/\(.*pcap.h\)@${STAGING_INCDIR}/\1@" aclocal.m4
	sed -i "s@d=\"/usr/.*include/\(.*pcap\)@${STAGING_INCDIR}/\1@" aclocal.m4
}

do_configure() {
	aclocal
	autoconf
	oe_runconf
}
