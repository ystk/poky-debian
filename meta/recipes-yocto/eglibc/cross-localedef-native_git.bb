#
# cross-localedef-native_2.12.bb
#

DESCRIPTION = "Cross locale generation tool for eglibc"
HOMEPAGE = "http://www.eglibc.org/home"
SECTION = "libs"
LICENSE = "LGPL"

#LIC_DIR = "${WORKDIR}/${EGLIBC_BRANCH}/libc"
#LIC_FILES_CHKSUM = "file://${LIC_DIR}/LICENSES;md5=07a394b26e0902b9ffdec03765209770 \
#      file://${LIC_DIR}/COPYING;md5=393a5ca445f6965873eca0259a17f833 \
#      file://${LIC_DIR}/posix/rxspencer/COPYRIGHT;md5=dc5485bb394a13b2332ec1c785f5d83a \
#      file://${LIC_DIR}/COPYING.LIB;md5=bbb461211a33b134d42ed5ee802b37ff "


inherit native
inherit autotools

#PR = "r1"
#SRCREV="11982"
#EGLIBC_BRANCH="eglibc-2_12"
#SRC_URI = "svn://www.eglibc.org/svn/branches/;module=${EGLIBC_BRANCH};proto=http "
#S = "${WORKDIR}/${EGLIBC_BRANCH}/localedef"

#do_unpack_append() {
#	bb.build.exec_func('do_move_ports', d)
#}

#do_move_ports() {
#        if test -d ${WORKDIR}/${EGLIBC_BRANCH}/ports ; then
#	    rm -rf ${WORKDIR}/libc/ports
#	    mv ${WORKDIR}/${EGLIBC_BRANCH}/ports ${WORKDIR}/libc/
#	fi
#}

#EXTRA_OECONF = "--with-glibc=${WORKDIR}/${EGLIBC_BRANCH}/libc"

do_configure () {
	./configure ${EXTRA_OECONF}
}


do_install() {
	install -d ${D}${bindir} 
	install -m 0755 ${S}/localedef ${D}${bindir}/cross-localedef
}

#
# debian-squeeze
#

# Main source code is "localedef" which is included in upstream eglibc source.
# But debian source package doesn't include it, so we need the both "localedef"
# and "eglibc" source package here.

inherit debian-squeeze
DEBIAN_SQUEEZE_SRCPKG = "eglibc_git"
EGLIBC_DIR = "${WORKDIR}/eglibc-git"

inherit debian-squeeze-yocto
DEBIAN_SQUEEZE_MISC_NAME = "eglibc"
DEBIAN_SQUEEZE_UNPACKDIR = "${WORKDIR}/${DEBIAN_SQUEEZE_MISC_NAME}-yocto"

PR = "r0"
S = "${DEBIAN_SQUEEZE_UNPACKDIR}/localedef"

LIC_FILES_CHKSUM = " \
file://${EGLIBC_DIR}/LICENSES;md5=98a1128c4b58120182cbea3b1752d8b9 \
file://${EGLIBC_DIR}/COPYING;md5=393a5ca445f6965873eca0259a17f833 \
file://${EGLIBC_DIR}/posix/rxspencer/COPYRIGHT;md5=dc5485bb394a13b2332ec1c785f5d83a \
file://${EGLIBC_DIR}/COPYING.LIB;md5=bbb461211a33b134d42ed5ee802b37ff \
"

EXTRA_OECONF = "--with-glibc=${EGLIBC_DIR}"

SRC_URI = ""

PKG_SRC_VERSION = "2.11.3"
