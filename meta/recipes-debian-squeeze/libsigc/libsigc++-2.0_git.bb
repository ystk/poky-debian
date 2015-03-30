#
# debian-squeeze
#
DESCRIPTION = "type-safe Signal Framework for C++ - runtime"
LICENSE = "LGPLv2.1"
LIC_FILES_CHKSUM = "file://COPYING;md5=d8045f3b8f929c1cb29a1e3fd737b499"
PR = "r0"

inherit autotools 
inherit debian-squeeze
S=${WORKDIR}/${PN}-${PV}
do_configure() {
	oe_runconf --host=${HOST_SYS}
}
do_install_prepend() {
   sed -i "s:dependency_libs=.*:dependency_libs=\' ${STAGING_DIR_HOST}/usr/lib/libstdc++.la\':" ${S}/sigc++/.libs/libsigc-2.0.lai
}

