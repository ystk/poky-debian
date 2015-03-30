#
# debian-squeeze 
#
DESCRIPTION = "fast compression program" 
SECTION = "utils"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=dfeaf3dc4beef4f5a7bdbc35b197f39e"
PR = "r0"
DEPENDS = "lzo"

inherit autotools 
inherit debian-squeeze

do_configure() {
	aclocal
	oe_runconf
}
