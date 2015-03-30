#
# debian-squeeze
#

DESCRIPTION = "Shared memory library - development files"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=0ba10c36898067a75f550bfa27a47b8b"
SECTION = "devel"
PR = "r0"

LIBTOOL = "${HOST_SYS}-libtool"
EXTRA_OEMAKE = "'LIBTOOL=${LIBTOOL}'"

inherit autotools
inherit debian-squeeze

do_configure() {
	autoreconf
	oe_runconf
	echo "#define MM_SHM_MAXSEGSIZE 32768" >> config.h
}
SRC_URI += "file://fix_config.patch"
