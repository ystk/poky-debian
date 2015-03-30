#
# debian-squeeze
#
DESCRIPTION = "Base utilities for network administration (ifconfig, etc)."
HOMEPAGE = "http://www.gnu.org/software/inetutils/"
SECTION = "net"
PR = "r0"

LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=adefda309052235aa5d1e99ce7557010"

inherit autotools
inherit debian-squeeze

# NOTE: ifconfig conflicts with /sbin/ifconfig in net-tools
# We rename it according to Debian inetutils-tools package installation
do_install_append() {
	mv ${D}/${bindir}/ifconfig ${D}/${bindir}/inetutils-ifconfig
}
