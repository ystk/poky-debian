#
# debian-squeeze 
#
DESCRIPTION = "The Linux Logical Volume Manager" 
LICENSE = "GPLv2 & LGPLv2.1"
LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"
PR = "r0"
DEPENDS = "libselinux readline"

inherit autotools 
inherit debian-squeeze

do_install_append() {
	cd ${D}/${libdir}
	ln -sf libdevmapper.so.1.02 libdevmapper.so
}
