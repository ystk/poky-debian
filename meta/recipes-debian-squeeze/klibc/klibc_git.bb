#
# debian-squeeze
#

LICENSE = "BSD & GPL"
LIC_FILES_CHKSUM = "file://usr/klibc/LICENSE;md5=d75181f10e998c21eb147f6d2e43ce8b"

PR = "r0"

inherit debian-squeeze autotools

# patches for newer kernel
SRC_URI = " \
file://add-aligned-64.patch \
file://bury-kernel-nlink-t.patch \
"

DEPENDS += "virtual/kernel"

KLIBC_ARCH = '${TARGET_ARCH}'
KLIBC_ARCH_armeb = 'arm'
KLIBC_ARCH_mipsel = 'mips'
KLIBC_ARCH_x86 = 'i386'
KLIBC_ARCH_i486 = 'i386'
KLIBC_ARCH_i586 = 'i386'
KLIBC_ARCH_i686 = 'i386'
KLIBC_ARCH_pentium = 'i386'

EXTRA_OEMAKE = " \
KLIBCARCH=${KLIBC_ARCH} \
CROSS_COMPILE=${TARGET_PREFIX} \
KLIBCKERNELSRC=${STAGING_DIR_TARGET}/usr \
INSTALLROOT=${D} \
V=1 \
"

PACKAGES = " \
lib${PN} \
lib${PN}-dev \
lib${PN}-doc \
${PN}-utils \
"

FILES_lib${PN} = " \
${base_libdir}/klibc*.so \
"

FILES_lib${PN}-dev = " \
${libdir}/klibc/lib/* \
${libdir}/klibc/include/* \
${bindir}/klcc \
"

FILES_lib${PN}-doc = " \
/usr/man/man1/klcc.1 \
"

FILES_${PN}-utils = " \
${libdir}/klibc/bin/* \
"

#
# special pacakge for initramfs
#

PACKAGES += "${PN}-initramfs"

# includes only symlinks to ${libdir}/klibc/bin/* except klcc
RDEPENDS_${PN}-initramfs += "lib${PN} ${PN}-utils"
FILES_${PN}-initramfs = "${bindir}/*"

KLIBC_INITRAMFS_LINKS = " \
fstype \
ipconfig \
kinit \
kinit.shared \
minips \
nfsmount \
nuke \
resume \
run-init \
sh.shared \
"

# NOTE: Broken links to ${libdir}/klibc/bin/* are removed by mkinitramfs.
# Relative paths must be used here.
do_install_append() {
	for bin in ${KLIBC_INITRAMFS_LINKS}; do
		ln -sf ../lib/klibc/bin/$bin ${D}${bindir}
	done
}
