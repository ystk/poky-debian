#
# recipes-connectivity/wpa-supplicant/wpa-supplicant-0.7.inc
#

DESCRIPTION = "A Client for Wi-Fi Protected Access (WPA)."
HOMEPAGE = "http://hostap.epitest.fi/wpa_supplicant/"
BUGTRACKER = "http://hostap.epitest.fi/bugz/"
SECTION = "network"
LICENSE = "GPLv2 | BSD"
#LIC_FILES_CHKSUM = "file://../COPYING;md5=c54ce9345727175ff66d17b67ff51f58 \
#                    file://../README;md5=54cfc88015d3ce83f7156e63c6bb1738 \
#                    file://wpa_supplicant.c;beginline=1;endline=17;md5=acdc5a4b0d6345f21f136eace747260e"
DEPENDS = "gnutls dbus libnl"
RRECOMMENDS_${PN} = "wpa-supplicant-passphrase wpa-supplicant-cli"

#SRC_URI = "http://hostap.epitest.fi/releases/wpa_supplicant-${PV}.tar.gz \
#           file://defconfig-gnutls \
#           file://defaults-sane \
#           file://wpa-supplicant.sh \
#           file://wpa_supplicant.conf \
#           file://wpa_supplicant.conf-sane \
#           file://99_wpa_supplicant"

#S = "${WORKDIR}/wpa_supplicant-${PV}/wpa_supplicant"

PACKAGES_prepend = "wpa-supplicant-passphrase wpa-supplicant-cli "
FILES_wpa-supplicant-passphrase = "${sbindir}/wpa_passphrase"
FILES_wpa-supplicant-cli = "${sbindir}/wpa_cli"
FILES_${PN} += " ${datadir}/dbus-1/system-services/*

do_configure () {
	install -m 0755 ${WORKDIR}/defconfig-gnutls .config
}

export EXTRA_CFLAGS = "${CFLAGS}"
do_compile () {
	unset CFLAGS CPPFLAGS CXXFLAGS
	oe_runmake
}

#do_install () {
#	install -d ${D}${sbindir}
#	install -m 755 wpa_supplicant ${D}${sbindir}
#	install -m 755 wpa_passphrase ${D}${sbindir}
#	install -m 755 wpa_cli        ${D}${sbindir}
#
#	install -d ${D}${docdir}/wpa_supplicant
#	install -m 644 README ${WORKDIR}/wpa_supplicant.conf ${D}${docdir}/wpa_supplicant
#
#	install -d ${D}${sysconfdir}/default
#	install -m 600 ${WORKDIR}/defaults-sane ${D}${sysconfdir}/default/wpa
#	install -m 600 ${WORKDIR}/wpa_supplicant.conf-sane ${D}${sysconfdir}/wpa_supplicant.conf
#
#	install -d ${D}${sysconfdir}/network/if-pre-up.d/
#	install -d ${D}${sysconfdir}/network/if-post-down.d/
#	install -d ${D}${sysconfdir}/network/if-down.d/
#	install -m 644 ${WORKDIR}/wpa_supplicant.conf ${D}${sysconfdir}
#	install -m 755 ${WORKDIR}/wpa-supplicant.sh ${D}${sysconfdir}/network/if-pre-up.d/wpa-supplicant
#	cd ${D}${sysconfdir}/network/ && \
#	ln -sf ../if-pre-up.d/wpa-supplicant if-post-down.d/wpa-supplicant
#
#	install -d ${D}/${sysconfdir}/dbus-1/system.d
#	install -m 644 ${S}/dbus/dbus-wpa_supplicant.conf ${D}/${sysconfdir}/dbus-1/system.d
#	install -d ${D}/${datadir}/dbus-1/system-services
#	sed -i -e s:${base_sbindir}:${sbindir}:g ${S}/dbus/*.service
#	install -m 644 ${S}/dbus/*.service ${D}/${datadir}/dbus-1/system-services
#
#	install -d ${D}/etc/default/volatiles
#	install -m 0644 ${WORKDIR}/99_wpa_supplicant ${D}/etc/default/volatiles
#}

pkg_postinst_wpa-supplicant () {
	# can't do this offline
	if [ "x$D" != "x" ]; then
		exit 1
	fi

	DBUSPID=`pidof dbus-daemon`

	if [ "x$DBUSPID" != "x" ]; then
		/etc/init.d/dbus-1 reload
	fi
}

#
# debian-squeeze
#

inherit debian-squeeze
DEBIAN_SQUEEZE_SRCPKG_NAME = "wpasupplicant"

S = "${WORKDIR}/${DEBIAN_SQUEEZE_SRCPKG_NAME}-${PV}/wpa_supplicant"

LIC_FILES_CHKSUM = " \
file://${DEBIAN_SQUEEZE_UNPACKDIR}/COPYING;md5=c54ce9345727175ff66d17b67ff51f58 \
file://${DEBIAN_SQUEEZE_UNPACKDIR}/README;md5=54cfc88015d3ce83f7156e63c6bb1738 \
file://wpa_supplicant.c;beginline=1;endline=17;md5=1eb88410f8cc9b47f077c69b772ec0e2 \
"

PR = "r0"

SRC_URI = " \
file://defconfig-gnutls \
file://defaults-sane \
file://wpa-supplicant.sh \
file://wpa_supplicant.conf \
file://wpa_supplicant.conf-sane \
file://99_wpa_supplicant \
"

# fix paths of dbus related files ("${S}/dbus" => "${S}")
do_install () {
	install -d ${D}${sbindir}
	install -m 755 wpa_supplicant ${D}${sbindir}
	install -m 755 wpa_passphrase ${D}${sbindir}
	install -m 755 wpa_cli        ${D}${sbindir}

	install -d ${D}${docdir}/wpa_supplicant
	install -m 644 README ${WORKDIR}/wpa_supplicant.conf ${D}${docdir}/wpa_supplicant

	install -d ${D}${sysconfdir}/default
	install -m 600 ${WORKDIR}/defaults-sane ${D}${sysconfdir}/default/wpa
	install -m 600 ${WORKDIR}/wpa_supplicant.conf-sane ${D}${sysconfdir}/wpa_supplicant.conf

	install -d ${D}${sysconfdir}/network/if-pre-up.d/
	install -d ${D}${sysconfdir}/network/if-post-down.d/
	install -d ${D}${sysconfdir}/network/if-down.d/
	install -m 644 ${WORKDIR}/wpa_supplicant.conf ${D}${sysconfdir}
	install -m 755 ${WORKDIR}/wpa-supplicant.sh ${D}${sysconfdir}/network/if-pre-up.d/wpa-supplicant
	cd ${D}${sysconfdir}/network/ && \
	ln -sf ../if-pre-up.d/wpa-supplicant if-post-down.d/wpa-supplicant

	install -d ${D}/${sysconfdir}/dbus-1/system.d
	install -m 644 ${S}/dbus-wpa_supplicant.conf ${D}/${sysconfdir}/dbus-1/system.d
	install -d ${D}/${datadir}/dbus-1/system-services
	sed -i -e s:${base_sbindir}:${sbindir}:g ${S}/*.service
	install -m 644 ${S}/*.service ${D}/${datadir}/dbus-1/system-services

	install -d ${D}/etc/default/volatiles
	install -m 0644 ${WORKDIR}/99_wpa_supplicant ${D}/etc/default/volatiles
}
