#
# grub_0.97.bb
#

SUMMARY = "GRUB is the GRand Unified Bootloader"
DESCRIPTION = "GRUB is a GPLed bootloader intended to unify bootloading across x86 \
operating systems. In addition to loading the Linux kernel, it implements the Multiboot \
standard, which allows for flexible loading of multiple boot images."
HOMEPAGE = "http://www.gnu.org/software/grub/"
SECTION = "bootloaders"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=c93c0550bd3173f4504b2cbd8991e50b \
                    file://grub/main.c;beginline=3;endline=9;md5=22a5f28d2130fff9f2a17ed54be90ed6"

#RDEPENDS_${PN} = "diffutils"
#PR = "r4"

#SRC_URI = "ftp://alpha.gnu.org/gnu/grub/grub-${PV}.tar.gz; \
#            file://no-reorder-functions.patch \
#            file://autohell.patch "

#SRC_URI[md5sum] = "cd3f3eb54446be6003156158d51f4884"
#SRC_URI[sha256sum] = "4e1d15d12dbd3e9208111d6b806ad5a9857ca8850c47877d36575b904559260b"

inherit autotools

python __anonymous () {
    import re
    host = bb.data.getVar('HOST_SYS', d, 1)
    if not re.match('i.86.*-linux', host):
        raise bb.parse.SkipPackage("incompatible with host %s" % host)
}

do_install_append_vmware() {
	mkdir -p ${D}/boot/
	ln -sf ../usr/lib/grub/{$TARGET_ARCH}{$TARGET_VENDOR}/ ${D}/boot/grub
}

FILES_${PN}-doc = "${datadir}"
FILES_${PN} = "/boot /usr"

#
# debian-squeeze
#

inherit debian-squeeze-misc

DEBIAN_SQUEEZE_MISC_NAME = "grub-ubuntu"
DEBIAN_SQUEEZE_MISC_GIT = "${DEBIAN_SQUEEZE_GIT_BOOTLOADER}"
DEBIAN_SQUEEZE_MISC_COMMIT = "0.97-29ubuntu66"

PR = "r0"

# DEPENDENCY NOTE
# grub-legacy package depends on the following 'core' commands at least.
#   grep, cmd, findfs, mktemp
# But there is no dependency definition now because busybox or coreutils
# often provides them. Only in some rare cases (e.g. with minimal busybox), it doesn't.

# autohell.patch: solve the following error on configure
#   .../depend2.am: am__fastdepCCAS does not appear in AM_CONDITIONAL
#   .../depend2.am:   The usual way to define `am__fastdepCCAS' is to add `AM_PROG_AS'
#   .../depend2.am:   to `configure.ac' and run `aclocal' and `autoconf' again.
# Others are patches for embedded Debian system. See comments for details.
SRC_URI = " \
file://autohell.patch \
file://title.patch \
file://fix-update-grub-header.patch \
file://remove-quiet-support.patch \
file://remove-ucf.patch \
file://replace-tempfile-by-mktemp.patch \
file://replace-blkid.patch \
file://verbose.patch \
file://replace-grub-probe.patch \
file://disable-alternative.patch \
"

# same as deiban/rules
CFLAGS += "-Os -fno-strict-aliasing -fno-stack-protector -fno-reorder-functions"

do_patch_prepend() {
	bb.build.exec_func('patch_srcpkg', d)
}

patch_srcpkg() {
	# exclude unneeded patches
	sed -i "/grub-install_storeversion.diff/d" debian/patches/00list
	sed -i "/quiet.diff/d" debian/patches/00list

	# dpatch fails, so apply patches in the same way as debian/rulse
	for i in `cat debian/patches/00list | grep -v ^#`; do
		echo "--- $i ---"
		patch -p1 < debian/patches/$i
	done
}

# install additional scripts (see debian/grub.install)
do_install_append() {
	install -m 0755 ${S}/debian/update-grub ${D}${sbindir}
	install -m 0755 ${S}/debian/grub-floppy ${D}${sbindir}
	install -m 0755 ${S}/debian/grub-reboot ${D}${sbindir}
	install -m 0755 ${S}/util/mkbimage      ${D}${bindir}
}

PACKAGES = "${PN}-dbg ${PN}-doc ${PN}"
