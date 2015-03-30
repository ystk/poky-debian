DESCRIPTION = "PHP PEAR modules for creating an authentication system"
SECTION = "web"
LICENSE = "PHP-3.1"
LIC_FILES_CHKSUM = "file://debian/copyright;md5=17c22e8dfbdc7f4b82ac0ee25824aa14"

inherit debian-squeeze

PR = "r0"

do_install() {
	install -d ${D}${datadir}/php/Auth/Container
	install -d ${D}${datadir}/php/Auth/Frontend
	install -m 644 ${S}/Auth-1.6.2/Auth.php ${D}${datadir}/php/
	install -m 644 ${S}/Auth-1.6.2/Auth/* ${D}${datadir}/php/Auth
	install -m 644 ${S}/Auth-1.6.2/Container.php ${D}${datadir}/php/Auth
	install -m 644 ${S}/Auth-1.6.2/Container/* ${D}${datadir}/php/Auth/Container
	install -m 644 ${S}/Auth-1.6.2/Frontend/* ${D}${datadir}/php/Auth/Frontend
}

FILES_${PN} = "${datadir}/*"
