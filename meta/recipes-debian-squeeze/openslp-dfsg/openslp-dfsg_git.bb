#
# debian-squeeze
#

DESCRIPTION = "OpenSLP Server (slpd)"
SECTION = "net"
HOMEPAGE = "http://www.openslp.org/"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=69974e24a9fae771ced9240f7eb0770f"

DEPENDS = ""

inherit autotools
inherit debian-squeeze

SRC_URI += "file://01_have_net_if_arp.patch "

do_patch_srcpkg() {
	:
}
