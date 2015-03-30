#
# debian-squeeze 
#
DESCRIPTION = "SELinux runtime shared libraries"

HOMEPAGE =  "http://packages.debian.org/source/sid/libs/libselinux"

LICENSE = "GPLv2 & LGPLv2.1"
LIC_FILES_CHKSUM = "file://LICENSE;md5=84b4d2c6ef954a2d4081e775a270d0d0"

inherit autotools
inherit debian-squeeze


SRC_URI = "file://fix_makefile.patch"
DEPENDS += "libsepol file"

# INCLUDEDIR and LIBDIR must be overwritten ONLY for do_compile;
# we cannot use EXTRA_OEMAKE for overwriting LIBDIR because
# it's shared by do_compile and do_install. The default value of
# LIBDIR ($(DESTDIR)/usr/include) is correct.
do_compile_prepend() {
	export INCLUDEDIR=${STAGING_INCDIR}
	export LIBDIR=${STAGING_LIBDIR}
}
