DESCRIPTION = "authenticator for mod_authnz_external and the Apache HTTP Daemon"
SECTION = "utils"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://README;md5=7cfca744d6c07156559fedaa734e562a"

inherit autotools debian-squeeze

DEPENDS = "libpam"

do_patch_srcpkg_prepend() {
	rm -rf ${S}/debian/patches/series
	for f in ${S}/debian/patches/*.patch
	do
		echo `basename $f` "-p0"  >> ${S}/debian/patches/series
	done
}

do_compile_prepend() {
	sed -i -e "s:^CC.*:CC=${CC}:" ${S}/Makefile
	sed -i -e "s:^CFLAGS.*:CFLAGS=${CFLAGS}:" ${S}/Makefile
}

do_install() {
	mkdir -p ${D}${sbindir}
	install -m 755 ${S}/pwauth ${D}${sbindir}/pwauth
	mkdir -p ${D}${sysconfdir}/pam.d
	install -m 644 ${S}/debian/pam ${D}${sysconfdir}/pam.d/pwauth
}

FILES_${PN} = "${sbindir}/* ${sysconfdir}/*"
