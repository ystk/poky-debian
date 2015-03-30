#
# debian-squeeze
#

DESCRIPTION = "X11 DRI extension wire protocol"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=4641deddaa80fe7ca88e944e1fd94a94"
SECTION = "x11"
PR = "r0"

inherit autotools
inherit debian-squeeze

do_configure() {
	oe_runconf --disable-dri
}
