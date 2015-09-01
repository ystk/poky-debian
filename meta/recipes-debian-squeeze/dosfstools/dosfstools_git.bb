LICENSE = "GPLv3+"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"

PR = "r0"

inherit debian-squeeze

do_install() {
	install -d -m 0755 ${D}${base_sbindir}
	install -m 0755 dosfsck dosfslabel mkdosfs ${D}${base_sbindir}

	ln -sf dosfsck ${D}${base_sbindir}/fsck.msdos
	ln -sf dosfsck ${D}${base_sbindir}/fsck.vfat
	ln -sf mkdosfs ${D}${base_sbindir}/mkfs.msdos
	ln -sf mkdosfs ${D}${base_sbindir}/mkfs.vfat
}
