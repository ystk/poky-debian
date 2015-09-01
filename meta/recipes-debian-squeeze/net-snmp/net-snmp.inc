#
# net-snmp.inc
#
# imported from OpenEmbedded
# commit: 86ff745847ebfcfb77730851b72a7d576422c660
#

DESCRIPTION = "Various tools relating to the Simple Network Management Protocol"
HOMEPAGE = "http://www.net-snmp.org/"
LICENSE = "BSD"
DEPENDS = "openssl"
#RDEPENDS_${PN}-server += "net-snmp-mibs"
#RDEPENDS_${PN}-client += "net-snmp-mibs"
#RDEPENDS_${PN}-dev = "net-snmp-client (= ${EXTENDPV}) net-snmp-server (= ${EXTENDPV})"
#RRECOMMENDS_${PN}-dbg = "net-snmp-client (= ${EXTENDPV}) net-snmp-server (= ${EXTENDPV})"
#INC_PR = "r0"

inherit autotools update-rc.d siteinfo

TARGET_CC_ARCH += "${LDFLAGS}"

EXTRA_OECONF = "--enable-shared --disable-manuals --with-defaults \
${@base_conditional('SITEINFO_ENDIANNESS', 'le', '--with-endianness=little', '--with-endianness=big', d)}"

do_install_append() {
        install -d ${D}${sysconfdir}/snmp
        install -d ${D}${sysconfdir}/init.d
        install -m 755 ${WORKDIR}/init ${D}${sysconfdir}/init.d/snmpd
        install -m 644 ${WORKDIR}/snmpd.conf ${D}${sysconfdir}/snmp/
        install -m 644 ${WORKDIR}/snmptrapd.conf ${D}${sysconfdir}/snmp/

	# drop net-snmp-config, which provides build information
	rm -f ${D}${bindir}/net-snmp-config
}

#PACKAGES = "net-snmp-dbg net-snmp-doc net-snmp-dev net-snmp-static net-snmp-libs \
#            net-snmp-mibs net-snmp-server net-snmp-client"

#FILES_${PN}-static = "${libdir}/*.a"
#FILES_${PN}-libs = "${libdir}/*"
#FILES_${PN}-mibs = "${datadir}/snmp/mibs"
#FILES_${PN}-server = "${sbindir}/* ${sysconfdir}"
#FILES_${PN}-client = "${bindir}/* ${datadir}/snmp/"
#FILES_${PN}-dbg += "${libdir}/.debug/ ${sbindir}/.debug/ ${bindir}/.debug/"
#FILES_${PN}-dev += ${bindir}/net-snmp-config ${bindir}/mib2c ${bindir}/mib2c-update"

CONFFILES_${PN}-server = "${sysconfdir}/snmp/snmpd.conf \
                          ${sysconfdir}/snmp/snmptrapd.conf"

INITSCRIPT_PACKAGES = "${PN}-server"
INITSCRIPT_NAME_${PN}-server = "snmpd"
INITSCRIPT_PARAMS_${PN}-server = "defaults"

LEAD_SONAME = "libnetsnmp.so"

pkg_postinst_net-snmp-server() {
if test "x$D" != "x"; then
	D="-r $D"
else
	D="-s"
fi
update-rc.d $D snmpd defaults
}

pkg_postrm_net-snmp-server() {
if test "x$D" != "x"; then
	D="-r $D"
else
	/etc/init.d/snmpd stop
fi
update-rc.d $D snmpd remove
}

#
# debian-squeeze
#

EXTRA_OECONF += " \
--with-persistent-directory=/var/lib/snmp \
--with-mibdirs=\$HOME/.snmp/mibs:${datadir}/mibs/site:${datadir}/snmp/mibs:${datadir}/mibs/iana:${datadir}/mibs/ietf:${datadir}/mibs/netsnmp \
"

# override package contents based on Debian packaging
PACKAGES = " \
${PN}-dbg \
${PN}-doc \
${PN}-dev \
${PN}-staticdev \
${PN}-libs \
${PN}-mibs \
${PN}-server \
${PN}-client \
"

FILES_${PN}-dbg += " \
${libdir}/.debug/ \
${sbindir}/.debug/ \
${bindir}/.debug/ \
"
FILES_${PN}-dev += " \
${bindir}/mib2c \
${bindir}/mib2c-update \
${sysconfdir}/snmp/mib2c*.conf \
"
FILES_${PN}-staticdev = "${libdir}/*.a"
FILES_${PN}-libs = "${libdir}/*"
FILES_${PN}-mibs = " \
${datadir}/mibs/netsnmp/* \
${datadir}/snmp/mib2c-data/* \
"
FILES_${PN}-server = " \
${sbindir}/* \
${sysconfdir} \
${datadir}/snmp/snmpconf-data/snmpd-data/* \
${datadir}/snmp/snmpconf-data/snmptrapd-data/* \
${datadir}/snmp/snmp_perl_trapd.pl \
"
FILES_${PN}-client = " \
${bindir}/* \
${datadir}/snmp/snmpconf-data/snmp-data/* \
"

RDEPENDS_${PN}-dev = "${PN}-libs"
RDEPENDS_${PN}-libs += "${PN}-mibs"
RDEPENDS_${PN}-server += "${PN}-libs"
RDEPENDS_${PN}-client += "${PN}-libs"

# Debian install path
do_install_append() {
	mv ${D}${datadir}/snmp/mib2c*.conf ${D}${sysconfdir}/snmp
}
