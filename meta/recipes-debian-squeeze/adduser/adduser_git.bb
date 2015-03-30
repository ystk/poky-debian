#
# debian-squeeze 
#
DESCRIPTION = "add and remove users and groups"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://deluser;beginline=2;endline=25;md5=066433fd265e272e32f22979268e373e"
SECTION = "admin"
PR = "r0"

inherit debian-squeeze perl-version

RDEPENDS += "shadow"

do_install() {
	install -d ${D}${sbindir} 
	install -d ${D}${sysconfdir}
	install -m 755 ${S}/adduser ${D}${sbindir}
	install -m 644 ${S}/adduser.conf ${D}${sysconfdir}
	install -d ${D}${datadir}/adduser
	install -m 644 ${S}/adduser.conf ${D}${datadir}/adduser
	install -d ${D}${libdir}/perl/${PERL_PV}/Debian
	install -m 644 ${S}/AdduserCommon.pm ${D}${libdir}/perl/${PERL_PV}/Debian
}

FILES_${PN} += "${libdir}/*"
