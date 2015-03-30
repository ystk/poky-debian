#
# debian-squeeze
#
DESCRIPTION = "maintain multipath block device access"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=7be2873b6270e45abacc503abbe2aa3d"
SECTION = "admin"
PR = "r0"

DEPENDS += "lvm2 libaio readline"

inherit autotools
inherit debian-squeeze

do_compile_prepend() {
	export LDFLAGS=${LDFLAGS//-Wl,--as-needed}
}

FILES_${PN} += "${base_libdir}/multipath/* ${base_libdir}/udev"
FILES_${PN}-dbg =+ "${base_libdir}/multipath/.debug"
RDEPENDS_${PN} += "libaio"
