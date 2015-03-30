DESCRIPTION = "The ATK accessibility toolkit"
SECTION = "libs"
LICENSE = "LGPL"
LIC_FILES_CHKSUM = "file://COPYING;md5=3bf50002aefd002f49e7bb854063f7e7"


#
# debian-squeeze
#
inherit debian-squeeze
inherit autotools
DEPENDS += ""
do_patch_srcpkg(){
	:
}
