#
# eglibc.inc
#

require eglibc-common.inc
require eglibc-ld.inc

STAGINGCC = "${@['gcc-cross-intermediate', 'gcc-linaro-cross-intermediate']['gcc-linaro-cross-intermediate' in bb.data.getVar('PREFERRED_PROVIDER_virtual/${TARGET_PREFIX}gcc-intermediate', d, True)]}"
STAGINGCC_virtclass-nativesdk = "${@['gcc-crosssdk-intermediate', 'gcc-linaro-crosssdk-intermediate']['gcc-linaro-cross-intermediate' in bb.data.getVar('PREFERRED_PROVIDER_virtual/${TARGET_PREFIX}gcc-intermediate', d, True)]}"
PATH_prepend = "${STAGING_BINDIR_TOOLCHAIN}.${STAGINGCC}:"

TOOLCHAIN_OPTIONS = " --sysroot=${STAGING_DIR_TCBOOTSTRAP}"

# siteconfig.bbclass runs configure which needs a working compiler
# For the compiler to work we need a working libc yet libc isn't 
# in the sysroots directory at this point. This means the libc.so
# linker script won't work as the --sysroot setting isn't correct.
# Here we create a hacked up libc linker script and pass in the right
# flags to let configure work. Ugly.
EXTRASITECONFIG = "CFLAGS='${CFLAGS} -Wl,-L${WORKDIR}/site_config_libc -L${WORKDIR}/site_config_libc -L${SYSROOT_DESTDIR}${libdir} -L${SYSROOT_DESTDIR}${base_libdir} -Wl,-L${SYSROOT_DESTDIR}${libdir} -Wl,-L${SYSROOT_DESTDIR}${base_libdir}'"
siteconfig_do_siteconfig_gencache_prepend = " \
	mkdir -p ${WORKDIR}/site_config_libc; \
	cp ${SYSROOT_DESTDIR}${libdir}/libc.so ${WORKDIR}/site_config_libc; \
	sed -i -e 's# ${base_libdir}# ${SYSROOT_DESTDIR}${base_libdir}#g' -e 's# ${libdir}# ${SYSROOT_DESTDIR}${libdir}#g' ${WORKDIR}/site_config_libc/libc.so; \
"

# nptl needs unwind support in gcc, which can't be built without glibc.
DEPENDS = "virtual/${TARGET_PREFIX}gcc-intermediate linux-libc-headers"
# nptl needs libgcc but dlopens it, so our shlibs code doesn't detect this
#RDEPENDS_${PN} += "${@['','libgcc']['nptl' in '${GLIBC_ADDONS}']}"
PROVIDES = "virtual/libc virtual/${TARGET_PREFIX}libc-for-gcc"
PROVIDES += "virtual/libintl virtual/libiconv"
inherit autotools
require eglibc-options.inc

LEAD_SONAME = "libc.so"

GLIBC_EXTRA_OECONF ?= ""
GLIBC_EXTRA_OECONF_virtclass-nativesdk = ""
INHIBIT_DEFAULT_DEPS = "1"

ARM_INSTRUCTION_SET = "arm"

# eglibc uses PARALLELMFLAGS variable to pass parallel build info so transfer
# PARALLEL_MAKE into PARALLELMFLAGS and empty out PARALLEL_MAKE
EGLIBCPARALLELISM := "PARALLELMFLAGS="${PARALLEL_MAKE}""
EXTRA_OEMAKE += ${EGLIBCPARALLELISM}
PARALLEL_MAKE = ""

OE_FEATURES = "${@features_to_eglibc_settings(d)}"
do_configure_prepend() {
	sed -e "s#@BASH@#/bin/sh#" -i ${S}/elf/ldd.bash.in
	echo '${OE_FEATURES}' > ${B}/option-groups.config
}

#
# debian-squeeze
#

# Default march is "i386", but some C sources require functions
# only in i486 or higher sysdeps.
CFLAGS_append_virtclass-nativesdk += " ${@base_conditional('HOST_ARCH', 'i686', '-march=${HOST_ARCH}', '', d)}"
