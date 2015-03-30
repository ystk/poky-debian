#
# debian-squeeze
#

DESCRIPTION = "Common files for SELinux policy management libraries."
LICENSE = "GPLv2 & LGPL"
LIC_FILES_CHKSUM = "file://COPYING;md5=a6f89e2100d9b6cdffcea4f398e37343"
SECTION = "libdevel"
PR = "r0"

DEPENDS += "ustr bzip2 libsepol libselinux"

inherit autotools debian-squeeze

do_compile_prepend() {
	export setlib="${STAGING_LIBDIR}"
	export INCLUDEDIR=${STAGING_INCDIR}
}
SRC_URI += "file://fix_make.patch"
