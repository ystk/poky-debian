DESCRIPTION = "swig"
LICENSE = "GPLv3+"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e0eaeeef7b2662c0c2d0d3c0b2509f75"
PR = "r0"

#inherit autotools update-alternatives gettext

#
# debian-squeeze
#

inherit autotools native
inherit debian-squeeze

do_configure() {
	oe_runconf
}
