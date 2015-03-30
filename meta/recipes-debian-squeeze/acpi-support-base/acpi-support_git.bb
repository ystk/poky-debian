#
# debian-squeeze
#
DESCRIPTION = "scripts for handling many ACPI events"
SECTION = "admin"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"
PR = "r0"

inherit autotools
inherit debian-squeeze
INSDIR = ${D}/usr/share/acpi-support
do_install() {
	mkdir -p ${INSDIR}
	mkdir -p $D/usr/bin
	cp ${S}/lib/device-funcs ${INSDIR}/
	cp ${S}/lib/policy-funcs ${INSDIR}/
	cp ${S}/lib/power-funcs ${INSDIR}/
	cp ${S}/lib/screenblank ${INSDIR}/
	cp ${S}/lib/state-funcs ${INSDIR}/
	cp ${S}/key-constants ${INSDIR}/
	cp ${S}/acpi_fakekey ${D}/usr/bin/
}
