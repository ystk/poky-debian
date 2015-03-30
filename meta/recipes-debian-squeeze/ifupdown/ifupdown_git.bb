#
# ifupdown_0.6.10.bb
#
# imported from OpenEmbedded
# commit: 86ff745847ebfcfb77730851b72a7d576422c660
#

SRC_URI = "${DEBIAN_MIRROR}/main/i/ifupdown/ifupdown_${PV}.tar.gz;name=ifupdown \
          # file://busybox.patch \
          # file://zeroconf.patch \
           file://init "

EXTRA_OEMAKE = ""

#inherit update-rc.d

do_compile () {
	chmod a+rx *.pl *.sh
	oe_runmake 'CC=${CC}' "CFLAGS=${CFLAGS} -Wall -W -D'IFUPDOWN_VERSION=\"${PV}\"'"
}

#do_install () {
#	install -d ${D}${sysconfdir}/init.d \
#		   ${D}${sysconfdir}/network/run/ \
#		   ${D}${mandir}/man8 \
#		   ${D}${mandir}/man5 \
#		   ${D}${base_sbindir}
#	install -m 0755 ifup ${D}${base_sbindir}/
#	ln ${D}${base_sbindir}/ifup ${D}${base_sbindir}/ifdown
#	install -m 0755 ${WORKDIR}/init ${D}${sysconfdir}/init.d/ifup
#	install -m 0644 ifup.8 ${D}${mandir}/man8
#	install -m 0644 interfaces.5 ${D}${mandir}/man5
#	cd ${D}${mandir}/man8 && ln -s ifup.8 ifdown.8
#	mv ${D}${base_sbindir}/ifup ${D}${base_sbindir}/ifup.${PN}
#	mv ${D}${base_sbindir}/ifdown ${D}${base_sbindir}/ifdown.${PN}
#}

pkg_postinst_${PN} () {
	update-alternatives --install ${base_sbindir}/ifup ifup ifup.${PN} 100
	update-alternatives --install ${base_sbindir}/ifdown ifdown ifdown.${PN} 100
}

pkg_prerm_${PN} () {
	update-alternatives --remove ifup ifup.${PN}
	update-alternatives --remove ifdown ifdown.${PN}
}

#INITSCRIPT_NAME = "ifup"
#INITSCRIPT_PARAMS = "start 39 S . stop 39 0 6 1 ."

#SRC_URI[ifupdown.md5sum] = "70db0d8caf06a17d65b612fa8919732b"
#SRC_URI[ifupdown.sha256sum] = "d4ada5d3f1b1f7c93cb608bfb505605b4765e0d4791ce6a1c435fe924dd67101"

#
# debian-squeeze
#

SECTION = "admin"
DECRIPTION = "high level tools to configure network interfaces"
LICENSE = "GPL"
LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"
DEPENDS += "zlib net-tools"
RDEPENDS_${PN} += "debianutils"

inherit debian-squeeze

EXTRA_OEMAKE = "BASEDIR=${D}"

# 1. remove init script
# 2. replace ${sysconfdir}/network/run by symlink to ${localstatedir}/run
do_install () {
	install -d ${D}${sysconfdir}/network \
		   ${D}${localstatedir}/run \
		   ${D}${mandir}/man8 \
		   ${D}${mandir}/man5 \
		   ${D}${base_sbindir}
	install -m 0755 ifup ${D}${base_sbindir}/
	ln ${D}${base_sbindir}/ifup ${D}${base_sbindir}/ifdown
	install -m 0644 ifup.8 ${D}${mandir}/man8
	install -m 0644 interfaces.5 ${D}${mandir}/man5
	cd ${D}${mandir}/man8 && ln -s ifup.8 ifdown.8
	mv ${D}${base_sbindir}/ifup ${D}${base_sbindir}/ifup.${PN}
	mv ${D}${base_sbindir}/ifdown ${D}${base_sbindir}/ifdown.${PN}

	# ${localstatedir}/run is used by networking init script
	# ${sysconfdir}/network/run is used by ifupdown
	ln -s ${localstatedir}/run ${D}${sysconfdir}/network/run
}
