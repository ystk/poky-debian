#
# debian-squeeze
#

DESCRIPTION = "XFS Filesystem Dump Utility"
HOMEPAGE = "http://oss.sgi.com/projects/xfs"
LIC_FILES_CHKSUM = "file://README;md5=69912f4dee40f404f2dc78c10beabf69"
LICENSE = "GPL"
SECTION = "base"

PARALLEL_MAKE = ""

inherit debian-squeeze
inherit autotools

DEPENDS = "util-linux attr xfsprogs"
EXTRA_OECONF = "--enable-gettext=no"

TARGET_CC_ARCH += "${LDFLAGS}"
LIBTOOL = "${HOST_SYS}-libtool"
EXTRA_OEMAKE = "'LIBTOOL=${LIBTOOL}'"

do_configure () {
	export LIBTOOL="${STAGING_BINDIR_NATIVE}/${HOST_SYS}-libtool"
	export DEBUG="-DNDEBUG"
	oe_runconf
}

do_install () {
	export DIST_ROOT=${D}
	oe_runmake install
	rm -f ${D}${sbindir}/xfsdump
	rm -f ${D}${sbindir}/xfsrestore
	ln -s ${D}${base_sbindir}/xfsdump ${D}${sbindir}/xfsdump
	ln -s ${D}${base_sbindir}/xfsrestore ${D}${sbindir}/xfsrestore
}
