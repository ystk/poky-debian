#
# debian-squeeze
#
DESCRIPTION = "authenticate Apache against external authentication services"
SECTION = "web"
HOMEPAGE = "http://code.google.com/p/mod-auth-external"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://CHANGES;md5=9d757fe70910ccc504b2070c7a10b36e"
PR = "r0"

inherit debian-squeeze autotools

DEPENDS += "apache2"

do_compile() {
	${CC} -fPIC \
		-O2 -pipe  -DLINUX=2 -D_REENTRANT -D_GNU_SOURCE \
		-pthread -shared -I${STAGING_INCDIR}/apache2 \
		-o mod_authnz_external.so mod_authnz_external.c
}

do_install() {
	install -d ${D}${libdir}/apache2/modules
	install -m 0755 mod_authnz_external.so ${D}${libdir}/apache2/modules
	install -d ${D}${sysconfdir}/apache2/mods-available
	install -m 0644 ${S}/debian/authnz_external.load \
			${D}${sysconfdir}/apache2/mods-available
	install -d ${D}${sysconfdir}/apache2/mods-enabled
	pushd ${D}${sysconfdir}/apache2/mods-enabled
	ln -s ../mods-available/authnz_external.load .
	popd
}

FILES_${PN} = "${libdir} ${sysconfdir}"
FILES_${PN}-dbg = "${libdir}/apache2/modules/.debug"
