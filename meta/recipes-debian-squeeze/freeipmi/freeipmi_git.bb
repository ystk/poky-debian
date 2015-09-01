DESCRIPTION = "GNU implementation of the IPMI protocol"
HOMEPAGE = "http://www.gnu.org/software/freeipmi/"
SECTION = "admin"
LICENSE = "GPL-2"
LIC_FILES_CHKSUM = "file://COPYING;md5=59530bdf33659b29e73d4adb9f9f6552"
PR = "r0"

inherit debian-squeeze autotools

DEPENDS += "libgcrypt11"
RDEPENDS += "update-rc.d readline libgcrypt11"

SRC_URI += " \
file://ipmidetectd.conf \
file://freeipmi-bmc-watchdog.init \
file://freeipmi-ipmidetectd.init \
"

do_configure_prepend() {
	sed -i -e "/\/dev\/urandom/ d" configure.ac
	sed -i -e "/\/dev\/random/ d" configure.ac
	./autogen.sh
}

# according to bmc-watchdog manpage
BMC_WATCHDOG_STARTUP = " -d -u 4 -p 0 -a 1 -i 900 "

do_install_append() {
	install -m 0644 ${WORKDIR}/ipmidetectd.conf ${D}${sysconfdir}
	install -m 0755 ${WORKDIR}/freeipmi-bmc-watchdog.init \
			${D}${sysconfdir}/init.d/freeipmi-bmc-watchdog
	
	install -m 0755 ${WORKDIR}/freeipmi-ipmidetectd.init \
			${D}${sysconfdir}/init.d/freeipmi-ipmidetectd
	install -d ${D}${sysconfdir}/default
#	echo "RUN=yes" > ${D}${sysconfdir}/default/bmc-watchdog
#	echo 'OPTIONS="${BMC_WATCHDOG_STARTUP}"' >> ${D}${sysconfdir}/default/bmc-watchdog
	install -m 0644 ${S}/bmc-watchdog/freeipmi-bmc-watchdog.sysconfig ${D}${sysconfdir}/default/bmc-watchdog
	echo "RUN=yes" > ${D}${sysconfdir}/default/ipmidetectd
	sed -i -e "s|condrestart|reload > /dev/null|" -e "/}/i \ \ copytruncate" ${D}${sysconfdir}/logrotate.d/freeipmi-bmc-watchdog
}

pkg_postinst_${PN} () {
        if [ "x$D" != "x" ]; then
		OPT="-r $D"
		update-rc.d $OPT freeipmi-ipmidetectd defaults 21
                update-rc.d $OPT freeipmi-bmc-watchdog defaults
        fi
}
