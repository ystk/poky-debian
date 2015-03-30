DESCRIPTION = "SELinux policy compiler"
SECTION = "utils"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=393a5ca445f6965873eca0259a17f833"

inherit autotools debian-squeeze

DEPENDS += " libsepol libselinux"

# DESTDIR must be set to path to the sysroot only in do_compile, see Makefile
do_compile_prepend() {
	if [ -n "${STAGING_DIR_HOST}" ]; then
		export DESTDIR="${STAGING_DIR_HOST}"
	else
		export DESTDIR="${STAGING_DIR_NATIVE}"
	fi
}

BBCLASSEXTEND = "native"
