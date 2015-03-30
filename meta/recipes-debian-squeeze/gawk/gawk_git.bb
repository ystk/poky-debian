#
# gawk_3.1.5.bb
#

SUMMARY = "The GNU awk text processing utility"
DESCRIPTION = "The gawk package contains the GNU version of awk, a text processing \
utility. Awk interprets a special-purpose programming language to do \
quick and easy text pattern matching and reformatting jobs."
HOMEPAGE = "www.gnu.org/software/gawk"
BUGTRACKER  = "bug-gawk@gnu.org"
SECTION = "console/utils"

# gawk <= 3.1.5: GPLv2
# gawk >= 3.1.6: GPLv3
#LICENSE = "GPLv2"
#LIC_FILES_CHKSUM = "file://COPYING;md5=59530bdf33659b29e73d4adb9f9f6552"

RDEPENDS_gawk += "gawk-common"
RDEPENDS_pgawk += "gawk-common"
PR = "r0"

#SRC_URI = "${GNU_MIRROR}/gawk/gawk-${PV}.tar.gz"

inherit autotools gettext update-alternatives

PACKAGES += "gawk-common pgawk"

FILES_${PN} = "${bindir}/gawk* ${bindir}/igawk"
FILES_gawk-common += "${datadir}/awk/* ${libexecdir}/awk/*"
FILES_pgawk = "${bindir}/pgawk*"
FILES_${PN}-dbg += "${libexecdir}/awk/.debug"

ALTERNATIVE_NAME = "awk"
ALTERNATIVE_PATH = "gawk"
ALTERNATIVE_LINK = "${bindir}/awk"
ALTERNATIVE_PRIORITY = "100"

#
# debian-squeeze
#

inherit debian-squeeze

LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"

# remove "doc" dir from build targets
# (see commends in the patch for details)
SRC_URI = "file://remove-doc.patch"

# Don't build 'local' libsigsegv because it causes "redefinition"
# compile error on x86_64. libsigsegv is no longer included in v3.1.8,
# but unfortunately it still be alive as invalid libraries in v3.1.7.
# We need to disable this although we lose some functions (see NEWS).
EXTRA_OECONF += "--disable-libsigsegv"

# libsigsegv/libtool is not available
LIBTOOL = "${HOST_SYS}-libtool"
EXTRA_OEMAKE = "'LIBTOOL=${LIBTOOL}'"

do_configure_prepend() {
	# solve libtool version mismatch
	find ${S} -name aclocal.m4 | xargs rm
	find ${S} -name ltversion.m4 | xargs rm

	# see debian/rules and comments in remove-doc.patch
	touch --date="Jan 01 2000" \
	doc/gawk.info doc/gawk.texi doc/gawkinet.info doc/gawkinet.texi
}

do_install_append() {
	# this symlink should be created by update-alternative in postinst
	rm -f ${D}${bindir}/awk
}
