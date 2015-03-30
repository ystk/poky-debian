LICENSE = "PD"
LIC_FILES_CHKSUM = "file://debian/copyright;md5=08a41be50aba055e1d9ccf320758fd63"

inherit debian-squeeze
inherit allarch

PR = "r0"

do_compile() {
	:
}

do_install() {
	install -d ${D}${datadir}/fonts/${PN}
	for bdf in ucs.bdf unifont.bdf; do
		install -m 0644 ${S}/src/$bdf ${D}${datadir}/fonts/${PN}
	done
}

PACKAGES = "${PN}"
FILES_${PN} = "${datadir}/fonts/${PN}"
