#
# grub_1.99.bb
#

SUMMARY = "GRUB2 is the next-generation GRand Unified Bootloader"

DESCRIPTION = "GRUB2 is the next generaion of a GPLed bootloader \
intended to unify bootloading across x86 operating systems. In \
addition to loading the Linux kernel, it implements the Multiboot \
standard, which allows for flexible loading of multiple boot images."

HOMEPAGE = "http://www.gnu.org/software/grub/"
SECTION = "bootloaders"
PRIORITY = "optional"

LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"

RDEPENDS_${PN} = "diffutils freetype"
#PR = "r1"

#SRC_URI = "ftp://ftp.gnu.org/gnu/grub/grub-${PV}.tar.gz \
#          file://grub-install.in.patch;apply=yes \
#          file://40_custom"

inherit autotools
inherit gettext

EXTRA_OECONF = "--with-platform=pc --disable-grub-mkfont --program-prefix="""

do_configure() {
    oe_runconf
}

python __anonymous () {
    import re
    host = bb.data.getVar('HOST_SYS', d, 1)
    if not re.match('x86.64.*-linux', host) and not re.match('i.86.*-linux', host):
        raise bb.parse.SkipPackage("incompatible with host %s" % host)
}

#do_install_append () {
#    install -m 0755 ${WORKDIR}/40_custom ${D}${sysconfdir}/grub.d/40_custom
#}

#FILES_${PN}-doc = "${datadir}"
#FILES_${PN} = "/usr /etc"

#
# debian-squeeze
#

inherit debian-squeeze
DEBIAN_SQUEEZE_SRCPKG_NAME = "grub2"

# enable_zfs.patch:
#   debian/patches/enable_zfs.patch has a bug, override it
# remove-font-source-in-configure.patch:
#   By default, configure.ac add some target to FONT_SOURCE
#   according to some font data under /usr. If FONT_SOURCE
#   is set, some targets which depends on disabled mkfont
#   are built by Makefile.
# grub-1.99-fpmath-sse-387-fix.patch:
#   fix warnings (treated as errors) occurs only when
#   -mfpmath=sse is given
# gentrigtables-compile-fix.patch:
#   Backported from meta-intel
#   $(CC) used to build gentrigtables that is run on
#   host environment must be replaced by $(BUILD_CC)
# fix-cc-for-build-helpers.patch
#   same as gentrigtables-compile-fix.patch
SRC_URI = " \
file://enable_zfs.patch;apply=no \
file://remove-font-source-in-configure.patch \
file://grub-1.99-fpmath-sse-387-fix.patch \
file://gentrigtables-compile-fix.patch \
file://fix-cc-for-build-helpers.patch \
"

# enabled sources under debian/grub-extras require GRUB_CONTRIB
EXTRA_OEMAKE += "GRUB_CONTRIB=${S}/debian/grub-extras"

do_unpack_append() {
	bb.build.exec_func("override_patch", d)
}

override_patch() {
	cp ${WORKDIR}/enable_zfs.patch ${S}/debian/patches
}

do_configure_prepend() {
	cd ${S}
	./autogen.sh
}

GRUB_DEFAULT ?= ""
SRC_URI += "${@base_conditional('GRUB_DEFAULT', '', '', 'file://${GRUB_DEFAULT}', d)}"
do_install_append () {
	# install ${sysconfdir}/default/grub from debian source directory
	# or special local file
	install -d ${D}${sysconfdir}/default
	if [ -n "${GRUB_DEFAULT}" ]; then
		install -m 0644 ${WORKDIR}/${GRUB_DEFAULT} ${D}${sysconfdir}/default/grub
	else
		install -m 0644 ${S}/debian/default/grub ${D}${sysconfdir}/default/grub
	fi

	# install update-grub from debian source directory
	install -d ${D}${sbindir}
	install -m 0755 ${S}/debian/update-grub ${D}${sbindir}
}

# grep of busybox is not enough to run grub-install
RDEPENDS_${PN} += "grep"
