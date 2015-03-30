#
# debian-squeeze
#
DESCRIPTION = "dancer's shell, or distributed shell"
SECTION = "net"
LICENSE = "GPL-2"
LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"

inherit debian-squeeze autotools gettext

DEPENDS += "libdshconfig"

EXTRA_OECONF += " \
--sysconfdir=/etc/dsh \
"

do_install_prepend() {
	sed -i -e "s@^MKINSTALLDIRS =.*@MKINSTALLDIRS = mkinstalldirs@" po/Makefile
}

do_install_append() {
	install -d ${D}${sysconfdir}/dsh/group
	install -m 0644 ${S}/debian/machines.list ${D}${sysconfdir}/dsh
	cd ${D}${sysconfdir}/dsh/group
	ln -s ../machines.list all
	install -d ${D}${libdir}/update-cluster
	install -m 0644 ${S}/debian/dsh.updatelist ${D}${libdir}/update-cluster

	sed -i 's: remoteshell =rsh: remoteshell =ssh:' ${D}${sysconfdir}/dsh/dsh.conf
}

FILES_${PN} += "${libdir}"
