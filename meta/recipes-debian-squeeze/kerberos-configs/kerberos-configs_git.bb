DESCRIPTION = "Configuration files for Kerberos Version 5"
SECTION = "net"
LICENSE = "GPL-2"
LIC_FILES_CHKSUM = "file://README;md5=e9bca44aab7f198a27f812e17ef017fa"

inherit debian-squeeze

do_configure() {
	:
}

do_compile() {
	:
}

do_install() {
	mkdir -p ${D}${datadir}/kerberos-configs
	install -m 644 ${S}/krb5.conf.template ${D}${datadir}/kerberos-configs/krb5.conf.template
}

FILES_${PN} = "${datadir}/*"

