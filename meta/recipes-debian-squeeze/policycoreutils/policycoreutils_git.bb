#
# debian-squeeze
#

DESCRIPTION = "SELinux core policy utilities"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=393a5ca445f6965873eca0259a17f833"
SECTION = "utils"
PR = "r0"

inherit autotools
inherit debian-squeeze

DEPENDS += "libsepol libselinux libsemanage python audit libpam file"

# some Makefiles use DESTDIR as the path to library and include
# directories used while compiling, so DESTDIR must be set to paths
# to the sysroots only in do_compile.
do_compile_prepend() {
	# for run_init/Makefile
	export setlib="${STAGING_LIBDIR}"
	export setinc="${STAGING_INCDIR}"
	# for other Makefiles
	export DESTDIR="${STAGING_DIR_HOST}"
}

SRC_URI += "file://fix-make.patch "

FILES_${PN} += "${libdir}/python2.6/"
