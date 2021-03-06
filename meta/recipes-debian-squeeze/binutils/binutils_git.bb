#
# binutils_2.21.1a.bb
#

require binutils.inc

#PR = "r0"

LIC_FILES_CHKSUM="\
    file://src-release;endline=17;md5=4830a9ef968f3b18dd5e9f2c00db2d35\
    file://COPYING;md5=59530bdf33659b29e73d4adb9f9f6552\
    file://COPYING.LIB;md5=9f604d8a4f8e74f4f5140845a21b6674\
    file://COPYING3;md5=d32239bcb673463ab874e80d47fae504\
    file://COPYING3.LIB;md5=6a6a8e020838b23406c81b19c1d46df6\
    file://gas/COPYING;md5=d32239bcb673463ab874e80d47fae504\
    file://include/COPYING;md5=59530bdf33659b29e73d4adb9f9f6552\
    file://include/COPYING3;md5=d32239bcb673463ab874e80d47fae504\
    file://libiberty/COPYING.LIB;md5=a916467b91076e631dd8edb7424769c7\
    file://bfd/COPYING;md5=d32239bcb673463ab874e80d47fae504\
    "

#SRC_URI = "\
#     ${GNU_MIRROR}/binutils/binutils-${PV}.tar.bz2 \
#     file://binutils-uclibc-100-uclibc-conf.patch \
#     file://110-arm-eabi-conf.patch \
#     file://binutils-uclibc-300-001_ld_makefile_patch.patch \
#     file://binutils-uclibc-300-006_better_file_error.patch \
#     file://binutils-uclibc-300-012_check_ldrunpath_length.patch \
#     file://binutils-uclibc-gas-needs-libm.patch \
#     file://binutils-x86_64_i386_biarch.patch \
#     file://libtool-2.4-update.patch \
#     file://binutils-2.19.1-ld-sysroot.patch \
#     file://libiberty_path_fix.patch \
#     file://binutils-poison.patch \
#     file://libtool-rpath-fix.patch \
#     file://clone-shadow.patch \
#     file://binutils-powerpc-e5500.patch \
#     "

#SRC_URI[md5sum] = "bde820eac53fa3a8d8696667418557ad"
#SRC_URI[sha256sum] = "cdecfa69f02aa7b05fbcdf678e33137151f361313b2f3e48aba925f64eabf654"

# 2.21.1a has a mismatched dir name within the tarball
#S = "${WORKDIR}/binutils-2.21.1"

#BBCLASSEXTEND = "native"

#
# debian-squeeze
#

inherit debian-squeeze

PR = "r0"

# syslex-regenerate-fix.patch: fix compile error (git fetch)
# libiberty_path_fix.patch: fix install path of libiberty (x86_64)
SRC_URI += " \
file://syslex-regenerate-fix.patch \
file://libiberty_path_fix.patch \
"

# Temporal libtool settings
do_configure_prepend() {
	echo "libtool using compile is chenged to the one in sysroot..."

	SYSROOT_LIBTOOL="${STAGING_DIR_TARGET}/usr/bin/crossscripts/${TARGET_PREFIX}libtool"

	for i in libtool.m4 bfd/configure binutils/configure gas/configure gprof/configure ld/configure opcodes/configure
	do
		cat ${S}/${i} | \
		sed "s%\$(top_builddir)/libtool%${SYSROOT_LIBTOOL}%" \
		> ${S}/${i}.tmp

		mv ${S}/${i}.tmp ${S}/${i}
	done
}

CFLAGS += "-fPIC ${@['', '-Wno-error=unused-but-set-variable '][bb.utils.vercmp(('', bb.data.getVar('GCCVERSION', d), ''), ('', '4.7', '')) >= 0]}"

# workaround: remove invalid paths to the build directories
# from dependency_libs in .la. dependency_libs of .la in Debian package
# is also empty, so it might work fine.
do_install_append() {
	sed "s@^\(dependency_libs\)=.*@\1=''@" \
		-i ${D}${libdir}/libbfd.la ${D}${libdir}/libopcodes.la
}
