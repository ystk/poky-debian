#
# net-snmp_5.4.2.1.bb
#
# imported from OpenEmbedded
# commit: 86ff745847ebfcfb77730851b72a7d576422c660
#

require net-snmp.inc
#PR = "${INC_PR}.2"

#SRC_URI = "${SOURCEFORGE_MIRROR}/net-snmp/net-snmp-${PV}.tar.gz \
#        file://configure-tail.patch \
#        file://CVE-2008-6123.patch \
#        file://init \
#        file://snmpd.conf \
#        file://snmptrapd.conf"

EXTRA_OECONF += "--disable-embedded-perl --with-perl-modules=no"
EXTRA_OEMAKE = "INSTALL_PREFIX=${D}"

do_configure_prepend() {
        gnu-configize -f
        # We better change sources and re-autoconf here, but
        # required autoconf is too new for us.
        sed -e '/echo.*\".*\\\\.*\"/s/echo/echo -e/g' \
            -e 's/tail -1/tail -n 1/g'                \
            -i configure

        #The tarball for v5.4.2.1 is missing config.sub
        cp ${STAGING_DATADIR_NATIVE}/gnu-config/config.sub ${S}
}

PARALLEL_MAKE = ""
CCACHE = ""

#SRC_URI[md5sum] = "984932520143f0c8bf7b7ce1fc9e1da1"
#SRC_URI[sha256sum] = "11a8baf167f7bfff60d2590e050991400a3a082923dbcdbf85e0e0ce46eb247c"

#
# debian-squeeze
#

inherit debian-squeeze

LIC_FILES_CHKSUM = "file://COPYING;md5=dbef43af08cf7609b24a14367a6a445a"

PR = "r0"

SRC_URI = " \
file://configure-tail.patch \
file://init \
file://snmpd.conf \
file://snmptrapd.conf \
"
do_install_append() {
	cd ${S}/snmplib && oe_runmake installucdlibs
}
