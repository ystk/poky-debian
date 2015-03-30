#
# xfsprogs/xfsprogs_3.1.2.bb
# This recipe is imported from OpenEmbedded
# Commit: 86ff745847ebfcfb77730851b72a7d576422c660
#

DESCRIPTION = "Utilities for managing the XFS filesystem"
SECTION = "admin" 
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://README;md5=d00b0f506c5763c4a46971406a809c2e"
PR = "r0"
DEPENDS = "util-linux e2fsprogs ossp-uuid"

#
# debian-squeeze
#

inherit autotools
inherit debian-squeeze

FILES_${PN}-dev += "${base_libdir}/libhandle.la \
                    ${base_libdir}/libhandle.so"
EXTRA_OECONF = "--enable-gettext=no"

do_configure () {
	export DEBUG="-DNDEBUG"
	oe_runconf
}

LIBTOOL = "${HOST_SYS}-libtool"
EXTRA_OEMAKE = "'LIBTOOL=${LIBTOOL}'"
TARGET_CC_ARCH += "${LDFLAGS}"
PARALLEL_MAKE = ""

do_install () {
	export DIST_ROOT=${D}
	oe_runmake install
	# needed for xfsdump
        oe_runmake install-dev
        # replace extra links to /usr/lib with relative links (otherwise autotools_prepackage_lamangler fails to read nonexistent link)
        rm -f ${D}/${base_libdir}/libhandle.la
        rm -f ${D}/${base_libdir}/libhandle.a
        ln -s ../usr/lib/libhandle.la ${D}/${base_libdir}/libhandle.la
        ln -s ../usr/lib/libhandle.a ${D}/${base_libdir}/libhandle.a

        # and link from /usr/lib/libhandle.so to /lib/libhandle.so
        rm -f ${D}/${libdir}/libhandle.so
        ln -s ../../lib/libhandle.a ${D}/${libdir}/libhandle.so
}
