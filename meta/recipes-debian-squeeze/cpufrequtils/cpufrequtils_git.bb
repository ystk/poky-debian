#
# trace-cmd_git.bb
#

DESCRIPTION = "utilities to deal with the cpufreq Linux kernel feature"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"
SECTION = "admin"

inherit autotools
inherit debian-squeeze

DEPENDS += "libtool-cross"

do_configure_prepend() {
	sed -i -e "s#^CROSS = .*#CROSS = ${HOST_PREFIX}#" ${S}/Makefile
	sed -i -e "s#^LIBTOOL = .*#LIBTOOL = ${STAGING_BINDIR}/crossscripts/${HOST_PREFIX}libtool --tag=CC --mode=compile#" ${S}/Makefile
}
